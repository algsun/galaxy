package com.microwise.cybertron.bean;

/**
 * 系统配置实体类
 *
 * @author li.jianfei
 * @date 2014-07-16
 */
public class Config {

    /**
     * UUID
     */
    private String uuid;

    /**
     * 站点id
     */
    private String siteId;

    /**
     * 档案全宗号（官方指定）
     */
    private String officialId;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getOfficialId() {
        return officialId;
    }

    public void setOfficialId(String officialId) {
        this.officialId = officialId;
    }
}
