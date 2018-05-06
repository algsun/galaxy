package com.microwise.blackhole.action.role;

import com.google.common.collect.Lists;
import com.microwise.blackhole.bean.Privilege;
import com.microwise.blackhole.bean.Role;
import com.microwise.blackhole.service.PrivilegeService;
import com.microwise.blackhole.service.RoleService;
import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeLoggerUtil;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 访客权限修改Action
 *
 * @author wanggeng
 * @date: 2013-5-15  上午9:06
 */
@Beans.Action
@Blackhole
public class ModifyAnonymityRoleAction extends InitRoleDetailAction {

    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private BlackholeLoggerUtil logger;

    /**
     * 日志打印
     */
    private static final Logger log = LoggerFactory.getLogger(InitRoleDetailAction.class);

    /**
     * 角色权限
     */
    private String[] privileges;

    /**
     * 禁用状态
     */
    private boolean enable;

    public String view() {
        this.initPage();
        enable = userService.isAnonymityLoginEnable();
        return Action.SUCCESS;
    }

    public String execute() {

        List<String> privilegeList = new ArrayList<String>();
        if (privileges != null) {
            for (String privilege : privileges) {
                privilegeList.add(privilege);
            }
        }
        try {
            Role anonymityRole = roleService.findAnonymityRole();
            if (anonymityRole != null) {
                roleService.updateRole(anonymityRole, privilegeList);
            }
            this.initPage();
            enable = userService.isAnonymityLoginEnable();
            ActionMessage.createByAction().success("访客权限修改成功!");
            logger.log("角色管理", "添加名称为访客的角色");
            return Action.SUCCESS;
        } catch (Exception e) {
            this.initPage();
            ActionMessage.createByAction().fail("访客权限修改失败");
            logger.logFailed("角色管理", "添加名称为访客的角色");
            log.error("", e);
            return Action.ERROR;
        }
    }

    //启用停用访客权限，在第一次启用的时候，访客用户可能没有，需要创建
    public String enableAnonmity() {
        this.initPage();
        userService.enableAnonymityLogin(true);
        enable = true;
        return Action.SUCCESS;
    }

    public String disableAnonmity() {
        this.initPage();
        userService.enableAnonymityLogin(false);
        enable = false;
        return Action.SUCCESS;
    }

    /**
     * 初始化页面
     */
    protected void initPage() {
        super.initPage();
        Role anonymityRole = roleService.findAnonymityRole();
        List<Privilege> rolePrivileges = Lists.newArrayList();
        if (anonymityRole != null) {
            rolePrivileges = privilegeService.findPrivilegeListByRoleId(anonymityRole.getId());
        }
        //调用父类 设置已有权限的方法
        setAllHasPrivilege(rolePrivileges);
    }

    public String[] getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String[] privileges) {
        this.privileges = privileges;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
