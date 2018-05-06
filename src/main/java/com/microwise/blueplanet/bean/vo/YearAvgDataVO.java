package com.microwise.blueplanet.bean.vo;

/**
 * @author 王耕
 * @date 15-9-6
 */
public class YearAvgDataVO {
    /** 位置点编号 */
    private String locationId;
    /** 监测指标id */
    private int sensorPhysicalid;
    /** 月份 */
    private String mouth;
    /** 值 */
    private double val;

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public int getSensorPhysicalid() {
        return sensorPhysicalid;
    }

    public void setSensorPhysicalid(int sensorPhysicalid) {
        this.sensorPhysicalid = sensorPhysicalid;
    }

    public String getMouth() {
        return mouth;
    }

    public void setMouth(String mouth) {
        this.mouth = mouth;
    }

    public double getVal() {
        return val;
    }

    public void setVal(double val) {
        this.val = val;
    }
}
