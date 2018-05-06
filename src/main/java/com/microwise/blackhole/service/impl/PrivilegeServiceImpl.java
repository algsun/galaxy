package com.microwise.blackhole.service.impl;

import com.microwise.blackhole.bean.Privilege;
import com.microwise.blackhole.dao.PrivilegeDao;
import com.microwise.blackhole.service.PrivilegeService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.PrivilegeStateUtil;
import com.microwise.common.sys.annotation.Beans.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * 权限Service实现
 *
 * @author zhangpeng
 * @date 2012-11-15
 * @check 2012-11-28 xubaoji svn:440
 */
@Service
@Transactional
@Blackhole
public class PrivilegeServiceImpl implements PrivilegeService {

    /**
     * 权限Dao
     */
    @Autowired
    private PrivilegeDao privilegeDao;

    @Override
    public List<Privilege> findAll() {
        Set<Integer> set = PrivilegeStateUtil
                .getStateByFilter(PrivilegeStateUtil.PRIVILEGE_STATE_REUQIRED);
        return privilegeDao.findPrivilegesByStateSet(set);
    }

    @Override
    public List<Privilege> findPrivilegeListByRoleId(int roleId) {
        return privilegeDao.findPrivilegeListByRoleId(roleId);
    }

    @Override
    public List<Privilege> findPrivilegeListBySubId(int id) {
        Set<Integer> set = PrivilegeStateUtil
                .getDBFuzzyState(PrivilegeStateUtil.PRIVILEGE_STATE_OPTIONAL);
        return privilegeDao.findPrivilegeListBySubId(id, set);
    }

    @Override
    public List<Privilege> findPrivilegesCarryParent(int roleId,String language) {
        return privilegeDao.findPrivilegesCarryParent(roleId,language);
    }

    @Override
    public List<Privilege> findLeafPrivilegesByRoleId(int roleId) {
        return privilegeDao.findLeafPrivilegesByRoleId(roleId);
    }

    @Override
    public List<Privilege> findSupermanPrivileges(String language) {
        Set<Integer> set = PrivilegeStateUtil
                .getDBFuzzyState(PrivilegeStateUtil.PRIVILEGE_STATE_IS_SUPERMAN);
        return privilegeDao.findPrivilegesByStateSetByLanguage(set, language);
    }

    @Override
    public List<Privilege> findManagerPrivileges(String language) {
        Set<Integer> set = PrivilegeStateUtil
                .getDBFuzzyState(PrivilegeStateUtil.PRIVILEGE_STATE_IS_MANAGER);
        return privilegeDao.findPrivilegesByStateSetByLanguage(set, language);
    }

    @Override
    public List<Privilege> findGuestPrivileges(String language) {
        Set<Integer> set = PrivilegeStateUtil
                .getDBFuzzyState(PrivilegeStateUtil.PRIVILEGE_STATE_IS_GUEST);
        return privilegeDao.findPrivilegesByStateSetByLanguage(set, language);
    }

    @Override
    public List<Privilege> findRequiredPrivileges(String language) {
        Set<Integer> set = PrivilegeStateUtil
                .getDBFuzzyState(PrivilegeStateUtil.PRIVILEGE_STATE_REUQIRED);
        return privilegeDao.findPrivilegesByStateSetByLanguage(set, language);
    }

    @Override
    public List<Privilege> findRequiredPrivilegesByLanguage(String language) {
        Set<Integer> set = PrivilegeStateUtil
                .getDBFuzzyState(PrivilegeStateUtil.PRIVILEGE_STATE_REUQIRED);
        return privilegeDao.findPrivilegesByStateSetByLanguage(set, language);
    }

}
