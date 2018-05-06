package com.microwise.blackhole.bean;


/**
 * 用户个性化设备表
 *
 * @author zhangpeng
 * @date 2012-11-15
 */
public class Individuation {

    /**
     * 流水号
     */
    private int id;

    /**
     * 用户
     */
    private User user;

    /**
     * 个性化设置key
     */
    private String key;

    /**
     * 个性化设置value
     */
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
