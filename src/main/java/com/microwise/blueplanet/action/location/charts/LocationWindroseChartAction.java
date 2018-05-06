package com.microwise.blueplanet.action.location.charts;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.ControllerPackage;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.vo.HighChartWindRoseVO;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.WindRoseVO;
import com.microwise.blueplanet.service.ChartService;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.blueplanet.util.HighChartUtil;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 风向玫瑰图
 *
 * @author xuyuexi
 * @date 14-7-10
 */
@Beans.Action
@Blueplanet
@ControllerPackage(parent = "blueplanet-location")
public class LocationWindroseChartAction extends BlueplanetLoggerAction {
    /**
     * 内容页面
     */
    private static final String _pagePath = "charts/windrose-chart.ftl";
    private static Logger log = LoggerFactory.getLogger(LocationWindroseChartAction.class);
    @Autowired
    private ChartService chartService;
    @Autowired
    private LocationService locationService;

    //input
    /**
     * 位置点id
     */
    private String locationId;

    /**
     * 默认时间
     */
    private Date defaultTime;

    /**
     * 时间类型
     */
    private int dateType = Constants.FIND_TYPE_DAY;

    /**
     * 开始日期
     */
    private Date date = new Date();

    /**
     * 开始日期
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 时间
     */
    private Date time;
    //output
    /**
     * 风向玫瑰图数据
     */
    private HighChartWindRoseVO data;

    @Route(value = "/blueplanet/location/{locationId}/windrose-chart", interceptors = "locationStack")
    public String view() {
        try {
            //默认昨天的数据
            defaultTime = DateTime.now().minusDays(1).toDate();
            //初始化时间
            initDate();
            log("位置点", "风向玫瑰图");
        } catch (Exception e) {
            log.error("风向图", e);
        }
        return Results.ftl("/blueplanet/pages/location/layout");
    }


    @Route(value = "/blueplanet/location/{locationId}/windrose-chart.json")
    public String execute() {
        try {
            WindRoseVO windroseData = chartService.findWindRose(locationId, dateType, time, startDate, endDate);
            LocationVO location = locationService.findLocationById(locationId);
            String locationName = location.getLocationName();
            data = HighChartUtil.packageWindRose(locationId, locationName, windroseData);
            log("位置点", "风向玫瑰图");
        } catch (Exception e) {
            log.error("风向图", e);
        }
        return Results.json().asRoot(data).done();
    }

    private void initDate() {
        if (startDate == null) {
            startDate = DateTime.now().minusDays(1).withTimeAtStartOfDay().toDate();
        }
        if (endDate == null) {
            endDate = DateTime.now().withTimeAtStartOfDay().toDate();
        }
    }


    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getDefaultTime() {
        return defaultTime;
    }

    public void setDefaultTime(Date defaultTime) {
        this.defaultTime = defaultTime;
    }

    public HighChartWindRoseVO getData() {
        return data;
    }

    public void setData(HighChartWindRoseVO data) {
        this.data = data;
    }

    public int getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        if (dateType.equals("")) return;
        this.dateType = Integer.parseInt(dateType);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
}
