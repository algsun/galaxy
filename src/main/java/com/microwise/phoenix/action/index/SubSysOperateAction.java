package com.microwise.phoenix.action.index;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.phoenix.bean.vo.SubsystemOperate;
import com.microwise.phoenix.service.UserOperateService;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.sys.PhoenixLoggerAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 用户操作统计 action
 *
 * @author liuzhu
 * @date 2013.08.21
 * @check @wang.geng 2013年9月2日 #5271
 */
@Beans.Action
@Phoenix
public class SubSysOperateAction extends PhoenixLoggerAction {
    public static final Logger log = LoggerFactory.getLogger(SubSysOperateAction.class);

    public static final String _pagePath = "../index/sub-sys-operate.ftl";

    @Autowired
    private UserOperateService service;

    // input
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
     * 业务系统数据
     */
    private List<SubsystemOperate> subsystemOperate;
    /**
     * 业务系统信息
     */
    private String subSysOperateInfo;
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
     * 业务系统操作数据显示
     *
     * @author liuzhu
     * @date 2013-08-22
     */
    @Route("/phoenix/index/subSysOperate")
    public String view() {
        if (date == null) {
            date = new Date();
        }
        // 站点编号
        int LogicGroupId = Sessions.createByAction().currentLogicGroup().getId();
        // 图表数据
        subsystemOperate = service.findSubsystemOperate(LogicGroupId, date, dateType, size);
        dataToJson(subsystemOperate);
        log("数据分析", "业务系统操作");
        return Results.ftl("/phoenix/pages/index/layout");
    }

    /**
     * 组装json数据
     *
     * @param subSysOperateList 业务系统日志list
     * @author liuzhu
     * @date 2013-08-22
     */
    private void dataToJson(List<SubsystemOperate> subSysOperateList) {
        StringBuilder chartData = new StringBuilder();
        for (SubsystemOperate so : subsystemOperate) {
            chartData.append("{\"subsystemCode\":\"").append(so.getSubsystemCode()).append("\",\"subsystemName\":\"").append(so.getSubsystemName()).append("\",");
            chartData.append(so.getOperatesInfo());
            chartData.append("},");
        }
        Conclusion();//结论
        subSysOperateInfo = chartData.toString();
        subSysOperateInfo = subSysOperateInfo.substring(0, subSysOperateInfo.length() - 1);
        subSysOperateInfo = "[" + subSysOperateInfo + "]";
    }

    /**
     * 封装结论
     *
     * @author liuzhu
     * @date 2013-08-22
     */
    private void Conclusion() {
        String timeTemp = "";
        if (dateType == 1) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年");
            timeTemp = sdf.format(date);
        } else if (dateType == 2) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
            timeTemp = sdf.format(date);
        }
        int subSysOperateListSize = subsystemOperate.size();

        conclusion += timeTemp + "<br/>";
        for (int i = 0; i < subSysOperateListSize; i++) {
            SubsystemOperate so = subsystemOperate.get(i);
            if (so.getOperates().size() > 0) {
                Set<String> key = so.getOperates().keySet();
                conclusion += so.getSubsystemName() + "中操作次数最多的人是：" + "<strong>" + key.toArray()[0] + "</strong><br/>";
            }
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<SubsystemOperate> getSubsystemOperate() {
        return subsystemOperate;
    }

    public void setSubsystemOperate(List<SubsystemOperate> subsystemOperate) {
        this.subsystemOperate = subsystemOperate;
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

    public String getSubSysOperateInfo() {
        return subSysOperateInfo;
    }

    public void setSubSysOperateInfo(String subSysOperateInfo) {
        this.subSysOperateInfo = subSysOperateInfo;
    }
}