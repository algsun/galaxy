package com.microwise.phoenix.action.uma;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.MessageAction;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.phoenix.bean.po.UserDistributionInfo;
import com.microwise.phoenix.service.UserDistributionService;
import com.microwise.phoenix.sys.Phoenix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 区域活动分布
 *
 * @author li.jianfei
 * @date 2013-07-09
 */
@Beans.Action
@Phoenix
public class UserDistributionAction extends MessageAction {

    public static final Logger log = LoggerFactory.getLogger(UserDistributionAction.class);
    public static final String _pagePath = "../uma/user-distribution.ftl";

    /**
     * 人员分布情况 Service
     */
    @Autowired
    private UserDistributionService userDistributionService;

    //input
    /**
     * 日期类型
     */
    private int dateType = Constants.FIND_TYPE_MONTH; //时间类型  yyyy-MM

    /**
     * 日期
     */
    private Date date;

    //output
    /**
     * 人员分布情况统计信息
     */
    private UserDistributionInfo userDistribution;

    private String siteId = Sessions.createByAction().currentSiteId();

    @Route("/phoenix/uma/userDistribution")
    public String view() {
        getCurrentDate();  //获取当前时间
        return Results.ftl("/phoenix/pages/index/layout");
    }

    @Route("/phoenix/uma/chart.json")
    public String execute() {
        getCurrentDate(); //获取当前时间
        try {
            userDistribution = userDistributionService.getUserDistributionInfo(siteId, date, dateType);
        } catch (Exception e) {
            log.error("人员分布情况", e);
        }
        log("人员管理", "区域活动分布");
        return Results.json().root("userDistribution").done();


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

    public UserDistributionInfo getUserDistribution() {
        return userDistribution;
    }

    public void setUserDistribution(UserDistributionInfo userDistribution) {
        this.userDistribution = userDistribution;
    }
}
