package com.microwise.blueplanet.action.location.charts;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.ControllerPackage;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 基础曲线图
 *
 * @author Wang yunlong
 * @date 13-1-31 上午10:52
 * @check @li.jianfei 2013-03-13 #1994
 */
@Beans.Action
@Blueplanet
@ControllerPackage(parent = "blueplanet-location")
public class BasicChart2Action extends BlueplanetLoggerAction {
    /**
     * 内容页面
     */
    private static final String _pagePath = "charts/basic-chart.ftl";

    @Autowired
    private LocationService locationService;

    //input
    /**
     * 默认结束时间
     */
    private Date startTime;
    /**
     * 默认结束时间
     */
    private Date endTime;

    /**
     * 位置点ID
     */
    private String locationId;


    //output
    /**
     * 位置点
     */
    private LocationVO location;

    @Route(value = "/blueplanet/location/{locationId}/basic-chart", interceptors = "locationStack")
    public String execute() {
        startTime = DateTime.now().withTimeAtStartOfDay().toDate();
        endTime = DateTime.now().plusDays(1).withTimeAtStartOfDay().minusMillis(1).toDate();
        location = locationService.findLocationById(locationId);
        log("位置点", "基础曲线图");
        return Results.ftl("/blueplanet/pages/location/layout");
    }

    public String get_pagePath() {
        return _pagePath;
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
}
