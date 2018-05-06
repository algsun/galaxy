package com.microwise.blackhole.bean;

/**
 * 物理站点Bean
 *
 * @author zhangpeng
 * @date 2012-11-15
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Site {

    /**
     * 物理站点唯一标识
     */
    private String siteId;

    /**
     * 物理站点名称
     */
    private String siteName;

    /**
     * 所属区域
     */
    private AreaCodeCN areacodeCN;

    /**
     * 百度地图：经度
     */
    private Double lngBaiDu;

    /**
     * 百度地图：纬度
     */
    private Double latBaiDu;

    /**
     * 谷歌地图：精度
     */
    private Double lngGoogle;

    /**
     * 谷歌地图：纬度
     */
    private Double latGoogle;

    /**
     * 高德地图：经度
     */
    private Double lngAmap;

    /**
     * 高德地图：纬度
     */
    private Double latAmap;

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public AreaCodeCN getAreacodeCN() {
        return areacodeCN;
    }

    public void setAreacodeCN(AreaCodeCN areacodeCN) {
        this.areacodeCN = areacodeCN;
    }

    public Double getLngBaiDu() {
        return lngBaiDu;
    }

    public void setLngBaiDu(Double lngBaiDu) {
        this.lngBaiDu = lngBaiDu;
    }

    public Double getLatBaiDu() {
        return latBaiDu;
    }

    public void setLatBaiDu(Double latBaiDu) {
        this.latBaiDu = latBaiDu;
    }

    public Double getLngGoogle() {
        return lngGoogle;
    }

    public void setLngGoogle(Double lngGoogle) {
        this.lngGoogle = lngGoogle;
    }

    public Double getLatGoogle() {
        return latGoogle;
    }

    public void setLatGoogle(Double latGoogle) {
        this.latGoogle = latGoogle;
    }

    public Double getLngAmap() {
        return lngAmap;
    }

    public void setLngAmap(Double lngAmap) {
        this.lngAmap = lngAmap;
    }

    public Double getLatAmap() {
        return latAmap;
    }

    public void setLatAmap(Double latAmap) {
        this.latAmap = latAmap;
    }
}
