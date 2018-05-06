package com.microwise.proxima.action.infraredImage;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.proxima.bean.DVPlaceBean;
import com.microwise.proxima.bean.InfraredDVPlaceBean;
import com.microwise.proxima.bean.InfraredMarkRegionBean;
import com.microwise.proxima.bean.Zone;
import com.microwise.proxima.service.InfraredChartService;
import com.microwise.proxima.service.InfraredDVPlaceService;
import com.microwise.proxima.service.InfraredMarkRegionService;
import com.microwise.proxima.service.ZoneService;
import com.microwise.proxima.sys.Proxima;
import com.microwise.proxima.sys.ProximaLoggerAction;
import com.opensymphony.xwork2.Action;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 红外图片温度变换趋势图表 action
 *
 * @author li.jianfei
 * @date 2012-9-10
 * @check guo.tian li.jianfei 2012-09-19
 * @check li.jianfei liu.zhu 2014-4-15 # 8353
 */
@Beans.Action
@Proxima
public class ViewInfraredChartAction extends ProximaLoggerAction {

    private static final Logger log = LoggerFactory.getLogger(ViewInfraredChartAction.class);
    /**
     * 温度变化趋势图数据处理
     */
    @Autowired
    private InfraredChartService infraredChartService;
    @Autowired
    private InfraredDVPlaceService infraredDVPlaceService;
    @Autowired
    private InfraredMarkRegionService infraredMarkRegionService;
    /**
     * 区域service
     */
    @Autowired
    private ZoneService zoneService;
    // input
    /**
     * 监测点id
     */
    private int monitorPointId;

    /**
     * 点位ID
     */
    private String dvPlaceId;

    /**
     * 有摄像机点位的区域
     */
    private List<Zone> zones;

    /**
     * 回显时的区域ID
     */
    private String zoneId;

    /**
     * 默认展示点位
     */
    private List<InfraredDVPlaceBean> infraredDVPlaces;

    /**
     * 区域 id
     */
    private String markRegionId;

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
     * 监测点 => 监测点下面的摄像机点位集合
     */
    private Map<Zone, Collection<DVPlaceBean>> zoneMap;

    /**
     * 摄像机点位 => 摄像机点位下的区域信息
     */
    private Map<DVPlaceBean, Collection<InfraredMarkRegionBean>> dvPlaceMap;

    /**
     * 标记区域
     */
    private List<InfraredMarkRegionBean> infraredMarkRegionList;

