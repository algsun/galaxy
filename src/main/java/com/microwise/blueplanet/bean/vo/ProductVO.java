package com.microwise.blueplanet.bean.vo;

/**
 * 产品基本信息
 *
 * @author li.jianfei
 * @date 2015-01-15
 */
public class ProductVO {

    /**
     * 站点id
     */
    private String siteId;

    /**
     * 站点名称
     */
    private String siteName;

    /**
     * 产品序列号
     */
    private String serialNumber;

    /**
     * 设备类型
     */
    private int type;

    /**
     * 设备状态
     */
    private ProductStateVO state;

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ProductStateVO getState() {
        return state;
    }

    public void setState(ProductStateVO state) {
        this.state = state;
    }
}
