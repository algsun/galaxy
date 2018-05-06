package com.microwise.blackhole.action.app;


import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.bean.Privilege;
import com.microwise.blackhole.bean.Subsystem;
import com.microwise.blackhole.service.LogicGroupSubsystemDisableService;
import com.microwise.blackhole.service.PrivilegeOperateService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 数据分析权限监听
 *
 * @author liuzhu
 * @date 2013-09-22
 * @check #5630 @gaohui 2013-09-23
 */
@Beans.Bean
@Blackhole
public class PhoenixPrivilegesListener implements BeforeInitPrivilegesListener {

    @Autowired
    private LogicGroupSubsystemDisableService logicGroupSubsystemDisableService;

    @Autowired
    private PrivilegeOperateService privilegeOperateService;

    @Override
    public void beforeInit(Collection<Privilege> privileges, LogicGroup logicGroup) {

        List<Subsystem> subsystemList = new ArrayList<Subsystem>();
        List<String> privilegeList = new ArrayList<String>();

        if (logicGroup != null) {
            subsystemList = logicGroupSubsystemDisableService.findAllSubsystemByLogicGroupId(logicGroup.getId());
            privilegeList = privilegeOperateService.findDisablePrivilegeId(logicGroup.getId());
        }
        Sessions.createByAction().currentUser();

        //过滤禁用的系统
        for (Subsystem subsystem : subsystemList) {
            if (subsystem.getState() == 1 || !subsystem.isEnable()) {    //业务系统未启用
                String subCode = subsystem.getSubsystemCode();
                iteratorPrivileges(privileges, subCode);
            }
        }

        //过滤禁用的权限
        for (String privilegeId : privilegeList) {
            for (Iterator<Privilege> it = privileges.iterator(); it.hasNext(); ) {
                Privilege privilege = it.next();
                if (privilege.getPrivilegeId().equals(privilegeId)) {
                    it.remove();
                }
            }
        }
    }

    /**
     * 迭代系统所有的privileges
     *
     * @param privileges 权限的iterator
     * @param subCode    业务系统代号
     * @author liuzhu
     * @date 2013-09-22
     */
    private void iteratorPrivileges(Collection<Privilege> privileges, String subCode) {
        for (Iterator<Privilege> iterator = privileges.iterator(); iterator.hasNext(); ) {
            Privilege privilege = iterator.next();
            if (privilege.getSubsystemId() == Constants.Subsystems.Phoenix) {//所属业务系统id为数据分析id
                if (privilege.getPrivilegeId().contains(subCode)) {
                    iterator.remove();
                } else if (privilege.getPrivilegeId().equals("phoenix:index:morningAndEvening")) {//权限id为“phoenix:index:morningAndEvening”(早晚考勤)
                    iterator.remove();
                }
            }
        }
    }
}
