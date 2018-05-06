package com.microwise.blueplanet.action.zone;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.ZoneVO;
import com.microwise.blueplanet.service.ZoneService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 浏览模式
 *
 * @author Wang yunlong
 * @date 13-4-15 上午11:10
 * @check #2667 @gaohui 2014-04-19
 */
@Beans.Action
@Blueplanet
public class ZonePlanImageViewAction {
    private final String _pagePath = "plan-image-view2.ftl";

    /**
     * 本体监测资源路径
     */
    public String proximaResourcesUrl = Sessions.createByAction().getGalaxyResourceURL() + "/proxima/images";

    /**
     * 环境监控资源路径
     */
//    public String resourcesPath = "http://localhost:8080/galaxy-resources/blueplanet/images/zonePlanImage";
    public String resourcesPath = Sessions.createByAction().getGalaxyResourceURL() + "/blueplanet/images/zonePlanImage";


    @Autowired
    private ZoneService zoneService;
    //input
    /**
     * 区域id
     */
    private String zoneId;
    //output
    /**
     * 区域
     */
    private ZoneVO zone;

    // 编辑模式
    private boolean editMode = false;


    @Route("/blueplanet/zone/{zoneId}/plan-image")
    public String view() {
        zone = zoneService.findZoneById(zoneId);
        return Results.ftl("/blueplanet/pages/zone/layout-b4.ftl");
    }

    @Route("/blueplanet/zone/{zoneId}/plan-image/edit")
    public String edit(){
        editMode = true;
        zone = zoneService.findZoneById(zoneId);
        return Results.ftl("/blueplanet/pages/zone/layout-b4.ftl");
    }

    public String get_pagePath() {
        return _pagePath;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public ZoneVO getZone() {
        return zone;
    }

    public String getResourcesPath() {
        return resourcesPath;
    }

    public String getProximaResourcesUrl() {
        return proximaResourcesUrl;
    }

    public boolean isEditMode() {
        return editMode;
    }
}
