package com.microwise.blueplanet.action.location;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.ControllerPackage;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.RecentDataVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.PagingUtil;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 历史数据
 *
 * @author xuyuexi
 * @date 14-7-7
 */
@Beans.Action
@Blueplanet
@ControllerPackage(parent = "blueplanet-location")
public class LocationHistoryDataAction extends BlueplanetLoggerAction {

    private static final Logger logger = LoggerFactory.getLogger(LocationHistoryDataAction.class);

    // 内容页面
    private static final String _pagePath = "history-data.ftl";
    // 一页显示数目
    public static final int SIZE_PER_PAGE = 20;

    @Autowired
    private LocationService locationService;


    //input
    /**
     * 设备id
     */
    private String locationId;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 当前页, 默认第一页
     */
    private int page = 1;

    //output
    /**
     * 设备
     */
    private LocationVO location;
    /**
     * 历史数据
     */
    private List<RecentDataVO> historyDatas;
    /**
     * 设备监测指标
     */
    private List<SensorinfoVO> sensorinfos;
    /**
     * 监测指标 id => 监测指标
     */
    private Map<Integer, SensorinfoVO> sensorinfoMap;
    /**
     * 总页数
     */
    private int pageSum;


    /**
     * 设备历史数据
     */
    @Route(value = "/blueplanet/location/{locationId}/history-data", interceptors = "locationStack")
    public String defaultView() {

        try {
            // 默认当天时间
            startTime = DateTime.now().withTimeAtStartOfDay().toDate();
            endTime = DateTime.now().plusDays(1).withTimeAtStartOfDay().minusMillis(1).toDate();
            queryData(page, startTime, endTime);
            log("位置点管理", "历史数据");
        } catch (Exception e) {
            logger.error("位置点历史数据", e);
        }
        return Results.ftl("/blueplanet/pages/location/layout");
    }

    /**
     * 设备历史数据
     */
    @Route(value = "/blueplanet/location/{locationId}/history-data", params = {"startTime", "endTime"}, interceptors = "locationStack")
    public String customView() {
        try {
            queryData(page, startTime, endTime);
            log("位置点管理", "历史数据");
        } catch (Exception e) {
            logger.error("位置点历史数据", e);
        }
        return Results.ftl("/blueplanet/pages/location/layout");
    }

    /**
     * 查询数据
     *
     * @param page      第几页(从 1 开始)
     * @param startDate
     * @param endDate
     */
    private void queryData(int page, Date startDate, Date endDate) {
        location = locationService.findLocationById(locationId);
        sensorinfos = location.getSensorInfoList();
        sensorinfoMap = Maps.uniqueIndex(sensorinfos, new Function<SensorinfoVO, Integer>() {
            @Override
            public Integer apply(SensorinfoVO sensorinfoVO) {
                return sensorinfoVO.getSensorPhysicalid();
            }
        });

        historyDatas = locationService.findHistoryDataList(locationId, startDate, endDate, page, SIZE_PER_PAGE);
        int historyDataCount = locationService.findRecentDataListCount(locationId, startDate, endDate);
        pageSum = PagingUtil.pagesCount(historyDataCount, SIZE_PER_PAGE);
    }

    public String get_pagePath() {
        return _pagePath;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public LocationVO getLocation() {
        return location;
    }

    public void setLocation(LocationVO location) {
        this.location = location;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<RecentDataVO> getHistoryDatas() {
        return historyDatas;
    }

    public void setHistoryDatas(List<RecentDataVO> historyDatas) {
        this.historyDatas = historyDatas;
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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSum() {
        return pageSum;
    }

    public void setPageSum(int pageSum) {
        this.pageSum = pageSum;
    }

}
