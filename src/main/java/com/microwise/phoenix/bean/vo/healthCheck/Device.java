package com.microwise.phoenix.bean.vo.healthCheck;

import java.util.Date;

/**
 * 系统检测：环境监控异常设备实体
 *
 * @author xu.baoji
 * @date 2013-7-25
 */
public class Device {

    /**
     * 设备id
     */
    private String nodeId;

    /**
     * 设备名称
     */
    private String nodeName;

    /**
     * 设备类型：1：节点 2：中继 3:节点-主模块(可控) 4:节点-从模块(可控) 7：网关
     */
    private int nodeType;

    /**
     * 设备创建时间
     */
    private Date createTime;

    /**
     * 设备隶属区域名
     */
    private String roomName;

    /**
     * 设备异常状态：-1、超时, 0、正常, 1、低电压, 2、掉电 。不会有正常
     */
    private int anomaly;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public int getNodeType() {
        return nodeType;
    }

    public void setNodeType(int nodeType) {
        this.nodeType = nodeType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getAnomaly() {
        return anomaly;
    }

    public void setAnomaly(int anomaly) {
        this.anomaly = anomaly;
    }

}
