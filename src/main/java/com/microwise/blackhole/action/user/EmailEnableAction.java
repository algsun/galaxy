package com.microwise.blackhole.action.user;

import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <pre>
 * 通过邮箱判断用户是否存在 （邮箱是否可用）
 * </pre>
 *
 * @author: Wang yunlong
 * @time: 12-12-5 下午1:09
 */
@Beans.Action
@Blackhole
public class EmailEnableAction {
    @Autowired
    private UserService userService;
    /**
     * 用户邮箱
     */
    private String email;
    //output
    /**
     * 此邮箱是否可用
     */
    private boolean enable;

    public String execute() {
        User user = userService.findUserByEmail(email);
        enable = (user == null);
        return Action.SUCCESS;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
