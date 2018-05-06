package com.microwise.blueplanet.bean.vo;

import com.microwise.blueplanet.bean.po.LocationHistoryPO;

/**
 * 位置点绑定历史业务类
 *
 * @author liuzhu
 * @date 2014-6-26
 */
public class LocationHistoryVO extends LocationHistoryPO {
    /**
     * 位置点绑定设备
     */
    private DeviceVO device;

    public DeviceVO getDevice() {
        return device;
    }

    public void setDevice(DeviceVO device) {
        this.device = device;
    }
}
