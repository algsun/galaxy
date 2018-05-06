package com.microwise.phoenix.action.uma;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.MessageAction;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.phoenix.bean.po.uma.ActionStat;
import com.microwise.phoenix.service.ActionStatService;
import com.microwise.phoenix.sys.Phoenix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 人员管理：规则出发频率
 *
 * @author duan.qixin
 * @date 2013-07-17
 * @check @xu.baoji 2013年7月18日 #4581
 */
@Beans.Action
@Phoenix
public class UserActionStatAction extends MessageAction {

    public static final Logger log = LoggerFactory.getLogger(UserActionStatAction.class);

    public static final String _pagePath = "../uma/user-action-stat.ftl";

    @Autowired
    private ActionStatService actionStatService;

    //input
    private int dateType = Constants.FIND_TYPE_MONTH;  //时间类型 yyyy
    private Date date;

    //output
    /**
     * 规则名集合列表
     */
    private String actionNames = "";
    /**
     * 规则数据
     */
    private String countData = "";
    /**
     * 结论
     */
    private String backup;

    //获得siteId 当前站点编号
    private String siteId = Sessions.createByAction().currentSiteId();

    @Route("/phoenix/uma/userActionStat")
    public String view() {
        if (date == null) {
            date = new Date();
        }
        //日期格式化
        if (dateType == Constants.FIND_TYPE_YEAR) {
            backup = new SimpleDateFormat("yyyy年").format(date);
        } else {
            backup = new SimpleDateFormat("yyyy年MM月").format(date);
        }
        List<ActionStat> actionStatList = actionStatService.findActionStat(siteId, date, dateType);

        //JSON数据装配
        objectToJson(actionStatList);
        log("人员管理", "规则出发频率");
        return Results.ftl("/phoenix/pages/index/layout");
    }

    /**
     * 组装json数据
     *
     * @author 刘柱
     * @date 2013-09-10
     */
    public void objectToJson(List<ActionStat> objList) {
        if (objList != null && objList.size() > 0) {
            backup += "触发最频繁的规则是：" + objList.get(0).getActionName() + "。";
            for (ActionStat as : objList) {
                actionNames += "'" + as.getActionName() + "',";
                countData += as.getActionCount() + ",";
            }
        }
        if (actionNames.length() > 0) {
            actionNames = "[" + actionNames.substring(0, actionNames.length() - 1) + "]";
        }
        if (countData.length() > 0) {
            countData = "[" + countData.substring(0, countData.length() - 1) + "]";
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

    public String getActionNames() {
        return actionNames;
    }

    public void setActionNames(String actionNames) {
        this.actionNames = actionNames;
    }

    public String getCountData() {
        return countData;
    }

    public void setCountData(String countData) {
        this.countData = countData;
    }

    public String getBackup() {
        return backup;
    }

    public void setBackup(String backup) {
        this.backup = backup;
    }
}
