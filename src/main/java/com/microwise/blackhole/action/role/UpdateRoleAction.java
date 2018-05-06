package com.microwise.blackhole.action.role;

import com.microwise.blackhole.bean.Privilege;
import com.microwise.blackhole.bean.Role;
import com.microwise.blackhole.service.PrivilegeService;
import com.microwise.blackhole.service.RoleService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeLoggerUtil;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *     角色更新
 * </pre>
 *
 * @author Wang Yunlong
 * @date: 12-10-24 Time: 上午9:35
 * @check @gaohui #459 2012-11-29
 */
@Beans.Action
@Blackhole
public class UpdateRoleAction extends InitRoleDetailAction {
    private static final Logger log = LoggerFactory.getLogger(UpdateRoleAction.class);
    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BlackholeLoggerUtil logger;
    //input
    /**
     * 角色id
     */
    private int roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色拥有的权限列表
     */
    private String[] privileges;

    //output
    /**
     * 页面跳转role
     */
    private Role role;


    public String view() {
        initPage();
        return Action.SUCCESS;
    }

    public String execute() {
        Role updateRole = roleService.findRoleById(roleId);
        updateRole.setRoleName(roleName);
        //给将要更新的角色分配新的权限
        List<String> privilegeList = new ArrayList<String>();
        if (privileges != null) {
            privilegeList = Arrays.asList(privileges);
        }
        try {
            roleService.updateRole(updateRole, privilegeList);
            ActionMessage.createByAction().success("修改成功");
            logger.log("角色管理", "修改角色信息，该角色id为：" + roleId);
            return Action.SUCCESS;
        } catch (Exception e) {
            initPage();
            ActionMessage.createByAction().fail("修改失败");
            logger.logFailed("角色管理", "修改角色信息，该角色id为：" + roleId);
            log.error("", e);
            return Action.ERROR;
        }
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

    public String[] getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String[] privileges) {
        this.privileges = privileges;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
