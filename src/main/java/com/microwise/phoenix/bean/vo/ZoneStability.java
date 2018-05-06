package com.microwise.phoenix.bean.vo;

import java.util.List;

/**
 * 区域稳定性VO对象
 *
 * @author zhangpeng
 * @date 13-8-7
 * @check @wang.geng 2013年8月14日 #4915
 */
public class ZoneStability {

    /**
     * 区域id
     */
    private String zoneId;

    /**
     * 区域名称
     */
    private String zoneName;

    /**
     * 位置点id
     */
    private List<String> locationIds;

    /**
     * 区域稳定性，标准差
     */
    private float stability;

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

    public float getStability() {
        return stability;
    }

    public void setStability(float stability) {
        this.stability = stability;
    }

    public List<String> getLocationIds() {
        return locationIds;
    }

    public void setLocationIds(List<String> locationIds) {
        this.locationIds = locationIds;
    }
}
