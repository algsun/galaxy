package com.microwise.blackhole.action.role;

import com.microwise.blackhole.service.RoleService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeLoggerUtil;
import com.microwise.blackhole.sys.MessageActionUtil;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <pre>
 *     删除角色
 * </pre>
 *
 * @author Wang Yunlong
 * @date 12-10-24  上午9:37
 * @check @gaohui #485 2012-11-29
 */
@Beans.Action
@Blackhole
public class DeleteRoleAction {
    private static final Logger log = LoggerFactory.getLogger(DeleteRoleAction.class);

    @Autowired
    private RoleService roleService;
    @Autowired
    private BlackholeLoggerUtil logger;
    //input
    /**
     * 权限id
     */
    private int roleId;

    public String execute() {
        try {
            roleService.deleteRoleById(roleId);
            MessageActionUtil.success("删除成功");
            logger.log("角色管理", "删除角色,角色id为:" + roleId);
        } catch (Exception e) {
            logger.logFailed("角色管理", "删除角色,角色id为:" + roleId);
            MessageActionUtil.fail("删除失败");
            log.error("", e);
        }
        return Action.SUCCESS;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
