package com.microwise.phoenix.action.uma;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.LoggingAction;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.phoenix.bean.vo.UserStopOver;
import com.microwise.phoenix.service.UserStopOverService;
import com.microwise.phoenix.sys.Phoenix;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 人员停留情况
 *
 * @author li.jianfei
 * @date 2013-07-11
 * @check @duan.qixin 2013-7-18 #4574
 */
@Beans.Action
@Phoenix
public class UserStopoverAction extends LoggingAction {

    public static final Logger log = LoggerFactory.getLogger(UserStopoverAction.class);
    public static final String _pagePath = "../uma/user-stopover.ftl";

    /**
     * 人员停留情况 Service
     */
    @Autowired
    private UserStopOverService userStopOverService;

    // Input && Output
    /**
     * 日期类型
     */
    private int dateType = Constants.FIND_TYPE_MONTH;

    /**
     * 日期
     */
    private String date;

    // Output
    /**
     * 人员停留情况统计信息
     */
    private UserStopOver userStopover;


    @Route("/phoenix/uma/userStopover")
    public String view() {
        getCurrentDate(); //获取当前时间
        ActionMessage.createByAction().consume();
        return Results.ftl("/phoenix/pages/index/layout");
    }

    @Route("/phoenix/uma/userStopoverChart.json")
    public String execute() {
        getCurrentDate(); //获取当前时间
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            userStopover = userStopOverService.findUserStopOver(siteId, getDate(), dateType);
        } catch (Exception e) {
            log.error("人员停留情况", e);
        }
        log("人员管理", "人员停留情况");
        return Results.json().root("userStopover").done();
    }

    /**
     * 格式化当前时间
     *
     * @author 刘柱
     * @date 2013-09-10
     */
    private void getCurrentDate() {
        if (date == null) {
            date = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
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
        return DateTime.parse(date).toDate();
    }

    public void setDate(String date) {
        this.date = date;
    }

    public UserStopOver getUserStopover() {
        return userStopover;
    }

    public void setUserStopover(UserStopOver userStopover) {
        this.userStopover = userStopover;
    }
}
