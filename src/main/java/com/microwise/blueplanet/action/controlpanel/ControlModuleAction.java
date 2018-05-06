package com.microwise.blueplanet.action.controlpanel;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.po.AliasPO;
import com.microwise.blueplanet.bean.po.SwitchChange;
import com.microwise.blueplanet.bean.po.Switches;
import com.microwise.blueplanet.bean.vo.DeviceVO;
import com.microwise.blueplanet.service.DeviceService;
import com.microwise.blueplanet.service.SwitchService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.sys.freemarker.tools.LocaleBundleTools;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author gaohui
 * @date 14-2-17 14:55
 */
@Beans.Action
@Blueplanet
@Route("/blueplanet")
public class ControlModuleAction {
    public static final String _pagePath = "control-module.ftl";

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private SwitchService switchService;

    //input
    private String deviceId;

    //output
    private DeviceVO device;
    private List<DeviceVO> subDevices;
    private Switches switches;
    private List<SwitchChange> switchChanges;
    private List<AliasPO> aliasList;

    @Route("control-panel/{deviceId}")
    public String show() {
        device = deviceService.findDeviceById(deviceId);
        subDevices = deviceService.findSubDevicesByDeviceId(deviceId, LocaleBundleTools.appLocale());
        switches = switchService.findLastSwitchByDeviceId(deviceId);
        switchChanges = switchService.findRecentSwitchChangesByDeviceId(deviceId, 10);
        //xuyuexi 备注列表
        aliasList = switchService.findAliasByDeviceId(deviceId);
        return Results.ftl("/blueplanet/pages/controlpanel/layout");
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

    public DeviceVO getDevice() {
        return device;
    }

    public void setDevice(DeviceVO device) {
        this.device = device;
    }

    public Switches getSwitches() {
        return switches;
    }

    public void setSwitches(Switches switches) {
        this.switches = switches;
    }

    public List<DeviceVO> getSubDevices() {
        return subDevices;
    }

    public void setSubDevices(List<DeviceVO> subDevices) {
        this.subDevices = subDevices;
    }

    public List<SwitchChange> getSwitchChanges() {
        return switchChanges;
    }

    public void setSwitchChanges(List<SwitchChange> switchChanges) {
        this.switchChanges = switchChanges;
    }

    public List<AliasPO> getAliasList() {
        return aliasList;
    }

    public void setAliasList(List<AliasPO> aliasList) {
        this.aliasList = aliasList;
    }
}
