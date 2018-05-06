package com.microwise.blackhole.util;

import com.microwise.blackhole.action.role.PrivilegeFtlModel;
import com.microwise.blackhole.bean.Privilege;
import com.microwise.blackhole.bean.Subsystem;
import com.microwise.blackhole.service.LogicGroupSubsystemDisableService;
import com.microwise.blackhole.service.PrivilegeOperateService;
import com.microwise.blackhole.service.PrivilegeService;
import com.microwise.blackhole.sys.Sessions;

import java.util.*;

/**
 * 权限操作util
 *
 * @author liuzhu
 * @date 2014/4/1
 */
public class PrivilegeUtil {

    /**
     * 权限数据组织
     */
    public static List<PrivilegeFtlModel> formatPrivilege(List<Privilege> privileges) {
        List<PrivilegeFtlModel> privilegeFtlModels = new ArrayList<PrivilegeFtlModel>();
        //Privilege 转换为 PrivilegeFtlModel
        for (Privilege p : privileges) {
            privilegeFtlModels.add(toPrivilegeFtlModel(p));
        }

        //组织关系
        for (PrivilegeFtlModel p1 : privilegeFtlModels) {
            for (PrivilegeFtlModel p2 : privilegeFtlModels) {
                if (p1.getId().equals(p2.getParentId())) {
                    p1.getChildren().add(p2);
                }
            }
        }

        List<PrivilegeFtlModel> topPrivileges = new ArrayList<PrivilegeFtlModel>();
        for (PrivilegeFtlModel p : privilegeFtlModels) {
            if (p.getParentId() == null) {
                topPrivileges.add(p);
            }
        }

        sortPrivileges(topPrivileges);
        return topPrivileges;
    }


    /**
     * 将原始权限的转换为负责页面展示的数据格式
     *
     * @param privilege 权限
     * @return privilegeFltModel
     */
    private static PrivilegeFtlModel toPrivilegeFtlModel(Privilege privilege) {
        PrivilegeFtlModel pf = new PrivilegeFtlModel();
        if (privilege.getParent() != null) {
            pf.setParentId(privilege.getParent().getPrivilegeId());
        }
        pf.setId(privilege.getPrivilegeId());
        pf.setName(privilege.getPrivilegeCnName());
        pf.setSequence(privilege.getSequence());
        pf.setChildren(new ArrayList<PrivilegeFtlModel>());
        pf.setBelongCurrentRole(privilege.isDisable());
        return pf;
    }

    /**
     * 对权限进行排序
     *
     * @param topPrivileges
     */
    private static void sortPrivileges(List<PrivilegeFtlModel> topPrivileges) {
        //同级权限根据 sequence 排序的 Comparator
        Comparator<PrivilegeFtlModel> sequenceComparator = new Comparator<PrivilegeFtlModel>() {
            @Override
            public int compare(PrivilegeFtlModel o1, PrivilegeFtlModel o2) {
                return o1.getSequence() - o2.getSequence();
            }
        };

        // 将一级权限进行排序
        Collections.sort(topPrivileges, sequenceComparator);
        // 将二级权限进行排序
        for (PrivilegeFtlModel topPrivilege : topPrivileges) {
            Collections.sort(topPrivilege.getChildren(), sequenceComparator);
        }
    }

    public static List<Subsystem> findAbleSubsystem(LogicGroupSubsystemDisableService logicGroupSubsystemDisableService) {
        List<Subsystem> subsystems = logicGroupSubsystemDisableService.findSubsystemOpen(Sessions.createByAction().currentLogicGroup().getId());
        return subsystems;
    }

    /**
     * 获取所有启用的权限
     *
     * @param privilegeService
     * @param privilegeOperateService
     * @return privilege 集合
     * @author liuzhu
     * @date 2014-4-1
     */
    public static List<Privilege> findAblePrivilege(int logicGroupId, PrivilegeService privilegeService, PrivilegeOperateService privilegeOperateService) {
        //以下是对所有权限处理
        List<Privilege> allPrivileges = privilegeService.findAll();

        //获取系统禁用的名称
        List<String> disableSubsystemId = privilegeOperateService.findDisableSubsystemId(logicGroupId);
        List<String> disablePrivilegeId = new ArrayList<String>();
        for (String string : disableSubsystemId) {
            String privilegeStr = "blackhole:subsystem:";
            disablePrivilegeId.add(privilegeStr + string);
        }

        Iterator iterator = allPrivileges.iterator();
        while (iterator.hasNext()) {
            Privilege privilege = (Privilege) iterator.next();
            for (String privilegeId : disablePrivilegeId) {
                if (privilege.getPrivilegeId().contains(privilegeId)) {
                    iterator.remove();
                }
            }
        }
        return allPrivileges;
    }

}
