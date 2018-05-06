package com.microwise.blackhole.action.user;

import com.google.common.base.Strings;
import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.LogicGroupService;
import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户激活
 *
 * @author gaohui
 * @date 12-11-30 16:45
 */
@Beans.Action
@Blackhole
public class ActiveUserAction {
    private static final Logger log = LoggerFactory.getLogger(ActiveUserAction.class);

    @Autowired
    private UserService userService;
    @Autowired
    private LogicGroupService logicGroupService;

    //input
    private String token;
    private String userName;
    private String password;
    private int sex;
    private String mobile;

    //output
    private User user;
    private String logicGroupName;

    public String view() {
        user = userService.findUserByToken(token);
        if (user == null) {
            ActionMessage.createByAction().fail("链接无效或过期").consume();

            return Action.ERROR;
        }

        LogicGroup userLogicGroup = logicGroupService.findLogicGroupByUserId(user.getId());
        logicGroupName = userLogicGroup.getLogicGroupName();

        return Action.SUCCESS;
    }

    public String execute() {
        user = userService.findUserByToken(token);
        if (user == null) {
            ActionMessage.createByAction().fail("链接无效或过期").consume();
            return Action.ERROR;
        }

        try {
            mobile = Strings.emptyToNull(mobile);
            //将用户状态设置为激活. 如果是站点管理员，同时还将站点设置为激活.
            userService.activeUser(user.getId(), userName, password, sex, mobile);
            ActionMessage.createByAction().success("激活成功").consume();
        } catch (Exception e) {
            log.error("用户激活", e);
            ActionMessage.createByAction().fail("激活失败").consume();
            return Action.INPUT;
        }
        return Action.SUCCESS;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLogicGroupName() {
        return logicGroupName;
    }

    public void setLogicGroupName(String logicGroupName) {
        this.logicGroupName = logicGroupName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
