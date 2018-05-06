package com.microwise.proxima.action.opticsImage;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.DateTimeUtil;
import com.microwise.proxima.bean.DVPlaceBean;
import com.microwise.proxima.bean.MarkSegmentPositionBean;
import com.microwise.proxima.bean.Zone;
import com.microwise.proxima.service.MarkSegmentPositionService;
import com.microwise.proxima.service.OpticsDVPlaceService;
import com.microwise.proxima.sys.Proxima;
import com.opensymphony.xwork2.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 处理两点位置变化图表数据
 *
 * @author li.jianfei
 * @date 2012-08-13
 * @check guo.tian li.jianfei 2012-09-19
 */
@Beans.Action
@Proxima
public class ViewMarkChangeChartAction {

    private static final Logger log = LoggerFactory.getLogger(ViewMarkChangeChartAction.class);

    /**
     * 标记段位置 service
     */
    @Autowired
    private MarkSegmentPositionService mspService;

    /**
     * 光学摄像机点位 service
     */
    @Autowired
    private OpticsDVPlaceService dvPlaceService;

    @Autowired
    private OpticsDVPlaceService opticsDVPlaceService;

    // input
    /**
     * 区域id
     */
    private String zoneId = "";

    /**
     * 点位
     */
    private String dvPlaceId = "";

    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

    // output

    /**
     * 监测点id
     */
    private String chartData;

    /**
     * 最大 X 轴坐标
     */
    private int maxX;

    /**
     * 最大Y轴坐标
     */
    private int maxY;

    /**
     * 最小X轴坐标
     */
    private int minX;

    /**
     * 最小Y周坐标
     */
    private int minY;

    /**
     * 标记点坐标集合
     */
    private List<MarkSegmentPositionBean> mspList;

    /**
     * 标记段数据
     */
    private Map<String, Map<Date, String>> mapData;

    /**
     * 时间列表
     */
    private List<Date> listTime;

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

        try {
            String siteId = Sessions.createByAction().currentSiteId();
            informationmap = Helper.zoneToDvPlaces(opticsDVPlaceService.findAll(siteId));
        } catch (Exception e) {
            log.error("", e);
        }
        return Action.SUCCESS;
    }

    /**
     * 查询并处理图表数据
     *
     * @return
     */
    public String execute() {

        try {
            // 如果时间为空, 则时间初始化为默认时间
            if (startDate == null || endDate == null) {
                initDefaultDate();
            }

            String siteId = Sessions.createByAction().currentSiteId();
            informationmap = Helper.zoneToDvPlaces(opticsDVPlaceService.findAll(siteId));
            mspList = mspService.findAllMarkPositionData(dvPlaceId, startDate, endDate);

            listTime = new ArrayList<Date>();
            for (MarkSegmentPositionBean msp : mspList) {
                Date date = msp.getPicture().getSaveTime();
                if (!listTime.contains(date)) {
                    listTime.add(date);
                }
            }

            // 获取最大最小XY轴坐标
            Integer[] positions = mspService.findMaxXY(dvPlaceId, startDate, endDate);
            if (positions == null) {
                return Action.SUCCESS;
            }

            DVPlaceBean dvPlace = dvPlaceService.findById(dvPlaceId);

            maxX = dvPlace.getImageWidth();
            maxY = dvPlace.getImageHeight();
            minX = 0;
            minY = 0;

            // 根据查询到的数据组织图标数据串
            chartData = "";
            mapData = new HashMap<String, Map<Date, String>>();
            String markName = ""; // 标记段名称
            Map<Date, String> mapSegmentData = null;

            for (int i = 0; i < mspList.size(); i++) {
                MarkSegmentPositionBean msp = mspList.get(i);

                if (!markName.equals(msp.getMarkSegment().getName())) {
                    if (!markName.equals("")) {
                        chartData = chartData + ",{id: '" + markName + "',name : '" + markName + "'}";
                        mapData.put(markName, mapSegmentData);
                    }

                    mapSegmentData = new HashMap<Date, String>();
                }
                markName = msp.getMarkSegment().getName();
                Date date = msp.getPicture().getSaveTime();

                mapSegmentData.put(date, "[["
                        + msp.getPositionX()
                        + ","
                        + (dvPlace.getImageHeight() - msp.getPositionY())
                        + "],["
                        + msp.getPositionX2()
                        + ","
                        + (dvPlace.getImageHeight() - msp.getPositionY2())
                        + "]]");

                if (i == mspList.size() - 1) {
                    chartData = chartData + ",{id: '" + markName + "',name : '" + markName + "'}";
                    mapData.put(markName, mapSegmentData);
                }

            }
            if (chartData.length() > 0) {
                chartData = "[" + chartData.substring(1) + "]";
            }
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

    public String getDvPlaceId() {
        return dvPlaceId;
    }

    public void setDvPlaceId(String dvPlaceId) {
        this.dvPlaceId = dvPlaceId;
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

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public int getMinX() {
        return minX;
    }

    public void setMinX(int minX) {
        this.minX = minX;
    }

    public int getMinY() {
        return minY;
    }

    public void setMinY(int minY) {
        this.minY = minY;
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

    public List<Date> getListTime() {
        return listTime;
    }

    public void setListTime(List<Date> listTime) {
        this.listTime = listTime;
    }

    public Map<Zone, Collection<DVPlaceBean>> getInformationmap() {
        return informationmap;
    }

    public void setInformationmap(Map<Zone, Collection<DVPlaceBean>> informationmap) {
        this.informationmap = informationmap;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }
}
