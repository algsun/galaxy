package com.microwise.blackhole.action.user;

import com.google.common.base.Strings;
import com.microwise.blackhole.bean.Department;
import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.DepartmentService;
import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 修改用户部门
 *
 * @author liuzhu
 * @date 2012-08-19
 */

@Beans.Action
@Blackhole
public class UpdateUserAction {

    public static final Logger log = LoggerFactory.getLogger(UpdateUserAction.class);

    /**
     * 用户 service
     */
    @Autowired
    private UserService userService;

    /**
     * 部门service
     */
    @Autowired
    private DepartmentService departmentService;

    /**
     * 用户id
     */
    private int userId;

    /**
     * 部门id
     */
    private int departmentId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 当前页面
     */
    private int index;

    /**
     * 客户对象
     */
    private User user;

    /**
     * 部门列表
     */
    private List<Department> departments;

    /**
     * 初始化修改用户页面
     *
     * @return
     */
    public String view() {
        // 获取当前logicGroup
        LogicGroup currentLogicGroup = new Sessions(ActionContext.getContext()).currentLogicGroup();
        user = userService.findUser(currentLogicGroup.getId(), userId);
        return Action.SUCCESS;
    }

    public String execute() {
        LogicGroup currentLogicGroup = Sessions.createByAction().currentLogicGroup();
        try {
            departmentName = Strings.emptyToNull(departmentName);
            userService.updateUserDepartment(userId, currentLogicGroup.getId(), departmentName);
            ActionMessage.createByAction().success("修改用户部门成功");
        } catch (Exception e) {
            log.error("", e);
            ActionMessage.createByAction().fail("修改用户部门失败");
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
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<Department> getDepartments() {
        LogicGroup currentLogicGroup = new Sessions(ActionContext.getContext()).currentLogicGroup();
        departments = departmentService.findAll(currentLogicGroup.getId());
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
