package com.microwise.blueplanet.bean.vo;

import java.util.Date;

/**
 * 拓扑图vo对象
 *
 * @author xiedeng
 * @date 2013-1-18
 * @check liuzhu 2013-10-11 #5894
 */
public class TopoViewVO {
    /**
     * 监测点编号
     */
    private String nodeId;
    /**
     * 设备类型
     */
    private int nodeType;
    /**
     * 监测点名称
     */
    private String nodeName;
    /**
     * 区域编号
     */
    private String zoomId;
    /**
     * 区域名称
     */
    private String zoomName;
    /**
     * 是否可控
     */
    private int isControl;

    /**
     * 自身节点号
     */
    private int selfIp;
    /**
     * 父节点号
     */
    private int parentIp;

    /**
     * 父节点号(5位字符串显示)
     */
    private String parentIpStr;

    /**
     * 间隔周期
     */
    private int interval;
    /**
     * 设备状态：-1、超时, 0、正常, 1、低电压, 2、掉电
     */
    private int anomaly;
    /**
     * 接收信号强度
     */
    private int rssi;
    /**
     * 节点协议版本号
     */
    private int nodeVersion;

    /**
     * 连接质量参数
     */
    private int lqi;

    /**
     * 子设备数量
     */
    private int childrenCount;

    /**
     * 预计包数
     */
    private int expectedCount;

    /**
     * 实际包数
     */
    private int actualCount;

    /**
     * 时间戳
     */
    private Date stamp;

    /**
     * 设备创建时间
     */
    private Date createTime;

    /**
     * 丢包率
     */
    private float loseRate;

    public Date getStamp() {
        return stamp;
    }

    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }

    public String getParentIpStr() {
        return parentIpStr;
    }

    public void setParentIpStr(String parentIpStr) {
        this.parentIpStr = parentIpStr;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public int getActualCount() {
        return actualCount;
    }

    public void setActualCount(int actualCount) {
        this.actualCount = actualCount;
    }

    public int getNodeType() {
        return nodeType;
    }

    public void setNodeType(int nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getZoomId() {
        return zoomId;
    }

    public void setZoomId(String zoomId) {
        this.zoomId = zoomId;
    }

    public String getZoomName() {
        return zoomName;
    }

    public void setZoomName(String zoomName) {
        this.zoomName = zoomName;
    }

    public int getControl() {
        return isControl;
    }

    public void setControl(int control) {
        isControl = control;
    }

    public int getParentIp() {
        return parentIp;
    }

    public int getSelfIp() {
        return selfIp;
    }

    public void setSelfIp(int selfIp) {
        this.selfIp = selfIp;
    }

    public void setParentIp(int parentIp) {
        this.parentIp = parentIp;
    }

    public int getExpectedCount() {
        return expectedCount;
    }

    public void setExpectedCount(int expectedCount) {
        this.expectedCount = expectedCount;
    }

    public int getAnomaly() {
        return anomaly;
    }

    public void setAnomaly(int anomaly) {
        this.anomaly = anomaly;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public int getLqi() {
        return lqi;
    }

    public void setLqi(int lqi) {
        this.lqi = lqi;
    }

    public int getNodeVersion() {
        return nodeVersion;
    }

    public void setNodeVersion(int nodeVersion) {
        this.nodeVersion = nodeVersion;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }

    public float getLoseRate() {
        return loseRate;
    }

    public void setLoseRate(float loseRate) {
        this.loseRate = loseRate;
    }


}
