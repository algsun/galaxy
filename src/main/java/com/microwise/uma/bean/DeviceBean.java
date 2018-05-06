package com.microwise.uma.bean;

import java.util.Date;

/**
 * 设备 实体
 *
 * @author li.jianfei
 * @date 2013-4-16
 */
public class DeviceBean {

    // 设备id
    private Integer id;

    // 设备类型 1：读卡器 2：激发器 3：电子标签
    private Integer type;

    // 读卡器ID
    private String deviceId;

    // 设备名称
    private String name;

    // 设备编号 SN编号
    private String sn;

    // 读卡器IP
    private String ip;

    // 读卡器端口号
    private int port;

    // 设备电量 （电子卡）
    private float voltage;

    // 设备区域编号
    private String zoneId;

    // 设备隶属区域
    private String zoneName;

    // 读卡器工作状态
    private boolean state;

    // 是否启用
    private boolean enable;

    // 设备 创建时间
    private Date createTime;

    // 最后一次工作时间
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public float getVoltage() {
        return voltage;
    }

    public void setVoltage(float voltage) {
        this.voltage = voltage;
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

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
