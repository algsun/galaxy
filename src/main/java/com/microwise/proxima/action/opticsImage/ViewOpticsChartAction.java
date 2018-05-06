package com.microwise.proxima.action.opticsImage;

import com.google.gson.Gson;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.DateTimeUtil;
import com.microwise.proxima.bean.DVPlaceBean;
import com.microwise.proxima.bean.MarkSegmentBean;
import com.microwise.proxima.bean.MarkSegmentPositionBean;
import com.microwise.proxima.bean.Zone;
import com.microwise.proxima.service.MarkSegmentPositionService;
import com.microwise.proxima.service.MarkSegmentService;
import com.microwise.proxima.service.OpticsDVPlaceService;
import com.microwise.proxima.sys.Proxima;
import com.microwise.proxima.sys.ProximaLoggerAction;
import com.microwise.proxima.util.PositionUtil;
import com.opensymphony.xwork2.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 处理图表数据
 *
 * @author li.jianfei
 * @date 2012-08-07
 * @check guo.tian li.jianfei 2012-09-18
 */
@Beans.Action
@Proxima
public class ViewOpticsChartAction extends ProximaLoggerAction {
    private static final Logger log = LoggerFactory.getLogger(ViewOpticsChartAction.class);

    /**
     * 标记段位置 service
     */
    @Autowired
    private MarkSegmentPositionService mspService;

    /**
     * 标记段 service
     */
    @Autowired
    private MarkSegmentService msService;

    @Autowired
    private OpticsDVPlaceService opticsDVPlaceService;

    // input

    /**
     * 区域id
     */
    private String zoneId = "";

    /**
     * 点位id
     */
    private String dvPlaceId = "";

    /**
     * 标记段id
     */
    private String markId = "";

    // output
    /**
     * 开始日期
     */
    private Date startDate;

    /**
     * 结束日期
     */
    private Date endDate;

    /**
     * 图表数据
     */
    private String chartData;

    /**
     * 标记段坐标集合
     */
    private List<MarkSegmentPositionBean> mspList;

    /**
     * 图表数据
     */
    private Map<String, Map<Date, String>> mapData;

    /**
     * 点位下的标记段
     */
    private Map<String, List<MarkSegmentBean>> mapSegments;

    /**
     * 监测点到摄像机点位的二级联动信息集合
     */
    private Map<Zone, Collection<DVPlaceBean>> informationmap;

    /**
     * 查询光学摄像机点位列表
     *
     * @return
     */
    public String view() {
        initDefaultDate();
        log("裂隙监测", "裂隙监测");
        queryData();

        return Action.SUCCESS;
    }

