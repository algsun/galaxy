package com.microwise.blueplanet.action.zone;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.ContentBase;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.AlarmRecordVO;
import com.microwise.blueplanet.bean.vo.LocationDataVO;
import com.microwise.blueplanet.service.AlarmService;
import com.microwise.blueplanet.service.ThresholdService;
import com.microwise.blueplanet.service.ZoneService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 区域概览
 *
 * @author liuzhu
 * @date 13-11-1
 */
@Beans.Action
@Blueplanet
@ContentBase("/blueplanet/pages/zone")
public class IndexAction extends BlueplanetLoggerAction {
    public static final Logger log = LoggerFactory.getLogger(IndexAction.class);
    // 内容页面
    private static final String _pagePath = "index.ftl";

    /**
     * 区域service
     */
    @Autowired
    private ZoneService zoneService;


    @Autowired
    private AlarmService alarmService;

    /**
     * 区域id
     */
    private String zoneId;

    /**
     * 区域概览左边的数据
     */
    private List<LocationDataVO> findLocationFiveDay;

    /**
     * 区域概览右边的数据
     */
    private Map<LocationDataVO, List<LocationDataVO>> findLocationRealTimeAvg;

    /**
     * 报警记录
     */
    private List<AlarmRecordVO> alarmRecords;

    /**
     * 区域概览
     */
    @Route("/blueplanet/zone/{zoneId}")
    public String show() {
        try {
            //站点id
            String siteId = Sessions.createByAction().currentSiteId();
            //区域概览左边的数据
            findLocationFiveDay = zoneService.findZoneChartData(zoneId, siteId);
            //区域概览右边的数据
            findLocationRealTimeAvg = zoneService.findZoneRealTimeData(siteId, zoneId);
            alarmRecords = alarmService.findAlarmRecordList(zoneId);
        } catch (Exception e) {
            log.error("区域概览", e);
        }
        log("区域概览", "环境监控");
        return Results.ftl("layout-b4.ftl");
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

    public List<LocationDataVO> getFindLocationFiveDay() {
        return findLocationFiveDay;
    }

    public void setFindLocationFiveDay(List<LocationDataVO> findLocationFiveDay) {
        this.findLocationFiveDay = findLocationFiveDay;
    }

    public Map<LocationDataVO, List<LocationDataVO>> getFindLocationRealTimeAvg() {
        return findLocationRealTimeAvg;
    }

    public void setFindLocationRealTimeAvg(Map<LocationDataVO, List<LocationDataVO>> findLocationRealTimeAvg) {
        this.findLocationRealTimeAvg = findLocationRealTimeAvg;
    }

    public List<AlarmRecordVO> getAlarmRecords() {
        return alarmRecords;
    }

    public void setAlarmRecords(List<AlarmRecordVO> alarmRecords) {
        this.alarmRecords = alarmRecords;
    }
}
