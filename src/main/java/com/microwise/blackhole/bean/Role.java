package com.microwise.blackhole.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;

/**
 * 角色Bean
 *
 * @author zhangpeng
 * @date 2012-11-15
 */
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Role {
    /**
     * 匿名用户
     */
    public static final int STATE_ANONYMITY = 1;

    /**
     * 流水号
     */
    private int id;

    /**
     * 角色所属logicGroup
     */
    private LogicGroup logicGroup;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 是否是管理员：0不是；1是；同一logicGroup只允许有一个管理员
     */
    private boolean manager;

    /**
     * 角色权限
     */
    private Set<Privilege> privileges;

    /**
     * 拥有该角色的用户
     */
    private Set<User> users;

    /**
     * 角色状态. 1.匿名用户
     */
    private int state;

    /**
     * 空构造函数
     *
     * @author zhangpeng
     * @date 2012-11-28
     */
    public Role() {
    }

    /**
     * 有参构造函数
     *
     * @param logicGroup 角色归属站点组
     * @param roleName   角色名称
     * @param manager    是否是管理员
     * @param privileges 角色权限列表
     * @author zhangpeng
     * @date 2012-11-28
     */
    public Role(LogicGroup logicGroup, String roleName, boolean manager,
                Set<Privilege> privileges) {
        this.logicGroup = logicGroup;
        this.roleName = roleName;
        this.manager = manager;
        this.privileges = privileges;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LogicGroup getLogicGroup() {
        return logicGroup;
    }

    public void setLogicGroup(LogicGroup logicGroup) {
        this.logicGroup = logicGroup;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    public Set<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Set<Privilege> privileges) {
        this.privileges = privileges;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
