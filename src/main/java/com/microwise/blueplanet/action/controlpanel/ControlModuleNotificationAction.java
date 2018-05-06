package com.microwise.blueplanet.action.controlpanel;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.po.NotificationPO;
import com.microwise.blueplanet.bean.vo.DeviceVO;
import com.microwise.blueplanet.service.DeviceService;
import com.microwise.blueplanet.service.NotificationService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.ResourceBundleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * 控制面板通知设置action
 * @author xu.yuexi
 * @date 14-2-23
 */
@Beans.Action
@Blueplanet
@Route("/blueplanet")
public class ControlModuleNotificationAction extends BlueplanetLoggerAction {
    private static final Logger logger = LoggerFactory.getLogger(ControlModuleNotificationAction.class);

    public static final String _pagePath = "control-module-notification.ftl";

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private NotificationService notificationService;
    /**
     * 页面设备列表
     */
    private List<DeviceVO> devices;

    /**
     * 全部设备或者自定义设备，全部设备存1，自定义设备存2
     */
    private int subscribeType;

    /**
     * 选择的控制模块
     */
    private List<String> deviceList;

    /**
     * 发送事件：1电池供电，2开关切换，3全选
     */
    private List<Integer> triggerEvent;

    /**
     * 通知方式：1短信，2邮件，3全选
     */
    private List<Integer> notifyMethod;

    /**
     * 页面回显
     */
    private NotificationPO notification;

    @Route("control-panel/notification")
    public String view() {
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            //供页面选择list
            devices = deviceService.findDevicesByType(siteId, DeviceVO.DEVICE_TYPE_CONTROL_MODUlE);
            //页面回显的NotificationPO
            notification = notificationService.findNotificationByUserId(Sessions.createByAction().currentUser().getId(),Sessions.createByAction().currentSiteId());
            log("控制面板", "通知设置");
        } catch (Exception e) {
            logger.error("通知设置失败", e);
        }
        return Results.ftl("/blueplanet/pages/controlpanel/layout");
    }

    @Route("control-panel/notification/config")
    public String config() {
        try {
            NotificationPO notification = new NotificationPO();
            String uuid = UUID.randomUUID().toString();
            String siteId= Sessions.createByAction().currentSiteId();
            notification.setId(uuid);
            notification.setDeviceIds(deviceList);
            notification.setNotifyMethod(plus(notifyMethod));
            notification.setSubscribeType(subscribeType);
            notification.setTriggerEvent(plus(triggerEvent));
            int userId = Sessions.createByAction().currentUser().getId();
            notification.setUserId(userId);
            notification.setSiteId(siteId);
            notificationService.addNotification(notification,siteId);
            ActionMessage.createByAction().success(ResourceBundleUtil.getBundle().getString("blueplanet.action.controlpanel.settingsSuccess"));
            log("控制面板", "通知设置");
        } catch (Exception e) {
            ActionMessage.createByAction().fail(ResourceBundleUtil.getBundle().getString("blueplanet.action.controlpanel.settingsFails"));
            logger.error("通知设置失败", e);
        }

        return Results.redirect("/blueplanet/control-panel");
    }

    /**
     * 处理 发送事件和通知方式，
     * 发送事件：1电池供电，2开关切换，3全选
     * 通知方式：1短信，2邮件，3全选
     * @param list
     * @return
     */
    private int plus(List<Integer> list) {
        int j = 0;
        for (int i : list) {
            j += i;
        }
        return j;
    }


    public static String get_pagePath() {
        return _pagePath;
    }

    public List<DeviceVO> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceVO> devices) {
        this.devices = devices;
    }

    public int getSubscribeType() {
        return subscribeType;
    }

    public void setSubscribeType(int subscribeType) {
        this.subscribeType = subscribeType;
    }

    public List<String> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<String> deviceList) {
        this.deviceList = deviceList;
    }

    public List getTriggerEvent() {
        return triggerEvent;
    }

    public void setTriggerEvent(List triggerEvent) {
        this.triggerEvent = triggerEvent;
    }

    public List getNotifyMethod() {
        return notifyMethod;
    }

    public void setNotifyMethod(List notifyMethod) {
        this.notifyMethod = notifyMethod;
    }

    public NotificationPO getNotification() {
        return notification;
    }

    public void setNotification(NotificationPO notification) {
        this.notification = notification;
    }
}
