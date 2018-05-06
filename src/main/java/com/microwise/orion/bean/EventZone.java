package com.microwise.orion.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.microwise.blackhole.bean.User;
import com.microwise.proxima.bean.Zone;

import java.util.List;
import java.util.Set;

/**
 * 按库房 确认出库单 实体
 *
 * @author xubaoji
 * @date 2013-6-18
 */
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class EventZone {

    /**
     * 库房确认状态 通过常量
     */
    public static final int EVENT_ZONE_STATE_PASS = 1;
    /**
     * 库房确认状态不通过常量
     */
    public static final int EVENT_ZONE_STATE_NOT_PASS = 2;

    /**
     * id 编号
     */
    private Integer id;

    /**
     * 出库事件
     */
    private OutEvent outEvent;

    /**
     * 区域
     */
    private Zone zone;

    /**
     * 确认人 ，审核人
     */
    private User user;

    /**
     * 文物列表
     */
    private Set<Relic> relics;

    /**
     * 管理员用户
     */
    private List<User> users;

    /**
     * 确认状态 ： 0 未 确认 ，1 确认通过 ，2 确认不通过
     */
    private int state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OutEvent getOutEvent() {
        return outEvent;
    }

    public void setOutEvent(OutEvent outEvent) {
        this.outEvent = outEvent;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Relic> getRelics() {
        return relics;
    }

    public void setRelics(Set<Relic> relics) {
        this.relics = relics;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
