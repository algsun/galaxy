package com.microwise.blackhole.action.role;

import com.microwise.blackhole.service.RoleService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <pre>
 *     判断角色是否存在
 * </pre>
 *
 * @author: Wang yunlong
 * @date: 12-11-23
 * @time: 上午11:21
 * @check @gaohui #438 2012-11-29
 */

@Beans.Action
@Blackhole
public class RoleExistAction {
    @Autowired
    private RoleService roleService;
    /**
     * 角色名存在与否
     */
    private boolean exist;
    //input
    /**
     * 角色id
     */
    private int roleId;
    /**
     * 角色名称
     */
    private String roleName;

    public String execute() {
        Sessions sessions = new Sessions(ServletActionContext.getContext());
        int logicGroupId = sessions.currentLogicGroup().getId();
        exist = roleService.hasSameRole(logicGroupId, roleId, roleName);
        return Action.SUCCESS;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
