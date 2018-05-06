package com.microwise.blueplanet.bean.po;

import java.sql.Time;

/**
 * 控制模块定时动作对象
 *
 * @date 2014-03-05
 */
public class SwitchDailyAction  extends SwitchAction{

    public SwitchDailyAction() {
        this.type = SwitchAction.TYPE_DAILY;
    }

    /**
     * 定时的时间
     */
    private Time time;

    /**
     * 动作 1开 0关
     */
    private int action;

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

}