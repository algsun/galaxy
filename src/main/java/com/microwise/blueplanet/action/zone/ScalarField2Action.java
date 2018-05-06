package com.microwise.blueplanet.action.zone;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.LocationOnceDataVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.bean.vo.ZoneVO;
import com.microwise.blueplanet.service.SensorinfoService;
import com.microwise.blueplanet.service.ZoneService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.ActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  标量场 action
 * </pre>
 *
 * @author xu.yuexi
 * @date 2014-10-21
 */
@Beans.Action
@Blueplanet
public class ScalarField2Action {

    public static final Logger log = LoggerFactory.getLogger(ScalarField2Action.class);

    /**
     * 监测指标场原始数据 时间范围 增减量
     */
    public static final int DELTA_MINUTE = 20;
    // 内容页面
    private final String _pagePath = "scalar-field.ftl";

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private SensorinfoService sensorinfoService;

    //input
    /**
     * 区域id
     */
    private String zoneId;
    /**
     * 监测指标
     */
    private Integer sensorPhysicalId;

    /**
     * 时间参数
     */
    private Date date;


    /**
     * 环境监控资源路径
     */
    private String resourcesPath = Sessions.createByAction().getGalaxyResourceURL() + "/blueplanet/images/zonePlanImage";

    /**
     * 区域实体
     */
    private ZoneVO zone;


    @Route(value = "/blueplanet/zone/scalar-field2", params = {"date", "sensorPhysicalId", "zoneId"})
    public String view() {
        try {
            date = getDate();
            sensorPhysicalId = getSensorPhysicalId();
            zone = zoneService.findZoneById(zoneId);
        } catch (Exception e) {
            log.error("", e);
        }
        return Results.ftl("/blueplanet/pages/zone/scalar-field2.ftl");
    }

    @Route(value = "/blueplanet/zone/scalar-field2.json")
    public String fieldData() {
        try {
            List<LocationOnceDataVO> scalarFieldData = zoneService.findLocationHistoryData(zoneId, sensorPhysicalId, date, DELTA_MINUTE);
            SensorinfoVO sensorinfoVO = sensorinfoService.findByPhysicalid(sensorPhysicalId);
            Map<String, Object> paramData = new HashMap<String, Object>();
            paramData.put("data", scalarFieldData);
            paramData.put("sensorinfo", sensorinfoVO);
            ActionContext.getContext().put("paramData", paramData);
        } catch (Exception e) {
            log.error("", e);
        }
        return Results.json().root("paramData").done();
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

    public ZoneService getZoneService() {
        return zoneService;
    }

    public void setZoneService(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    public Integer getSensorPhysicalId() {
        return sensorPhysicalId;
    }

    public void setSensorPhysicalId(Integer sensorPhysicalId) {
        this.sensorPhysicalId = sensorPhysicalId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getResourcesPath() {
        return resourcesPath;
    }

    public void setResourcesPath(String resourcesPath) {
        this.resourcesPath = resourcesPath;
    }

    public ZoneVO getZone() {
        return zone;
    }

    public void setZone(ZoneVO zone) {
        this.zone = zone;
    }

    public static Logger getLog() {
        return log;
    }
}
