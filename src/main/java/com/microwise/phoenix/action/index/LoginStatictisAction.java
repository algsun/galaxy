package com.microwise.phoenix.action.index;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.gson.Gson;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.phoenix.bean.vo.UserLoginStat;
import com.microwise.phoenix.service.UserLoginStatService;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.sys.PhoenixLoggerAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 登录习惯统计
 *
 * @author li.jianfei
 * @date 2013-07-23
 * @check duan.qixin 2013年7月29日 #4668
 */
@Beans.Action
@Phoenix
public class LoginStatictisAction extends PhoenixLoggerAction {
    public static final Logger log = LoggerFactory.getLogger(LoginStatictisAction.class);

    public static final String _pagePath = "../index/login-statistics.ftl";

    /**
     * 用户登录习惯统计 Service
     */
    @Autowired
    private UserLoginStatService userLoginStatService;

    /**
     * 日期类型
     */
    private int dateType;

    /**
     * 日期
     */
    private Date date;

    /**
     * 用户登录习惯统计结果Json
     */
    private String userLoginStatJson;

    /**
     * 每周登录最多的一天
     * 0-6 表示周一到周日
     */
    private int maxInWeek;

    /**
     * 每天登录最多的时间段
     * 0-5 (凌晨，早上，上午，中午，下午，晚上)
     */
    private int maxInDay;


    @Route("/phoenix/index/loginStatistics")
    public String execute() {

        try {
            // 默认按月统计
            dateType = (dateType == 0 ? 2 : dateType);

            // 日期为空时赋予默认日期
            if (date == null) {
                date = new Date();
            }

            // 获取用户登录统计信息
            int logicGroupId = Sessions.createByAction().currentLogicGroup().getId();
            UserLoginStat userLoginStat = userLoginStatService.findUserLoginStat(logicGroupId, getDate(), dateType);

            // 获取一周中哪一天登录人数最多
            float max = Float.MIN_VALUE;
            if (userLoginStat.isHasData()) {
                for (int i = 0; i < userLoginStat.getWeekStat().size(); i++) {
                    if (userLoginStat.getWeekStat().get(i) == null) {
                        continue;
                    }
                    if (max < userLoginStat.getWeekStat().get(i)) {
                        max = userLoginStat.getWeekStat().get(i);
                        maxInWeek = i;
                    }
                }

                // 获取一天中哪个时段登录人数最多
                max = Float.MIN_VALUE;
                for (int i = 0; i < userLoginStat.getDayStat().size(); i++) {
                    if (userLoginStat.getDayStat().get(i) == null) {
                        continue;
                    }
                    if (max < userLoginStat.getDayStat().get(i)) {
                        max = userLoginStat.getDayStat().get(i);
                        maxInDay = i;
                    }
                }
            }
            Gson gson = new Gson();
            userLoginStatJson = gson.toJson(userLoginStat);
            log("数据分析", "登录习惯");
        } catch (Exception e) {
            log.error("用户登录习惯统计", e);
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

    public String getUserLoginStatJson() {
        return userLoginStatJson;
    }

    public void setUserLoginStatJson(String userLoginStatJson) {
        this.userLoginStatJson = userLoginStatJson;
    }

    public int getMaxInWeek() {
        return maxInWeek;
    }

    public void setMaxInWeek(int maxInWeek) {
        this.maxInWeek = maxInWeek;
    }

    public int getMaxInDay() {
        return maxInDay;
    }

    public void setMaxInDay(int maxInDay) {
        this.maxInDay = maxInDay;
    }
}
