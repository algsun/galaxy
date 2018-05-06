package com.microwise.phoenix.action.index;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.proxy.UserProxy;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.phoenix.bean.vo.UserOperate;
import com.microwise.phoenix.service.UserOperateService;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.sys.PhoenixLoggerAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 用户操作统计 action
 *
 * @author xu.baoji
 * @author liuzhu
 * @date 2013.08.19
 * @check @wang.geng 2013年9月2日 #5250
 */
@Beans.Action
@Phoenix
public class UserOperateAction extends PhoenixLoggerAction {
    public static final Logger log = LoggerFactory.getLogger(UserOperateAction.class);

    public static final String _pagePath = "../index/user-operate.ftl";

    /**
     * 用户操作service
     */
    @Autowired
    private UserOperateService service;

    /**
     * 用户代理service
     */
    @Autowired
    private UserProxy userProxy;

    // input

    /**
     * 用户email
     */
    private String email;

    /**
     * 时间类型 （1-年，2-月）
     */
    private int dateType = Constants.FIND_TYPE_MONTH;

    /**
     * 时间
     */
    private Date date;

    /**
     * 显示的数量
     */
    private int size = 10;

    //output

    /**
     * 用户list
     */
    private List<User> users;

    /**
     * 用户操作list
     */
    private List<UserOperate> userOperates;

    /**
     * 结论
     */
    private String conclusion = "";

    /**
     * 操作最大
     */
    private String max;

    /**
     * 操作最小
     */
    private String min;

    /**
     * 用户操作数据显示
     *
     * @author liuzhu
     * @date 2013-08-19
     */
    @Route("/phoenix/index/operate")
    public String view() {
        if (date == null) {
            date = new Date();
        }
        int LogicGroupId = Sessions.createByAction().currentLogicGroup().getId();
        // 站点下已经激活可用的 用户列表
        users = userProxy.findUserList(LogicGroupId);
        if (email == null || "".equals(email)) {
            email = users.get(0).getEmail();
        }
        // 图表数据
        userOperates = service.findUserOperates(email, date, dateType, size);
        Conclusion();
        log("数据分析", "用户操作统计");
        return Results.ftl("/phoenix/pages/index/layout");
    }

    /**
     * 结论
     *
     * @author liuzhu
     * @date 2013-08-22
     */
    private void Conclusion() {
        String timeTemp = "";
        if (dateType == 1) {
            timeTemp = new SimpleDateFormat("yyyy年").format(date);
        } else if (dateType == 2) {
            timeTemp = new SimpleDateFormat("yyyy年MM月").format(date);
        }
        if (userOperates.size() > 0) {
            max = "<strong>" + userOperates.get(0).getFunctionName() + "</strong>操作次数为：<strong>" + userOperates.get(0).getCount() + "</strong>次";
            min = "<strong>" + userOperates.get(userOperates.size() - 1).getFunctionName() + "</strong>操作次数为：<strong>"
                    + userOperates.get(userOperates.size() - 1).getCount() + "</strong>次";
            conclusion = timeTemp + "<br/>" + "操作次数最多的是：" + max +
                    "<br/>操作次数最少的是：" + min;
        } else if (userOperates.size() == 1) {
            max = userOperates.get(0).getFunctionName() + ",操作次数为：" + userOperates.get(0).getCount() + "次";
            conclusion = timeTemp + "<br/>" + "操作次数最多的是：" + max;
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

    public UserOperateService getService() {
        return service;
    }

    public void setService(UserOperateService service) {
        this.service = service;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<UserOperate> getUserOperates() {
        return userOperates;
    }

    public void setUserOperates(List<UserOperate> userOperates) {
        this.userOperates = userOperates;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }
}