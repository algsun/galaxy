package com.microwise.blueplanet.bean.vo;

import java.util.List;

/**
 * 区域位置点关系实体
 *
 * @author wang.geng
 */
public class ZoneLocationVO {
    /** 区域名称 */
    private String zoneName;

    /** 位置点集合 */
    private List<LocationVO> locationList;

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public List<LocationVO> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<LocationVO> locationList) {
        this.locationList = locationList;
    }
}
