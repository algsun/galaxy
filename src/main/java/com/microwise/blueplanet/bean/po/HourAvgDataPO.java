package com.microwise.blueplanet.bean.po;

import java.util.Date;


/**
 * 日统计实体
 *
 * @author 王耕
 * @date 15-8-27
 */
public class HourAvgDataPO {
    /** 主键，uuid */
    private String id;
    /** 监测指标ID */
    private int sensorPhysicalid;
    /** 平均值 */
    private double avgValue;
    /** 记录时间 */
    private Date msDatetime;
    /** 是否修改 */
    private int isupdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSensorPhysicalid() {
        return sensorPhysicalid;
    }

    public void setSensorPhysicalid(int sensorPhysicalid) {
        this.sensorPhysicalid = sensorPhysicalid;
    }

    public double getAvgValue() {
        return avgValue;
    }

    public void setAvgValue(double avgValue) {
        this.avgValue = avgValue;
    }

    public Date getMsDatetime() {
        return msDatetime;
    }

    public void setMsDatetime(Date msDatetime) {
        this.msDatetime = msDatetime;
    }

    public int getIsupdate() {
        return isupdate;
    }

    public void setIsupdate(int isupdate) {
        this.isupdate = isupdate;
    }
}
