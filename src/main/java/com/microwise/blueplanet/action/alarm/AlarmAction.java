package com.microwise.blueplanet.action.alarm;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.bean.Site;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.ZoneVO;
import com.microwise.blueplanet.service.AlarmService;
import com.microwise.blueplanet.service.ZoneService;
import com.microwise.blueplanet.sys.AppCacheHolder;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * 监测预警action
 *
 * @author liuzhu
 * @date 14-3-11
 */
@Beans.Action
@Blueplanet
@Route("/blueplanet")
public class AlarmAction extends BlueplanetLoggerAction {
    public static final Logger log = LoggerFactory.getLogger(AlarmAction.class);

    public static final String _pagePath = "index.ftl";

    /**
     * 监测预警service
     */
    @Autowired
    private AlarmService alarmService;

    /**
     * （环境监控缓存）
     */
    @Autowired
    private AppCacheHolder appCacheHolder;

    @Autowired
    private ZoneService zoneService;

    //input
    /**
     * 区域id
     */
    private String zoneId;

    /**
     * 当前区域
     */
    private ZoneVO zone;

    //output
    /**
     * 下级区域列表
     */
    private List<ZoneVO> zones;

    /**
     * 当前位置
     */
    private List<ZoneVO> currentZones;

    /**
     * 当前站点
     */
    private Site currentSite;

    {
        currentSite = Sessions.createByAction().currentLogicGroup().getSite();
    }

    @Route("alarm/index")
    public String index() {
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            zones = alarmService.findZoneMeasure(siteId, null);
            currentZones = new ArrayList<ZoneVO>();
        } catch (Exception e) {
            log.error("监测预警", e);
        }

        log("系统管理", "区域管理");
        return Results.ftl("/blueplanet/pages/alarm/layout");
    }

    @Route("alarm/index/{zoneId}")
    public String execute() {
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            zones = alarmService.findZoneMeasure(siteId, zoneId);
            zone = zoneService.findZoneById(zoneId);
            currentZones = new ArrayList<ZoneVO>();
            loadCurrentZone(zone, currentZones, appCacheHolder);
        } catch (Exception e) {
            log.error("监测预警", e);
        }
        log("查询监测预警", "监测预警");
        return Results.ftl("/blueplanet/pages/alarm/layout");
    }

    /**
     * 递归获取当前区域的层级
     *
     * @param zone
     * @param currentZones
     * @param appCacheHolder
     * @throws java.util.concurrent.ExecutionException
     *
     */
    private static void loadCurrentZone(ZoneVO zone, List<ZoneVO> currentZones, AppCacheHolder appCacheHolder) throws ExecutionException {
        currentZones.add(zone);
        String preId = zone.getParentId();
        if (preId != null) {
            ZoneVO preZone = appCacheHolder.loadZone(preId);
            loadCurrentZone(preZone, currentZones, appCacheHolder);
        }
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public List<ZoneVO> getZones() {
        return zones;
    }

    public void setZones(List<ZoneVO> zones) {
        this.zones = zones;
    }

    public List<ZoneVO> getCurrentZones() {
        return currentZones;
    }

    public void setCurrentZones(List<ZoneVO> currentZones) {
        this.currentZones = currentZones;
    }

    public Site getCurrentSite() {
        return currentSite;
    }

    public void setCurrentSite(Site currentSite) {
        this.currentSite = currentSite;
    }

    public ZoneVO getZone() {
        return zone;
    }

    public void setZone(ZoneVO zone) {
        this.zone = zone;
    }
}
