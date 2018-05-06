package com.microwise.blueplanet.bean.po;

import java.util.Date;

/**
 * 区域均值统计数据 PO
 *
 * @author li.jianfei
 * @since 2017/5/27
 */
public class ZoneAvgDataPO {

    /**
     * UUID
     */
    private String id;

    /**
     * 区域ID
     */
    private String zoneId;

    /**
     * 监测指标ID
     */
    private int sensorId;

    /**
     * 均值
     */
    private double avgValue;

    /**
     * 统计日期
     */
    private Date msDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public double getAvgValue() {
        return avgValue;
    }

    public void setAvgValue(double avgValue) {
        this.avgValue = avgValue;
    }

    public Date getMsDate() {
        return msDate;
    }

    public void setMsDate(Date msDate) {
        this.msDate = msDate;
    }
}
