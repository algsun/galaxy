package com.microwise.blackhole.action.profile;

import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeLoggerUtil;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 修改密码
 *
 * @author li.jianfei
 * @date 2012-11-19
 * @check @gaohui #379 2012-11-27
 */
@Beans.Action
@Blackhole
public class ChangePasswordAction {

    /**
     * 用户信息 service
     */
    @Autowired
    private UserService userService;

    @Autowired
    private BlackholeLoggerUtil logger;

    // Input 输入参数
    /**
     * 原始密码
     */
    private String password;

    /**
     * 新密码
     */
    private String newPassword;

    public String view() {
        return Action.SUCCESS;
    }

    public String execute() {

        User user = new Sessions(ActionContext.getContext()).currentUser();

        // 判断原始密码是否正确
        if (BCrypt.checkpw(password, user.getPassword())) {
            try {
                //进行密码加密
                userService.updateUserPassword(user.getId(), newPassword);
                ActionMessage.createByAction().success("修改密码成功").consume();
                logger.log("修改密码", "修改密码");
            } catch (Exception ex) {
                ActionMessage.createByAction().fail("修改密码失败").consume();
                logger.logFailed("修改密码", "修改密码");
                ex.printStackTrace();
            }
        } else {
            ActionMessage.createByAction().fail("您输入的当前密码有误").consume();
            logger.logFailed("修改密码", "修改密码");
        }

        return Action.SUCCESS;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
