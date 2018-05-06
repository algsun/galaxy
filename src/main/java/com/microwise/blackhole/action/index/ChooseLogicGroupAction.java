package com.microwise.blackhole.action.index;


import com.microwise.blackhole.action.app.LoginAction;
import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.service.LogicGroupService;
import com.microwise.blackhole.service.LogicGroupSubsystemDisableService;
import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 选择站点(LogicGroup)
 *
 * @author bastengao
 * @date 12-11-21 15:44
 * @check @wang.yunlong & li.jianfei    #776     2012-12-18
 */
@Beans.Action
@Blackhole
public class ChooseLogicGroupAction {
    @Autowired
    private LogicGroupService logicGroupService;

    @Autowired
    private UserService userService;

    @Autowired
    private LogicGroupSubsystemDisableService logicGroupSubsystemDisableService;

    @Autowired
    private LoginAction loginAction;

    //output
    /**
     * 顶级站点
     */
    private List<LogicGroup> logicGroups;

    /**
     * 跳转到选择页面
     */
    public String view() {
        logicGroups = logicGroupService.findTopLogicGroupList();
        ActionMessage.createByAction().consume();
        return Action.SUCCESS;
    }

    //input
    /**
     * 选择的站点
     */
    private int logicGroupId;

    /**
     * 执行选择站点
     */
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

        ConfigFactory.Configs appConfig = ConfigFactory.getInstance().getConfig(Constants.Config.CONFIG_PROPERTIES_URL);
        String customize = appConfig.get(Constants.Config.GALAXY_CUSTOMIZE);
        if ("1".equals(customize)) {
            return "toSaturn";
        }
        String mode = appConfig.get(Constants.Config.GALAXY_MODE);
        if (logicGroup.getSite() != null && "mode7".equals(mode)) {
            return "toBlueplanet";
        }

        return Action.SUCCESS;
    }

    public List<LogicGroup> getLogicGroups() {
        return logicGroups;
    }

    public void setLogicGroups(List<LogicGroup> logicGroups) {
        this.logicGroups = logicGroups;
    }

    public int getLogicGroupId() {
        return logicGroupId;
    }

    public void setLogicGroupId(int logicGroupId) {
        this.logicGroupId = logicGroupId;
    }
}
