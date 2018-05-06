package com.microwise.blueplanet.bean.po;

import java.util.Date;

/**
 * 开关自动动作
 *
 * @author gaohui
 * @date 14-3-6 13:08
 */
public class SwitchAction {
    //type为1，则是定时动作
    public static final int TYPE_DAILY = 1;
    //type为2，则是间隔动作
    public static final int TYPE_INTERVAL = 2;

    /**
     * id uuid
     */
    protected String id;

    /**
     * 设备Id
     */
    protected String deviceId;

    /**
     * 路数
     */
    protected int route;

    /**
     * 执行动作 1开 0关
     */
    protected int action;

    /**
     * 最后修改时间
     */
    protected Date updateTime;

    /**
     * 类型, 此属性不入库
     */
    protected int type;

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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
