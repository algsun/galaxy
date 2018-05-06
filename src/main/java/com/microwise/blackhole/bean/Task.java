package com.microwise.blackhole.bean;

import java.util.Date;
import java.util.Set;

/**
 * 任务 实体
 *
 * @author xubaoji
 * @date 2013-6-8
 */
public class Task {

    /**
     * 任务id主键
     */
    private Integer id;

    /**
     * 任务标题
     */
    private String taskTitle;

    /**
     * 任务描述信息
     */
    private String taskInfo;

    /**
     * 发布时间
     */
    private Date releaseDate;

    /**
     * 截至时间
     */
    private Date endDate;

    /**
     * 发布人
     */
    private User releaser;

    /**
     * 接受人 列表
     */
    private Set<User> designees;

    /**
     * 完成进度 此处为int 数字 ，页面需展示 百分比 如 20%
     */
    private Integer completeStatus;

    /**
     * 任务状态 false：未结束 true ：已结束
     */
    private boolean state;

    /**
     * 站点编号
     */
    private int logicGroupId;

    /**
     * 任务回复记录实体列表 按回复记录时间排序
     */
    private Set<TaskRecord> taskRecords;

    public Task() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(String taskInfo) {
        this.taskInfo = taskInfo;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public User getReleaser() {
        return releaser;
    }

    public void setReleaser(User releaser) {
        this.releaser = releaser;
    }

    public Set<User> getDesignees() {
        return designees;
    }

    public void setDesignees(Set<User> designees) {
        this.designees = designees;
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

    public void setTaskRecords(Set<TaskRecord> taskRecords) {
        this.taskRecords = taskRecords;
    }

    public Set<TaskRecord> getTaskRecords() {
        return taskRecords;
    }

    public int getLogicGroupId() {
        return logicGroupId;
    }

    public void setLogicGroupId(int logicGroupId) {
        this.logicGroupId = logicGroupId;
    }

}
