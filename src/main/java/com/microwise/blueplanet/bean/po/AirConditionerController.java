package com.microwise.blueplanet.bean.po;

/**
 * Created by Administrator on 2017/12/27.
 */
public class AirConditionerController {
    /**
     * 空调组类型
     * 1-融通
     */
    public static final int TYPE_RT = 1;

    /**
     * 空调组开关状态 0-关 1-开
     */
    public static final int SWITCH_OFF = 0;
    public static final int SWITCH_ON = 1;
    /**
     * 空调模组类型
     * 1-融通
     */
    private int type;
    /**
     * 目标温度
     */
    private float targetTemperature;
    /**
     * 目标湿度
     */
    private float targetHumidity;

    /**
     * 空调组开关状态 0-关 1-开
     */
    private int switchState;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getTargetTemperature() {
        return targetTemperature;
    }

    public void setTargetTemperature(float targetTemperature) {
        this.targetTemperature = targetTemperature;
    }

    public float getTargetHumidity() {
        return targetHumidity;
    }

    public void setTargetHumidity(float targetHumidity) {
        this.targetHumidity = targetHumidity;
    }

    public int getSwitchState() {
        return switchState;
    }

    public void setSwitchState(int switchState) {
        this.switchState = switchState;
    }
}