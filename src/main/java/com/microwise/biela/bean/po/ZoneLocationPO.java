package com.microwise.biela.bean.po;

import com.microwise.blueplanet.bean.vo.LocationVO;

import java.util.List;

/**
 * @author liuzhu
 * @date 14-1-9
 */
public class ZoneLocationPO {
    private String zoneName;
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

