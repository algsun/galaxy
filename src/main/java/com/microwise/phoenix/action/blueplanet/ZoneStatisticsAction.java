package com.microwise.phoenix.action.blueplanet;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.phoenix.bean.vo.ZoneStatistics;
import com.microwise.phoenix.service.ZoneStatisticsService;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.sys.PhoenixLoggerAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 环境监控： 区域环境统计
 *
 * @author li.jianfei
 * @date 2013-7-4
 * @check duan.qixin 2013-7-8  #4409
 */
@Beans.Action
@Phoenix
public class ZoneStatisticsAction extends PhoenixLoggerAction {


    public static final Logger log = LoggerFactory.getLogger(ZoneStatisticsAction.class);
    public static final String _pagePath = "../blueplanet/zone-statistics.ftl";

    @Autowired
    private ZoneStatisticsService zoneStatisticsService;


    /**
     * 时间类型
     * 1-年  2-月
     */
    private int dateType;

    /**
     * 日期
     */
    private Date date;

    /**
     * 区域ID
     */
    private String zoneId;

    /**
     * 区域名称
     */
    private String zoneName;

    /**
     * 区域监测指标统计数据集合
     */
    private List<ZoneStatistics> zoneStatisticsList;

    @Route("/phoenix/blueplanet/zoneStatistics")
    public String view() {
        dateType = Constants.FIND_TYPE_MONTH;
        getCurrentDate();// 获取当前时间
        log("数据分析", "区域环境统计");
        return Results.ftl("/phoenix/pages/index/layout.ftl");
    }


    @Route("/phoenix/index/chart.json")
    public String execute() {
        try {
            getCurrentDate();// 获取当前时间
            zoneStatisticsList = zoneStatisticsService.findZoneStatistics(zoneId, getDate(), dateType);
            log("数据分析", "区域环境统计");
        } catch (Exception e) {
            log.error("区域环境统计", e);
        }
        return Results.json().root("zoneStatisticsList").done();
    }

    /**
     * 获取当前时间
     *
     * @author 刘柱
     * @date 2013-09-10
     */
    private void getCurrentDate() {
        if (date == null) {
            date = new Date();
        }
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public int getDateType() {
        return dateType;
    }

    public void setDateType(int dateType) {
        this.dateType = dateType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public List<ZoneStatistics> getZoneStatisticsList() {
        return zoneStatisticsList;
    }

    public void setZoneStatisticsList(List<ZoneStatistics> zoneStatisticsList) {
        this.zoneStatisticsList = zoneStatisticsList;
    }
}

