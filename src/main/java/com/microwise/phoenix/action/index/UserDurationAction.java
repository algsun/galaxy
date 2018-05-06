package com.microwise.phoenix.action.index;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.phoenix.bean.vo.UserLogLength;
import com.microwise.phoenix.service.UserLoginStatService;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.sys.PhoenixLoggerAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 用户登录时长统计
 *
 * @author duan.qixin
 * @date 2013-08-12
 * @check @xu.baoji 2013-8-12 #5009
 */
@Beans.Action
@Phoenix
public class UserDurationAction extends PhoenixLoggerAction {
    public static final Logger log = LoggerFactory.getLogger(UserDurationAction.class);

    public static final String _pagePath = "../index/user-duration.ftl";

    @Autowired
    private UserLoginStatService service;

    //input
    /**
     * 时间类型（默认为：年）
     */
    private int dateType = Constants.FIND_TYPE_MONTH;

    /**
     * 下拉选框默认显示的数量
     */
    private int size = 10;

    /**
     * 初始化的时间
     */
    private Date date;

    //output
    /**
     * 结论
     */
    private String conclusion;
    /**
     * 人员集
     */
    private String userNames = "";
    /**
     * 登录时长集
     */
    private String durations = "";

    //站点编号
    private int LogicGroupId = Sessions.createByAction().currentLogicGroup().getId();

    @Route("/phoenix/index/duration")
    public String view() {
        if (date == null) {
            date = new Date();
        }
        List<UserLogLength> ullList = service.findUserLonLength(LogicGroupId, date, dateType, size);
        if (ullList != null && ullList.size() > 0) {
            //组装数据
            dataToJson(ullList);
            //结论
            conclusion(ullList);
        }
        log("数据分析", "登录时长");
        return Results.ftl("/phoenix/pages/index/layout");
    }

    /**
     * 组装数据
     *
     * @param ullList 登录时长list
     * @author liuzhu
     * @date 2013-09-12
     */
    private void dataToJson(List<UserLogLength> ullList) {
        for (UserLogLength ul : ullList) {
            if (ul != null) {
                userNames += "'" + ul.getUserName() + "',";
                durations += ul.getLogLength() + ",";
            }
        }
        if (userNames.length() > 0) {
            userNames = "[" + userNames.substring(0, userNames.length() - 1) + "]";
        }
        if (durations.length() > 0) {
            durations = "[" + durations.substring(0, durations.length() - 1) + "]";
        }
    }

    /**
     * 登录时长结论
     *
     * @param ullList 登录时长数据
     * @author liuzhu
     * @date 2013-09-12
     */
    private void conclusion(List<UserLogLength> ullList) {
        //日期格式化
        if (dateType == Constants.FIND_TYPE_YEAR) {
            conclusion = new SimpleDateFormat("yyyy年").format(date);
        } else {
            conclusion = new SimpleDateFormat("yyyy年MM月").format(date);
        }
        conclusion += "登录系统时间最长的人员是：<strong>" + ullList.get(0).getUserName() + "</strong>共登录<strong>" + ullList.get(0).getLogLength() + "</strong>小时。";
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDateType() {
        return dateType;
    }

    public void setDateType(int dateType) {
        this.dateType = dateType;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getUserNames() {
        return userNames;
    }

    public void setUserNames(String userNames) {
        this.userNames = userNames;
    }

    public String getDurations() {
        return durations;
    }

    public void setDurations(String durations) {
        this.durations = durations;
    }
}