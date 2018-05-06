package com.microwise.blackhole.action.index;

import com.google.common.base.Strings;
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
 * 切换到归属站点， 然后跳转到指定页面
 *
 * @author gaohui
 * @date 12-12-3 10:08
 */
@Beans.Action
@Blackhole
public class ToUserLogicGroupAction {
    @Autowired
    private LogicGroupService logicGroupService;

    @Autowired
    private UserService userService;

    @Autowired
    private LogicGroupSubsystemDisableService logicGroupSubsystemDisableService;

    //input, output
    /**
     * 跳转到指定链接(链接只限定站点链接)
     */
    private String forward;

    public String execute() {
        //如果没有跳转到首页
        if (Strings.isNullOrEmpty(forward)) {
            return Action.INPUT;
        }

        //如果 forward 不是 "/" 开头，则前加 "/"
        if (!forward.startsWith("/")) {
            forward = "/" + forward;
        }

        Sessions sessions = new Sessions(ActionContext.getContext());
        LogicGroup userLogicGroup = sessions.currentUserLogicGroup();
        if (userLogicGroup == null) {
            return Action.SUCCESS;
        }

        // 如果现在已经是归属站点则不需要切换了
        if (userLogicGroup.getId() == sessions.currentLogicGroup().getId()) {
            return Action.SUCCESS;
        }

        sessions.refreshCurrentLogicGroupAndSub(userLogicGroup.getId(), logicGroupService, userService, logicGroupSubsystemDisableService);

        return Action.SUCCESS;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }
}
