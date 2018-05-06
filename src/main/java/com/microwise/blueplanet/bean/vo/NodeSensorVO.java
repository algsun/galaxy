package com.microwise.blueplanet.bean.vo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author liuzhu
 * @date 13-10-30
 */
public class NodeSensorVO {

    /**
     * 设备id
     */
    private String nodeId;

    /**
     * 传感量标识
     */
    private Integer sensorPhysicalId;

    /**
     * 传感量中文名
     */
    private String sensorPhysicalCnName;

    /**
     * 传感量值
     */
    private String sensorPhysicalValue;

    /**
     * 数据采样时间
     */
    private Date stamp;

    /**
     * 单位
     */
    private String units;

    /**
     * 检测指标英文名
     */
    private String enName;

    /**
     * 检测指标中文名
     */
    private String cnName;

    /**
     * 设备名称
     */
    private String nodeName;

    /**
     * 监测指标的最大值
     */
    private String bigValue;

    /**
     * 监测指标的最小值
     */
    private String smallValue;

    /**
     * 监测值 map集合
     */
    private Map<Long, NodeSensorVO> sensorPhysicalValueMap;

    /**
     * nodeSensorVO集合
     */
    private List<NodeSensorVO> nodeSensorVOList;

    /**
     * 数据采样状态
     */
    private int state;

    /**
     * 设备类型
     */
    private int nodeType;

    /**
     * 是否是设备
     */
    private boolean isNode;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getSensorPhysicalId() {
        return sensorPhysicalId;
    }

    public void setSensorPhysicalId(Integer sensorPhysicalId) {
        this.sensorPhysicalId = sensorPhysicalId;
    }

    public String getSensorPhysicalCnName() {
        return sensorPhysicalCnName;
    }

    public void setSensorPhysicalCnName(String sensorPhysicalCnName) {
        this.sensorPhysicalCnName = sensorPhysicalCnName;
    }

    public String getSensorPhysicalValue() {
        return sensorPhysicalValue;
    }

    public void setSensorPhysicalValue(String sensorPhysicalValue) {
        this.sensorPhysicalValue = sensorPhysicalValue;
    }

    public Date getStamp() {
        return stamp;
    }

    public void setStamp(Date stamp) {
        this.stamp = stamp;
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

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getBigValue() {
        return bigValue;
    }

    public void setBigValue(String bigValue) {
        this.bigValue = bigValue;
    }

    public String getSmallValue() {
        return smallValue;
    }

    public void setSmallValue(String smallValue) {
        this.smallValue = smallValue;
    }

    public Map<Long, NodeSensorVO> getSensorPhysicalValueMap() {
        return sensorPhysicalValueMap;
    }

    public void setSensorPhysicalValueMap(Map<Long, NodeSensorVO> sensorPhysicalValueMap) {
        this.sensorPhysicalValueMap = sensorPhysicalValueMap;
    }

    public int getNodeType() {
        return nodeType;
    }

    public void setNodeType(int nodeType) {
        this.nodeType = nodeType;
    }

    public boolean isNode() {
        return isNode;
    }

    public void setNode(boolean node) {
        isNode = node;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<NodeSensorVO> getNodeSensorVOList() {
        return nodeSensorVOList;
    }

    public void setNodeSensorVOList(List<NodeSensorVO> nodeSensorVOList) {
        this.nodeSensorVOList = nodeSensorVOList;
    }
}
