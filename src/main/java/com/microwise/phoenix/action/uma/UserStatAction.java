package com.microwise.phoenix.action.uma;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.MessageAction;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.phoenix.bean.po.uma.UserStat;
import com.microwise.phoenix.service.UserStatService;
import com.microwise.phoenix.sys.Phoenix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 人员活动频率
 *
 * @author duan.qixin
 * @date 2013-07-15
 * @check @xu.baoji 2013-7-18 #4558
 */
@Beans.Action
@Phoenix
public class UserStatAction extends MessageAction {
    public static final Logger log = LoggerFactory.getLogger(UserStatAction.class);

    public static final String _pagePath = "../uma/user-stat.ftl";

    @Autowired
    private UserStatService userStatService;

    //input
    private int dateType = Constants.FIND_TYPE_MONTH;//时间格式 yyyy
    private Date date;
    private int dataSize = 10;

    //output
    /**
     * 用户名集合列表
     */
    private String userNames = "";
    /**
     * 用户活动数据
     */
    private String countData = "";
    /**
     * 结论
     */
    private String backup;
    /**
     * 结论Desc
     */
    private String backupDc;
    /**
     * 格式化日期
     */
    private String fmtTime;
    /**
     * 活动人员最不频繁个数
     */
    private Integer count;

    //站点编号
    private int logicGroupId = Sessions.createByAction().currentLogicGroup().getId();

    @Route("/phoenix/uma/userStat")
    public String view() {
        if (date == null) {
            date = new Date();
        }
        //日期格式化
        if (dateType == Constants.FIND_TYPE_YEAR) {
            fmtTime = new SimpleDateFormat("yyyy").format(date);
            backup = fmtTime + "年";
        } else {
            fmtTime = new SimpleDateFormat("yyyy-MM").format(date);
            backup = new SimpleDateFormat("yyyy年MM月").format(date);
            ;
        }
        backupDc = backup;

        //数据查询--活动最频繁
        List<UserStat> users = userStatService.findUserFrequencyOfActivitiesStat(logicGroupId, date, dateType, dataSize, true);

        //JSON数据装配
        objToJsonOften(users);

//        数据查询--活动最不频繁
//        users = userStatService.findUserFrequencyOfActivitiesStat(LogicGroupId, date, dateType, dataSize, false);
//
//        JSON数据装配
//        objToJsonNonOften(users);
        log("人员管理", "人员活动频率");
        return Results.ftl("/phoenix/pages/index/layout");
    }

    /**
     * 组装json数据
     *
     * @author 刘柱
     * @date 2013-09-10
     */
    public void objToJsonOften(List<UserStat> users) {
        if (users != null && users.size() > 0) {
            backup += "活动最频繁的人员是：" + users.get(0).getUserName() + "。";

            for (int i = 0; i < users.size(); i++) {
                userNames += "'" + users.get(i).getUserName() + "',";
                countData += users.get(i).getUserCount() + ",";
            }
        }
        if (userNames.length() > 0) {
            userNames = "[" + userNames.substring(0, userNames.length() - 1) + "]";
        }
        if (countData.length() > 0) {
            countData = "[" + countData.substring(0, countData.length() - 1) + "]";
        }
    }

//    public void objToJsonNonOften(List<UserStat> users) {
//        if (users != null && users.size() > 0) {
//            count = userStatService.findUserCountByActivityCount(LogicGroupId, date, dateType, users.get(0).getUserCount());
//            backupDc += "活动最不频繁的人员是：" + users.get(0).getUserName() + "等" + count + "人";
//
//            for (int i = 0; i < users.size(); i++) {
//                userNamesDc += "'" + users.get(i).getUserName() + "',";
//                countDataDc += users.get(i).getUserCount() + ",";
//            }
//        }
//        if (userNamesDc.length() > 0) {
//            userNamesDc = "[" + userNamesDc.substring(0, userNamesDc.length() - 1) + "]";
//        }
//        if (countDataDc.length() > 0) {
//            countDataDc = "[" + countDataDc.substring(0, countDataDc.length() - 1) + "]";
//        }
//    }

    public static String get_pagePath() {
        return _pagePath;
    }

    //getter setter
    public String getUserNames() {
        return userNames;
    }

    public void setUserNames(String userNames) {
        this.userNames = userNames;
    }

    public String getCountData() {
        return countData;
    }

    public void setCountData(String countData) {
        this.countData = countData;
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

    public String getFmtTime() {
        return fmtTime;
    }

    public void setFmtTime(String fmtTime) {
        this.fmtTime = fmtTime;
    }

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public String getBackup() {
        return backup;
    }

    public void setBackup(String backup) {
        this.backup = backup;
    }

    public String getBackupDc() {
        return backupDc;
    }

    public void setBackupDc(String backupDc) {
        this.backupDc = backupDc;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
