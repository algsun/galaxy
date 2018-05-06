package com.microwise.blueplanet.bean.vo;

import com.microwise.blueplanet.bean.po.ZonePO;

import java.util.List;

/**
 * 区域vo对象
 *
 * @author zhangpeng
 * @date 2013-1-17
 */
public class ZoneVO extends ZonePO {

    private static final long serialVersionUID = -4272711523771665672L;
    /**
     * 平面图上X坐标
     */
    private float coordinateX = -1;

    /**
     * 平面图上 Y 坐标
     */
    private float coordinateY = -1;

    /**
     * 区域下的区域集合
     */
    private List<ZoneVO> zones;

    /**
     * 区域下的位置点集合
     */
    private List<LocationVO> locations;

    /**
     * 区域下的设备集合
     */
    private List<DeviceVO> devices;

    /**
     * 区域下的措施
     */
    private List<MeasureVO> measureVOs;

    /**
     * 区域阈值list
     */
    private List<ZoneThresholdVO> thresholdVOs;

    public ZoneVO() {
    }

    public ZoneVO(String zoneId) {
        this.zoneId = zoneId;
    }

    public ZoneVO(String zoneId, String parentId) {
        this.zoneId = zoneId;
        this.parentId = parentId;
    }

    public List<ZoneVO> getZones() {
        return zones;
    }

    public void setZones(List<ZoneVO> zones) {
        this.zones = zones;
    }

    public List<LocationVO> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationVO> locations) {
        this.locations = locations;
    }

    public List<DeviceVO> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceVO> devices) {
        this.devices = devices;
    }

    @Override
    public String toString() {
        return "ZoneVO{" +
                "zones=" + zones +
                ", devices=" + devices +
                '}'
                + super.toString();
    }

    public List<MeasureVO> getMeasureVOs() {
        return measureVOs;
    }

    public void setMeasureVOs(List<MeasureVO> measureVOs) {
        this.measureVOs = measureVOs;
    }

    public List<ZoneThresholdVO> getThresholdVOs() {
        return thresholdVOs;
    }

    public void setThresholdVOs(List<ZoneThresholdVO> thresholdVOs) {
        this.thresholdVOs = thresholdVOs;
    }

    public float getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(float coordinateX) {
        this.coordinateX = coordinateX;
    }

    public float getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(float coordinateY) {
        this.coordinateY = coordinateY;
    }
}