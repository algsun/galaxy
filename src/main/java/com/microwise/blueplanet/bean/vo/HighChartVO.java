package com.microwise.blueplanet.bean.vo;

import java.util.List;
import java.util.Map;

/**
 * HighChart图表数据处理封装类（基础曲线图/降雨量柱状图/累计光照柱状图）
 *
 * @author zhangpeng
 * @date 2013-2-28
 * @check 2013-03-11 xubaoji svn:2014
 */
public class HighChartVO extends ChartVO {

    /**
     * locationId，用于和设备一起唯一确定一个图形
     */
    protected String locationId;

    /**
     * 图形类型column，spline
     */
    protected String type;

    /**
     * Y轴指标显示
     */
    protected String yText;

    /**
     * Y轴对应号码
     */
    protected int yAxis;

    /**
     * tooltip显示的数值单位，key为ySuffix，值为units；key为yDecimals的值为精度
     */
    protected Map<String, Object> tooltip;

    /**
     * 图形名称deviceName/deviceId（有名称时去名称，没有时取id） + "-" + sensorinfoName +
     * (sensorinfoUtil)
     */
    protected String name;

    /**
     * 图表数据 List<List<>> 中间的List对应：0 time 1 value
     */
    protected List<List<Object>> data;

    public HighChartVO() {
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getyText() {
        return yText;
    }

    public void setyText(String yText) {
        this.yText = yText;
    }

    public int getyAxis() {
        return yAxis;
    }

    public void setyAxis(int yAxis) {
        this.yAxis = yAxis;
    }

    public Map<String, Object> getTooltip() {
        return tooltip;
    }

    public void setTooltip(Map<String, Object> tooltip) {
        this.tooltip = tooltip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<List<Object>> getData() {
        return data;
    }

    public void setData(List<List<Object>> data) {
        this.data = data;
    }

}
