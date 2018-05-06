package com.microwise.halley.bean.vo;

import java.util.Date;

/**
 * 外展时段划分 VO
 *
 * @author li.jianfei
 * @date 2013-10-31
 */
public class TimeIntervalVO {

    /**
     * 名称
     */
    private String name;

    /**
     * 开始时间
     */
    private Date beginTime;

    /**
     * 结束时间
     */
    private Date endTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
