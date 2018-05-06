package com.microwise.halley.action.route;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.RecentDataVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.proxy.LocationProxy;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.PagingUtil;
import com.microwise.halley.bean.po.AlarmPO;
import com.microwise.halley.bean.po.PathPO;
import com.microwise.halley.bean.vo.TimeIntervalVO;
import com.microwise.halley.service.HistoryDataService;
import com.microwise.halley.service.LocationService;
import com.microwise.halley.sys.Halley;
import com.microwise.halley.sys.HalleyLoggerAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 历史传感器数据和路线数据
 *
 * @author xu.yuexi
 * @date 13-11-4
 * @check @li.jianfei 2014-05-22 #8588
 */
@Beans.Action
@Halley
public class HistoryDataAction {

    public static final Logger log = LoggerFactory.getLogger(HistoryDataAction.class);

    /**
     * 页面
     */
    public static final String _pagePath = "../route/history-data.ftl";


    @Autowired
    private HalleyLoggerAction halleyLogger;

    /**
     * 设备服务
     */
    @Autowired
    private LocationService locationService;
    /**
     * 设备代理
     */
    @Autowired
    private LocationProxy locationProxy;

    /**
     * 历史数据查询服务
     */
    @Autowired
    private HistoryDataService historyDataService;


    // Input
    /**
     * 外展ID
     */
    private int exhibitionId;

    /**
     * 位置点ID
     */
    private String locationId;

    /**
     * 分页大小
     */
    private int pageCount;

    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 当前页, 默认第一页
     */
    private int page = 1;

    /**
     * 当前时间段
     */
    private TimeIntervalVO timeInterval;

    /**
     * 页面选择的Index
     */
    private int timeIntervalIndex;


    //output
    /**
     * 设备
     */
    private LocationVO location;

    /**
     * 线路预设点
     */
    private List<PathPO> pathPOList;

    /**
     * 车辆历史数据形式路线，按车辆分开，可能存在多个GPS设备，一辆车只能放置一个GPS设备
     */
    private Map<String, List<Map<String, String>>> historyDataMap;

    /**
     * 时间段划分
     */
    private List<TimeIntervalVO> timeIntervalVOs;

    /**
     * 位置点列表
     */
    private List<LocationVO> locations;

    /**
     * 历史数据集合
     */
    private List<RecentDataVO> historyDataVOList;

    /**
     * 设备监测指标
     */
    private List<SensorinfoVO> sensorinfos;

    /**
     * 监测指标 id => 监测指标
     */
    private Map<Integer, SensorinfoVO> sensorinfoMap;

    /**
     * 报警短信记录集合
     */
    private List<AlarmPO> alarmList;

    /**
     * 历史数据总页数
     */
    private int pageSum;




    @Route(value = "/halley/routeHistory", params = {"exhibitionId", "page"})
    public String historyData() {
        try {
            //获取时间段列表
            timeIntervalVOs = historyDataService.getTimeInterval(exhibitionId);
            //获取节点列表
            locations = locationService.findLocationListByExhibitionId(exhibitionId);
            //如果传入-1，则没有可查询位置点
            if (locationId.equals("-1")) {
                ActionMessage.createByAction().fail("没有可查询的位置点").consume();
            } else {
                if (locationId.equals("")) {
                    locationId = locations.get(0).getNodeId();
                }
                queryData(locationId, page, startDate, endDate);
            }
            halleyLogger.log("查询历史数据", "查询历史数据");
        } catch (Exception e) {
            log.error("查询历史数据", e);
            halleyLogger.logFailed("查询历史数据", "查询历史数据");
        }
        return Results.ftl("/halley/pages/index/layout.ftl");
    }

    @Route("/halley/historyData/exhibition/{exhibitionId}/timeIntervalIndex/{timeIntervalIndex}")
    public String defaultHistoryData() {
        try {
            //获取时间段列表
            timeIntervalVOs = historyDataService.getTimeInterval(exhibitionId);
            //获取节点列表
            locations = locationService.findLocationListByExhibitionId(exhibitionId);

            //开始时间为所选时间段的开始时间  ，timeIntervalIndex是时间段序号
            startDate = timeIntervalVOs.get(timeIntervalIndex).getBeginTime();
            //时间段的结束时间可能为空，如果不给空将时间段的结束时间赋给结束时间
            endDate = timeIntervalVOs.get(timeIntervalIndex).getEndTime();
            if (endDate == null) {
                endDate = new Date();
            }

            //分页，如果节点列表有数据获取页数，查找数据，默认的数据列表为第一个节点的设备的数据
            if (Strings.isNullOrEmpty(locationId)) {
                locationId = locations.get(0).getId();
                queryData(locationId, page, startDate, endDate);
            } else {
                queryData(locationId, page, startDate, endDate);
            }
            halleyLogger.log("查询历史数据", "查询历史数据");
        } catch (Exception e) {
            log.error("查询历史数据", e);
            halleyLogger.logFailed("查询历史数据", "查询历史数据");
        }
        return Results.ftl("/halley/pages/index/layout.ftl");
    }

