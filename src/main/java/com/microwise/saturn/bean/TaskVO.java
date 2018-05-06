package com.microwise.saturn.bean;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 *
 */
public class TaskVO {

    /**
     * 自增id
     */
    private String id;

    /**
     * 站点id
     */
    private String siteId;

    /**
     * 标题
     */
    private String title;

    /**
     * 负责人
     */
    private String responsible;

    /**
     * 参与人
     */
    private String participate;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 预计开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date predictStart;

    /**
     * 预计结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date predictEnd;

    /**
     * 实际开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date realStart;

    /**
     * 实际结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date realEnd;

    /**
     * 任务描述
     */
    private String taskDescription;

    /**
     * 任务目标
     */
    private String taskTarget;

    /**
     * 需求保障
     */
    private String demand;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private int createUser;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    /**
     * 审核人
     */
    private int checkUser;

    /**
     * 审核时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date checkDate;

    /**
     * 作废人
     */
    private int invalidUser;

    /**
     * 作废时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date invalidDate;

    /**
     * 任务状态
     */
    private int state;

    /**
     * 负责人名称
     */
    private String userName;

    /**
     * 审核人名称
     */
    private String checkName;

    /**
     * 创建人名称
     */
    private String createName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPredictStart() {
        return predictStart;
    }

    public void setPredictStart(Date predictStart) {
        this.predictStart = predictStart;
    }

    public Date getPredictEnd() {
        return predictEnd;
    }

    public void setPredictEnd(Date predictEnd) {
        this.predictEnd = predictEnd;
    }

    public Date getRealStart() {
        return realStart;
    }

    public void setRealStart(Date realStart) {
        this.realStart = realStart;
    }

    public Date getRealEnd() {
        return realEnd;
    }

    public void setRealEnd(Date realEnd) {
        this.realEnd = realEnd;
    }


    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskTarget() {
        return taskTarget;
    }

    public void setTaskTarget(String taskTarget) {
        this.taskTarget = taskTarget;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(int checkUser) {
        this.checkUser = checkUser;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Date getInvalidDate() {
        return invalidDate;
    }

    public void setInvalidDate(Date invalidDate) {
        this.invalidDate = invalidDate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getParticipate() {
        return participate;
    }

    public void setParticipate(String participate) {
        this.participate = participate;
    }

    public int getCreateUser() {
        return createUser;
    }

    public void setCreateUser(int createUser) {
        this.createUser = createUser;
    }

    public int getInvalidUser() {
        return invalidUser;
    }

    public void setInvalidUser(int invalidUser) {
        this.invalidUser = invalidUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }
}
