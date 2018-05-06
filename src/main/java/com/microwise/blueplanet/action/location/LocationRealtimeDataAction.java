package com.microwise.blueplanet.action.location;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.ControllerPackage;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 实时数据
 *
 * @author xuyuexi
 * @date 14-7-1
 */
@Beans.Action
@Blueplanet
@ControllerPackage(parent = "blueplanet-location")
public class LocationRealtimeDataAction extends BlueplanetLoggerAction {

    private static final Logger logger = LoggerFactory.getLogger(LocationRealtimeDataAction.class);

    @Autowired
    private LocationService locationService;
    /**
     * 内容页面
     */
    private static final String _pagePath = "location-realtime-data.ftl";

    //input
    /**
     * 位置点id
     */
    private String locationId;

    //output
    private LocationVO location;


    @Route(value = "/blueplanet/location/{locationId}/realtime-data", interceptors = "locationStack")
    public String execute() {
        try {
            location = locationService.findLocationById(locationId);
            log("位置点", "实时数据");
        } catch (Exception e) {
            logger.error("位置点实时数据", e);
        }
        return Results.ftl("/blueplanet/pages/location/layout");
    }

    public String get_pagePath() {
        return _pagePath;
    }

    public LocationVO getLocation() {
        return location;
    }

    public void setLocation(LocationVO location) {
        this.location = location;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }
}
