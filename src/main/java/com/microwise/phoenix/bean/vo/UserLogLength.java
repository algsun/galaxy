package com.microwise.phoenix.bean.vo;

/**
 * 用户登录时长实体 ：实现排序
 *
 * @author xu.baoji
 * @date 2013-8-9
 */
public class UserLogLength {

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户登录时长：单位小时
     */
    private Float logLength;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Float getLogLength() {
        return logLength;
    }

    public void setLogLength(Float logLength) {
        this.logLength = logLength;
    }

}
