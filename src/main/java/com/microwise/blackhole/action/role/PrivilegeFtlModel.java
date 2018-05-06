package com.microwise.blackhole.action.role;

import java.util.List;

/**
 * 页面上展示系统权限的model
 * <p/>
 * TODO  重构代码, 此类可以通过包装 Privilege 来扩展 Privilege ，而不是在重新定义一遍 Privilege 中的属性。 @gaohui 2012-12-10
 *
 * @author: Wang yunlong
 * @date: 12-11-22
 * @time: 下午4:34
 */
public class PrivilegeFtlModel {
    /**
     * 父级权限id
     */
    private String parentId;
    /**
     * 权限标识
     */
    private String id;
    /**
     * 权限中文名称
     */
    private String name;

    private int sequence;
    /**
     * 子权限
     */
    private List<PrivilegeFtlModel> children;

    /**
     * 是否归当前角色拥有
     */
    private boolean belongCurrentRole;

    /**
     * 是否禁用
     */
    private boolean disable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public List<PrivilegeFtlModel> getChildren() {
        return children;
    }

    public void setChildren(List<PrivilegeFtlModel> children) {
        this.children = children;
    }

    public boolean isBelongCurrentRole() {
        return belongCurrentRole;
    }

    public void setBelongCurrentRole(boolean belongCurrentRole) {
        this.belongCurrentRole = belongCurrentRole;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }
}
