package com.microwise.blackhole.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.Set;

/**
 * 用户Bean
 *
 * @author zhangpeng
 * @date 2012-11-15
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "logicGroup", "logicGroups", "roles", "department"})
public class User {

    /**
     * 女
     */
    public static final int SEX_WOMAN = 1;

    /**
     * 男
     */
    public static final int SEX_MAN = 2;

    /**
     * 用户ID
     */
    private int id;

    /**
     * 归属站点
     */
    private LogicGroup logicGroup;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 性别
     */
    private int sex;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 认证标识
     */
    private String token;

    /**
     * 禁用状态
     */
    private boolean disable;

    /**
     * 是否激活
     */
    private boolean active;

    /**
     * 用户头像文件名
     */
    private String photo;

    /**
     * 用户角色
     */
    private Set<Role> roles;

    /**
     * 用户对应站点组
     */
    private Set<LogicGroup> logicGroups;

    /**
     * 用户对应部门
     */
    private Department department;

    /**
     * 空构造函数
     *
     * @author zhangpeng
     * @date 2012-11-28
     */
    public User() {
    }

    /**
     * 有参构造函数
     *
     * @param logicGroup 归属站点
     * @param email      用户邮箱
     * @param password   用户密码
     * @param createTime 创建时间
     * @param token      邮箱token
     * @param roles      用户角色
     * @author zhangpeng
     * @date 2012-11-28
     */
    public User(LogicGroup logicGroup, String email, String password, Date createTime,
                String token, Set<Role> roles) {
        this.logicGroup = logicGroup;
        this.email = email;
        this.password = password;
        this.createTime = createTime;
        this.token = token;
        this.roles = roles;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<LogicGroup> getLogicGroups() {
        return logicGroups;
    }

    public void setLogicGroups(Set<LogicGroup> logicGroups) {
        this.logicGroups = logicGroups;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

}
