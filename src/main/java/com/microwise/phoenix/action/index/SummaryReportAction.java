package com.microwise.phoenix.action.index;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.bean.Subscribe;
import com.microwise.blackhole.service.SubscribeService;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.phoenix.sys.Phoenix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 综合报告
 *
 * @author li.jianfei
 * @date 2013-07-25
 * @check @duan.qixin 2013年7月29日 #4733
 */
@Beans.Action
@Phoenix
public class SummaryReportAction extends SummaryReportHelper {


    public static final Logger log = LoggerFactory.getLogger(SummaryReportAction.class);
    public static final String _pagePath = "../index/summary-report.ftl";

    /**
     * 月报表常量
     */
    private static final int MONTH_REPORT = 2;

    /**
     * 报表订阅 Service
     */
    @Autowired
    private SubscribeService subscribeService;

    // Input
    /**
     * 日期类型
     */
    private int dateType = Constants.FIND_TYPE_MONTH;

    /**
     * 日期
     */
    private Date date;

    // Output
    /**
     * 是否已订阅月报表
     */
    private boolean monthReport;


    @Route("/phoenix/index/summaryReport")
    public String execute() {

        try {
            // 日期为空时赋予默认日期
            if (date == null) {
                date = new Date();
            }

            // 获取当前站点ID
            String siteId = Sessions.createByAction().currentSiteId();

            // 获取用户ID
            int userId = Sessions.createByAction().currentUser().getId();

            // 检查用户是否订阅报表
            List<Subscribe> subscribeList = subscribeService.findSubscribeByUser(siteId, userId);
            for (Subscribe subscribe : subscribeList) {
                if (subscribe.getSubscribeType() == MONTH_REPORT) {
                    monthReport = true;
                }
            }

            // 获取当前站点组ID
            int logicGroupId = Sessions.createByAction().currentLogicGroup().getId();

            // 获取统计信息
            getStatisticInfo(siteId, logicGroupId, dateType, date);
            log("数据分析", "综合报告");
        } catch (Exception e) {
            log.error("综合报告", e);
        }

        return Results.ftl("/phoenix/pages/index/layout.ftl");
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

    public boolean isMonthReport() {
        return monthReport;
    }

    public void setMonthReport(boolean monthReport) {
        this.monthReport = monthReport;
    }
}
