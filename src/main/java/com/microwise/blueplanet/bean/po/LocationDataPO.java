package com.microwise.blueplanet.bean.po;

/**
 * 位置点历史数据PO
 *
 * @author li.jianfei
 * @date 2016-08-18
 */
public class LocationDataPO {

    /**
     * 位置點ID
     */
    private String locationId;

    /**
     * 监测指标ID
     */
    private int sensorId;

    /**
     * 监测指标值
     */
    private String sensorPhysicalValue;

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public String getSensorPhysicalValue() {
        return sensorPhysicalValue;
    }

    public void setSensorPhysicalValue(String sensorPhysicalValue) {
        this.sensorPhysicalValue = sensorPhysicalValue;
    }
}
