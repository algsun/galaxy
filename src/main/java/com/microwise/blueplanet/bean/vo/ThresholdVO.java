package com.microwise.blueplanet.bean.vo;

import java.io.Serializable;

/**
 * 阈值po 实体
 *
 * @author xu.baoji
 * @date 2013-8-26
 */

public class ThresholdVO implements Serializable {

    private static final long serialVersionUID = 135754419165625097L;

    /**
     * 自增主键
     */
    private Integer id;

    /**
     * 位置点id
     */
    private String locationId;

    /**
     * 监测指标标识
     */
    private Integer sensorPhysicalId;

    /**
     * 监测指标名称
     */
    private String sensorName;

    /**
     * 单位
     */
    private String sensorUnit;

    /**
     * 条件类型
     */
    private Integer conditionType;

    /**
     * 目标值
     */
    private Float target;

    /**
     * 浮动值
     */
    private Float floating;

    private Integer textureId;
    /**
     * 指标类型
     * 0：位置点 1：区域 2：质地
     */
    private int thresholdType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSensorPhysicalId() {
        return sensorPhysicalId;
    }

    public void setSensorPhysicalId(Integer sensorPhysicalId) {
        this.sensorPhysicalId = sensorPhysicalId;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getSensorUnit() {
        return sensorUnit;
    }

    public void setSensorUnit(String sensorUnit) {
        this.sensorUnit = sensorUnit;
    }

    public Integer getConditionType() {
        return conditionType;
    }

    public void setConditionType(Integer conditionType) {
        this.conditionType = conditionType;
    }

    public Float getTarget() {
        return target;
    }

    public void setTarget(Float target) {
        this.target = target;
    }

    public Float getFloating() {
        return floating;
    }

    public void setFloating(Float floating) {
        this.floating = floating;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public Integer getTextureId() {
        return textureId;
    }

    public void setTextureId(Integer textureId) {
        this.textureId = textureId;
    }

    public int getThresholdType() {
        return thresholdType;
    }

    public void setThresholdType(int thresholdType) {
        this.thresholdType = thresholdType;
    }
}
