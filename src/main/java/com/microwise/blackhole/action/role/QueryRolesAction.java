package com.microwise.blackhole.action.role;

import com.microwise.blackhole.bean.Role;
import com.microwise.blackhole.service.RoleService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeLoggerUtil;
import com.microwise.blackhole.sys.MessageActionUtil;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.PagingUtil;
import com.opensymphony.xwork2.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 角色查询
 *
 * @author Wang yunlong
 * @date: 12-10-24 Time: 上午9:35
 * @check @gaohui #485 2012-11-29
 */
@Beans.Action
@Blackhole
public class QueryRolesAction {
    private static final Logger log = LoggerFactory.getLogger(QueryRolesAction.class);

    @Autowired
    private RoleService roleService;
    @Autowired
    private BlackholeLoggerUtil logger;

    private String roleName;

    //output
    /**
     * 角色列表
     */
    private List<Role> roles;

    /**
     * 当前页
     */
    private int index = 1;

    /**
     * 总数
     */
    private int count;


    public String execute() {
        int currentLogicGroupId = Sessions.createByAction().currentLogicGroup().getId();
        try {
            count = PagingUtil.pagesCount(roleService.findRoleCountByLogicGroupId(currentLogicGroupId, roleName), Constants.SIZE_PER_PAGE);
            roles = roleService.findRoleListByLogicGroupId(currentLogicGroupId, roleName, index, Constants.SIZE_PER_PAGE);
            logger.log("角色管理", "查询角色");
        } catch (Exception e) {
            logger.logFailed("角色管理", "查询角色");
            MessageActionUtil.fail("查询失败");
            log.error("角色查询", e);
        }
        ActionMessage.createByAction().consume();
        return Action.SUCCESS;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
