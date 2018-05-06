package com.microwise.blueplanet.bean.po;

/**
 * @author xiedeng
 * @date 15-2-27
 */
public class HumidityController {
    public static final int TYPE_ZM = 1;
    public static final int TYPE_GQ = 2;
    /**
     * 恒湿机类型
     * 1-致美
     * 2-高强
     */
    private int type;

    /**
     * 当前展柜温度
     */
    private float currentTemperature;
    /**
     * 当前展柜湿度
     */
    private float currentHumidity;
    /**
     * 恒湿机设备状态
     */
    private HumidityControllerState humidityControllerState;
    /**
     * 目标温度
     */
    private int targetHumidity;
    /**
     * 湿度上限
     */
    private int highHumidity;
    /**
     * 湿度下限
     */
    private int lowHumidity;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(float currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public float getCurrentHumidity() {
        return currentHumidity;
    }

    public void setCurrentHumidity(float currentHumidity) {
        this.currentHumidity = currentHumidity;
    }

    public HumidityControllerState getHumidityControllerState() {
        return humidityControllerState;
    }

    public void setHumidityControllerState(HumidityControllerState humidityControllerState) {
        this.humidityControllerState = humidityControllerState;
    }

    public int getTargetHumidity() {
        return targetHumidity;
    }

    public void setTargetHumidity(int targetHumidity) {
        this.targetHumidity = targetHumidity;
    }

    public int getHighHumidity() {
        return highHumidity;
    }

    public void setHighHumidity(int highHumidity) {
        this.highHumidity = highHumidity;
    }

    public int getLowHumidity() {
        return lowHumidity;
    }

    public void setLowHumidity(int lowHumidity) {
        this.lowHumidity = lowHumidity;
    }
}
