package com.microwise.blueplanet.bean.vo;

import com.microwise.blueplanet.bean.po.NodeinfoPO;

import java.util.Date;
import java.util.List;

/**
 * 设备vo对象
 *
 * @author zhangpeng
 * @date 2013-1-17
 */
public class DeviceVO extends NodeinfoPO {

    private static final long serialVersionUID = 6574279639411240897L;

    /**
     * 默认区域ID
     */
    public static final String DEFAULT_ZONE_ID = "0";

    /**
     * 设备类型：节点
     */
    public static final int DEVICE_TYPE_SENSOR = 1;
    /**
     * 设备类型：中继
     */
    public static final int DEVICE_TYPE_REPETER = 2;
    /**
     * 设备类型：主模块
     */
    public static final int DEVICE_TYPE_MASTER_MODULE = 3;
    /**
     * 设备类型：从模块
     */
    public static final int DEVICE_TYPE_SLAVE_MODULE = 4;
    /**
     * 设备类型：控制模块
     */
    public static final int DEVICE_TYPE_CONTROL_MODUlE = 5;
    /**
     * 设备类型：网关
     */
    public static final int DEVICE_TYPE_GATEWAY = 7;

    /**
     * 位置点ID
     */
    private String locationId;


    /**
     * 位置点
     */
    private LocationVO location;

    /**
     * 设备所拥有的监测指标标识列表
     */
    private List<Integer> sensorPhysicalidList;
    /**
     * 监测指标集合
     */
    private List<SensorinfoVO> sensors;

    /**
     * 设备接收信号强度
     */
    private Integer rssi;

    /**
     * 设备链路质量
     */
    private Integer lqi;

    /**
     * 时间戳
     */
    private Date stamp;

    /**
     * 设备电压 (默认为0), 4 种情况：
     * <ul>
     * <li>0：正常</li>
     * <li>1：低电压</li>
     * <li>2：掉电</li>
     * <li>其他(20~255)：Y=x/10(实际电压，保留小数点1位) 其他情况参考协议内容</li>
     * </ul>
     */
    private float lowvoltage;

    /**
     * 设备工作状态0：正常 1：异常 （是否按工作周期采集数据）
     */
    private Integer anomaly;

    /**
     * 设备工作模式0：正常模式 1：巡检模式
     */
    private Integer deviceMode;

    /**
     * 主模块下的从模块列表
     */
    private List<DeviceVO> slaveModuleList;

    /**
     * 父节点ip
     */
    private String parentIP;

    /**
     * 当前节点ip，对应数据库实时数据状态表（m_nodeinfomemory）childIP字段
     */
    private String currentIP;

    /**
     * 设备工作周期，单位：秒
     */
    private Integer interval;

    /**
     * 设备预热时间，用于限定设备工作周期（设备工作周期必须大于预热时间）
     */
    private Integer warmUp;
    /**
     * 是否不可控：true:不可控 false:可控
     */
    private boolean notControl;

    /**
     * 节点协议版本号
     */
    private int nodeVersion;

    /**
     * 产品序列号
     */
    private String sn;

    /**
     * 是否为当前区域的设备
     */
    private boolean isLocalDevice;

    /**
     * 湿度开关 0 关， 1 开
     */
    private int humCompensate;

    /**
     * 标定状态
     * 0-非标定
     * 1-标定
     */
    private int demarcate;

    /**
     * 设备屏幕开关状态
     * <p>
     * 0-关
     * 1-开
     */
    private int screenState;


    /**
     * 设备类型 0-其他 1-恒湿机 2-空调
     */
    private int deviceType;
    /**
     * 设备的实时数据
     */
    private RealtimeDataVO realtimeData;

    public DeviceVO() {
    }

    public int getHumCompensate() {
        return humCompensate;
    }

    public void setHumCompensate(int humCompensate) {
        this.humCompensate = humCompensate;
    }

    public DeviceVO(String nodeId) {
        this.nodeId = nodeId;
    }

