package com.microwise.blueplanet.bean.po;

import java.util.Date;

/**
 * 均峰值数据对象
 *
 * @author wang.geng
 * @since 2014-04-22
 */
public class AvgdataPO{

    /**
     * 区域Id
     */
    private String zoneId;

    /**
     * column m_avgdata.id 唯一标识uuid
     */
    private String id;

    /**
     * column m_avgdata.nodeid 产品入网唯一标识
     */
    private String nodeid;

    /**
     * column m_avgdata.sensorPhysicalid 传感量标识
     */
    private Integer sensorPhysicalid;

    /**
     * column m_avgdata.maxValue 最大值
     */
    private Double maxValue;

    /**
     * column m_avgdata.maxTime 最大值时间
     */
    private Date maxTime;

    /**
     * column m_avgdata.minValue 最小值
     */
    private Double minValue;

    /**
     * column m_avgdata.minTime 最小值时间
     */
    private Date minTime;

    /**
     * column m_avgdata.avgValue 平均值
     */
    private Double avgValue;

    /**
     * column m_avgdata.waveValue 日波动值=最大值-最小值
     */
    private Double waveValue;

    /**
     * column m_avgdata.ms_date 日期值
     */
    private Date msDate;

    /**
     * column m_avgdata.isupdate
     * 当数据因链路问题出现丢包，然后通过数据回补逻辑将基础数据补充完整后，统计表需要重新进行数据统计。（涉及到：均峰值、降雨量、日照、风向）
     */
    private Integer isupdate;

    /**
     * column m_avgdata.dataVersion 数据同步版本
     */
    private Long dataVersion;

    public AvgdataPO() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNodeid() {
        return nodeid;
    }

    public void setNodeid(String nodeid) {
        this.nodeid = nodeid;
    }

    public Integer getSensorPhysicalid() {
        return sensorPhysicalid;
    }

    public void setSensorPhysicalid(Integer sensorPhysicalid) {
        this.sensorPhysicalid = sensorPhysicalid;
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

    public Date getMsDate() {
        return msDate;
    }

    public void setMsDate(Date msDate) {
        this.msDate = msDate;
    }

    public Integer getIsupdate() {
        return isupdate;
    }

    public void setIsupdate(Integer isupdate) {
        this.isupdate = isupdate;
    }

    public Long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Long dataVersion) {
        this.dataVersion = dataVersion;
    }

    public Date getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(Date maxTime) {
        this.maxTime = maxTime;
    }

    public Date getMinTime() {
        return minTime;
    }

    public void setMinTime(Date minTime) {
        this.minTime = minTime;
    }

    @Override
    public String toString() {
        return "AvgdataDO [id=" + id + ", nodeid=" + nodeid
                + ", sensorPhysicalid=" + sensorPhysicalid + ", maxValue="
                + maxValue + ", minValue=" + minValue + ", avgValue="
                + avgValue + ", waveValue=" + waveValue + ", msDate=" + msDate
                + ", isupdate=" + isupdate + ", dataVersion=" + dataVersion
                + "]";
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }
}