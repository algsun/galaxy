package com.microwise.blackhole.sys;

import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.bean.Subsystem;
import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.LogicGroupService;
import com.microwise.blackhole.service.LogicGroupSubsystemDisableService;
import com.microwise.blackhole.service.UserService;
import com.microwise.common.sys.Constants;
import com.opensymphony.xwork2.ActionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * session 中获取系统数据工具类.
 * 优先使用此类获取 session 的值.
 *
 * @author bastengao
 * @date 12-11-21 11:12
 * @check @wang.yunlong & li.jianfei    #847    2012-12-18
 */
public class Sessions {

    /**
     * 从 action 线程获取 sessions
     * <p/>
     * 注意: 此方法只能在 action 线程执行
     *
     * @return
     */
    public static Sessions createByAction() {
        return new Sessions(ActionContext.getContext());
    }

    private Map<String, Object> session;

    public Sessions(ActionContext actionContext) {
        this.session = actionContext.getSession();
    }

    public Sessions(Map<String, Object> session) {
        this.session = session;
    }

    /**
     * 当前登录用户, 如果不存在返回 null
     *
     * @return
     */
    public User currentUser() {
        return (User) session.get(Constants.Session.USER_OF_SESSION);
    }

    /**
     * 获取Galaxy Resource URL
     *
     * @return
     */
    public String getGalaxyResourceURL() {
        return session.get(Constants.Session.GALAXY_RESOURCE_URL).toString();
    }

    /**
     * 更新Session中的当前用户
     *
     * @param user
     * @author li.jianfei
     * @date 2012-11-28
     */
    public void setCurrentUser(User user) {
        session.put(Constants.Session.USER_OF_SESSION, user);
    }

    /**
     * 当前登录用户归属站点, 如果不存在返回 null
     *
     * @return
     */
    public LogicGroup currentUserLogicGroup() {
        return (LogicGroup) session.get(Constants.Session.USER_LOGIC_GROUP);
    }

    /**
     * 当前选择站点(当前站点)
     *
     * @return
     */
    public LogicGroup currentLogicGroup() {
        return (LogicGroup) session.get(Constants.Session.CURRENT_LOGIC_GROUP);
    }

    /**
     * 返回当前站点的 siteId, 如果无返回 null
     *
     * @return
     */
    public String currentSiteId() {
        if (currentLogicGroup().getSite() == null) {
            return null;
        }

        return currentLogicGroup().getSite().getSiteId();
    }

    /**
     * 设置用户当前站点
     *
     * @param currentLogicGroup
     * @return
     */
    public Sessions setCurrentLogicGroup(LogicGroup currentLogicGroup) {
        session.put(Constants.Session.CURRENT_LOGIC_GROUP, currentLogicGroup);
        return this;
    }

    /**
     * 设置用户当前站点的直接子站点列表
     *
     * @param subLogicGroups
     * @return
     */
    public Sessions setCurrentSubLogicGroups(List<LogicGroup> subLogicGroups) {
        session.put(Constants.Session.CURRENT_SUB_LOGIC_GROUPS, subLogicGroups);
        return this;
    }

    public Sessions setSubsystem(List<Subsystem> subsystems) {
        session.put("subsystemList", subsystems);
        return this;
    }
    /**
     * 设置用户当前的直接子站点列表显示在首页
     *
     * */
    public Sessions setSubsystemFromHome(List<Subsystem> subsystems) {
        session.put("subsystemListFromHome", subsystems);
        return this;
    }

    /**
     * 设置当前站点管理员
     *
     * @param user
     * @return
     */
    public Sessions setCurrentLogicGroupManager(User user) {
        session.put(Constants.Session.CURRENT_LOGICGROUP_MANAGER, user);
        return this;
    }

