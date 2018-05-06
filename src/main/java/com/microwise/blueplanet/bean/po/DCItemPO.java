package com.microwise.blueplanet.bean.po;

import java.io.Serializable;

/**
 * 数据中心布局控件实体类.
 *
 * @author wang.geng
 * @date 13-12-6 上午10:42
 */
public class DCItemPO implements Serializable{

    private static final long serialVersionUID = 2L;

    /**
     * 主键，自增，序号
     */
    protected int id;

    /**
     * 外键，关联到布局表的layoutId
     */
    protected String related_layoutId;

    /**
     * 控件选择器ID
     */
    protected String item_id;

    /**
     * 列坐标
     */
    protected int data_col;

    /**
     * 行坐标
     */
    protected int data_row;

    /**
     * 宽度
     */
    protected int data_sizex;

    /**
     * 高度
     */
    protected int data_sizey;

    /**
     * 控件类型  0:图表；1:幻灯片
     */
    protected int itemType;

    /**
     * 图形名称
     */
    protected String chartName;

    /**
     * 图表类型:1.实时图形;2.基础曲线图;
     *         3.降雨量图;4.累积光照图;5.风向玫瑰图
     */
    protected int chart_type;

    /**
     * 关联的设备ID
     */
    protected String locationId;

    /**
     * 监测指标ID，可以有多个，以逗号分隔
     */
    protected String sensorPhysicalid;

    /**
     * 时间参数 近几天
     */
    protected int dateNum;

    /**
     * 请求的url
     */
    private String url;

    /**
     * 序列化的参数
     */
    private String serializationParams;

    public String getChartName() {
        return chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    public DCItemPO(){
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRelated_layoutId() {
        return related_layoutId;
    }

    public void setRelated_layoutId(String related_layoutId) {
        this.related_layoutId = related_layoutId;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public int getData_col() {
        return data_col;
    }

    public void setData_col(int data_col) {
        this.data_col = data_col;
    }

    public int getData_row() {
        return data_row;
    }

    public void setData_row(int data_row) {
        this.data_row = data_row;
    }

    public int getData_sizex() {
        return data_sizex;
    }

    public void setData_sizex(int data_sizex) {
        this.data_sizex = data_sizex;
    }

    public int getData_sizey() {
        return data_sizey;
    }

    public void setData_sizey(int data_sizey) {
        this.data_sizey = data_sizey;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSerializationParams() {
        return serializationParams;
    }

    public void setSerializationParams(String serializationParams) {
        this.serializationParams = serializationParams;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
