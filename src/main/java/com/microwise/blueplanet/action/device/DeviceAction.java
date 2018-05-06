package com.microwise.blueplanet.action.device;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.MethodType;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.vo.DeviceVO;
import com.microwise.blueplanet.service.DeviceService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 设备
 *
 * @author gaohui
 * @date 13-2-21 17:53
 */
@Beans.Action
@Blueplanet
public class DeviceAction {

    @Autowired
    private DeviceService deviceService;

    //input
    private String deviceId;
    private DeviceVO device;

    @Route(value = "/blueplanet/device/{deviceId}.json", method = MethodType.GET)
    public String oneDevice() {
        device = deviceService.findDeviceById(deviceId);
        return Results.json().root("device").done();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public DeviceVO getDevice() {
        return device;
    }

    public void setDevice(DeviceVO device) {
        this.device = device;
    }
}
