package com.microwise.blackhole.action.app;

import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户忘记密码时 通过邮件申请重置密码页面
 *
 * @author Wang yunlong
 * @date 12-11-26  下午3:11
 * @check @li.jianfei #491 2012-11-29
 */
@Beans.Action
@Blackhole
public class ResetPasswordAction {
    @Autowired
    private UserService userService;
    //input
    /**
     * 新密码
     */
    private String password;

    /**
     * 申请修改密码密钥
     */
    private String token;

    /**
     * 是否可以修改
     */
    private boolean canDo;

    /**
     * 修改是否完成
     */
    private boolean complete;

    //output
    private String email;


    public String view() {
        User user = userService.findUserByToken(token);
        canDo = (user != null);
        complete = false;
        return Action.SUCCESS;
    }

    public String execute() {
        User user = userService.findUserByToken(token);
        if (user == null) {
            ActionMessage.createByAction().fail("修改失败").consume();
        } else {
            userService.updateUserPassword(user.getId(), password);
            email = user.getEmail();
            complete = true;
            ActionMessage.createByAction().success("修改成功").consume();
        }
        return Action.SUCCESS;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isCanDo() {
        return canDo;
    }

    public void setCanDo(boolean canDo) {
        this.canDo = canDo;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
