package com.microwise.biela.bean.vo;

import java.util.Date;

/**
 * 位置点数据vo
 *
 * @author liuzhu
 * @date 2014-12-17
 */
public class LocationSensorVO {

    /**
     * 监测指标id
     */
    private int sensorId;

    /**
     * 最大值
     */
    private float maxSensorValue;

    /**
     * 最小值
     */
    private float minSensorValue;

    /**
     * 时间戳
     */
    private Date stamp;

    /**
     * 单位
     */
    private String units;

    public int getSensorId() {
        return sensorId;
    }

    public float getMaxSensorValue() {
        return maxSensorValue;
    }

    public void setMaxSensorValue(float maxSensorValue) {
        this.maxSensorValue = maxSensorValue;
    }

    public float getMinSensorValue() {
        return minSensorValue;
    }

    public void setMinSensorValue(float minSensorValue) {
        this.minSensorValue = minSensorValue;
    }

    public Date getStamp() {
        return stamp;
    }

    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }
}
