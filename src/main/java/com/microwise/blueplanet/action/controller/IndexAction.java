package com.microwise.blueplanet.action.controller;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.collect.Lists;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.po.LocationPO;
import com.microwise.blueplanet.bean.vo.AlarmRecordVO;
import com.microwise.blueplanet.bean.vo.DevicePropertyVO;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.service.AlarmService;
import com.microwise.blueplanet.service.DeviceService;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 调控主页
 */
@Beans.Action("controllerIndexAction")
@Blueplanet
public class IndexAction {

    public static final Logger log = LoggerFactory.getLogger(IndexAction.class);

    // 内容页面
    private static final String _pagePath = "index.ftl";

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private AlarmService alarmService;

    @Autowired
    private LocationService locationService;

    /**
     * 调控设备属性list
     */
    private List<DevicePropertyVO> devicePropertyVOList = Lists.newArrayList();

    /**
     * 报警记录list
     */
    private List<AlarmRecordVO> alarmRecords;

    /**
     * 设备类型 0-其他 1-恒湿机 2-空调
     */
    private static final int DEVICE_TYPE_OTHERS = 0;
    private static final int DEVICE_TYPE_HUMIDITY = 1;
    private static final int DEVICE_TYPE_AIRCONDITIONER = 2;

    private int deviceType = DEVICE_TYPE_HUMIDITY;

    private boolean hasAirConditioner;

    private boolean hasHumidity;

    @Route(value = "/blueplanet/controller/index")
    public String show() {
        try {
            List<String> nodeIds = deviceService.findControlDevices(Sessions.createByAction().currentSiteId(), deviceType);
            if (deviceType == DEVICE_TYPE_HUMIDITY) {
                hasHumidity = nodeIds.size() > 0 ? true : false;
                if (!hasHumidity) {
                    deviceType = DEVICE_TYPE_AIRCONDITIONER;
                    nodeIds = deviceService.findControlDevices(Sessions.createByAction().currentSiteId(), deviceType);
                }
                hasAirConditioner = deviceService.findControlDevices(Sessions.createByAction().currentSiteId(),
                        DEVICE_TYPE_AIRCONDITIONER).size() > 0 ? true : false;
            } else {
                hasAirConditioner = nodeIds.size() > 0 ? true : false;
                if (!hasAirConditioner) {
                    deviceType = DEVICE_TYPE_HUMIDITY;
                    nodeIds = deviceService.findControlDevices(Sessions.createByAction().currentSiteId(), deviceType);
                }
                hasHumidity = deviceService.findControlDevices(Sessions.createByAction().currentSiteId(),
                        DEVICE_TYPE_HUMIDITY).size() > 0 ? true : false;
            }
            for (String nodeId : nodeIds) {
                //封装数据
                DevicePropertyVO devicePropertyTemp = deviceService.findContents(nodeId, 5);
                LocationPO location = locationService.findLocationByNodeId(nodeId);
                devicePropertyTemp.setDeviceId(nodeId);
                devicePropertyTemp.setLocation(location);
                String faultCode = deviceService.findLastFaultCode(nodeId);
                devicePropertyTemp.setFaultCode(faultCode);
                devicePropertyVOList.add(devicePropertyTemp);
            }
            //报警记录
            if (deviceType == 1) {
                alarmRecords = alarmService.findRecordVOByPages(0, null, null, 1, 5, Sessions.createByAction().currentSiteId());
            }
        } catch (Exception e) {
            log.error("位置点概览", e);
        }
        return Results.ftl("/blueplanet/pages/controller/layout");
    }


    public static String get_pagePath() {
        return _pagePath;
    }

    public List<DevicePropertyVO> getDevicePropertyVOList() {
        return devicePropertyVOList;
    }

    public void setDevicePropertyVOList(List<DevicePropertyVO> devicePropertyVOList) {
        this.devicePropertyVOList = devicePropertyVOList;
    }

    public List<AlarmRecordVO> getAlarmRecords() {
        return alarmRecords;
    }

    public void setAlarmRecords(List<AlarmRecordVO> alarmRecords) {
        this.alarmRecords = alarmRecords;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public boolean getHasAirConditioner() {
        return hasAirConditioner;
    }

    public void setHasAirConditioner(boolean hasAirConditioner) {
        this.hasAirConditioner = hasAirConditioner;
    }

    public boolean getHasHumidity() {
        return hasHumidity;
    }

    public void setHasHumidity(boolean hasHumidity) {
        this.hasHumidity = hasHumidity;
    }
}
