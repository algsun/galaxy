package com.microwise.blueplanet.bean.vo;

import com.microwise.blueplanet.bean.po.SwitchDailyAction;
import com.microwise.blueplanet.bean.po.SwitchIntervalAction;

import java.util.List;

/**
 * 开关自动动作vo对象
 *
 * @author xuyuexi
 * @date 2014-3-7
 */
public class SwitchActionVO {

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 路数
     */
    private int route;

    /**
     * 每日定时动作
     */
    private List<SwitchDailyAction> switchDailyActions;

    /**
     * 间隔动作
     */
    private SwitchIntervalAction switchIntervalAction;

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

    public List<SwitchDailyAction> getSwitchDailyActions() {
        return switchDailyActions;
    }

    public void setSwitchDailyActions(List<SwitchDailyAction> switchDailyActions) {
        this.switchDailyActions = switchDailyActions;
    }

    public SwitchIntervalAction getSwitchIntervalAction() {
        return switchIntervalAction;
    }

    public void setSwitchIntervalAction(SwitchIntervalAction switchIntervalAction) {
        this.switchIntervalAction = switchIntervalAction;
    }
}
