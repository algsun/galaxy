package com.microwise.blueplanet.action.device.manage;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.vo.DeviceVO;
import com.microwise.blueplanet.service.DeviceService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 设备详细信息 ajax
 *
 * @author Wang Yunlong
 * @date 2013-04-16 15:00
 */
@Beans.Action
@Blueplanet
public class DeviceDetailJsonAction extends BlueplanetLoggerAction {

    @Autowired
    private DeviceService deviceService;

    //input, output
    /**
     * 设备Id
     */
    private String deviceId;


    //output
    /**
     * 设备
     */
    private Map<String, Object> device;

    /**
     * 设备详细信息
     */
    @Route("/blueplanet/device/{deviceId}/detail.json")
    public String show() {
        device = new HashMap<String, Object>();
        DeviceVO devicevo = deviceService.findDeviceById(deviceId);
        device.put("nodeId", devicevo.getNodeId());
        device.put("rssi", devicevo.getRssi());
        device.put("lqi", devicevo.getLqi());
        device.put("lowvoltage", devicevo.getLowvoltage());
        device.put("anomaly", devicevo.getAnomaly());
        device.put("stamp", devicevo.getStamp());
        log("设备管理", "设备详细信息");
        return Results.json().root("device").done();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Map<String, Object> getDevice() {
        return device;
    }

    public void setDevice(Map<String, Object> device) {
        this.device = device;
    }
}
