package com.microwise.blueplanet.action.device.manage;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.bean.Site;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.action.site.SensorinfoOfSiteAction;
import com.microwise.blueplanet.bean.vo.RealtimeDataVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.service.SiteService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 站点实时数据跳转
 *
 * @author Wang yunlong
 * @time 13-1-23 下午3:59
 */
@Beans.Action
@Blueplanet
public class DeviceRealTimeDataAction {
    public static final Logger log = LoggerFactory.getLogger(SensorinfoOfSiteAction.class);

    /**
     * 内容页面
     */
    private static final String _pagePath = "realtime-data.ftl";

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

    @Route("/blueplanet/devices/realtime-data")
    public String execute() {
        Sessions sessions = Sessions.createByAction();
        LogicGroup logicGroup = sessions.currentLogicGroup();
        Site site = logicGroup.getSite();
        if (site != null) {
            try {
                data = siteService.findDeviceRealTimeData(site.getSiteId(),null);
                sensorinfoes = siteService.findDeviceSensorInfo(site.getSiteId());
            } catch (Exception e) {
                log.error("站点下监测指标", e);
            }
        }
        return Results.ftl("/blueplanet/pages/device/manage/device-manage-layout.ftl");
    }

    @Route(value = "/blueplanet/devices/realtime-data-filter", params = {"sensorPhysicalIds"})
    public String realTimeDataFilter() {
        Sessions sessions = Sessions.createByAction();
        LogicGroup logicGroup = sessions.currentLogicGroup();
        Site site = logicGroup.getSite();
        if (site != null) {
            try {
                data = siteService.findDeviceRealTimeData(site.getSiteId(), Arrays.asList(sensorPhysicalIds));
                sensorinfoes = siteService.findDeviceSensorInfo(site.getSiteId());
            } catch (Exception e) {
                log.error("站点下监测指标", e);
            }
        }
        return Results.ftl("/blueplanet/pages/device/manage/device-manage-layout.ftl");
    }

    @Route(value = "/blueplanet/devices/sensorinfoes.json")
    public String sensorinfos() {
        Sessions sessions = Sessions.createByAction();
        LogicGroup logicGroup = sessions.currentLogicGroup();
        Site site = logicGroup.getSite();
        if (site != null) {
            try {
                sensorinfoes = siteService.findDeviceSensorInfo(site.getSiteId());
            } catch (Exception e) {
                log.error("站点下监测指标", e);
            }
        }

        return Results.json().root("sensorinfoes").done();
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
