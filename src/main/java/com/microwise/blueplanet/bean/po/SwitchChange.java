package com.microwise.blueplanet.bean.po;

import java.util.Date;

/**
 * 控制模块开关状态变化
 * @author gaohui
 * @date 14-2-21 13:20
 */
public class SwitchChange {
    private String id;
    private String deviceId;
    private int route;
    private int action;

    private int enableBefore;
    private int switchBefore;
    private Date timestampBefore;

    private int enableAfter;
    private int switchAfter;
    private Date timestampAfter;
    private int actionDriver;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getRoute() {
        return route;
    }

    public void setRoute(int route) {
        this.route = route;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getEnableBefore() {
        return enableBefore;
    }

    public void setEnableBefore(int enableBefore) {
        this.enableBefore = enableBefore;
    }

    public int getSwitchBefore() {
        return switchBefore;
    }

    public void setSwitchBefore(int switchBefore) {
        this.switchBefore = switchBefore;
    }

    public Date getTimestampBefore() {
        return timestampBefore;
    }

    public void setTimestampBefore(Date timestampBefore) {
        this.timestampBefore = timestampBefore;
    }

    public int getEnableAfter() {
        return enableAfter;
    }

    public void setEnableAfter(int enableAfter) {
        this.enableAfter = enableAfter;
    }

    public int getSwitchAfter() {
        return switchAfter;
    }

    public void setSwitchAfter(int switchAfter) {
        this.switchAfter = switchAfter;
    }

    public Date getTimestampAfter() {
        return timestampAfter;
    }

    public void setTimestampAfter(Date timestampAfter) {
        this.timestampAfter = timestampAfter;
    }

    public int getActionDriver() {
        return actionDriver;
    }

    public void setActionDriver(int actionDriver) {
        this.actionDriver = actionDriver;
    }
}
