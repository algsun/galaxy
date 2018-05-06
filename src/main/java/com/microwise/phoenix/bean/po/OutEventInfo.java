package com.microwise.phoenix.bean.po;

import java.util.Date;

/***
 * 出库 记录信息
 *
 * @author xu.baoji
 * @date 2013-7-9
 */
public class OutEventInfo {

    /**
     * 出库事件名称
     */
    private String eventName;

    /**
     * 出库日期
     */
    private Date eventDate;

    /**
     * 出库数量
     */
    private Integer count;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
