package com.microwise.blueplanet.bean.po;

import com.microwise.blueplanet.bean.vo.SensorinfoVO;

import java.util.Date;

/**
 * 条件反射
 *
 * @author gaohui
 * @date 14-2-13 10:31
 */
public class ConditionRefl {

    private String id;

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 端口/路
     */
    private int route;
    /**
     * 子设备ID
     */
    private int subTerminalId;
    /**
     * 监测指标ID
     */
    private int sensorId;
    /**
     * 低阈值
     */
    private int low;

    /**
     * 结果值
     */
    private double lowTarget;
    /**
     * 高阈值
     */
    private int high;

    /**
     * 结果值
     */
    private double highTarget;

    /**
     * 动作
     */
    private int action;

    /**
     * 最后更新时间
     */
    private Date updateTime;

    /**
     * 检测指标
     */
    private SensorinfoVO sensorinfoVO;

    public int getRoute() {
        return route;
    }

    public void setRoute(int route) {
        this.route = route;
    }

    public int getSubTerminalId() {
        return subTerminalId;
    }

    public void setSubTerminalId(int subTerminalId) {
        this.subTerminalId = subTerminalId;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public double getLowTarget() {
        return lowTarget;
    }

    public void setLowTarget(double lowTarget) {
        this.lowTarget = lowTarget;
    }

    public double getHighTarget() {
        return highTarget;
    }

    public void setHighTarget(double highTarget) {
        this.highTarget = highTarget;
    }

    public SensorinfoVO getSensorinfoVO() {
        return sensorinfoVO;
    }

    public void setSensorinfoVO(SensorinfoVO sensorinfoVO) {
        this.sensorinfoVO = sensorinfoVO;
    }

    @Override
    public String toString() {
        return "ConditionRefl{" +
                "route=" + route +
                ", subTerminalId=" + subTerminalId +
                ", sensorId=" + sensorId +
                ", low=" + low +
                ", high=" + high +
                ", action=" + action +
                '}';
    }
}
