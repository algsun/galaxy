package com.microwise.blueplanet.bean.vo;

import com.microwise.common.util.DateTimeUtil;

import java.util.Date;

/**
 * 设备均峰值数据实体.
 *
 * @author wang.geng
 * @date 14-4-16 下午2:13
 */
public class DeviceAvePeakDataVO {
    /**
     * 设备ID
     */
    private String nodeId;

    /**
     * 监测指标ID
     */
    private String sensorPhysicalid;

    /**
     * 监测指标最小值
     */
    private Double minValue;

    /**
     * 监测指标最大值
     */
    private Double maxValue;

    /**
     * 监测指标波动值
     */
    private Double waveValue;

    /**
     * 监测指标平均值
     */
    private Double aveValue;

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

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getSensorPhysicalid() {
        return sensorPhysicalid;
    }

    public void setSensorPhysicalid(String sensorPhysicalid) {
        this.sensorPhysicalid = sensorPhysicalid;
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

    public Double getAveValue() {
        return aveValue;
    }

    public void setAveValue(Double aveValue) {
        this.aveValue = aveValue;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
