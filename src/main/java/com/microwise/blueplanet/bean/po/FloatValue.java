package com.microwise.blueplanet.bean.po;

/**
 * @author chenyaofei
 * @date 16-5-17
 */
public class FloatValue {
    /**
     *编号
     */
    private int id;
    /**
     * 设备编号
     */
    private String deviceId;
    /**
     * 监测指标编号
     */
    private int sensorId;
    /**
     * 上限浮动值向上浮动
     */
    private double maxUpFloat;
    /**
     * 下限浮动值向下浮动
     */
    private double minDownFloat;
    /**
     * 下限浮动值向上浮动
     */
    private double minUpFloat;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public double getMaxUpFloat() {
        return maxUpFloat;
    }

    public void setMaxUpFloat(double maxUpFloat) {
        this.maxUpFloat = maxUpFloat;
    }

    public double getMinDownFloat() {
        return minDownFloat;
    }

    public void setMinDownFloat(double minDownFloat) {
        this.minDownFloat = minDownFloat;
    }

    public double getMinUpFloat() {
        return minUpFloat;
    }

    public void setMinUpFloat(double minUpFloat) {
        this.minUpFloat = minUpFloat;
    }
}
