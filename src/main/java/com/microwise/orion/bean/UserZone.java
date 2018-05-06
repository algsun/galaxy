package com.microwise.orion.bean;

/**
 * 用户和区域 对应关系表
 *
 * @author xubaoji
 * @date 2013-6-13
 */
public class UserZone {

    /**
     * id 编号
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 区域id
     */
    private String zoneId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

}
