package com.microwise.blueplanet.bean.po;

import java.util.Date;

/**
 * 设备状态信息
 *
 * @author liuzhu
 * @date 15-1-21
 */
public class DeviceState {

    /**
     * 产品序列号
     */
    private int sn;

    /**
     * 工作周期
     */
    private Integer interval_i;

    /**
     * 设备接收信号强度
     */
    private Integer rssi;

    /**
     * 设备链路质量
     */
    private Integer lqi;

    /**
     * 电压
     */
    private float lowVoltage;

    /**
     * 设备状态
     */
    private Integer anomaly;

    /**
     * 时间戳
     */
    private Date stamp;

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public Integer getInterval_i() {
        return interval_i;
    }

    public void setInterval_i(Integer interval_i) {
        this.interval_i = interval_i;
    }

    public Integer getRssi() {
        return rssi;
    }

    public void setRssi(Integer rssi) {
        this.rssi = rssi;
    }

    public Integer getLqi() {
        return lqi;
    }

    public void setLqi(Integer lqi) {
        this.lqi = lqi;
    }

    public float getLowVoltage() {
        return lowVoltage;
    }

    public void setLowVoltage(float lowVoltage) {
        this.lowVoltage = lowVoltage;
    }

    public Integer getAnomaly() {
        return anomaly;
    }

    public void setAnomaly(Integer anomaly) {
        this.anomaly = anomaly;
    }

    public Date getStamp() {
        return stamp;
    }

    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }
}
