package com.microwise.phoenix.bean.po;

import java.util.Date;

/**
 * 用户登录实体
 *
 * @author xu.baoji
 * @date 2013-8-9
 */
public class UserLogin {

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户email
     */
    private String email;

    /**
     * 功能名称：登录或者退出
     */
    private String logName;

    /**
     * 功能操作时间
     */
    private Date logTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

}