    /**
     * 初始化页面数据
     *
     * @return
     * @author lj.jianfei
     * @date 2012-09-11
     */
    public String view() {
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            initDefaultDate();
            zones = zoneService.findHasInfrareds(siteId);
            infraredDVPlaces = infraredDVPlaceService.findAll(siteId);
            if (infraredDVPlaces.size() > 0) {
                dvPlaceId = infraredDVPlaces.get(0).getId();
                infraredMarkRegionList = infraredMarkRegionService.findAllByDVPlaceId(infraredDVPlaces.get(0).getId());
            }

            chartData = "";
            List<Map<String, Object>> mapList;
            if (Strings.isNullOrEmpty(markRegionId)) { // 全图
                mapList = infraredChartService.getInfraredPictureDataSeries(
                        dvPlaceId, startDate, endDate);

            } else { // 区域
                mapList = infraredChartService.getInfraredMarkRegionDataSeries(
                        dvPlaceId, markRegionId, startDate, endDate);
            }
            if (mapList != null) {
                Gson gson = new Gson();
                chartData = gson.toJson(mapList);
            }
            log("图片浏览", "温度变化趋势图");
            return Action.SUCCESS;
        } catch (Exception e) {
            log.error("温度变化趋势图", e);
            return Action.ERROR;
        }

    }

    /**
     * 处理温度变化趋势图表数据
     *
     * @return
     * @author li.jianfei
     * @date 2012-09-11
     */
    public String execute() {

        try {
            // 如果时间为空, 则时间初始化为默认时间
            if (startDate == null || endDate == null) {
                initDefaultDate();
            }
            String siteId = Sessions.createByAction().currentSiteId();
            zones = zoneService.findHasInfrareds(siteId);
            infraredDVPlaces = infraredDVPlaceService.findAll(siteId);
            infraredMarkRegionList = infraredMarkRegionService.findAllByDVPlaceId(dvPlaceId);
            chartData = "";
            List<Map<String, Object>> mapList;
            if (Strings.isNullOrEmpty(markRegionId)) { // 全图
                mapList = infraredChartService.getInfraredPictureDataSeries(
                        dvPlaceId, startDate, endDate);

            } else { // 区域
                mapList = infraredChartService.getInfraredMarkRegionDataSeries(
                        dvPlaceId, markRegionId, startDate, endDate);
            }
            if (mapList != null) {
                Gson gson = new Gson();
                chartData = gson.toJson(mapList);
            }
            log("图片浏览", "温度变化趋势图");
            return Action.SUCCESS;
        } catch (Exception e) {
            log.error("温度变化趋势图", e);
            return Action.ERROR;
        }
    }

    /**
     * 区域摄像机联动
     *
     * @return
     * @author xu.yuexi
     * @date 2014-04-14
     */
    public String ajax() {
        try {
            infraredMarkRegionList = infraredMarkRegionService.findAllByDVPlaceId(dvPlaceId);
        } catch (Exception e) {
            log.error("区域摄像机联动", e);
        }
        return Action.SUCCESS;
    }


    /**
     * 设置默认开始日期和结束日期
     */
    private void initDefaultDate() {
        int defaultDays = Integer.parseInt(ConfigFactory.getInstance().getConfig(Constants.Config.CONFIG_PROPERTIES_URL).get(Constants.Proxima.KEY_VIEW_IMAGE_QUERY_DEFAULT_DAYS));
        // n天前 0点0分
        startDate = DateTime.now().minusDays(defaultDays - 1).withHourOfDay(0)
                .withMinuteOfHour(0).withSecondOfMinute(0).toDate();
        // 今天 的 23点59分
        endDate = DateTime.now().withHourOfDay(23).withMinuteOfHour(59)
                .withSecondOfMinute(59).toDate();
    }

    public int getMonitorPointId() {
        return monitorPointId;
    }

    public void setMonitorPointId(int monitorPointId) {
        this.monitorPointId = monitorPointId;
    }

    public String getDvPlaceId() {
        return dvPlaceId;
    }

    public void setDvPlaceId(String dvPlaceId) {
        this.dvPlaceId = dvPlaceId;
    }

    public Map<Zone, Collection<DVPlaceBean>> getZoneMap() {
        return zoneMap;
    }

    public void setZoneMap(Map<Zone, Collection<DVPlaceBean>> zoneMap) {
        this.zoneMap = zoneMap;
    }

    public String getMarkRegionId() {
        return markRegionId;
    }

    public void setMarkRegionId(String markRegionId) {
        this.markRegionId = markRegionId;
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


    public Map<DVPlaceBean, Collection<InfraredMarkRegionBean>> getDvPlaceMap() {
        return dvPlaceMap;
    }

    public void setDvPlaceMap(
            Map<DVPlaceBean, Collection<InfraredMarkRegionBean>> dvPlaceMap) {
        this.dvPlaceMap = dvPlaceMap;
    }

    public String getChartData() {
        return chartData;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    public List<InfraredDVPlaceBean> getInfraredDVPlaces() {
        return infraredDVPlaces;
    }

    public void setInfraredDVPlaces(List<InfraredDVPlaceBean> infraredDVPlaces) {
        this.infraredDVPlaces = infraredDVPlaces;
    }

    public void setChartData(String chartData) {
        this.chartData = chartData;
    }

    public List<InfraredMarkRegionBean> getInfraredMarkRegionList() {
        return infraredMarkRegionList;
    }

    public void setInfraredMarkRegionList(List<InfraredMarkRegionBean> infraredMarkRegionList) {
        this.infraredMarkRegionList = infraredMarkRegionList;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }
}
