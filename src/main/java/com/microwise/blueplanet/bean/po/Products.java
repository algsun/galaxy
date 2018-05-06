package com.microwise.blueplanet.bean.po;

import java.util.List;

/**
 * 产品实体
 *
 * @author liuzhu
 * @date 15-1-21
 */
public class Products {

    /**
     * 站点id
     */
    private String siteId;

    /**
     * 站点名称
     */
    private String siteName;

    /**
     * 设备状态信息
     */
    private List<DeviceState> deviceStates;

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

    public List<DeviceState> getDeviceStates() {
        return deviceStates;
    }

    public void setDeviceStates(List<DeviceState> deviceStates) {
        this.deviceStates = deviceStates;
    }
}
