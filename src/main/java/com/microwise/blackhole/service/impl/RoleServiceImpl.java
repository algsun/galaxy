package com.microwise.blackhole.service.impl;

import com.microwise.blackhole.bean.Role;
import com.microwise.blackhole.dao.RoleDao;
import com.microwise.blackhole.service.RoleService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.common.sys.annotation.Beans.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色Service实现
 *
 * @author xubaoji
 * @date 2012-11-19
 * @check 2012-12-13 zhangpeng svn:809
 */
@Service
@Transactional
@Blackhole
public class RoleServiceImpl implements RoleService {

    /**
     * 角色Dao
     */
    @Autowired
    private RoleDao roleDao;

    @Override
    public void saveRole(int logicGroupId, String roleName,
                         List<String> privilegeIdList) {
        roleDao.saveRole(logicGroupId, roleName, privilegeIdList);
    }

    @Override
    public boolean hasSameRole(int logicGroupId, int roleId, String roleName) {

        Role role = roleDao.hasSameRole(logicGroupId, roleName);
        if (roleId == 0) { // 添加
            return role != null;
        } else { // 修改
            return role != null && role.getId() != roleId;
        }
    }

    @Override
    public void deleteRoleById(int roleId) {
        roleDao.deleteRoleById(roleId);
    }

    @Override
    public void updateRole(Role role, List<String> privilegeIdList) {
        roleDao.updateRole(role, privilegeIdList);
    }

    @Override
    public List<Role> findRoleListByLogicGroupId(int logicGroupId,
                                                 Boolean isManger) {
        return roleDao.findRoleListByLogicGroupId(logicGroupId, isManger);
    }

    @Override
    public List<Role> findRoleListByUserId(int userId) {
        return roleDao.findRoleListByUserId(userId);
    }

    @Override
    public List<Role> findRoleListByLogicGroupId(int logicGroupId,
                                                 String roleName, int stat, int max) {
        return roleDao.findRoleListByLogicGroupId(logicGroupId, roleName, stat,
                max);
    }

    @Override
    public Integer findRoleCountByLogicGroupId(int logicGroupId, String roleName) {
        return roleDao.findRoleCountByLogicGroupId(logicGroupId, roleName);
    }

    @Override
    public Role findRoleById(int id) {
        return roleDao.findById(id);
    }

    @Override
    public Role findAnonymityRole() {
        return roleDao.findAnonymityRole();
    }
}