    /**
     * 查询数据
     *
     * @param page      第几页(从 1 开始)
     * @param startDate 开始时间
     * @param endDate   结束时间
     */
    private void queryData(String locationId, int page, Date startDate, Date endDate) {
        location = locationProxy.findLocationById(locationId);
        sensorinfos = location.getSensorInfoList();
        sensorinfoMap = Maps.uniqueIndex(sensorinfos, new Function<SensorinfoVO, Integer>() {
            @Override
            public Integer apply(SensorinfoVO sensorinfoVO) {
                return sensorinfoVO.getSensorPhysicalid();
            }
        });
        timeInterval = timeIntervalVOs.get(timeIntervalIndex);
        historyDataVOList = locationProxy.findHistoryDataList(locationId, startDate, endDate, page, Constants.SIZE_PER_PAGE);
        int historyDataCount = locationProxy.findRecentDataListCount(locationId, startDate, endDate);
        pageSum = PagingUtil.pagesCount(historyDataCount, Constants.SIZE_PER_PAGE);
    }

    public void setHalleyLogger(HalleyLoggerAction halleyLogger) {
        this.halleyLogger = halleyLogger;
    }

    public int getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(int exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    public List<TimeIntervalVO> getTimeIntervalVOs() {
        return timeIntervalVOs;
    }

    public void setTimeIntervalVOs(List<TimeIntervalVO> timeIntervalVOs) {
        this.timeIntervalVOs = timeIntervalVOs;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public void setLocation(LocationVO location) {
        this.location = location;
    }

    public List<RecentDataVO> getHistoryDataVOList() {
        return historyDataVOList;
    }

    public void setHistoryDataVOList(List<RecentDataVO> historyDataVOList) {
        this.historyDataVOList = historyDataVOList;
    }

    public HalleyLoggerAction getHalleyLogger() {
        return halleyLogger;
    }

    public List<LocationVO> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationVO> locations) {
        this.locations = locations;
    }


    public static String get_pagePath() {
        return _pagePath;
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

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageSum() {
        return pageSum;
    }

    public void setPageSum(int pageSum) {
        this.pageSum = pageSum;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<SensorinfoVO> getSensorinfos() {
        return sensorinfos;
    }

    public void setSensorinfos(List<SensorinfoVO> sensorinfos) {
        this.sensorinfos = sensorinfos;
    }

    public Map<Integer, SensorinfoVO> getSensorinfoMap() {
        return sensorinfoMap;
    }

    public void setSensorinfoMap(Map<Integer, SensorinfoVO> sensorinfoMap) {
        this.sensorinfoMap = sensorinfoMap;
    }

    public TimeIntervalVO getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(TimeIntervalVO timeInterval) {
        this.timeInterval = timeInterval;
    }

    public int getTimeIntervalIndex() {
        return timeIntervalIndex;
    }

    public void setTimeIntervalIndex(int timeIntervalIndex) {
        this.timeIntervalIndex = timeIntervalIndex;
    }

    public List<PathPO> getPathPOList() {
        return pathPOList;
    }

    public void setPathPOList(List<PathPO> pathPOList) {
        this.pathPOList = pathPOList;
    }

    public Map<String, List<Map<String, String>>> getHistoryDataMap() {
        return historyDataMap;
    }

    public void setHistoryDataMap(Map<String, List<Map<String, String>>> historyDataMap) {
        this.historyDataMap = historyDataMap;
    }

    public List<AlarmPO> getAlarmList() {
        return alarmList;
    }

    public void setAlarmList(List<AlarmPO> alarmList) {
        this.alarmList = alarmList;
    }

    public LocationVO getLocation() {
        return location;
    }

}