    /**
     * 查询并处理图表数据
     *
     * @return
     */
    public String execute() {

        try {
            if (dvPlaceId == null) { // 如果没有 dvPlaceId 参数，那么根据 markId 初始化
                DVPlaceBean dvPlace = msService.findDVPlaceByMarkSegmentId(markId);
                if (dvPlace != null) {
                    dvPlaceId = dvPlace.getId();
                }
            }

            // 如果时间为空, 则时间初始化为默认时间
            if (startDate == null || endDate == null) {
                initDefaultDate();
            }

            queryData();
            DVPlaceBean dvPlace = opticsDVPlaceService.findById(dvPlaceId);
            mspList = mspService.findAllMarkedSegementData(dvPlaceId, markId, startDate, endDate);

            // 根据查询到的数据组织图标数据串

            // 初始化保存 series
            Map<String, Object> mapDisplaceMentSerie = new HashMap<String, Object>();
            Map<String, Object> mapTrendSerie = new HashMap<String, Object>();
            List<List<Object>> listDisplaceMentData = new ArrayList<List<Object>>();
            List<List<Object>> listTrendData = new ArrayList<List<Object>>();

            List<Map<String, Object>> listSeries = new ArrayList<Map<String, Object>>();
            listSeries.add(mapDisplaceMentSerie);
            listSeries.add(mapTrendSerie);

            mapDisplaceMentSerie.put("yAxis", 0);
            mapDisplaceMentSerie.put("color", "#4572A7");
            mapDisplaceMentSerie.put("name", "长度");
            mapDisplaceMentSerie.put("type", "spline");
            mapDisplaceMentSerie.put("data", listDisplaceMentData);

            mapTrendSerie.put("yAxis", 1);
            mapTrendSerie.put("color", "#89A54E");
            mapTrendSerie.put("name", "变化幅度");
            mapTrendSerie.put("type", "line");
            mapTrendSerie.put("data", listTrendData);

            for (int i = 0; i < mspList.size(); i++) {
                List<Object> listDisplaceMentPoint = new ArrayList<Object>();
                List<Object> listTrendPoint = new ArrayList<Object>();

                MarkSegmentPositionBean msp = mspList.get(i);
                Date date = msp.getPicture().getSaveTime();
                listDisplaceMentPoint.add(date.getTime());
                // 将像素转化为毫米(mm)
                float realLength = PositionUtil.realLength(msp.getMarkLength(), dvPlace.getImageRealWidth(), dvPlace.getImageWidth());
                listDisplaceMentPoint.add(realLength);
                listDisplaceMentData.add(listDisplaceMentPoint);

                listTrendPoint.add(date.getTime());
                if (i == 0) {
                    listTrendPoint.add(0);
                } else {
                    float realLengthDelta = PositionUtil.realLength(msp.getLengthDelta(), dvPlace.getImageRealWidth(), dvPlace.getImageWidth());
                    listTrendPoint.add(realLengthDelta);
                }
                listTrendData.add(listTrendPoint);
            }

            Gson gson = new Gson();
            chartData = gson.toJson(listSeries);
            log("裂隙监测", "查询裂隙");
        } catch (Exception e) {
            log.error("", e);
        }
        return Action.SUCCESS;
    }

    /**
     * 设置默认开始日期和结束日期
     */
    private void initDefaultDate() {
        int defaultDays = Integer.parseInt(ConfigFactory
                .getInstance().getConfig(Constants.Config.CONFIG_PROPERTIES_URL)
                .get(Constants.Proxima.KEY_VIEW_IMAGE_QUERY_DEFAULT_DAYS));
        // n天前 0点0分
        startDate = DateTimeUtil.startOfDayDT(new Date()).minusDays(defaultDays - 1).toDate();
        // 今天 的 23点59分
        endDate = DateTimeUtil.endOfDay(new Date());
    }

    private void queryData() {
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            informationmap = Helper.zoneToDvPlaces(opticsDVPlaceService.findAll(siteId));
            Map<DVPlaceBean, Collection<MarkSegmentBean>> dvPlaceToMarkSegments = msService.findAllBySiteId(siteId);

            mapSegments = new HashMap<String, List<MarkSegmentBean>>();
            for (Map.Entry<DVPlaceBean, Collection<MarkSegmentBean>> entry : dvPlaceToMarkSegments.entrySet()) {
                mapSegments.put(entry.getKey().getId(), (List<MarkSegmentBean>) entry.getValue());
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public Map<Zone, Collection<DVPlaceBean>> getInformationmap() {
        return informationmap;
    }

    public void setInformationmap(Map<Zone, Collection<DVPlaceBean>> informationmap) {
        this.informationmap = informationmap;
    }

    public String getDvPlaceId() {
        return dvPlaceId;
    }

    public void setDvPlaceId(String dvPlaceId) {
        this.dvPlaceId = dvPlaceId;
    }

    public String getMarkId() {
        return markId;
    }

    public void setMarkId(String markId) {
        this.markId = markId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getChartData() {
        return chartData;
    }

    public void setChartData(String chartData) {
        this.chartData = chartData;
    }

    public List<MarkSegmentPositionBean> getMspList() {
        return mspList;
    }

    public void setMspList(List<MarkSegmentPositionBean> mspList) {
        this.mspList = mspList;
    }

    public Map<String, Map<Date, String>> getMapData() {
        return mapData;
    }

    public void setMapData(Map<String, Map<Date, String>> mapData) {
        this.mapData = mapData;
    }

    public Map<String, List<MarkSegmentBean>> getMapSegments() {
        return mapSegments;
    }

    public void setMapSegments(Map<String, List<MarkSegmentBean>> mapSegments) {
        this.mapSegments = mapSegments;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }
}
