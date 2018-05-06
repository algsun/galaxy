package com.microwise.blackhole.action.user;


import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.bean.Role;
import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.RoleService;
import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeLoggerUtil;
import com.microwise.blackhole.sys.MessageActionUtil;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 分配角色
 *
 * @author li.jianfei
 * @date 2012-11-22
 * @check wang.yunlong #402 12-11-49 09:49
 */
@Beans.Action
@Blackhole
public class AssignRoleAction {

    public static final Logger log = LoggerFactory.getLogger(AssignRoleAction.class);

    /**
     * 用户信息 Service
     */
    @Autowired
    private UserService userService;

    @Autowired
    private BlackholeLoggerUtil logger;

    /**
     * 角色信息 Service
     */
    @Autowired
    private RoleService roleService;

    // Input
    /**
     * 用户ID
     */
    private int userId;

    /**
     * 角色列表
     */
    private String roles[];

    // Output
    /**
     * 用户对象
     */
    private User user;

    /**
     * 站点角色列表
     */
    private List<Role> roleList;

    /**
     * 用户角色列表
     */
    private List<Role> userRoleList;

    /**
     * 当前页面
     */
    private int index;

    /**
     * 用户名
     */
    private String name;

    /**
     * 初始化页面信息
     *
     * @return 操作标识
     */
    public String view() {
        try {
            LogicGroup currentLogicGroup = new Sessions(ActionContext.getContext()).currentLogicGroup();

            // 读取当前站点角色列表
            roleList = roleService.findRoleListByLogicGroupId(currentLogicGroup.getId(), false);

            // 读取用户角色
            userRoleList = roleService.findRoleListByUserId(userId);

            // 从站点角色列表中去除用户已拥有的角色
            for (Role userRole : userRoleList) {
                for (int i = 0; i < roleList.size(); i++) {
                    Role role = roleList.get(i);
                    if (userRole.getId() == role.getId()) {
                        roleList.remove(i);
                    }
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }

        return Action.SUCCESS;
    }

    /**
     * 分配角色
     *
     * @return 操作标识
     */
    public String execute() {
        try {
            // 处理权限id 列表
            List<Integer> roleIdList = null;
            if (roles != null) {
                roleIdList = new ArrayList<Integer>();
                for (String role : roles) {
                    roleIdList.add(Integer.parseInt(role));
                }
            }
            userService.assignRoleToUser(userId, roleIdList);
            MessageActionUtil.success("分配角色成功");
            logger.log("用户管理", "分配角色");
        } catch (Exception ex) {
            ex.printStackTrace();
            MessageActionUtil.fail("分配角色失败");
            logger.logFailed("用户管理", "分配角色");
            log.error("", ex);
        }
        return Action.SUCCESS;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User getUser() {
        user = userService.findUserById(userId);
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public List<Role> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<Role> userRoleList) {
        this.userRoleList = userRoleList;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
