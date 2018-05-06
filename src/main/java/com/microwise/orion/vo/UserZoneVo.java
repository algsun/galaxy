package com.microwise.orion.vo;

import com.microwise.blackhole.bean.User;
import com.microwise.orion.bean.Relic;
import com.microwise.proxima.bean.Zone;

import java.util.List;

/**
 * 用户区域关系vo 实体
 *
 * @author xubaoji
 * @date 2013-6-17
 */
public class UserZoneVo {

    /**
     * 区域实体
     */
    private Zone zone;

    /**
     * 区域管理员用户实体列表
     */
    private List<User> users;

    /**
     * 出库 选择 的当前有管理员的区域里下的文物
     */
    private List<Relic> relics;

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Relic> getRelics() {
        return relics;
    }

    public void setRelics(List<Relic> relics) {
        this.relics = relics;
    }

}
