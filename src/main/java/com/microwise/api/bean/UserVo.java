package com.microwise.api.bean;

/**
 * Created by Administrator on 2017/8/28.
 */
public class UserVo {

    public UserVo(int userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
    }

    private int userId;

    private String username;

    private String email;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
