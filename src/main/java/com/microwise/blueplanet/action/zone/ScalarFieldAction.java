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
 * @author xu.baoji
 * @date 2013-10-23
 * @check @gaohui 2013-11-06 #6418
 */
@Beans.Action
@Blueplanet
public class ScalarFieldAction {

    public static final Logger log = LoggerFactory.getLogger(ScalarFieldAction.class);

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

    // out
    /**
     * 监测指标 列表
     */
    private List<SensorinfoVO> sensorPhysicals;

    /**
     * 环境监控资源路径
     */
    private String resourcesPath = Sessions.createByAction().getGalaxyResourceURL() + "/blueplanet/images/zonePlanImage";

    /**
     * 区域实体
     */
    private ZoneVO zone;


    @Route("/blueplanet/zone/{zoneId}/scalar-field")
    public String view() {
        try {
            date = new Date();
            zone = zoneService.findZoneById(zoneId);
        } catch (Exception e) {
            log.error("", e);
        }
        return Results.ftl("/blueplanet/pages/zone/layout.ftl");
    }

    @Route(value = "/blueplanet/zone/{zoneId}/scalar-field-data.json")
    public String fieldData() {
        Map<String, Object> paramData = new HashMap<String, Object>();
        try {
            List<LocationOnceDataVO> scalarFieldData = zoneService.findLocationHistoryData(zoneId, sensorPhysicalId, date, DELTA_MINUTE);
            SensorinfoVO sensorinfoVO = sensorinfoService.findByPhysicalid(sensorPhysicalId);
            paramData.put("data", scalarFieldData);
            paramData.put("sensorinfo", sensorinfoVO);
        } catch (Exception e) {
            log.error("", e);
        }
        return Results.json().asRoot(paramData).done();
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

    public List<SensorinfoVO> getSensorPhysicals() {
        return sensorPhysicals;
    }

    public void setSensorPhysicals(List<SensorinfoVO> sensorPhysicals) {
        this.sensorPhysicals = sensorPhysicals;
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
