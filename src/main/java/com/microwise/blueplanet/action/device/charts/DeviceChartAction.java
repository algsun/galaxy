package com.microwise.blueplanet.action.device.charts;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.po.DeviceState;
import com.microwise.blueplanet.bean.vo.DeviceVO;
import com.microwise.blueplanet.service.DeviceService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 设备电压趋势图
 *
 * @author xuyeuxi
 * @date 15-2-28
 */
@Beans.Action
@Blueplanet
public class DeviceChartAction {
    public static final String _pagePath = "device-state.ftl";

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
    private DeviceVO device;

    /**
     * 历史数据
     */
    List<DeviceState> deviceStates;

    /**
     * 设备详细信息
     */
    @Route("/blueplanet/device/{deviceId}/device-state-chart")
    public String show() {
        device = deviceService.findDeviceById(deviceId);
        return Results.ftl("/blueplanet/pages/device/manage/device-manage-layout.ftl");
    }

    /**
     * 设备详细信息
     */
    @Route("/blueplanet/device/{deviceId}/device-state-chart.json")
    public String data() {
        Date startDate = DateTime.now().minusMonths(1).toDate();
        Date endDate = new Date();
        deviceStates = deviceService.findHistoryLowVoltage(deviceId, startDate, endDate);
        if (deviceStates.size() > 0) {
            return Results.json().asRoot(packageData(deviceStates)).done();
        } else {
            return Results.json().asRoot(null).done();
        }
    }

    private List<Map<String, Object>> packageData(List<DeviceState> deviceStates) {
        List list = new ArrayList<Map<String, Object>>();
        Map<String, Object> lowMap = new HashMap<String, Object>();
        List lowList = new ArrayList<List<Long>>();
        Map<String, Object> rssiMap = new HashMap<String, Object>();
        List rssiList = new ArrayList<List<Long>>();
        Map<String, Object> lqiMap = new HashMap<String, Object>();
        List lqiList = new ArrayList<List<Long>>();
        for (DeviceState deviceState : deviceStates) {
            List lowList1 = new ArrayList<Long>();
            lowList1.add(0, deviceState.getStamp().getTime());
            lowList1.add(1, deviceState.getLowVoltage());
            lowList.add(lowList1);
            List rssiList1 = new ArrayList<Long>();
            rssiList1.add(0, deviceState.getStamp().getTime());
            rssiList1.add(1, deviceState.getRssi());
            rssiList.add(rssiList1);
            List lqiList1 = new ArrayList<Long>();
            lqiList1.add(0, deviceState.getStamp().getTime());
            lqiList1.add(1, deviceState.getLqi());
            lqiList.add(lqiList1);
        }
        lowMap.put("name", "lowVoltage");
        lowMap.put("data", lowList);
        rssiMap.put("name", "rssi");
        rssiMap.put("data", rssiList);
        lqiMap.put("name", "lqi");
        lqiMap.put("data", lqiList);
        list.add(lowMap);
        list.add(rssiMap);
        list.add(lqiMap);
        return list;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public void setDevice(DeviceVO device) {
        this.device = device;
    }

    public DeviceVO getDevice() {
        return device;
    }

    public List<DeviceState> getDeviceStates() {
        return deviceStates;
    }

    public void setDeviceStates(List<DeviceState> deviceStates) {
        this.deviceStates = deviceStates;
    }
}
