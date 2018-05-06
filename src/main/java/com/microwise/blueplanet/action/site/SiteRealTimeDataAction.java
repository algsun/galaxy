package com.microwise.blueplanet.action.site;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Strings;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.RealtimeDataVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.service.SiteService;
import com.microwise.blueplanet.sys.AppCacheHolder;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 站点实时数据跳转
 *
 * @author Wang yunlong
 * @time 13-1-23 下午3:59
 * @check @gaohui 2013-01-29 #1297
 * @check @gaohui 2013-02-25 #1320
 */
@Beans.Action
@Blueplanet
public class SiteRealTimeDataAction {
    public static final Logger log = LoggerFactory.getLogger(SensorinfoOfSiteAction.class);

    /**
     * 内容页面
     */
    private static final String _pagePath = "realtime-data.ftl";

    @Autowired
    private AppCacheHolder appCacheHolder;

    @Autowired
    private SiteService siteService;

    // in
    /**
     * 是否过滤显示监测指标过滤
     */
    private int showFilters = 0;

    //被 选中的 监测 指标标识
    private Integer[] sensorPhysicalIds;

    // out
    // 监测指标
    private List<SensorinfoVO> sensorinfoes = new ArrayList<SensorinfoVO>();

    // 实时数据
    private List<RealtimeDataVO> data;


    @Route("/blueplanet/site/realtime-data")
    public String execute() {
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            if (!Strings.isNullOrEmpty(siteId)) {
                data = siteService.findRealtimeDataLocation(siteId);
                sensorinfoes = appCacheHolder.loadAvailableSensorinfoOfSite(siteId);
            }
        } catch (ExecutionException e) {
            log.error("站点下监测指标", e);
        }
        return Results.ftl("/blueplanet/pages/site/layout.ftl");
    }

    @Route(value = "/blueplanet/site/realtime-data-filter", params = {"sensorPhysicalIds"})
    public String realTimeDataFilter() {
        String siteId = Sessions.createByAction().currentSiteId();
        if (!Strings.isNullOrEmpty(siteId)) {
            try {
                data = siteService.findRealtimeDataLocation(siteId, Arrays.asList(sensorPhysicalIds));
                sensorinfoes = appCacheHolder.loadAvailableSensorinfoOfSite(siteId);
            } catch (ExecutionException e) {
                log.error("站点下监测指标", e);
            }
        }
        return Results.ftl("/blueplanet/pages/site/layout.ftl");
    }

    public static String getSiteId() {
        return Sessions.createByAction().currentLogicGroup().getSite().getSiteId();
    }

    public String get_pagePath() {
        return _pagePath;
    }

    public List<RealtimeDataVO> getData() {
        return data;
    }

    public void setData(List<RealtimeDataVO> data) {
        this.data = data;
    }

    public List<SensorinfoVO> getSensorinfoes() {
        return sensorinfoes;
    }

    public void setSensorinfoes(List<SensorinfoVO> sensorinfoes) {
        this.sensorinfoes = sensorinfoes;
    }

    public int getShowFilters() {
        return showFilters;
    }

    public void setShowFilters(int showFilters) {
        this.showFilters = showFilters;
    }

    public Integer[] getSensorPhysicalIds() {
        return sensorPhysicalIds;
    }

    public void setSensorPhysicalIds(Integer[] sensorPhysicalIds) {
        this.sensorPhysicalIds = sensorPhysicalIds;
    }
}
