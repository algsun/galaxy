package com.microwise.phoenix.bean.vo;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 业务系统操作统计
 *
 * @author xu.baoji
 * @date 2013-8-16
 */
public class SubsystemOperate {

    /**
     * 业务系统代号
     */
    private String subsystemCode;

    /**
     * 业务系统名称
     */
    private String subsystemName;

    /**
     * 业务系统id
     */
    private Integer subsystemId;

    /**
     * 该业务系统下 操作记录 map key：用户名 value：操作次数
     */
    private Map<String, Integer> operates;

    /**
     * 处理过的operates
     */
    private String operatesInfo;

    public Map<String, Integer> getOperates() {
        return operates;
    }

    public void setOperates(Map<String, Integer> operates) {
        this.operates = operates;
    }

    public String getSubsystemName() {
        return subsystemName;
    }

    public void setSubsystemName(String subsystemName) {
        this.subsystemName = subsystemName;
    }

    public Integer getSubsystemId() {
        return subsystemId;
    }

    public void setSubsystemId(Integer subsystemId) {
        this.subsystemId = subsystemId;
    }

    public String getSubsystemCode() {
        return subsystemCode;
    }

    public void setSubsystemCode(String subsystemCode) {
        this.subsystemCode = subsystemCode;
    }

    public String getOperatesInfo() {
        Map<String, Integer> op = this.getOperates();
        StringBuilder chartDatas = new StringBuilder();
        List<String> chartXData = new ArrayList<String>(op.keySet());
        List<Integer> chartYData = new ArrayList<Integer>(op.values());
        chartDatas.append("\"operates\":{\"xd\":");
        Gson gson = new Gson();
        chartDatas.append(gson.toJson(chartXData));
        chartDatas.append(",\"yd\":");
        chartDatas.append(gson.toJson(chartYData));
        chartDatas.append("}");
        return chartDatas.toString();
    }

    public void setOperatesInfo(String operatesInfo) {
        this.operatesInfo = operatesInfo;
    }
}
