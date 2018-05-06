package com.microwise.blueplanet.bean.po;

import java.io.Serializable;

/**
 * 数据对象
 *
 * @since 2013-01-07
 */
public class ZonePO implements Serializable {

    private static final long serialVersionUID = 135754419223441054L;

    /**
     * column t_zone.zoneId 区域uuid
     */
    protected String zoneId;

    /**
     * column t_zone.siteId 父id为0的表明是：监测区域
     */
    protected String siteId;

    /**
     * column t_zone.parentId 父id为null的表明是顶级区域
     */
    protected String parentId;

    /**
     * column t_zone.zoneName 监测区域平面部署图， 主要用于设备在平面图进行部署
     */
    protected String zoneName;

    /**
     * column t_zone.planImage 实景图
     */
    protected String planImage;

    /**
     * 区域位置属性 1-室内，2-室外，默认为室内
     */
    protected int position;

    /**
     * column t_zone.dataVersion 数据版本号
     */
    protected long dataVersion;

    public ZonePO() {
        super();
    }

    public ZonePO(String zoneId, String parentId) {
        this.zoneId = zoneId;
        this.parentId = parentId;
    }

    public ZonePO(String zoneId, String zoneName, String planImage) {
        this.zoneId = zoneId;
        this.zoneName = zoneName;
        this.planImage = planImage;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getPlanImage() {
        return planImage;
    }

    public void setPlanImage(String planImage) {
        this.planImage = planImage;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(long dataVersion) {
        this.dataVersion = dataVersion;
    }

    @Override
    public String toString() {
        return "ZonePO [zoneId=" + zoneId + ", siteId=" + siteId
                + ", parentId=" + parentId + ", zoneName=" + zoneName
                + ", planImage=" + planImage + ", dataVersion=" + dataVersion
                + "]";
    }

}