package com.microwise.blueplanet.bean.vo;

import java.util.List;

/**
 * 环境监控: 设备均峰值统计分析实体.
 *
 * TODO 优化数据模型 @gaohui 2014-04-22
 *
 * @author wang.geng
 * @date 14-4-16 上午10:56
 */
public class DeviceStatisticsVO {
    /**
     * 监测指标 标识
     */
    private int sensorPhysicalid;

    /**
     * 监测指标中文名称
     */
    private String cnName;

    /**
     * 监测指标单位
     */
    private String units;

    /**
     * 波动值
     * TODO 波动值也是按时间分的 @gaohui 2014-04-22
     */
    private Double waveValue;

    /**
     * 监测指标最大值和最小值集合: list<object> 第一个值为日期，第二个值为max，第三个值为min
     */
    private List<List<Object>> maxAndMinDatas;

    /**
     * 最大值和最小值时刻: list<object> 第一个值为max时刻，第二个值为min时刻
     */
    private List<List<Object>> maxAndMinDate;

    /**
     * 监测指标平均值集合: list<object> 第一个值为日期，第二个值为平均值
     */
    private List<List<Object>> aveDatas;

    /**
     * 判断是否有数据: true 图表有数据  false 图表无数据
     */
    private boolean hasData;

    /**
     * 显示的日期类型，不同的监测指标各自是不同的
     */
    private int dateType;

    public int getSensorPhysicalid() {
        return sensorPhysicalid;
    }

    public void setSensorPhysicalid(int sensorPhysicalid) {
        this.sensorPhysicalid = sensorPhysicalid;
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

    public List<List<Object>> getMaxAndMinDatas() {
        return maxAndMinDatas;
    }

    public void setMaxAndMinDatas(List<List<Object>> maxAndMinDatas) {
        this.maxAndMinDatas = maxAndMinDatas;
    }

    public List<List<Object>> getMaxAndMinDate() {
        return maxAndMinDate;
    }

    public void setMaxAndMinDate(List<List<Object>> maxAndMinDate) {
        this.maxAndMinDate = maxAndMinDate;
    }

    public List<List<Object>> getAveDatas() {
        return aveDatas;
    }

    public void setAveDatas(List<List<Object>> aveDatas) {
        this.aveDatas = aveDatas;
    }

    public boolean isHasData() {
        return hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }

    public Double getWaveValue() {
        return waveValue;
    }

    public void setWaveValue(Double waveValue) {
        this.waveValue = waveValue;
    }

    public int getDateType() {
        return dateType;
    }

    public void setDateType(int dateType) {
        this.dateType = dateType;
    }
}
