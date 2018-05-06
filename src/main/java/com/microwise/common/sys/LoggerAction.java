package com.microwise.common.sys;

import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.bean.SysLog;
import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.proxy.LogProxy;
import com.microwise.blackhole.sys.Sessions;
import com.opensymphony.xwork2.ActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * <pre>
 *
 * </pre>
 *
 * @author Wang yunlong
 * @time 13-2-5 下午2:33
 */
public abstract class LoggerAction {
    private static final Logger log = LoggerFactory.getLogger(LoggerAction.class);

    /**
     * 日志 service
     */
    @Autowired
    protected LogProxy logProxy;

    /**
     * 成功日志
     * <p/>
     * 业务系统为 系统管理,
     * 用户信息来自当前登录用户,
     * 站点信息来自用户当前站点
     * <p/>
     *
     * @param logName
     * @param logContent
     */
    public void log(String logName, String logContent) {
        logCurrentInSubsystem(logName, logContent, true);
    }

    /**
     * 失败日志
     * <p/>
     * 业务系统为 系统管理,
     * 用户信息来自当前登录用户,
     * 站点信息来自用户当前站点
     * <p/>
     *
     * @param logName
     * @param logContent
     */
    public void logFailed(String logName, String logContent) {
        logCurrentInSubsystem(logName, logContent, false);
    }

    /**
     * 子系统为 系统管理,
     * 用户信息来自当前登录用户,
     * 站点信息来自用户当前站点
     *
     * @param logName
     * @param logContent
     * @param state
     */
    abstract protected void logCurrentInSubsystem(String logName, String logContent, boolean state);


    /**
     * 用户信息来自当前登录用户
     * 站点信息来处用户当前站点
     *
     * @param logName
     * @param logContent
     * @param subsystemId
     * @param state
     */
    protected void logAtCurrentLogicGroup(String logName, String logContent, int subsystemId, boolean state) {
        LogicGroup logicGroup = new Sessions(ActionContext.getContext()).currentLogicGroup();
        logCurrentUserAt(logName, logContent, subsystemId, state, logicGroup);
    }

    /**
     * 用户信息来自当前登录用户
     *
     * @param logName
     * @param logContent
     * @param subsystemId
     * @param state
     * @param logicGroup
     */
    protected void logCurrentUserAt(String logName, String logContent, int subsystemId, boolean state, LogicGroup logicGroup) {
        if (logicGroup == null) {
            logCurrentUser(logName, logContent, -1, null, subsystemId, state);
        } else {
            logCurrentUser(logName, logContent, logicGroup.getId(), logicGroup.getLogicGroupName(), subsystemId, state);
        }
    }

    /**
     * 写操作成功的日志
     *
     * @param logName
     * @param logContent
     * @param logicGroupId
     * @param logicGroupName
     */
    protected void log(String logName, String logContent, int logicGroupId, String logicGroupName, int subsystemId) {
        logCurrentUser(logName, logContent, logicGroupId, logicGroupName, subsystemId, true);
    }

    /**
     * 写操作失败的日志
     *
     * @param logName
     * @param logContent
     * @param logicGroupId
     * @param logicGroupName
     */
    protected void logFailed(String logName, String logContent, int logicGroupId, String logicGroupName, int subsystemId) {
        logCurrentUser(logName, logContent, logicGroupId, logicGroupName, subsystemId, false);
    }

    /**
     * 写日志. 用户信息来自当前登录的用户
     *
     * @param logName
     * @param logContent
     * @param logicGroupId
     * @param logicGroupName
     * @param state
     */
    protected void logCurrentUser(String logName, String logContent, int logicGroupId, String logicGroupName, int subsystemId, boolean state) {
        User user = new Sessions(ActionContext.getContext()).currentUser();
        if (user == null) {
            log.warn("当前用户不存在无法写日志");
            return;
        }

        SysLog sysLog = new SysLog();
        sysLog.setUserEmail(user.getEmail());
        sysLog.setUserName(user.getUserName());
        sysLog.setLogTime(new Date());
        sysLog.setLogName(logName);
        sysLog.setLogContent(logContent);
        sysLog.setLogicGroupId(logicGroupId);
        sysLog.setLogicGroupName(logicGroupName);
        sysLog.setSubsystemId(subsystemId);
        sysLog.setLogState(state);

        logProxy.saveLog(sysLog);
    }
}
