package com.microwise.phoenix.bean.vo;

/**
 * 区域峰值对象
 *
 * @author xuyeuxi
 * @date 14-9-26
 */
public class ZonePeakValue {

    /**
     * 区域id
     */
    private String zoneId;

    /**
     * 区域名称
     */
    private String zoneName;

    /**
     * 峰值
     */
    private double peakValue;

    /**
     * 月份
     */
    private int month;

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public double getPeakValue() {
        return peakValue;
    }

    public void setPeakValue(double peakValue) {
        this.peakValue = peakValue;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
