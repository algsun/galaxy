package com.microwise.blueplanet.bean.vo;

import java.util.Date;

/**
 * 设备vo
 *
 * @author liuzhu
 * @date 2015-1-30
 */
public class ProductStateVO {

    /**
     * 设备id
     */
    private String id;

    /**
     * 设备类型
     */
    private int nodeType;

    /**
     * 产品序列号
     */
    private String serialNumber;

    /**
     * 工作周期
     */
    private int interval;

    /**
     * rssi
     */
    private int rssi;

    /**
     * lqi
     */
    private int lqi;

    /**
     * 电压
     */
    private float voltage;

    /**
     * 状态
     */
    private int anomaly;

    /**
     * 时间
     */
    private Date stamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public int getLqi() {
        return lqi;
    }

    public void setLqi(int lqi) {
        this.lqi = lqi;
    }

    public float getVoltage() {
        return voltage;
    }

    public void setVoltage(float voltage) {
        this.voltage = voltage;
    }

    public int getAnomaly() {
        return anomaly;
    }

    public void setAnomaly(int anomaly) {
        this.anomaly = anomaly;
    }

    public Date getStamp() {
        return stamp;
    }

    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }

    public int getNodeType() {
        return nodeType;
    }

    public void setNodeType(int nodeType) {
        this.nodeType = nodeType;
    }
}
