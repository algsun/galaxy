package com.microwise.orion.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * 出库事件 相关文物实体
 *
 * @author xubaoji
 * @date 2013-5-25
 * @check 2013-06-04 zhangpeng svn:3966
 */
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class EventRelic {
    /**
     * 在库
     */
    public static final int STATE_EXIST = 1;

    /**
     * id 编号
     */
    private Integer id;

    /**
     * 出库时间
     */
    private Date outDate;

    /**
     * 入库时间
     */
    private Date inDate;

    /**
     * 1.在库 0.未确认(默认)
     */
    private int state;

    /**
     * 对应文物
     */
    private Relic relic;

    /**
     * 出库事件
     */
    private OutEvent outEvent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Relic getRelic() {
        return relic;
    }

    public void setRelic(Relic relic) {
        this.relic = relic;
    }

    public OutEvent getOutEvent() {
        return outEvent;
    }

    public void setOutEvent(OutEvent outEvent) {
        this.outEvent = outEvent;
    }

}
