package com.microwise.phoenix.action.index;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.gson.Gson;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.DateTimeUtil;
import com.microwise.phoenix.service.UserStatService;
import com.microwise.phoenix.sys.Phoenix;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户早晚时刻 统计 action
 *
 * @author xu.baoji
 * @date 2013.08.20
 * @check li.jianfei  2013.09.02 #5217
 */
@Beans.Action
@Phoenix
public class UserMorningAndEveningAction {
    public static final Logger log = LoggerFactory.getLogger(UserMorningAndEveningAction.class);

    public static final String _pagePath = "../index/user-morningAndEvening.ftl";

    @Autowired
    private UserStatService userStatService;

    // input
    /**
     * 查询日期类型
     */
    private int dateType = Constants.FIND_TYPE_YEAR;
    /**
     * 查询日期
     */
    private Date date;
    /**
     * 查询数量
     */
    private int dataSize = 10;

    // output
    // 图表数据 默认为 null 表示没有数据 前台页面 使用 null 来判断是否需要加载图表
    /**
     * morning 图表数据
     */
    private String morningChartData = "null";
    /**
     * morning 结论数据
     */
    private String morningConclusion;
    /**
     * evening 图表数据
     */
    private String eveningChartData = "null";
    /**
     * evening 结论数据
     */
    private String eveningConclusion;
    /**
     * 回显日期
     */
    private String fmtTime;

    // 站点编号
    private int logicGroupId = Sessions.createByAction().currentLogicGroup().getId();

    @Route("/phoenix/index/morningAndEvening")
    public String view() {
        try {
            if (date == null) {
                date = new Date();
            }
            // 日期格式化
            if (dateType == Constants.FIND_TYPE_YEAR) {
                fmtTime = new SimpleDateFormat("yyyy").format(date);
            } else {
                fmtTime = new SimpleDateFormat("yyyy-MM").format(date);
            }
            // morning 图表 数据
            Map<String, Integer> morningCharData = userStatService.findUserMorningAndEveningStat(
                    logicGroupId, date, dateType, false, dataSize);
            // morning 结论数据
            Map<String, Date> morningConclusion = userStatService.findMorningAndEveningUser(
                    logicGroupId, date, dateType, false);
            // 处理morning 数据
            if (morningCharData.size() > 0 && morningConclusion.size() > 0) {
                handleData(morningCharData, morningConclusion, false);
            }
            // evening 图表数据
            Map<String, Integer> eveningCharData = userStatService.findUserMorningAndEveningStat(
                    logicGroupId, date, dateType, true, dataSize);
            // evening 结论数据
            Map<String, Date> eveningConclusion = userStatService.findMorningAndEveningUser(
                    logicGroupId, date, dateType, true);
            // 处理evening 数据
            if (eveningCharData.size() > 0 && eveningConclusion.size() > 0) {
                handleData(eveningCharData, eveningConclusion, true);
            }
        } catch (Exception e) {
            log.error("早晚时刻统计action", e);
        }
        return Results.ftl("/phoenix/pages/index/layout");
    }

    /**
     * 处理数据
     *
     * @param chartData      图表数据
     * @param conclusionData 结论数据
     * @param isEvening      是否是 evening 数据
     * @author xu.baoji
     * @date 2013-8-20
     */
    private void handleData(Map<String, Integer> chartData, Map<String, Date> conclusionData,
                            boolean isEvening) {
        // 图表数据
        StringBuilder chartDatas = new StringBuilder();
        // 将 xy 轴数据转换为字符串并存放在 chartDatas 对象中
        List<String> chartXData = new ArrayList<String>(chartData.keySet());
        List<Integer> chartYData = new ArrayList<Integer>(chartData.values());
        chartDatas.append("{xd:");
        Gson gson = new Gson();
        chartDatas.append(gson.toJson(chartXData));
        chartDatas.append(",yd:");
        chartDatas.append(gson.toJson(chartYData));
        chartDatas.append("}");

        // 结论数据
        StringBuilder conclusion = new StringBuilder();
        String userName = new ArrayList<String>(conclusionData.keySet()).get(0);
        Date date = new ArrayList<Date>(conclusionData.values()).get(0);
        DateTime dateTime = new DateTime(date);
        // 处理结论数据
        conclusion.append(dateTime.getYear());
        conclusion.append("年");
        if (dateType == Constants.FIND_TYPE_MONTH) {
            conclusion.append(dateTime.getMonthOfYear());
            conclusion.append("月");
        }
        if (isEvening) {
            conclusion.append("最晚离开次数最多的人员是：");
        } else {
            conclusion.append("最早开始工作次数最多的人员是：");
        }
        conclusion.append(chartXData.get(0));
        conclusion.append(", 共 ");
        conclusion.append(chartYData.get(0));
        conclusion.append(" 次。");
        conclusion.append("<br/>");
        conclusion.append(DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD, date) + " ");
        conclusion.append(userName);
        String HHMM = DateTimeUtil.format(DateTimeUtil.HH_MM, date);
        if (isEvening) {
            conclusion.append(" 曾最晚在  ");
            conclusion.append(HHMM);
            conclusion.append(" 离开");
            eveningChartData = chartDatas.toString();
            eveningConclusion = conclusion.toString();
        } else {
            conclusion.append(" 曾最早在  ");
            conclusion.append(HHMM);
            conclusion.append(" 开始工作");
            morningChartData = chartDatas.toString();
            morningConclusion = conclusion.toString();
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

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public String getMorningChartData() {
        return morningChartData;
    }

    public void setMorningChartData(String morningChartData) {
        this.morningChartData = morningChartData;
    }

    public String getEveningChartData() {
        return eveningChartData;
    }

    public void setEveningChartData(String eveningChartData) {
        this.eveningChartData = eveningChartData;
    }

    public String getMorningConclusion() {
        return morningConclusion;
    }

    public void setMorningConclusion(String morningConclusion) {
        this.morningConclusion = morningConclusion;
    }

    public String getEveningConclusion() {
        return eveningConclusion;
    }

    public void setEveningConclusion(String eveningConclusion) {
        this.eveningConclusion = eveningConclusion;
    }

    public String getFmtTime() {
        return fmtTime;
    }

    public void setFmtTime(String fmtTime) {
        this.fmtTime = fmtTime;
    }
}
