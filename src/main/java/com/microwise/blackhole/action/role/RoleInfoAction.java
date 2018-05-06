package com.microwise.blackhole.action.role;

import com.microwise.blackhole.bean.Privilege;
import com.microwise.blackhole.bean.Role;
import com.microwise.blackhole.service.PrivilegeService;
import com.microwise.blackhole.service.RoleService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeLoggerUtil;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <pre>
 *     角色详细信息
 * </pre>
 *
 * @author Wang yunlong
 * @date 12-11-27
 * @time 下午2:17
 * @check @gaohui #459 2012-11-29
 */
@Beans.Action
@Blackhole
public class RoleInfoAction extends InitRoleDetailAction {
    @Autowired
    private RoleService roleService;
    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private BlackholeLoggerUtil logger;
    //output
    /**
     * 角色
     */
    private Role role;
    //input
    /**
     * 角色id
     */
    private int roleId;

    public String execute() {
        initPage();
        return Action.SUCCESS;
    }

    /**
     * 初始化页面
     */
    protected void initPage() {
        super.initPage();
        role = roleService.findRoleById(roleId);
        List<Privilege> rolePrivileges = privilegeService.findPrivilegeListByRoleId(roleId);
        //调用父类 设置已有权限的方法
        setAllHasPrivilege(rolePrivileges);
        logger.log("角色管理", "查询角色详细");
    }


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
