package com.microwise.phoenix.bean.vo;

import java.util.List;

/**
 * 人员管理：人员在区域停留时长统计vo
 *
 * @author xu.baoji
 * @date 2013-7-15
 */
public class UserStopOver {

    /**
     * 平均活动时长最大的区域名称
     */
    private String avgZoneName;

    /**
     * 平均活动时长最大的值
     */
    private Float maxAvgTime;

    /**
     * 总活动时长最大的区域名称
     */
    private String inZoneName;

    /**
     * 总活动时长最大的值
     */
    private Float maxInTime;

    /**
     * 区域名称 列表
     */
    private List<String> zoneNames;

    /**
     * 平均活动时长列表
     */
    private List<Float> avgTime;

    /**
     * 总活动时长列表
     */
    private List<Float> inTime;

    /**
     * 是否有数据 true：有数据 ，fase：无数据
     */
    private boolean hasData = false;

    public List<String> getZoneNames() {
        return zoneNames;
    }

    public String getAvgZoneName() {
        return avgZoneName;
    }

    public void setAvgZoneName(String avgZoneName) {
        this.avgZoneName = avgZoneName;
    }

    public String getInZoneName() {
        return inZoneName;
    }

    public void setInZoneName(String inZoneName) {
        this.inZoneName = inZoneName;
    }

    public Float getMaxAvgTime() {
        return maxAvgTime;
    }

    public void setMaxAvgTime(Float maxAvgTime) {
        this.maxAvgTime = maxAvgTime;
    }

    public Float getMaxInTime() {
        return maxInTime;
    }

    public void setMaxInTime(Float maxInTime) {
        this.maxInTime = maxInTime;
    }

    public void setZoneNames(List<String> zoneNames) {
        this.zoneNames = zoneNames;
    }

    public List<Float> getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(List<Float> avgTime) {
        this.avgTime = avgTime;
    }

    public List<Float> getInTime() {
        return inTime;
    }

    public void setInTime(List<Float> inTime) {
        this.inTime = inTime;
    }

    public boolean isHasData() {
        return hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }

}
