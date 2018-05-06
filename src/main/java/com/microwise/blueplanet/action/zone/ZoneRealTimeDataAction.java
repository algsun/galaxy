package com.microwise.blueplanet.action.zone;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.action.site.SensorinfoOfSiteAction;
import com.microwise.blueplanet.bean.vo.RealtimeDataVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.bean.vo.ZoneVO;
import com.microwise.blueplanet.service.ZoneService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.ActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * 区域实施数据 无监测指标过滤
 *
 * @author Wang yunlong
 * @date 13-1-18 上午9:29
 * @check @gaohui 2013-01-29 #1297
 * @check @gaohui 2013-02-25 #1400
 */
@Beans.Action
@Blueplanet
public class ZoneRealTimeDataAction {

    public static final Logger log = LoggerFactory.getLogger(SensorinfoOfSiteAction.class);

    /**
     * 内容页面
     */
    private static final String _pagePath = "realtime-data2.ftl";

    @Autowired
    private ZoneService zoneService;

    //input
    /**
     * 区域id
     */
    private String zoneId;

    // in
    /**
     * 是否过滤显示监测指标过滤
     */
    private int showFilters = 0;

    //被 选中的 监测 指标标识
    private Integer[] sensorPhysicalIds;

    //output
    /**
     * 当前区域
     */
    private ZoneVO zone;

    private List<RealtimeDataVO> data;

    private List<SensorinfoVO> sensorinfoes;

    @Route("/blueplanet/zone/{zoneId}/realtime-data")
    public String execute() {
        zone = zoneService.findZoneById(zoneId);
        sensorinfoes = zoneService.findSensorinfo(zoneId);
        data = zoneService.findRealtimeDataLocation(zoneId);
        return Results.ftl("/blueplanet/pages/zone/layout.ftl");
    }


    @Route(value ="/blueplanet/zone/{zoneId}/realtime-data-filter", params = {"sensorPhysicalIds"})
    public String realtimeDataFilter() {
        try {
            zone = zoneService.findZoneById(zoneId);
            sensorinfoes = zoneService.findSensorinfo(zoneId);
            data = zoneService.findRealtimeDataLocation(zoneId,Arrays.asList(sensorPhysicalIds));
        } catch (Exception e){
            log.error("区域实时数据",e);
        }
        return Results.ftl("/blueplanet/pages/zone/layout.ftl");
    }

    /**
     * 区域实时数据 ajax json
     * <p/>
     * TODO 与 ZoneFilterRealTimeDataAction 合并 @gaohui 2013-02-25
     */
    @Route("/blueplanet/zone/{zoneId}/realtime-data.json")
    public String noFilterRealtimeDataJson() {
        List<RealtimeDataVO> realtimeData = zoneService.findRealtimeDataLocation(zoneId);
        ActionContext.getContext().put("data", realtimeData);
        return Results.json().root("data").done();
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

    public void setZone(ZoneVO zone) {
        this.zone = zone;
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
