package com.microwise.blueplanet.bean.po;

import java.util.Date;

/**
 * 位置点实体类
 *
 * @author li.jianfei
 * @date 2014-06-20
 */
public class LocationPO {

    /**
     * 位置点ID
     */
    private String id;

    /**
     * 位置点名称
     */
    private String locationName;

    /**
     * 实景图
     */
    private String photo;

    /**
     * 节点ID
     */
    private String nodeId;

    /**
     * 区域ID
     */
    private String zoneId;

    /**
     * 站点编号
     */
    private String siteId;


    /**
     * 位置点创建时间
     */
    private Date createTime;

    /**
     * 位置点类型 0:设备位置点，1：批次位置点.
     */
    private int type;
    /**
     * 备注
     */
    private String remark;
    /**
     * 经度
     */
    private Double lng;
    /**
     * 维度
     */
    private Double lat;



    public LocationPO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}
