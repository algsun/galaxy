package com.microwise.blackhole.action.user;

import com.google.common.base.Strings;
import com.microwise.blackhole.bean.Department;
import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.bean.Role;
import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.DepartmentService;
import com.microwise.blackhole.service.RoleService;
import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeLoggerUtil;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.TokenUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 添加用户
 *
 * @author li.jianfei
 * @date 2012-11-22
 * @check wang.yunlong #402 12-11-29 09:49
 * @check @gaohui #488 12-11-29 16:39
 */
@Beans.Action
@Blackhole
public class AddUserAction {

    public static final Logger log = LoggerFactory.getLogger(AddUserAction.class);

    /**
     * 用户信息 service
     */
    @Autowired
    private UserService userService;

    /**
     * 角色信息 service
     */
    @Autowired
    private RoleService roleService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private BlackholeLoggerUtil logger;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 性别
     */
    private int sex;

    /**
     * 是否发送验证邮件 -added by wanggeng 2013-05-13
     */
    private boolean isSendValidationEmail;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 角色
     */
    private String roles[];

    // Output
    /**
     * 角色信息列表
     */
    private List<Role> roleList;

    /**
     * 部门列表
     */
    private List<Department> departments;

    private String departmentName;


    /**
     * 初始化添加用户页面
     */
    public String view() {
        return Action.SUCCESS;
    }

    /**
     * 添加用户
     */
    public String execute() {
        try {

            // 检查当前邮箱是否已被占用
            User user = userService.findUserByEmail(email);
            if (user != null) { // 邮箱被占用
                ActionMessage.createByAction().fail("添加用户失败，邮箱已被使用").consume();
                logger.logFailed("用户管理", "添加用户");
                return Action.ERROR;
            }

            // 初始化用户信息
            user = new User();
            user.setEmail(email.toLowerCase());
            user.setUserName(userName);
            user.setSex(sex);
            user.setMobile(mobile);
            String token = TokenUtil.createToken();
            user.setToken(token);

            // 用户激活连接
            HttpServletRequest request = ServletActionContext.getRequest();
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/" + Constants.Blackhole.USER_ACTIVATE_ACTION + "?token=" + token;

            // 处理权限id 列表
            List<Integer> roleIdList = null;
            if (roles != null) {
                roleIdList = new ArrayList<Integer>();
                for (String role : roles) {
                    roleIdList.add(Integer.parseInt(role));
                }
            }

            // 获取当前logicGroup
            LogicGroup currentLogicGroup = new Sessions(ActionContext.getContext()).currentLogicGroup();

            departmentName = Strings.emptyToNull(departmentName);
            userService.saveUser(user, currentLogicGroup.getId(), departmentName, roleIdList, url, isSendValidationEmail);
            ActionMessage.createByAction().success("添加用户成功");
            logger.log("用户管理", "添加用户");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("添加用户失败").consume();
            logger.logFailed("用户管理", "添加用户");
            log.error("", e);
            return Action.ERROR;
        }
        return Action.SUCCESS;
    }

    public List<Role> getRoleList() {
        LogicGroup currentLogicGroup = new Sessions(ActionContext.getContext()).currentLogicGroup();

        // 读取当前站点角色列表
        roleList = roleService.findRoleListByLogicGroupId(currentLogicGroup.getId(), false);
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public boolean isSendValidationEmail() {
        return isSendValidationEmail;
    }

    public void setSendValidationEmail(boolean sendValidationEmail) {
        isSendValidationEmail = sendValidationEmail;
    }

    public List<Department> getDepartments() {
        LogicGroup currentLogicGroup = new Sessions(ActionContext.getContext()).currentLogicGroup();
        departments = departmentService.findAll(currentLogicGroup.getId());
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
