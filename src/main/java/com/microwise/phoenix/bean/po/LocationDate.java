package com.microwise.phoenix.bean.po;

/**
 * 位置点监测指标对象
 * author : chenyaofei
 * date : 2016-10-11
 */
public class LocationDate {

    /**
     * 位置点Id
     */
    private String locationId;
    /**
     * 位置点名称
     */
    private String locationName;

    /**
     *监测指标精度
     */
    private int sensorPrecision;

    /**
     *最大值
     */
    private Double maxValue;

    /**
     * 最小值
     */
    private Double minValue;

    /**
     * 平均值
     */
    private Double avgValue;

    /**
     * 波动值
     */
    private Double waveValue;

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public int getSensorPrecision() {
        return sensorPrecision;
    }

    public void setSensorPrecision(int sensorPrecision) {
        this.sensorPrecision = sensorPrecision;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Double getAvgValue() {
        return avgValue;
    }

    public void setAvgValue(Double avgValue) {
        this.avgValue = avgValue;
    }

    public Double getWaveValue() {
        return waveValue;
    }

    public void setWaveValue(Double waveValue) {
        this.waveValue = waveValue;
    }
}
