package com.microwise.blueplanet.bean.po;

/**
 * 控制模块周期动作对象
 *
 * @since 2014-03-05
 */
public class SwitchIntervalAction extends SwitchAction {

    public SwitchIntervalAction() {
        this.type = SwitchAction.TYPE_INTERVAL;
    }

    /**
     * 间隔时间 单位:秒
     */
    private int intervalTime;

    /**
     * 执行时间 单位:秒
     */
    private int executionTime;


    public int getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

}