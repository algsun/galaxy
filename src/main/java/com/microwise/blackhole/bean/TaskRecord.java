package com.microwise.blackhole.bean;

import java.util.Date;

/**
 * 任务回复记录实体
 *
 * @author xubaoji
 * @date 2013-6-8
 */
public class TaskRecord {

    /**
     * id编号
     */
    private Integer id;

    /**
     * 任务实体
     */
    private Task task;

    /**
     * 回复人
     */
    private User replier;

    /**
     * 回复信息描述
     */
    private String recordInfo;

    /**
     * 回复记录时间
     */
    private Date recordDate;

    /**
     * 截至时间
     */
    private Date endDate;

    /**
     * 任务完成进度
     */
    private Integer completeStatus;

    /**
     * 任务状态 false：未结束 true ：结束
     */
    private boolean state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public User getReplier() {
        return replier;
    }

    public void setReplier(User replier) {
        this.replier = replier;
    }

    public String getRecordInfo() {
        return recordInfo;
    }

    public void setRecordInfo(String recordInfo) {
        this.recordInfo = recordInfo;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getCompleteStatus() {
        return completeStatus;
    }

    public void setCompleteStatus(Integer completeStatus) {
        this.completeStatus = completeStatus;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

}
