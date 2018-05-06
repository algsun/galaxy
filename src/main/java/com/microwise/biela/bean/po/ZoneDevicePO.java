package com.microwise.biela.bean.po;

import com.microwise.blueplanet.bean.vo.DeviceVO;

import java.util.List;

/**
 * @author liuzhu
 * @date 14-1-9
 */
public class ZoneDevicePO {
    private String zoneName;
    private List<DeviceVO> deviceVOList;

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public List<DeviceVO> getDeviceVOList() {
        return deviceVOList;
    }

    public void setDeviceVOList(List<DeviceVO> deviceVOList) {
        this.deviceVOList = deviceVOList;
    }
}

