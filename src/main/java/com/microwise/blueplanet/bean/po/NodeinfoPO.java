package com.microwise.blueplanet.bean.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据对象
 *
 * @since 2013-01-07
 */
public class NodeinfoPO implements Serializable {

    private static final long serialVersionUID = 135754419031288743L;

    /**
     * column m_nodeinfo.nodeid 产品入网唯一标识
     */
    protected String nodeId;

    /**
     * column m_nodeinfo.nodeType 1：节点 2：中继 3:节点-主模块(可控) 4:节点-从模块(可控) 7：网关
     */
    protected Integer nodeType;

    /**
     * column m_nodeinfo.createTime 节点创建时间或更新时间，与原add_time字段合并 （记录生成后不可修改）
     */
    protected Date createTime;

    /**
     * column m_nodeinfo.X X轴坐标
     */
    protected Integer x;

    /**
     * column m_nodeinfo.Y Y轴坐标
     */
    protected Integer y;

    /**
     * column m_nodeinfo.Z Z轴坐标
     */
    protected Integer z;

    /**
     * column m_nodeinfo.siteId 设备对应站点
     */
    protected String siteId;

    /**
     * column m_nodeinfo.deviceImage 系统相对路径和名称
     */
    protected String deviceImage;

    /**
     * column m_nodeinfo.dataVersion 数据版本
     */
    protected Long dataVersion;

    /**
     * 电压阈值
     */
    protected  Float voltageThreshold;

    public NodeinfoPO() {
        super();
    }

    public NodeinfoPO(String nodeId, Long dataVersion) {
        this.nodeId = nodeId;
        this.dataVersion = dataVersion;
    }

    public NodeinfoPO(String nodeId, String deviceImage, Long dataVersion) {
        this.nodeId = nodeId;
        this.deviceImage = deviceImage;
        this.dataVersion = dataVersion;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getZ() {
        return z;
    }

    public void setZ(Integer z) {
        this.z = z;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getDeviceImage() {
        return deviceImage;
    }

    public void setDeviceImage(String deviceImage) {
        this.deviceImage = deviceImage;
    }

    public Long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Long dataVersion) {
        this.dataVersion = dataVersion;
    }

    public Float getVoltageThreshold() {
        return voltageThreshold;
    }

    public void setVoltageThreshold(Float voltageThreshold) {
        this.voltageThreshold = voltageThreshold;
    }

    @Override
    public String toString() {
        return "NodeinfoDO [nodeid=" + nodeId + ", nodeType=" + nodeType
                + ", createTime=" + createTime
                + ", x=" + x + ", y=" + y + ", z=" + z
                + ", siteId=" + siteId + ", deviceImage=" + deviceImage
                + ", dataVersion=" + dataVersion + "]";
    }

}