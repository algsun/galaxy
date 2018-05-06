package com.microwise.blackhole.action.index;

import com.microwise.blackhole.action.app.LoginAction;
import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.service.LogicGroupService;
import com.microwise.blackhole.service.LogicGroupSubsystemDisableService;
import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 切换站点
 *
 * @author gaohui
 * @date 13-1-18 16:13
 */
@Beans.Action
@Blackhole
public class SwitchLogicGroupAction {
    @Autowired
    private LogicGroupService logicGroupService;
    @Autowired
    private UserService userService;
    @Autowired
    private LogicGroupSubsystemDisableService logicGroupSubsystemDisableService;
    @Autowired
    private LoginAction loginAction;

    // 要切换的站点
    private int logicGroupId;
    // 切换站点后，跳转到页面
    private String forward;

    public String execute() {
        //查找站点, 并 put 到 session
        LogicGroup logicGroup = logicGroupService.findLogicGroupById(logicGroupId);
        if (logicGroup == null) {
            //重新选择
            return Action.INPUT;
        }

        Sessions sessions = new Sessions(ActionContext.getContext());
        sessions.refreshCurrentLogicGroupAndSub(logicGroupId, logicGroupService, userService, logicGroupSubsystemDisableService);
        loginAction.initPrivileges(sessions.currentUser());
        return Action.SUCCESS;
    }


    public int getLogicGroupId() {
        return logicGroupId;
    }

    public void setLogicGroupId(int logicGroupId) {
        this.logicGroupId = logicGroupId;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }
}
