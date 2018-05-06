package com.microwise.biela.bean.vo;

/**
 * @author liuzhu
 * @date 14-1-9
 */
public class CustomizeVO {

    /**
     * 自增id
     */
    private int id;

    /**
     * 站点id
     */
    private String siteId;

    /**
     * 设备id
     */
    private String locationId;

    /**
     * 监测指标id
     */
    private int sensorId;

    /**
     * 区域名称
     */
    private String zoneName;

    /**
     * 设备名称
     */
    private String locationName;

    /**
     * 监测指标名称
     */
    private String sensorName;

    /**
     * 定制备注
     */
    private String customizeRemark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getCustomizeRemark() {
        return customizeRemark;
    }

    public void setCustomizeRemark(String customizeRemark) {
        this.customizeRemark = customizeRemark;
    }

}
