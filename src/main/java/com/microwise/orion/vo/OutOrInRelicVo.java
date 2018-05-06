package com.microwise.orion.vo;

import java.util.Date;

/**
 * 文物 出入库vo 实体对象（用来接收文物出库 手持设备上传来的数据）
 *
 * @author xubaoji
 * @date 2013-5-23
 */
public class OutOrInRelicVo {

    /**
     * 出库单号
     */
    private String eventId;

    /**
     * 文物id
     */
    private int relicId;

    /**
     * 流程任务编号
     */
    private String taskId;

    /**
     * 出库时间
     */
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public int getRelicId() {
        return relicId;
    }

    public void setRelicId(int relicId) {
        this.relicId = relicId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
