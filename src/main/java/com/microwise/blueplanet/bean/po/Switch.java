package com.microwise.blueplanet.bean.po;

/**
 * @author gaohui
 * @date 14-2-14 17:30
 */
public class Switch {

    private int route;

    private boolean enable;

    private boolean onOff;

    public int getRoute() {
        return route;
    }

    public void setRoute(int route) {
        this.route = route;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isOnOff() {
        return onOff;
    }

    public void setOnOff(boolean onOff) {
        this.onOff = onOff;
    }
}
