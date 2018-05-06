package com.microwise.phoenix.bean.vo;

import java.util.List;

/***
 * 环境监控： 区域统计分析 实体
 *
 * @author xu.baoji
 * @date 2013-7-3
 */
public class ZoneStatistics {

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
     * 监测指标最大值和最小值集合 : list<object> 第一个值为 日期，第二个值为max 第三个值为 min
     */
    private List<List<Object>> maxAndMinDatas;

    /**
     * 最大值和最小值 时刻 ：list<object> 第一个值为 max 时刻 第二个值为 min 时刻
     */
    private List<List<Object>> maxAndMinDate;

    /**
     * 监测指标平均值集合 ：list<object> 第一个值为 日期 第二个值 为平均值
     */
    private List<List<Object>> aveDatas;

    /**
     * 判断是否有数据的 属性 ：true：图表有数据   false ：图表无数据  所有数据都为 null 的数据
     */
    private boolean hasData;

    /**
     * 目标值
     */
    private float target;

    /**
     * 浮动值
     */
    private float floating;

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

    public List<List<Object>> getAveDatas() {
        return aveDatas;
    }

    public void setAveDatas(List<List<Object>> aveDatas) {
        this.aveDatas = aveDatas;
    }

    public List<List<Object>> getMaxAndMinDate() {
        return maxAndMinDate;
    }

    public void setMaxAndMinDate(List<List<Object>> maxAndMinDate) {
        this.maxAndMinDate = maxAndMinDate;
    }

    public int getSensorPhysicalid() {
        return sensorPhysicalid;
    }

    public void setSensorPhysicalid(int sensorPhysicalid) {
        this.sensorPhysicalid = sensorPhysicalid;
    }

    public boolean isHasData() {
        return hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
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
