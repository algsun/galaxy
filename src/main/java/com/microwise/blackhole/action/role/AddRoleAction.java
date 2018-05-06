package com.microwise.blackhole.action.role;


import com.microwise.blackhole.service.RoleService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeLoggerUtil;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     添加角色
 * </pre>
 *
 * @author Wang yunlong
 * @date 12-10-24 Time: 上午9:35
 * @check @gaohui #485 2012-11-29
 */
@Beans.Action
@Blackhole
public class AddRoleAction extends InitRoleDetailAction {
    /**
     * 日志打印
     */
    private static final Logger log = LoggerFactory.getLogger(InitRoleDetailAction.class);

    @Autowired
    private RoleService roleService;
    @Autowired
    private BlackholeLoggerUtil logger;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色权限
     */
    private String[] privileges;

    /**
     * 跳转
     *
     * @return action
     */
    public String view() {
        initPage();
        return Action.SUCCESS;
    }

    /**
     * 添加
     */
    public String execute() {
        int currentLogicGroupId = Sessions.createByAction().currentLogicGroup().getId();
        List<String> privilegeList = new ArrayList<String>();
        if (privileges != null) {
            for (String i : privileges) {
                privilegeList.add(i);
            }
        }
        try {
            roleService.saveRole(currentLogicGroupId, roleName, privilegeList);
            ActionMessage.createByAction().success("添加成功");
            logger.log("角色管理", "添加名称为" + roleName + "的角色");
            return Action.SUCCESS;
        } catch (Exception e) {
            initPage();
            ActionMessage.createByAction().fail("添加失败");
            logger.logFailed("角色管理", "添加名称为" + roleName + "的角色");
            log.error("", e);
            return Action.ERROR;
        }
    }

    /**
     * 初始化页面
     */
    protected void initPage() {
        super.initPage();
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
}
