package com.microwise.blackhole.action.logicgroup.current;

import com.microwise.blackhole.bean.Subsystem;
import com.microwise.blackhole.service.LogicGroupSubsystemDisableService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeLoggerUtil;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.GalaxyIdUtil;
import com.opensymphony.xwork2.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 站点开关子系统action
 *
 * @author liuzhu
 * @date 2014/3/24
 * @check 2014.03.27 xiedeng  #svn: 8234
 */
@Beans.Action
@Blackhole
public class LogicGroupSubsystemDisableAction {

    public static final Logger log = LoggerFactory.getLogger(LogicGroupSubsystemDisableAction.class);

    /**
     * 系统操作日志
     */
    @Autowired
    private BlackholeLoggerUtil logger;

    /**
     * 开关子系统service
     */
    @Autowired
    private LogicGroupSubsystemDisableService logicGroupSubsystemDisableService;

    //input
    /**
     * 系统list
     */
    private List<Subsystem> subsystemList = new ArrayList<Subsystem>();

    //output
    /**
     * 开启状态 0未开启 1开启
     */
    private int state;

    /**
     * 系统id
     */
    private int systemId;

    /**
     * 站点开关子系统页面
     */
    public String execute() {
        try {
            int logicGroupId = Sessions.createByAction().currentLogicGroup().getId();
            subsystemList = logicGroupSubsystemDisableService.findAllSubsystemByLogicGroupId(logicGroupId);
        } catch (Exception e) {
            log.error("站点开关子系统", e);
        }
        logger.log("开关子系统", "系统管理");
        return Action.SUCCESS;
    }

    /**
     * 站点配置页面
     */
    public String siteConfig() {
        try {
            subsystemList = logicGroupSubsystemDisableService.findSubsystem();
        } catch (Exception e) {
            log.error("站点配置", e);
        }
        logger.log("站点配置", "系统管理");
        return Action.SUCCESS;
    }

    /**
     * 启用业务系统
     */
    public String able() {
        try {
            int logicGroupId = Sessions.createByAction().currentLogicGroup().getId();
            logicGroupSubsystemDisableService.able(logicGroupId, systemId);
        } catch (Exception e) {
            log.error("启用业务系统", e);
        }
        logger.log("启用业务系统", "系统管理");
        return Action.SUCCESS;
    }

    /**
     * 禁用业务系统
     */
    public String disable() {
        try {
            int logicGroupId = Sessions.createByAction().currentLogicGroup().getId();
            logicGroupSubsystemDisableService.disable(GalaxyIdUtil.get64UUID(), logicGroupId, systemId);
        } catch (Exception e) {
            log.error("禁用业务系统", e);
        }
        logger.log("禁用业务系统", "系统管理");
        return Action.SUCCESS;
    }

    /**
     * 站点开启或关闭业系统
     */
    public String changeState() {
        try {
            logicGroupSubsystemDisableService.changeState(systemId, state);
        } catch (Exception e) {
            log.error("全局启用或禁用业务系统", e);
        }
        logger.log("全局启用或禁用业务系统", "系统管理");
        return Action.SUCCESS;
    }

    public List<Subsystem> getSubsystemList() {
        return subsystemList;
    }

    public void setSubsystemList(List<Subsystem> subsystemList) {
        this.subsystemList = subsystemList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getSystemId() {
        return systemId;
    }

    public void setSystemId(int systemId) {
        this.systemId = systemId;
    }
}
