package com.microwise.phoenix.action.orion;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.gson.Gson;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.phoenix.bean.vo.RelicBasicStats;
import com.microwise.phoenix.bean.vo.healthCheck.RelicPieData;
import com.microwise.phoenix.service.RelicStatService;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.sys.PhoenixLoggerAction;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @author li.jianfei
 * @date 2013-7-16
 * @check @duan.qixin 2013-7-18 #4574
 */
@Beans.Action
@Phoenix
public class PropertyPercentagePieChartAction extends PhoenixLoggerAction {

    public static final Logger log = LoggerFactory.getLogger(PropertyPercentagePieChartAction.class);
    public static final String _pagePath = "../orion/property-percentage-pie-chart.ftl";

    /**
     * 统计文物各属性 Service
     */
    @Autowired
    private RelicStatService relicStatService;

    // Output
    /**
     * 统计结果，Json字符串
     */
    private String relicBasicInfo;

    /**
     * 文物总数
     */
    private int relicCount;

    /**
     * 当前年
     */
    private Date date;

    @Route("/phoenix/orion/property-pie-chart")
    public String pieChart() {
        String siteId = Sessions.createByAction().currentSiteId();
        RelicBasicStats relicBasicStats = null;

        date = DateTime.now().withMillis(new Date().getTime()).toDate();

        try {
            // 获取当前站点下文物统计信息
            relicBasicStats = relicStatService.findRelicStatData(siteId);

            // 获取文物总数
            relicCount = relicStatService.findRelicSum(siteId);

            if (relicBasicStats != null && relicBasicStats.getZoneStat() != null) {
                RelicPieData rpd = relicBasicStats.getZoneStat();
                if (rpd.isHasData()) {
                    for (List<Object> ol : rpd.getPieData()) {
                        if (ol.get(0) == null || "".equals(ol.get(0))) {
                            ol.set(0, "未分配区域");
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("各属性饼状图", e);
        }
        log("数据分析", "各属性饼状图");
        Gson gson = new Gson();
        relicBasicInfo = gson.toJson(relicBasicStats);
        return Results.ftl("/phoenix/pages/index/layout");
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public String getRelicBasicInfo() {
        return relicBasicInfo;
    }

    public void setRelicBasicInfo(String relicBasicInfo) {
        this.relicBasicInfo = relicBasicInfo;
    }

    public int getRelicCount() {
        return relicCount;
    }

    public void setRelicCount(int relicCount) {
        this.relicCount = relicCount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

