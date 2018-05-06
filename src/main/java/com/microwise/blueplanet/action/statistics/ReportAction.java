package com.microwise.blueplanet.action.statistics;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.io.Resources;
import com.microwise.blackhole.bean.Subscribe;
import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.SubscribeService;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.ReportVO;
import com.microwise.blueplanet.service.ReportService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表 EventAction
 *
 * @author li.jianfei
 * @date 2013-06-13
 * @check @gaohui #4234 2013-06-20
 */
@Beans.Action
@Blueplanet
public class ReportAction extends BlueplanetLoggerAction {

    private static final Logger log = LoggerFactory.getLogger(ReportAction.class);

    /**
     * 报表模版路径
     */
    private static final String EMAIL_TEMPLATE = "common/email/report-template.ftl";

    /**
     * 周报表
     */
    private static final int WEEK_REPORT = 1;

    /**
     * 页面内容
     */
    private static final String _pagePath = "../../statistics/report.ftl";

    /**
     * 报表 Service
     */
    @Autowired
    private ReportService reportService;

    /**
     * 报表订阅 Service
     */
    @Autowired
    private SubscribeService subscribeService;

    // input
    /**
     * 日期类型
     */
    private int dateType;

    /**
     * 日期
     */
    private String date;

    /**
     * 选择周时的实际日期
     */
    private String week;

    /**
     * WeekOfYear
     */
    private String weekOfYear;

    /**
     * 订阅类型
     */
    private int subscribeType;

    /**
     * 是否订阅成功
     */
    private boolean success = true;

    /**
     * 用户是否订阅周报表
     */
    private boolean weekReport;

    /**
     * 报表数据集合
     */
    private List<ReportVO> reportVOList;

    @Route("/blueplanet/statistics/report")
    public String execute() {
        // 初始化日期
        if (date == null) {
            date = DateFormatUtils.format(DateUtils.addDays(new Date(), -1), "yyyy-MM-dd");
        }
        if (week == null) {
            week = date;
        }

        // 获取站点ID
        String siteId = Sessions.createByAction().currentSiteId();

        // 获取用户ID
        int userId = Sessions.createByAction().currentUser().getId();

        // 检查用户是否订阅报表
        List<Subscribe> subscribeList = subscribeService.findSubscribeByUser(siteId, userId);
        for (Subscribe subscribe : subscribeList) {
            if (subscribe.getSubscribeType() == WEEK_REPORT) {
                weekReport = true;
            }
        }

        // 默认日期类型是日
        dateType = dateType == 0 ? 3 : dateType;


        Date queryDate;
        if (dateType == 4) {    // 周
            weekOfYear = date;
            queryDate = DateTime.parse(week).toDate();
        } else {// 年月日
            queryDate = DateTime.parse(date).toDate();
        }
        reportVOList = reportService.findReportInfo(siteId, queryDate, dateType);
        log("统计分析", "查询报表");
        return Results.ftl("/blueplanet/pages/zone/manager/layout.ftl");
    }

    @Route("/blueplanet/statistics/report/addSubscribe.json")
    public String addSubscribe() {
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            User user = Sessions.createByAction().currentUser();
            Subscribe subscribe = new Subscribe();
            subscribe.setSubscribeType(subscribeType);
            subscribe.setUser(user);
            subscribe.setSiteId(siteId);
            subscribeService.addSubscribe(subscribe);
            log("统计分析", "订阅周报表");
        } catch (Exception e) {
            success = false;
            log.error("订阅出错", e);
        }
        return Results.json().root("success").done();
    }

    @Route("/blueplanet/statistics/report/deleteSubscribe.json")
    public String deleteSubsrcibe() {
        try {
            int userId = Sessions.createByAction().currentUser().getId();
            String siteId = Sessions.createByAction().currentSiteId();
            subscribeService.deleteSubscribe(userId, subscribeType, siteId,null);
            log("统计分析", "取消周报表");
        } catch (Exception e) {
            success = false;
            log.error("取消订阅出错", e);
        }
        return Results.json().root("success").done();
    }

    /**
     * 组织报表模版数据
     *
     * @param siteName     站点名称
     * @param startTime    开始时间
     * @param endTime      结束时间
     * @param reportVOList 报表数据
     * @return 报表模版（html）
     */
    public static String reportData(String siteName, String startTime, String endTime, List<ReportVO> reportVOList) {

        String templateString = null;
        try {
            // 创建和调整配置
            Configuration cfg = new Configuration();
            String path = URLDecoder.decode(Resources.getResource(EMAIL_TEMPLATE).getFile(), "utf-8");
            cfg.setDirectoryForTemplateLoading(new File(path).getParentFile());
            cfg.setDefaultEncoding("utf-8");
            cfg.setObjectWrapper(new DefaultObjectWrapper());

            // 获取或创建模板
            Template template = cfg.getTemplate("report-template.ftl");

            // 创建数据模型
            Map<String, Object> root = new HashMap<String, Object>();
            root.put("siteName", siteName);
            root.put("startTime", startTime);
            root.put("endTime", endTime);
            root.put("reportVOList", reportVOList);

            // 将模板和数据模型合并
            StringWriter out = new StringWriter();
            template.process(root, out);

            templateString = out.toString();
        } catch (IOException e) {
            log.error("生成报表模版", e);
        } catch (TemplateException e) {
            log.error("生成报表模版", e);
        }
        return templateString;
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
        return DateTime.parse(date).toDate();
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Date getWeek() {
        return DateTime.parse(week).toDate();
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getWeekOfYear() {
        return weekOfYear;
    }

    public int getSubscribeType() {
        return subscribeType;
    }

    public void setSubscribeType(int subscribeType) {
        this.subscribeType = subscribeType;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isWeekReport() {
        return weekReport;
    }

    public void setWeekReport(boolean weekReport) {
        this.weekReport = weekReport;
    }

    public List<ReportVO> getReportVOList() {
        return reportVOList;
    }

    public void setReportVOList(List<ReportVO> reportVOList) {
        this.reportVOList = reportVOList;
    }

}
