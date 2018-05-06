package com.microwise.phoenix.bean.po;

import com.microwise.common.util.DateTimeUtil;

import java.util.Date;

public class ZoneData {

    /**
     * 区域名称
     */
    private String zoneName;

    /**
     * 区域位置
     */
    private int position;

    /**
     * 监测指标 最小值
     */
    private Double minValue;

    /**
     * 监测指标 最大值
     */
    private Double maxValue;

    /**
     * 监测指标 波动值
     */
    private Double waveValue;

    /**
     * 监测指标 平均值
     */
    private Double avgValue;

    /**
     * 最小值发生时刻
     */
    private Date minDate;

    /**
     * 最大值发生时刻
     */
    private Date maxDate;

    /**
     * 日期
     */
    private Date date;

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public Double getWaveValue() {
        return waveValue;
    }

    public void setWaveValue(Double waveValue) {
        this.waveValue = waveValue;
    }

    public Double getAvgValue() {
        return avgValue;
    }

    public void setAvgValue(Double avgValue) {
        this.avgValue = avgValue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getMinTime() {
        if (minDate != null) {
            return DateTimeUtil.formatFull(minDate);
        }
        return null;
    }

    public String getMaxTime() {
        if (maxDate != null) {
            return DateTimeUtil.formatFull(maxDate);
        }
        return null;
    }

}
