package com.microwise.blueplanet.bean.vo;

/**
 * date 2016-07-22
 * author chenyaofei
 */
public class ZoneThresholdVO {
    /**表id*/
    private int id;

    /**区域uuid*/
    private String zoneId;

    /**监测指标id*/
    private int sensorPhysicalId;

    /** 监测指标中文名称 */
    private String cnName;

    /** 监测指标计量单位 */
    private String units;

    /**
     * 达标条件类型，1-范围；2-大于；3-小于；4-大于等于；5-小于等于。与目标值/浮动值有关
     * */
    private int conditionType;

    /**目标值*/
    private float target;

    /**浮动值*/
    private float floating;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public int getSensorPhysicalId() {
        return sensorPhysicalId;
    }

    public void setSensorPhysicalId(int sensorPhysicalId) {
        this.sensorPhysicalId = sensorPhysicalId;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
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
}
