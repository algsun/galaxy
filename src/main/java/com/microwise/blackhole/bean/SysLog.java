package com.microwise.blackhole.bean;

import java.util.Date;

/**
 * 系统日志Bean
 *
 * @author zhangpeng
 * @date 2012-11-15
 */
public class SysLog {

    /**
     * 流水号
     */
    private int id;

    /**
     * 操作用户名称
     */
    private String userName;

    /**
     * 操作用户邮箱
     */
    private String userEmail;

    /**
     * 操作站点
     */
    private int logicGroupId;

    /**
     * 站点名称
     */
    private String logicGroupName;

    /**
     * 业务子系统
     */
    private int subsystemId;

    /**
     * 日志名称
     */
    private String logName;

    /**
     * 日志内容
     */
    private String logContent;

    /**
     * 操作时间
     */
    private Date logTime;

    /**
     * 操作结果：0失败；1成功
     */
    private Boolean logState;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getLogicGroupId() {
        return logicGroupId;
    }

    public void setLogicGroupId(int logicGroupId) {
        this.logicGroupId = logicGroupId;
    }

    public String getLogicGroupName() {
        return logicGroupName;
    }

    public void setLogicGroupName(String logicGroupName) {
        this.logicGroupName = logicGroupName;
    }

    public int getSubsystemId() {
        return subsystemId;
    }

    public void setSubsystemId(int subsystemId) {
        this.subsystemId = subsystemId;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public Boolean getLogState() {
        return logState;
    }

    public void setLogState(Boolean logState) {
        this.logState = logState;
    }

}
