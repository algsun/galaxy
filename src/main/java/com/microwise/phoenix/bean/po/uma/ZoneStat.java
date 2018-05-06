package com.microwise.phoenix.bean.po.uma;

/**
 * 人员管理：区域活动统计，区域活动 总时长，区域平均活动时长
 *
 * @author xu.baoji
 * @date 2013-7-15
 */
public class ZoneStat {

    /**
     * 区域名称
     */
    private String zoneName;

    /**
     * 区域活动平均时长
     */
    private Long avgTime;

    /**
     * 区域活动总时长
     */
    private Long inTime;

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public Long getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(Long avgTime) {
        this.avgTime = avgTime;
    }

    public Long getInTime() {
        return inTime;
    }

    public void setInTime(Long inTime) {
        this.inTime = inTime;
    }

}
