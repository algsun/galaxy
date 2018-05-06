package com.microwise.blueplanet.bean.vo;

/**
 * 质地阈值vo
 */
public class TextureThresholdVO {

    /**
     * 自增id
     */
    private int id;

    /**
     * 质地id
     */
    private int textureId;

    /**
     * 监测指标id
     */
    private int sensorPhysicalId;

    /**
     * 监测指标名称
     */
    private String sensorName;

    /**
     * 条件
     */
    private int conditionType;

    /**
     * 目标值
     */
    private float target;

    /**
     * 浮动值
     */
    private float floating;

    /**
     * 阈值类型
     */
    private int thresholdType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTextureId() {
        return textureId;
    }

    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }

    public int getSensorPhysicalId() {
        return sensorPhysicalId;
    }

    public void setSensorPhysicalId(int sensorPhysicalId) {
        this.sensorPhysicalId = sensorPhysicalId;
    }

    public int getConditionType() {
        return conditionType;
    }

    public void setConditionType(int conditionType) {
        this.conditionType = conditionType;
    }

    public float getTarget() {
        return target;
    }

    public void setTarget(float target) {
        this.target = target;
    }

    public float getFloating() {
        return floating;
    }

    public void setFloating(float floating) {
        this.floating = floating;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public int getThresholdType() {
        return thresholdType;
    }

    public void setThresholdType(int thresholdType) {
        this.thresholdType = thresholdType;
    }
}
