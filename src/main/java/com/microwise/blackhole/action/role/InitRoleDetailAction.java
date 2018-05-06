package com.microwise.blackhole.action.role;

import com.microwise.blackhole.bean.Privilege;
import com.microwise.blackhole.bean.Subsystem;
import com.microwise.blackhole.service.LogicGroupSubsystemDisableService;
import com.microwise.blackhole.service.PrivilegeOperateService;
import com.microwise.blackhole.service.PrivilegeService;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blackhole.sys.SystemPrivileges;
import com.microwise.blackhole.util.PrivilegeUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * <pre>
 *     初始化业务系统和权限
 * </pre>
 *
 * @author: Wang yunlong
 * @date: 12-11-22
 * @time: 下午3:44
 */
public abstract class InitRoleDetailAction {
    /**
     * 业务系统列表
     */
    private List<Subsystem> subsystems;
    /**
     * 系统管理权限
     * TODO bp 可能没有用到 @gaohui 2013-04-03
     */
    protected List<PrivilegeFtlModel> bp;
    /**
     * 所有权限
     */
    protected Map<String, List<PrivilegeFtlModel>> privilegeList;

    @Autowired
    private PrivilegeOperateService privilegeOperateService;

    @Autowired
    private PrivilegeService privilegeService;

    /**
     * 开关子系统service
     */
    @Autowired
    private LogicGroupSubsystemDisableService logicGroupSubsystemDisableService;

    /**
     * 初始化权限分配页面基础数据 子类action需要时重写该方法
     */
    protected void initPage() {
        subsystems = PrivilegeUtil.findAbleSubsystem(logicGroupSubsystemDisableService);
        initBlackholePrivilege();
    }

    /**
     * 初始化业务系统的权限
     * <p/>
     * TODO 方法名有歧义 @gaohui 2013-04-03
     */
    private void initBlackholePrivilege() {
        privilegeList = new HashMap<String, List<PrivilegeFtlModel>>();
        //对系统管理权限处理
        List<Privilege> blackholePrivileges = SystemPrivileges.getBlackholePrivileges();
//        // TODO bp 可能没有用到 @gaohui 2013-04-03
//        bp = formatPrivilege2(blackholePrivileges);
        int logicGroupId = Sessions.createByAction().currentLogicGroup().getId();
        //以下是对所有权限处理
        List<Privilege> allPrivileges = PrivilegeUtil.findAblePrivilege(logicGroupId, privilegeService, privilegeOperateService);
        ;
        for (Privilege privilege : allPrivileges) {
            privilege.setDisable(privilegeOperateService.isDisableById(privilege.getPrivilegeId(), logicGroupId));
        }

        for (Subsystem s : subsystems) {
            List<Privilege> privileges = new ArrayList<Privilege>();
            for (Privilege p : allPrivileges) {
                if (p.getSubsystemId() == s.getSubsystemId()) {
                    privileges.add(p);
                }
            }
            privilegeList.put(s.getSubsystemCode(), formatPrivilege2(privileges));
        }
    }

    private List<PrivilegeFtlModel> formatPrivilege2(List<Privilege> privileges) {
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
     * 将权限列表格式化成树形数据结构
     *
     * @param privileges
     * @return
     * @deprecated @wang yunlong 2013-04-24
     */
    private List<PrivilegeFtlModel> formatPrivilege(List<Privilege> privileges) {
        List<PrivilegeFtlModel> topPrivileges = new ArrayList<PrivilegeFtlModel>();
        //一级权限
        for (Privilege p : privileges) {
            if (p.getParent() == null) {
                topPrivileges.add(toPrivilegeFtlModel(p));
            }
        }

        //二级权限
        for (PrivilegeFtlModel p : topPrivileges) {
            for (Privilege privilege : privileges) {
                if (privilege.getParent() != null) {
                    if (privilege.getParent().getPrivilegeId().equals(p.getId())) {
                        PrivilegeFtlModel pf = toPrivilegeFtlModel(privilege);
                        p.getChildren().add(pf);
                    }
                }
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
    private PrivilegeFtlModel toPrivilegeFtlModel(Privilege privilege) {
        PrivilegeFtlModel pf = new PrivilegeFtlModel();
        if (privilege.getParent() != null) {
            pf.setParentId(privilege.getParent().getPrivilegeId());
        }
        pf.setId(privilege.getPrivilegeId());
        pf.setName(privilege.getPrivilegeCnName());
        pf.setSequence(privilege.getSequence());
        pf.setChildren(new ArrayList<PrivilegeFtlModel>());
        pf.setBelongCurrentRole(false);
        pf.setDisable(privilege.isDisable());
        return pf;
    }

    /**
     * 设置角色已经有的权限(所有权限) belongCurrentRole为true
     *
     * @param rolePrivileges 角色权限列表
     */
    protected void setAllHasPrivilege(List<Privilege> rolePrivileges) {
        List<String> rolePrivilegeIds = new ArrayList<String>();
        for (Privilege p : rolePrivileges) {
            rolePrivilegeIds.add(p.getPrivilegeId());
        }
        for (Collection<PrivilegeFtlModel> sPfm : privilegeList.values()) {
            childPrivilege(rolePrivilegeIds, sPfm);
        }
    }

    /**
     * 递归设置权限是否属于该角色拥有的权限
     *
     * @param rolePrivilegeIds
     * @param sPfm
     */
    private void childPrivilege(List<String> rolePrivilegeIds, Collection<PrivilegeFtlModel> sPfm) {
        for (PrivilegeFtlModel pfm : sPfm) {
            pfm.setBelongCurrentRole(rolePrivilegeIds.contains(pfm.getId()));
            childPrivilege(rolePrivilegeIds, pfm.getChildren());
        }
    }

    /**
     * 对权限进行排序
     *
     * @param topPrivileges
     */
    private void sortPrivileges(List<PrivilegeFtlModel> topPrivileges) {
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

    public List<Subsystem> getSubsystems() {
        return subsystems;
    }

    public void setSubsystems(List<Subsystem> subsystems) {
        this.subsystems = subsystems;
    }

    public List<PrivilegeFtlModel> getBp() {
        return bp;
    }

    public void setBp(List<PrivilegeFtlModel> bp) {
        this.bp = bp;
    }

    public Map<String, List<PrivilegeFtlModel>> getPrivilegeList() {
        return privilegeList;
    }

    public void setPrivilegeList(Map<String, List<PrivilegeFtlModel>> privilegeList) {
        this.privilegeList = privilegeList;
    }
}
