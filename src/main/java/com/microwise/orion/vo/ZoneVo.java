package com.microwise.orion.vo;

/**
 * 提供 http 服务的zoneVo 对象
 *
 * @author xubaoji
 * @date 2013-5-21
 */
public class ZoneVo {

    /**
     * 区域编号
     */
    private String zoneId;

    /**
     * 区域名称
     */
    private String zoneName;

    /**
     * 站点编号
     */
    private String siteId;

    /**
     * 父区域 编号
     */
    private String parentId;

    /**
     *
     */
    public ZoneVo() {
        super();
    }

    /**
     * @param zoneId
     * @param zoneName
     * @param siteId
     * @param parentId
     */
    public ZoneVo(String zoneId, String zoneName, String siteId, String parentId) {
        super();
        this.zoneId = zoneId;
        this.zoneName = zoneName;
        this.siteId = siteId;
        this.parentId = parentId;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
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

}
