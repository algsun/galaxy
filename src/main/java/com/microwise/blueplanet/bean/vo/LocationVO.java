package com.microwise.blueplanet.bean.vo;

import com.microwise.blueplanet.bean.po.LocationPO;
import com.microwise.orion.bean.Relic;

import java.util.List;

/**
 * 位置点 业务类
 *
 * @author li.jianfei
 * @date 2014-06-20
 */
public class LocationVO extends LocationPO {

    /**
     * 是否区域的直接位置点
     */
    private boolean localLocation;

    /**
     * 在父区域平面部署图的X坐标
     */
    private float coordinateX = -1;

    /**
     * 在父区域平面部署图的Y坐标
     */
    private float coordinateY = -1;
    /**
     * 位置点绑定设备(当前)
     */
    private DeviceVO device;

    /**
     * 位置点区域
     */
    private ZoneVO zone;

    /**
     * 位置点图标
     */
    private String locationIcon;


    /**
     * 位置点的实时数据
     */
    private RealtimeDataVO realtimeData;

    /**
     * 位置点包含的所有监测指标
     */
    private List<SensorinfoVO> sensorInfoList;

    /**
     * 位置点监测文物
     */
    private List<Relic> relics;

    private List<Integer> sensorIdList;

    /**
     * 当日最新报警记录
     */
    private AlarmRecordVO recentAlarm;

    public boolean isLocalLocation() {
        return localLocation;
    }

    public void setLocalLocation(boolean localLocation) {
        this.localLocation = localLocation;
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

    public DeviceVO getDevice() {
        return device;
    }

    public void setDevice(DeviceVO device) {
        this.device = device;
    }

    public ZoneVO getZone() {
        return zone;
    }

    public void setZone(ZoneVO zone) {
        this.zone = zone;
    }

    public List<SensorinfoVO> getSensorInfoList() {
        return sensorInfoList;
    }

    public void setSensorInfoList(List<SensorinfoVO> sensorInfoList) {
        this.sensorInfoList = sensorInfoList;
    }

    public List<Integer> getSensorIdList() {
        return sensorIdList;
    }

    public String getLocationIcon() {
        return locationIcon;
    }

    public void setLocationIcon(String locationIcon) {
        this.locationIcon = locationIcon;
    }

    public void setSensorIdList(List<Integer> sensorIdList) {
        this.sensorIdList = sensorIdList;
    }

    public RealtimeDataVO getRealtimeData() {
        return realtimeData;
    }

    public void setRealtimeData(RealtimeDataVO realtimeData) {
        this.realtimeData = realtimeData;
    }

    public List<Relic> getRelics() {
        return relics;
    }

    public void setRelics(List<Relic> relics) {
        this.relics = relics;
    }

    public AlarmRecordVO getRecentAlarm() {
        return recentAlarm;
    }

    public void setRecentAlarm(AlarmRecordVO recentAlarm) {
        this.recentAlarm = recentAlarm;
    }
}
