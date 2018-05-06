package com.microwise.blueplanet.action.location.charts;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.ControllerPackage;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.vo.ChartVO;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.service.ChartService;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.blueplanet.util.HighChartUtil;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 降雨量柱状图
 *
 * @author liuzhu
 * @date 2014-7-1
 */
@Beans.Action("locationRainfallChartAction")
@Blueplanet
@ControllerPackage(parent = "blueplanet-location")
public class RainfallChartAction extends BlueplanetLoggerAction {

    public static final Logger log = LoggerFactory.getLogger(RainfallChartAction.class);

    // 内容页面
    private final String _pagePath = "charts/rainfall-chart.ftl";

    @Autowired
    private ChartService chartService;

    @Autowired
    private LocationService locationService;


    // Input
    private String locationId;

    /**
     * 监测指标id
     */
    private Integer[] sensorIds;

    /**
     * 时间类型
     */
    private int dateType = 2;

    /**
     * 日期
     */
    private String date;

    // Output
    /**
     * HighChartData
     */
    private Map<String, Object> highChartData;

    /**
     * 监测指标列表
     */
    private List<SensorinfoVO> sensorInfoList;

    @Route(value = "/blueplanet/location/{locationId}/rainfall-chart", interceptors = "locationStack")
    public String view() {
        if (date == null) {
            date = DateFormatUtils.format(new Date(), "yyyy-MM");
        }
        sensorInfoList = locationService.findSensorInfoList(locationId);
        log("位置点", "降雨量图");
        return Results.ftl("/blueplanet/pages/location/layout");
    }

    @Route("/blueplanet/location/{locationId}/rainfall-chart.json")
    public String execute() {
        try {
            // 获取该设备所有监测指标信息
            sensorInfoList = locationService.findSensorInfoList(locationId);
            // 通过 Service 获取原始数据
            List<Integer> sensorIdList = new ArrayList<Integer>(Arrays.asList(sensorIds));
            List<ChartVO> chartList = chartService.findRainfall(locationId, sensorIdList, dateType, DateTime.parse(date).toDate());

            // 将原始数据转换为 HighChart 格式数据
            LocationVO locationVO = locationService.findLocationById(locationId);
            highChartData = HighChartUtil.packageRainfull(locationId, locationVO.getLocationName(), chartList);
        } catch (Exception e) {
            log.error("位置点降雨量错误",e);
        }

        return Results.json().root("highChartData").done();
    }

    public String get_pagePath() {
        return _pagePath;
    }

    public Integer[] getSensorIds() {
        return sensorIds;
    }

    public void setSensorIds(Integer[] sensorIds) {
        this.sensorIds = sensorIds;
    }

    public int getDateType() {
        return dateType;
    }

    public void setDateType(int dateType) {
        this.dateType = dateType;
    }

    public Date getDate() {
        return DateTime.parse(date).toDate();
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<SensorinfoVO> getSensorInfoList() {
        return sensorInfoList;
    }

    public void setSensorInfoList(List<SensorinfoVO> sensorInfoList) {
        this.sensorInfoList = sensorInfoList;
    }

    public Map<String, Object> getHighChartData() {
        return highChartData;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public void setHighChartData(Map<String, Object> highChartData) {
        this.highChartData = highChartData;
    }
}
