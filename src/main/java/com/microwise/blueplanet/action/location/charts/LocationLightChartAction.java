package com.microwise.blueplanet.action.location.charts;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.ControllerPackage;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.vo.ChartVO;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.service.ChartService;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.blueplanet.util.HighChartUtil;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * 光照图
 *
 * @author liuzhu
 * @date 2014-7-3
 */
@Beans.Action
@Blueplanet
@ControllerPackage(parent = "blueplanet-location")
public class LocationLightChartAction extends BlueplanetLoggerAction {

    public static final Logger log = LoggerFactory.getLogger(LocationLightChartAction.class);
    // 内容页面
    private final String _pagePath = "charts/light-chart.ftl";

    @Autowired
    private LocationService locationService;

    @Autowired
    private ChartService chartService;


    // Input

    /**
     * 位置点id
     */
    private String locationId;

    /**
     * 时间类型
     */
    private int dateType;


    /**
     * 日期
     */
    private String date;

    // Output
    /**
     * HighChartData
     */
    private Map<String, Object> highChartData;

    @Route(value = "/blueplanet/location/{locationId}/light-chart", interceptors = "locationStack")
    public String view() {

        if (date == null) {
            date = DateFormatUtils.format(DateUtils.addDays(new Date(), -1), "yyyy-MM-dd");
        }
        log("位置点", "累计光照图");
        return Results.ftl("/blueplanet/pages/location/layout");
    }

    @Route("/blueplanet/location/{locationId}/light-chart.json")
    public String execute() {
        try {
            ChartVO chart = chartService.findLight(locationId, dateType, DateTime.parse(date).toDate());

            // 将原始数据转换为 HighChart 格式数据
            LocationVO locationVO = locationService.findLocationById(locationId);
            highChartData = HighChartUtil.packageLight(locationId, locationVO.getLocationName(), chart);
        } catch (Exception e) {
            log.error("位置点累积光照错误",e);
        }
        return Results.json().root("highChartData").done();
    }

    public String get_pagePath() {
        return _pagePath;
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

    public Map<String, Object> getHighChartData() {
        return highChartData;
    }

    public void setHighChartData(Map<String, Object> highChartData) {
        this.highChartData = highChartData;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }
}
