package com.microwise.blueplanet.bean.po;

import java.io.Serializable;

/**
 * 数据对象
 *
 * @since 2013-01-07
 */
public class SitemapPO implements Serializable {

    private static final long serialVersionUID = 135754419210976836L;

    /**
     * column t_sitemap.siteId  站点唯一标识
     */
    private String siteId;

    /**
     * column t_sitemap.targetHost  目标主机地址(IP或域名)
     */
    private String targetHost;

    /**
     * column t_sitemap.siteIp  站点IP
     */
    private String siteIp;

    /**
     * column t_sitemap.isActive  网络状态-标识其是否加入网络:0未加入、1加入。
     */
    private Integer isActive;

    /**
     * column t_sitemap.isCurrentSite  是否当前站点：0不是，1是。
     */
    private Integer isCurrentSite;

    public SitemapPO() {
        super();
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getTargetHost() {
        return targetHost;
    }

    public void setTargetHost(String targetHost) {
        this.targetHost = targetHost;
    }

    public String getSiteIp() {
        return siteIp;
    }

    public void setSiteIp(String siteIp) {
        this.siteIp = siteIp;
    }

    public Integer getActive() {
        return isActive;
    }

    public void setActive(Integer active) {
        isActive = active;
    }

    public Integer getCurrentSite() {
        return isCurrentSite;
    }

    public void setCurrentSite(Integer currentSite) {
        isCurrentSite = currentSite;
    }

    @Override
    public String toString() {
        return "SitemapDO{" +
                "siteId='" + siteId + '\'' +
                ", targetHost='" + targetHost + '\'' +
                ", siteIp='" + siteIp + '\'' +
                ", isActive=" + isActive +
                ", isCurrentSite=" + isCurrentSite +
                '}';
    }
}