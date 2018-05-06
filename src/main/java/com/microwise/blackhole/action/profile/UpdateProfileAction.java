package com.microwise.blackhole.action.profile;

import com.google.common.base.Strings;
import com.microwise.blackhole.bean.Role;
import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.RoleService;
import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeLoggerUtil;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 修改个人信息
 *
 * @author li.jianfei
 * @date 2012-11-19
 * @check @gaohui #379 2012-11-27
 */

@Beans.Action("updateProfileAction")
@Blackhole
public class UpdateProfileAction {

    /**
     * 用户信息 Service
     */
    @Autowired
    private UserService userService;

    /**
     * 角色信息 Service
     */
    @Autowired
    private RoleService roleService;

    @Autowired
    private BlackholeLoggerUtil logger;

    // Input 输入参数
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
     * 手机号码
     */
    private String mobile;

    // Output 输出参数
    /**
     * 用户对象
     */
    private User user;

    /**
     * 用户角色列表
     */
    private List<Role> listRole;

    /**
     * 用户照片路径
     */
    private String filePath;

    /**
     * 默认头像
     */
    public static final String DEFAULT_HEAD_PORTRAIT_URL = "../blackhole/images/head_portrait.png";

    /**
     * 查询用户信息
     *
     * @return 操作标识
     */
    public String view() {
        user = new Sessions(ActionContext.getContext()).currentUser();
        if (Strings.isNullOrEmpty(user.getPhoto())) {
            filePath = DEFAULT_HEAD_PORTRAIT_URL;
        } else {
            filePath = Sessions.createByAction().getGalaxyResourceURL() + "/blackhole/images/user/" + user.getPhoto();
        }

        // 获取用户角色列表
        listRole = roleService.findRoleListByUserId(user.getId());

        ActionMessage.createByAction().consume();
        return Action.SUCCESS;
    }

    /**
     * 修改个人信息
     *
     * @return 操作标识
     */
    //暂时有问题 @gaohui 2012-12-10
    //@RequiresPermissions({ "blackhole:profile:update" })
    public String execute() {

        Sessions sessions = new Sessions(ActionContext.getContext());
        user = sessions.currentUser();

        // 修改个人信息
        try {
            userService.updateUser(user.getId(), userName, sex, mobile);

            // 更新 session
            user = userService.findUserById(user.getId());
            filePath = Sessions.createByAction().getGalaxyResourceURL() + "/blackhole/images/user/" + user.getPhoto();
            sessions.setCurrentUser(user);
            ActionMessage.createByAction().success("修改成功").consume();
            logger.log("个人信息", "修改基本信息");
            logger.logCurrentInSubsystem("个人信息", "修改基本信息", true);
        } catch (Exception ex) {
            ActionMessage.createByAction().fail("修改失败").consume();
            logger.logCurrentInSubsystem("个人信息", "修改基本信息", false);
        }
        return Action.SUCCESS;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public List<Role> getListRole() {
        return listRole;
    }

    public void setListRole(List<Role> listRole) {
        this.listRole = listRole;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
