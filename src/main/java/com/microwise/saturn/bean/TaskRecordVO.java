package com.microwise.saturn.bean;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 任务记录vo
 */
public class TaskRecordVO {

    /**
     * uuid
     */
    private String id;

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 重要性
     */
    private int important;

    /**
     * 记录时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date recordDate;

    /**
     * 记录内容
     */
    private String recordContent;

    /**
     * 记录人名称
     */
    private String recordUserName;

    /**
     * 处理时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date handleDate;

    /**
     * 处理结果
     */
    private String handleResult;

    /**
     * 处理人名称
     */
    private String handleUserName;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getImportant() {
        return important;
    }

    public void setImportant(int important) {
        this.important = important;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getRecordContent() {
        return recordContent;
    }

    public void setRecordContent(String recordContent) {
        this.recordContent = recordContent;
    }

    public String getRecordUserName() {
        return recordUserName;
    }

    public void setRecordUserName(String recordUserName) {
        this.recordUserName = recordUserName;
    }

    public Date getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(Date handleDate) {
        this.handleDate = handleDate;
    }

    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getHandleUserName() {
        return handleUserName;
    }

    public void setHandleUserName(String handleUserName) {
        this.handleUserName = handleUserName;
    }
}
