package com.microwise.blueplanet.bean.po;

import java.util.List;

/**
 * @author xuyuexi
 * @date 14-4-24
 */
public class NotificationPO {

    /**
     * uuid主键
     */
    private String id;

    /**
     * siteId
     */
    private String siteId;

    /**
     * 用户id
     */
    private int userId;

    /**
     * 全部设备或者自定义设备，全部设备存1，自定义设备存2
     */
    private int subscribeType;

    /**
     * 发送事件：1电池供电，2开关切换，3全选
     */
    private int triggerEvent;

    /**
     * 通知方式：1短信，2邮件，3全选
     */
    private int notifyMethod;

    /**
     * 选择的控制模块
     */
    private List<String> deviceIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSubscribeType() {
        return subscribeType;
    }

    public void setSubscribeType(int subscribeType) {
        this.subscribeType = subscribeType;
    }

    public int getTriggerEvent() {
        return triggerEvent;
    }

    public void setTriggerEvent(int triggerEvent) {
        this.triggerEvent = triggerEvent;
    }

    public int getNotifyMethod() {
        return notifyMethod;
    }

    public void setNotifyMethod(int notifyMethod) {
        this.notifyMethod = notifyMethod;
    }

    public List<String> getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(List<String> deviceIds) {
        this.deviceIds = deviceIds;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }
}
