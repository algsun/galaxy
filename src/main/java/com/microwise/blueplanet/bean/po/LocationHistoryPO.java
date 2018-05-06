package com.microwise.blueplanet.bean.po;

import java.util.Date;

/**
 * 位置点绑定历史实体
 *
 * @author liuzhu
 * @date 2014-6-26
 */
public class LocationHistoryPO {

    /**
     * uuid
     */
    private String id;

    /**
     * 位置点id
     */
    private String locationId;

    /**
     * 设备id
     */
    private String nodeId;

    /**
     * 绑定开始时间
     */
    private Date startTime;

    /**
     * 绑定结束时间
     */
    private Date endTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
