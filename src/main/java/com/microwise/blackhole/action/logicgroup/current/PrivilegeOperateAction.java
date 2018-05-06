package com.microwise.blackhole.action.logicgroup.current;

import com.microwise.blackhole.action.role.InitRoleDetailAction;
import com.microwise.blackhole.action.role.PrivilegeFtlModel;
import com.microwise.blackhole.bean.Privilege;
import com.microwise.blackhole.bean.Subsystem;
import com.microwise.blackhole.service.LogicGroupSubsystemDisableService;
import com.microwise.blackhole.service.PrivilegeOperateService;
import com.microwise.blackhole.service.PrivilegeService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeLoggerUtil;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blackhole.util.PrivilegeUtil;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 权限操作action
 *
 * @author liuzhu
 * @date 2014/3/31
 */
@Beans.Action
@Blackhole
public class PrivilegeOperateAction extends InitRoleDetailAction {

    public static final Logger log = LoggerFactory.getLogger(PrivilegeOperateAction.class);

    /**
     * 开关子系统service
     */
    @Autowired
    private LogicGroupSubsystemDisableService logicGroupSubsystemDisableService;

    /**
     * 权限操作service
     */
    @Autowired
    private PrivilegeOperateService privilegeOperateService;

    /**
     * 系统操作日志
     */
    @Autowired
    private BlackholeLoggerUtil blackholeLoggerUtil;

    @Autowired
    private PrivilegeService privilegeService;

    //output
    /**
     * 业务系统列表
     */
    private List<Subsystem> subsystems;

    /**
     * 所有权限
     */
    protected Map<String, List<PrivilegeFtlModel>> privilegeList;

    //input
    /**
     * 角色拥有的权限列表
     */
    private String[] privileges;

    /**
     * 初始化页面
     */
    public String execute() {
        try {
            subsystems = PrivilegeUtil.findAbleSubsystem(logicGroupSubsystemDisableService);
            privilegeList = new HashMap<String, List<PrivilegeFtlModel>>();

            int logicGroupId = Sessions.createByAction().currentLogicGroup().getId();
            //以下是对所有权限处理
            List<Privilege> allPrivileges = PrivilegeUtil.findAblePrivilege(logicGroupId, privilegeService, privilegeOperateService);

            //获取系统禁用的名称
            for (Privilege privilege : allPrivileges) {
                privilege.setDisable(privilegeOperateService.isDisableById(privilege.getPrivilegeId(), logicGroupId));
            }

            //将所有业务系统分类
            for (Subsystem s : subsystems) {
                List<Privilege> privileges = new ArrayList<Privilege>();
                for (Privilege p : allPrivileges) {
                    if (p.getSubsystemId() == s.getSubsystemId()) {
                        privileges.add(p);
                    }
                }
                privilegeList.put(s.getSubsystemCode(), PrivilegeUtil.formatPrivilege(privileges));
            }
        } catch (Exception e) {
            log.error("站点开关权限", e);
        }
        blackholeLoggerUtil.log("系统管理", "站点开关子系统");
        return Action.SUCCESS;
    }

    /**
     * 禁用权限
     */
    public String disablePrivilege() {
        try {
            int groupId = Sessions.createByAction().currentLogicGroup().getId();
            if (privileges != null) {
                List<String> list = Arrays.asList(privileges);
                privilegeOperateService.disable(list, groupId);
            } else {
                privilegeOperateService.clearDisable(groupId);
            }
            ActionMessage.createByAction().success("修改成功").consume();
        } catch (Exception e) {
            log.error("禁用权限失败", e);
            ActionMessage.createByAction().fail("修改失败").consume();
        }
        blackholeLoggerUtil.log("系统管理", "禁用权限");
        return Action.SUCCESS;
    }


    public List<Subsystem> getSubsystems() {
        return subsystems;
    }

    public void setSubsystems(List<Subsystem> subsystems) {
        this.subsystems = subsystems;
    }

    public Map<String, List<PrivilegeFtlModel>> getPrivilegeList() {
        return privilegeList;
    }

    public void setPrivilegeList(Map<String, List<PrivilegeFtlModel>> privilegeList) {
        this.privilegeList = privilegeList;
    }

    public String[] getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String[] privileges) {
        this.privileges = privileges;
    }
}
