package com.microwise.blueplanet.action.controlpanel;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.po.SwitchChange;
import com.microwise.blueplanet.bean.vo.DeviceVO;
import com.microwise.blueplanet.service.DeviceService;
import com.microwise.blueplanet.service.SwitchService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.DateTimeUtil;
import com.microwise.common.util.PagingUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @author gaohui
 * @date 14-2-19 15:14
 */
@Beans.Action
@Blueplanet
@Route("/blueplanet")
public class ControlModuleActivitiesAction {
    public static final String _pagePath = "control-module-activities.ftl";
    public static final int SIZE_PER_PAGE = 10;

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private SwitchService switchService;

    //input
    private String deviceId;
    private int route = -1; // 默认
    private int switchAction = -1; // 默认
    private Date startTime;
    private Date endTime;
    private int page = 1;

    //output
    private DeviceVO device;
    private List<SwitchChange> switchChanges;
    private int pageSum;

    @Route("control-panel/{deviceId}/activities")
    public String activities(){
        initDate();

        device = deviceService.findDeviceById(deviceId);
        int count =switchService.findSwitchChangeCountByDeviceId(deviceId, startTime, endTime, route, switchAction);
        pageSum = PagingUtil.pagesCount(count, SIZE_PER_PAGE);
        switchChanges = switchService.findSwitchChangeByDeviceId(deviceId,
                startTime,
                endTime,
                route,
                switchAction,
                page,
                SIZE_PER_PAGE);
        return Results.ftl("/blueplanet/pages/controlpanel/layout");
    }

    private void initDate(){
        if(startTime == null || endTime == null){
            startTime = DateTimeUtil.startOfDay(new Date());
            endTime = DateTimeUtil.endOfDay(new Date());
        }
    }

    public static String get_pagePath() {
        return _pagePath;
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

    public int getPageSum() {
        return pageSum;
    }

    public void setPageSum(int pageSum) {
        this.pageSum = pageSum;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<SwitchChange> getSwitchChanges() {
        return switchChanges;
    }

    public void setSwitchChanges(List<SwitchChange> switchChanges) {
        this.switchChanges = switchChanges;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getRoute() {
        return route;
    }

    public void setRoute(int route) {
        this.route = route;
    }

    public int getSwitchAction() {
        return switchAction;
    }

    public void setSwitchAction(int switchAction) {
        this.switchAction = switchAction;
    }
}
