package com.microwise.blueplanet.bean.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 控件图表条件表.
 *
 * @author wang.geng
 * @date 13-12-6 上午10:52
 */
public class DCConditionPO implements Serializable {

    private static final long serialVersionUID = 3L;

    /**
     * 主键，自增，序号
     */
    protected int id;

    /**
     * 关联的布局ID
     */
    protected String related_layout_id;

    /**
     * 外键，关联到控制表的选择器ID
     */
    protected String related_item_id;

    /**
     * 图表类型:1.实时图形;2.基础曲线图;
     * 3.降雨量图;4.累积光照图;5.风向玫瑰图
     */
    protected int chart_type;

    /**
     * 关联的设备ID
     */
    protected String locationId;

    /**
     * 基础曲线图的条件:起始时间
     */
    protected Date startTime;

    /**
     * 基础曲线图的条件:结束时间
     */
    protected Date endTime;

    /**
     * 监测指标ID，可以有多个，以逗号分隔
     */
    protected String sensorPhysicalid;

    /**
     * 时间参数 近几天
     */
    protected int dateNum;

    /**
     * 预留两个配置参数
     */
    protected String param1;
    protected int param2;

    /**
     * 请求的url
     */
    private String url;

    /**
     * 序列换的参数
     */
    private String serializationParam;

    public DCConditionPO() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRelated_layout_id() {
        return related_layout_id;
    }

    public void setRelated_layout_id(String related_layout_id) {
        this.related_layout_id = related_layout_id;
    }

    public String getRelated_item_id() {
        return related_item_id;
    }

    public void setRelated_item_id(String related_item_id) {
        this.related_item_id = related_item_id;
    }

    public int getChart_type() {
        return chart_type;
    }

    public void setChart_type(int chart_type) {
        this.chart_type = chart_type;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getSensorPhysicalid() {
        return sensorPhysicalid;
    }

    public void setSensorPhysicalid(String sensorPhysicalid) {
        this.sensorPhysicalid = sensorPhysicalid;
    }

    public int getDateNum() {
        return dateNum;
    }

    public void setDateNum(int dateNum) {
        this.dateNum = dateNum;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public int getParam2() {
        return param2;
    }

    public void setParam2(int param2) {
        this.param2 = param2;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSerializationParam() {
        return serializationParam;
    }

    public void setSerializationParam(String serializationParam) {
        this.serializationParam = serializationParam;
    }
}