    public DeviceVO(String nodeId, Long dataVersion) {
        super(nodeId, dataVersion);
    }

    public DeviceVO(String nodeId, String deviceImage, Long dataVersion) {
        super(nodeId, deviceImage, dataVersion);
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public LocationVO getLocation() {
        return location;
    }

    public void setLocation(LocationVO location) {
        this.location = location;
    }

    public List<Integer> getSensorPhysicalidList() {
        return sensorPhysicalidList;
    }

    public void setSensorPhysicalidList(List<Integer> sensorPhysicalidList) {
        this.sensorPhysicalidList = sensorPhysicalidList;
    }

    public Integer getRssi() {
        return rssi;
    }

    public void setRssi(Integer rssi) {
        this.rssi = rssi;
    }

    public Integer getLqi() {
        return lqi;
    }

    public void setLqi(Integer lqi) {
        this.lqi = lqi;
    }

    public Date getStamp() {
        return stamp;
    }

    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public float getLowvoltage() {
        return lowvoltage;
    }

    public void setLowvoltage(float lowvoltage) {
        this.lowvoltage = lowvoltage;
    }

    public Integer getAnomaly() {
        return anomaly;
    }

    public void setAnomaly(Integer anomaly) {
        this.anomaly = anomaly;
    }

    public Integer getDeviceMode() {
        return deviceMode;
    }

    public void setDeviceMode(Integer deviceMode) {
        this.deviceMode = deviceMode;
    }

    public List<DeviceVO> getSlaveModuleList() {
        return slaveModuleList;
    }

    public void setSlaveModuleList(List<DeviceVO> slaveModuleList) {
        this.slaveModuleList = slaveModuleList;
    }

    public String getParentIP() {
        return parentIP;
    }

    public void setParentIP(String parentIP) {
        this.parentIP = parentIP;
    }

    public String getCurrentIP() {
        return currentIP;
    }

    public void setCurrentIP(String currentIP) {
        this.currentIP = currentIP;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public boolean isNotControl() {
        return notControl;
    }

    public void setNotControl(boolean notControl) {
        this.notControl = notControl;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Integer getWarmUp() {
        return warmUp;
    }

    public void setWarmUp(Integer warmUp) {
        this.warmUp = warmUp;
    }

    @Override
    public String toString() {
        return "DeviceVO{" +
                "sensorPhysicalidList=" + sensorPhysicalidList +
                ", rssi=" + rssi +
                ", lqi=" + lqi +
                ", stamp=" + stamp +
                ", lowvoltage=" + lowvoltage +
                ", anomaly=" + anomaly +
                ", deviceMode=" + deviceMode +
                ", slaveModuleList=" + slaveModuleList +
                ", parentIP='" + parentIP + '\'' +
                ", currentIP='" + currentIP + '\'' +
                ", interval=" + interval +
                ", notControl=" + notControl +
                '}'
                + super.toString();
    }

    public int getNodeVersion() {
        return nodeVersion;
    }

    public void setNodeVersion(int nodeVersion) {
        this.nodeVersion = nodeVersion;
    }

    public RealtimeDataVO getRealtimeData() {
        return realtimeData;
    }

    public void setRealtimeData(RealtimeDataVO realtimeData) {
        this.realtimeData = realtimeData;
    }

    public List<SensorinfoVO> getSensors() {
        return sensors;
    }

    public void setSensors(List<SensorinfoVO> sensors) {
        this.sensors = sensors;
    }

    public boolean isLocalDevice() {
        return isLocalDevice;
    }

    public int getDemarcate() {
        return demarcate;
    }

    public void setDemarcate(int demarcate) {
        this.demarcate = demarcate;
    }

    public void setLocalDevice(boolean localDevice) {
        isLocalDevice = localDevice;
    }

    public int getScreenState() {
        return screenState;
    }

    public void setScreenState(int screenState) {
        this.screenState = screenState;
    }


}