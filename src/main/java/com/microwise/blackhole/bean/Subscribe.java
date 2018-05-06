package com.microwise.blackhole.bean;

/**
 * 用户订阅实体
 *
 * @author xubaoji
 * @date 2013-6-13
 */
public class Subscribe {

    /**
     * 周报表订阅类型
     */
    public static final int SUBSCRIBE_TYPE_WEEK = 1;

    /**
     * 综合分析月报表
     */
    public static final int SUBSCRIBE_TYPE_PHOENIX_MONTH = 2;

    /**
     * KDJ报警订阅
     */
    public static final int SUBSCRIBE_TYPE_KDJ_ALARM = 3;

    /**
     * id
     */
    private Integer id;

    /**
     * 用户
     */
    private User user;

    /**
     * 站点编号
     */
    private String siteId;

    /**
     * 订阅类型 1：周报表;2：月报表;3：KDJ报警
     */
    private Integer subscribeType;

    /**
     * KDJ报警订阅的位置点
     */
    private String locationId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getSubscribeType() {
        return subscribeType;
    }

    public void setSubscribeType(Integer subscribeType) {
        this.subscribeType = subscribeType;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }
}