    /**
     * 刷新当前站点和当前站点的直接子站点
     *
     * @param logicGroupId
     * @param logicGroupService
     * @return 当前站点
     */
    public LogicGroup refreshCurrentLogicGroupAndSub(int logicGroupId, LogicGroupService logicGroupService, UserService userService, LogicGroupSubsystemDisableService logicGroupSubsystemDisableService) {
        //设置当前站点
        LogicGroup logicGroup = logicGroupService.findLogicGroupCarrySite(logicGroupId);
        setCurrentLogicGroup(logicGroup);

        //设置当前站点的直接子站点
        List<LogicGroup> subLogicGroups = logicGroupService.findSubLogicGroupsCarrySite(logicGroupId);
        subLogicGroups = fetchSite(subLogicGroups, logicGroupService);
        setCurrentSubLogicGroups(subLogicGroups);

        // 当前站点管理员(目前仅用来控制超级管理员在用户管理中删除站点管理员)
        User user = userService.findManagerByLogicGroupId(logicGroupId);
        setCurrentLogicGroupManager(user);
        //设置站点管理员业务系统权限
        setSubsystem(logicGroupSubsystemDisableService.findSubsystemOpen(logicGroupId));
        //设置首页显示的子系统名称
        setSubsystemFromHome(logicGroupSubsystemDisableService.findSubsystemOpenFormHome(logicGroupId));
        return logicGroup;
    }

    public List<LogicGroup> refreshCurrentLogicGroupAndSub(LogicGroup currentLogicGroup, LogicGroupService logicGroupService, UserService userService, LogicGroupSubsystemDisableService logicGroupSubsystemDisableService) {
        int logicGroupId = currentLogicGroup.getId();
        setCurrentLogicGroup(currentLogicGroup);

        //设置当前站点的直接子站点
        List<LogicGroup> subLogicGroups = resetSubLogicGroupOfCurrent(logicGroupId, logicGroupService);

        // 当前站点管理员(目前仅用来控制超级管理员在用户管理中删除站点管理员)
        User user = resetCurrentLogicGroupManager(logicGroupId, userService);

        //设置站点管理员业务系统权限
        setSubsystem(logicGroupSubsystemDisableService.findSubsystemOpen(logicGroupId));
        //设置首页显示子系统名称
        setSubsystemFromHome(logicGroupSubsystemDisableService.findSubsystemOpenFormHome(logicGroupId));

        return subLogicGroups;
    }


    /**
     * 清楚当前站点相关的所有 session.  包括：当前站点，当前站点直接子站点，当前站点的站点管理员
     */
    public void clearCurrentLogicGroupAll() {
        session.remove(Constants.Session.CURRENT_LOGIC_GROUP);
        session.remove(Constants.Session.CURRENT_SUB_LOGIC_GROUPS);
        session.remove(Constants.Session.CURRENT_LOGICGROUP_MANAGER);
    }

    /**
     * 加载 logicGroup 的 site 如果有
     *
     * @param logicGroups
     * @return
     */
    private List<LogicGroup> fetchSite(List<LogicGroup> logicGroups, LogicGroupService logicGroupService) {
        List<LogicGroup> logicGroupsWithSite = new ArrayList<LogicGroup>();
        for (LogicGroup logicGroup : logicGroups) {
            LogicGroup logicGroupWithSite = logicGroupService.findLogicGroupCarrySite(logicGroup.getId());
            logicGroupsWithSite.add(logicGroupWithSite);
        }
        return logicGroupsWithSite;
    }

    /**
     * 重置当前站点的直接子站点
     *
     * @param logicGroupId
     * @param logicGroupService
     * @return
     */
    private List<LogicGroup> resetSubLogicGroupOfCurrent(int logicGroupId, LogicGroupService logicGroupService) {
        List<LogicGroup> subLogicGroups = logicGroupService.findSubLogicGroupsCarrySite(logicGroupId);
        subLogicGroups = fetchSite(subLogicGroups, logicGroupService);
        setCurrentSubLogicGroups(subLogicGroups);
        return subLogicGroups;
    }

    /**
     * 设置当前站点的站点管理员
     *
     * @param logicGroupId
     * @param userService
     * @return
     */
    private User resetCurrentLogicGroupManager(int logicGroupId, UserService userService) {
        User user = userService.findManagerByLogicGroupId(logicGroupId);
        setCurrentLogicGroupManager(user);
        return user;
    }
}
