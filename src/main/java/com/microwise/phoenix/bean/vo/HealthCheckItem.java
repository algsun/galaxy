package com.microwise.phoenix.bean.vo;

import com.microwise.phoenix.bean.po.CheckItem;

import java.util.List;

/**
 * 系统 健康检测 统计项 vo实体
 *
 * @author xu.baoji
 * @date 2013-7-25
 */
public class HealthCheckItem {

    /**
     * 业务系统id
     */
    private int subsystemId;

    /**
     * 业务系统名称
     */
    private String subsystemName;

    /**
     * 该业务系统下 检测项实体列表
     */
    private List<CheckItem> checkItems;

    public int getSubsystemId() {
        return subsystemId;
    }

    public void setSubsystemId(int subsystemId) {
        this.subsystemId = subsystemId;
    }

    public String getSubsystemName() {
        return subsystemName;
    }

    public void setSubsystemName(String subsystemName) {
        this.subsystemName = subsystemName;
    }

    public List<CheckItem> getCheckItems() {
        return checkItems;
    }

    public void setCheckItems(List<CheckItem> checkItems) {
        this.checkItems = checkItems;
    }

}
