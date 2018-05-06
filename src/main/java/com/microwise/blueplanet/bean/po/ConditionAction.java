package com.microwise.blueplanet.bean.po;

import com.microwise.blueplanet.bean.vo.DeviceVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;

import java.util.Date;

/**
 * 控制模块定时动作对象
 *
 * @date 2014-03-05
 */
public class ConditionAction {

    /**
     * id
     */
    private String id;

    /**
     * 动作逻辑Id
     */
    private String sensorActionId;


    /**
     * 设备Id
     */
    private String deviceId;

    /**
     * 检测指标id
     */
    private int sensorId;

    /**
     * 条件的值
     */
    private double conditionValue;

    /**
     * 条件 1>,2<,3=
     */
    private int operator;

    /**
     * 设备
     */
    private DeviceVO deviceVO;

    /**
     * 检测指标id
     */
    private SensorinfoVO sensorinfoVO;

    /**
     * 最后修改时间
     */
    protected Date updateTime;

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public double getConditionValue() {
        return conditionValue;
    }

    public void setConditionValue(double conditionValue) {
        this.conditionValue = conditionValue;
    }


    public int getOperator() {
        return operator;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }

    public DeviceVO getDeviceVO() {
        return deviceVO;
    }

    public void setDeviceVO(DeviceVO deviceVO) {
        this.deviceVO = deviceVO;
    }

    public SensorinfoVO getSensorinfoVO() {
        return sensorinfoVO;
    }

    public void setSensorinfoVO(SensorinfoVO sensorinfoVO) {
        this.sensorinfoVO = sensorinfoVO;
    }

    public String getSensorActionId() {
        return sensorActionId;
    }

    public void setSensorActionId(String sensorActionId) {
        this.sensorActionId = sensorActionId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}