package com.microwise.blueplanet.action.location;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.ControllerPackage;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.List;

/**
 * 实时图形
 *
 * @author xuyuexi
 * @date 13-2-17 14:21
 */
@Beans.Action
@Blueplanet
@ControllerPackage(parent = "blueplanet-location")
public class LocationRealtimeChartAction extends BlueplanetLoggerAction {

    private static final Logger logger = LoggerFactory.getLogger(LocationRealtimeChartAction.class);

    // 内容页面
    private static final String _pagePath = "location-realtime-chart.ftl";

    @Autowired
    private LocationService locationService;

    //input
    /**
     * 位置点ID
     */
    private String locationId;

    //output
    /**
     * 设备
     */
    private LocationVO location;
    /**
     * 设备监测指标(除过风向类)
     */
    private List<SensorinfoVO> sensorinfos;

    @Route(value = "/blueplanet/location/{locationId}/realtime-chart", interceptors = "locationStack")
    public String view() {
        try {
            location = locationService.findLocationById(locationId);
            sensorinfos = location.getSensorInfoList();
            // 过滤掉风向类
            for (Iterator<SensorinfoVO> it = sensorinfos.iterator(); it.hasNext(); ) {
                if (it.next().getShowType() == 1) {
                    it.remove();
                }
            }
            log("位置点", "实时图形");
        } catch (Exception e) {
            logger.error("位置点实施图形",e);
        }
        return Results.ftl("/blueplanet/pages/location/layout");
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public List<SensorinfoVO> getSensorinfos() {
        return sensorinfos;
    }

    public void setSensorinfos(List<SensorinfoVO> sensorinfos) {
        this.sensorinfos = sensorinfos;
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
