package com.microwise.biela.bean.vo;

/**
 * 节点监测指标监测指标实体.
 *
 * @author wang.geng
 * @date 14-1-11  下午3:21
 */
public class NodeSensorInfoVO {

    /**
     * id
     */
    private int id;

    /**
     * 站点编号
     */
    private String siteId;

    /**
     * 区域编号
     */
    private int areaCode;

    /**
     * 检测指标ID
     */
    private int sensorPhysicalid;
    /**
     * 检测指标值
     */
    private String sensorPhysicalValue;
    /**
     * 单位
     */
    private String unit;

    /**
     * 监测指标英文名称
     */
    private String enName;

    /**
     * 检测指标中文名称
     */
    private String cnName;

    /**
     * 区域名称
     */
    private String zoneName;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 定制说明
     */
    private String customizeRemark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSensorPhysicalid() {
        return sensorPhysicalid;
    }

    public void setSensorPhysicalid(int sensorPhysicalid) {
        this.sensorPhysicalid = sensorPhysicalid;
    }

    public String getSensorPhysicalValue() {
        return sensorPhysicalValue;
    }

    public void setSensorPhysicalValue(String sensorPhysicalValue) {
        this.sensorPhysicalValue = sensorPhysicalValue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getCustomizeRemark() {
        return customizeRemark;
    }

    public void setCustomizeRemark(String customizeRemark) {
        this.customizeRemark = customizeRemark;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public int getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }
}
