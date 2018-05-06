package com.microwise.blueplanet.bean.vo;

import com.microwise.blueplanet.bean.po.AvgdataPO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 均峰值业务实体.
 *
 * @author wang.geng
 * @date 14-4-22 下午2:19
 */
public class LocationAvgdataVO {


    /**
     * 区域Id
     */
    private String zoneId;

    /**
     * 区域Id
     */
    private String zoneName;


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
     * 判断是否有数据: true 图表有数据  false 图表无数据
     */
    private boolean hasData;

    /**
     * 显示的日期类型，不同的监测指标各自是不同的
     */
    private int dateType;

    /**
     * 均峰值实际实体数据
     */
    private List<AvgdataPO> avgdataList;

    /**
     * 页面图表判断使用
     */
    private List<Date> dateList = new ArrayList<Date>();


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

    public boolean isHasData() {
        return hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }

    public int getDateType() {
        return dateType;
    }

    public void setDateType(int dateType) {
        this.dateType = dateType;
    }

    public List<AvgdataPO> getAvgdataList() {
        return avgdataList;
    }

    public void setAvgdataList(List<AvgdataPO> avgdataList) {
        this.avgdataList = avgdataList;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public List<Date> getDateList() {
        return dateList;
    }

    public void setDateList(List<Date> dateList) {
        this.dateList = dateList;
    }
}
