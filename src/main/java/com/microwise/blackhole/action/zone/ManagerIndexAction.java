package com.microwise.blackhole.action.zone;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.bean.Site;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.ZoneVO;
import com.microwise.blueplanet.proxy.ZoneManagerProxy;
import com.microwise.blueplanet.sys.AppCacheHolder;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * <pre>
 * 区域管理
 * </pre>
 *
 * @author Wang yunlong
 * @date 13-1-18 上午9:55
 */
@Beans.Action
@Blackhole
public class ManagerIndexAction extends BlueplanetLoggerAction {
    private static final Logger log = LoggerFactory.getLogger(ManagerIndexAction.class);
    @Autowired
    private AppCacheHolder appCacheHolder;
    @Autowired
    private ZoneManagerProxy zoneManagerProxy;
    /**
     * 内容页面
     */
    private final String _pagePath = "manager-index.ftl";
    /**
     * 区域id
     */
    private String zoneId;
    /**
     * 当前区域
     */
    private ZoneVO zone;
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

    /**
     * 平面图资源路径
     */
    private String resourcesPath;

    {
        resourcesPath = Sessions.createByAction().getGalaxyResourceURL() + "/blueplanet/images/zonePlanImage";
        currentSite = Sessions.createByAction().currentLogicGroup().getSite();
    }

    @Route("/blackhole/zone")
    public String atSite() {
        String siteId = Sessions.createByAction().currentSiteId();
        zones = zoneManagerProxy.findZoneList(siteId, null);
        currentZones = new ArrayList<ZoneVO>();
        log("系统管理", "区域管理");
        return Results.ftl("/blackhole/pages/zone/manager-index.ftl");
    }

    @Route("/blackhole/zone/{zoneId}")
    public String execute() {
        try {
            String siteId = ManagerIndexAction.getSiteId();
            zones = zoneManagerProxy.findZoneList(siteId, zoneId);
            zone = zoneManagerProxy.findZoneById(zoneId);
            currentZones = new ArrayList<ZoneVO>();
            loadCurrentZone(zone, currentZones, appCacheHolder);
        } catch (Exception e) {
            log.error(e + "");
        }
        return Results.ftl("/blackhole/pages/zone/manager-index.ftl");
    }

    /**
     * 递归获取当前区域的层级
     *
     * @param zone
     * @param currentZones
     * @param appCacheHolder
     * @throws ExecutionException
     */
    private static void loadCurrentZone(ZoneVO zone, List<ZoneVO> currentZones, AppCacheHolder appCacheHolder) throws ExecutionException {
        currentZones.add(zone);
        String preId = zone.getParentId();
        if (preId != null) {
            ZoneVO preZone = appCacheHolder.loadZone(preId);
            loadCurrentZone(preZone, currentZones, appCacheHolder);
        }
    }

    /**
     * 获取当前站点id
     *
     * @return 当前站点ID
     */
    public static String getSiteId() {
        return Sessions.createByAction().currentLogicGroup().getSite().getSiteId();
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

    public List<ZoneVO> getZones() {
        return zones;
    }

    public void setZones(List<ZoneVO> zones) {
        this.zones = zones;
    }

    public ZoneVO getZone() {
        return zone;
    }

    public void setZone(ZoneVO zone) {
        this.zone = zone;
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

    public String getResourcesPath() {
        return resourcesPath;
    }

    public void setResourcesPath(String resourcesPath) {
        this.resourcesPath = resourcesPath;
    }
}
