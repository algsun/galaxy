package com.microwise.blueplanet.bean.vo;

import java.util.Date;
import java.util.List;

/**
 * 报警记录vo
 *
 * @author liuzhu
 * @date 14-3-11
 */
public class AlarmRecordVO {

    /**
     * 自增id
     */
    private String id;

    /**
     * 监测指标id
     */
    private int sensorId;

    /**
     * 因素
     */
    private String factor;

    /**
     * 措施id
     */
    private String measureId;

    /**
     * 站点id
     */
    private String siteId;

    /**
     * 报警时间
     */
    private Date alarmTime;

    /**
     * 区域id
     */
    private String zoneId;

    /**
     * 区域名称
     */
    private String locationName;

    /**
     * 措施描述
     */
    private String measureDescription;

    /**
     * 报警区域list
     */
    private List<MeasureVO> measureVOs;


    public Date getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getFactor() {
        return factor;
    }

    public void setFactor(String factor) {
        this.factor = factor;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getMeasureDescription() {
        return measureDescription;
    }

    public void setMeasureDescription(String measureDescription) {
        this.measureDescription = measureDescription;
    }

    public List<MeasureVO> getMeasureVOs() {
        return measureVOs;
    }

    public void setMeasureVOs(List<MeasureVO> measureVOs) {
        this.measureVOs = measureVOs;
    }

    public String getMeasureId() {
        return measureId;
    }

    public void setMeasureId(String measureId) {
        this.measureId = measureId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
