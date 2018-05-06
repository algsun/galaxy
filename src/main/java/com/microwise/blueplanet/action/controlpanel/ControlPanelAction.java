package com.microwise.blueplanet.action.controlpanel;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.po.AliasPO;
import com.microwise.blueplanet.bean.po.Switches;
import com.microwise.blueplanet.bean.vo.DeviceVO;
import com.microwise.blueplanet.service.DeviceService;
import com.microwise.blueplanet.service.SwitchService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author gaohui
 * @date 14-2-13 15:03
 */
@Beans.Action
@Blueplanet
@Route("/blueplanet")
public class ControlPanelAction {
    public static final String _pagePath = "index.ftl";

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private SwitchService switchService;

    //outout
    private List<DeviceVO> devices;
    private Map<String,Switches> switchesMap;
    private Map<String,List<AliasPO>> aliasMap;

    @Route("control-panel")
    public String index(){
        String siteId = Sessions.createByAction().currentSiteId();
        devices = deviceService.findDevicesByType(siteId, DeviceVO.DEVICE_TYPE_CONTROL_MODUlE);
        switchesMap = switchService.findSwitchesBySiteId(siteId);
        aliasMap=switchService.findAliasBySite(siteId,DeviceVO.DEVICE_TYPE_CONTROL_MODUlE);
        ActionMessage.createByAction().consume();
        return Results.ftl("/blueplanet/pages/controlpanel/layout");
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public List<DeviceVO> getDevices() {
        return devices;
    }

    public Map<String, Switches> getSwitchesMap() {
        return switchesMap;
    }

    public void setSwitchesMap(Map<String, Switches> switchesMap) {
        this.switchesMap = switchesMap;
    }

    public Map<String, List<AliasPO>> getAliasMap() {
        return aliasMap;
    }

    public void setAliasMap(Map<String, List<AliasPO>> aliasMap) {
        this.aliasMap = aliasMap;
    }
}
