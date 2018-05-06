package com.microwise.blackhole.bean;

import java.util.Comparator;
import java.util.Set;

/**
 * 系统权限Bean
 *
 * @author zhangpeng
 * @date 2012-11-15
 */
public class Privilege {

    /**
     * 根据 privileges.sequence 排序
     */
    public static class PrivilegeComparator implements Comparator<Privilege> {
        @Override
        public int compare(Privilege o1, Privilege o2) {
            int s1 = o1.getSequence();
            int s2 = o2.getSequence();

            // 如果 sequence 一致，则使用 privilegeId.hashCode() 排序
            if (s1 - s2 == 0) {
                return o1.getPrivilegeId().hashCode()
                        - o2.getPrivilegeId().hashCode();
            }

            return s1 - s2;
        }
    }

    /**
     * 权限ID
     */
    private String privilegeId;

    /**
     * 业务系统ID
     */
    private int subsystemId;

    /**
     * 权限名称
     */
    private String privilegeCnName;

    /**
     *权限英文名称
     * */
    private String privilegeEnName;

    /**
     * 父级权限
     */
    private Privilege parent;

    /**
     * url
     */
    private String url;

    /**
     * 分组序列
     */
    private int sequence;

    /**
     * 权限状态：1必选；2可选；3超级管理员独占
     */
    private int state;

    /**
     * 是否属于导航栏权限
     */
    private boolean navigation;

    /**
     * 子权限列表
     */
    private Set<Privilege> childPrivileges;

    /**
     * 是否被禁用
     */
    private boolean disable;

    public String getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(String privilegeId) {
        this.privilegeId = privilegeId;
    }

    public int getSubsystemId() {
        return subsystemId;
    }

    public void setSubsystemId(int subsystemId) {
        this.subsystemId = subsystemId;
    }

    public String getPrivilegeCnName() {
        return privilegeCnName;
    }

    public void setPrivilegeCnName(String privilegeCnName) {
        this.privilegeCnName = privilegeCnName;
    }

    public Privilege getParent() {
        return parent;
    }

    public void setParent(Privilege parent) {
        this.parent = parent;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isNavigation() {
        return navigation;
    }

    public void setNavigation(boolean navigation) {
        this.navigation = navigation;
    }

    public Set<Privilege> getChildPrivileges() {
        return childPrivileges;
    }

    public void setChildPrivileges(Set<Privilege> childPrivileges) {
        this.childPrivileges = childPrivileges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Privilege privilege = (Privilege) o;

        if (privilegeId != null ? !privilegeId.equals(privilege.privilegeId)
                : privilege.privilegeId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return privilegeId != null ? privilegeId.hashCode() : 0;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public String getPrivilegeEnName() {
        return privilegeEnName;
    }

    public void setPrivilegeEnName(String privilegeZnName) {
        this.privilegeEnName = privilegeZnName;
    }
}
