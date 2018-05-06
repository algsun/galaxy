package com.microwise.phoenix.bean.po;

import java.util.List;

public class UserDistributionInfo {

    /**
     * 区域集合
     */
    private List<String> districtList;

    /**
     * 图表的x轴数据
     */
    private List<List<List<Object>>> data;

    /**
     * 最活跃的区域
     */
    private String maxActiveArea;

    /**
     * 最活跃的区域次数
     */
    private int maxActiveAreaCount;

    /**
     * 最活跃的时间段
     */
    private String maxActiveTime;

    /**
     * 最活跃的时间段的次数
     */
    private int maxActiveTimeCount;

    /**
     * 最活跃的区域中最活跃的时间段
     */
    private String maxActiveTimeRange;

    /**
     * 最活跃的时间段中最活跃的区域
     */
    private String maxActiveTimeArea;

    /**
     * 最活跃的时间段中最活跃的区域的次数
     */
    private int maxActiveTimeAreaCount;

    /**
     * 是否存在数据 true 存在 ， false 不存在
     */
    public boolean hasData;

    public List<String> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<String> districtList) {
        this.districtList = districtList;
    }

    public List<List<List<Object>>> getData() {
        return data;
    }

    public void setData(List<List<List<Object>>> data) {
        this.data = data;
    }

    public String getMaxActiveArea() {
        return maxActiveArea;
    }

    public void setMaxActiveArea(String maxActiveArea) {
        this.maxActiveArea = maxActiveArea;
    }

    public int getMaxActiveAreaCount() {
        return maxActiveAreaCount;
    }

    public void setMaxActiveAreaCount(int maxActiveAreaCount) {
        this.maxActiveAreaCount = maxActiveAreaCount;
    }

    public String getMaxActiveTime() {
        return maxActiveTime;
    }

    public void setMaxActiveTime(String maxActiveTime) {
        this.maxActiveTime = maxActiveTime;
    }

    public int getMaxActiveTimeCount() {
        return maxActiveTimeCount;
    }

    public void setMaxActiveTimeCount(int maxActiveTimeCount) {
        this.maxActiveTimeCount = maxActiveTimeCount;
    }

    public String getMaxActiveTimeArea() {
        return maxActiveTimeArea;
    }

    public void setMaxActiveTimeArea(String maxActiveTimeArea) {
        this.maxActiveTimeArea = maxActiveTimeArea;
    }

    public int getMaxActiveTimeAreaCount() {
        return maxActiveTimeAreaCount;
    }

    public void setMaxActiveTimeAreaCount(int maxActiveTimeAreaCount) {
        this.maxActiveTimeAreaCount = maxActiveTimeAreaCount;
    }

    public boolean isHasData() {
        return hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }

    public String getMaxActiveTimeRange() {
        return maxActiveTimeRange;
    }

    public void setMaxActiveTimeRange(String maxActiveTimeRange) {
        this.maxActiveTimeRange = maxActiveTimeRange;
    }

}
