package com.microwise.blackhole.dao.impl;

import com.google.common.base.Strings;
import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.bean.Privilege;
import com.microwise.blackhole.bean.Role;
import com.microwise.blackhole.dao.RoleDao;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeBaseDao;
import com.microwise.common.sys.annotation.Beans.Dao;
import org.hibernate.Query;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 角色Dao实现
 *
 * @author xubaoji
 * @date 2012-11-19
 * @check 2012-12-13 zhangpeng svn:809
 */
@Dao
@Blackhole
public class RoleDaoImpl extends BlackholeBaseDao<Role> implements RoleDao {

    public RoleDaoImpl() {
        super(Role.class);
    }

    @Override
    public void saveRole(int logicGroupId, String roleName,
                         List<String> privilegeIdList) {
        // 组装 角色 po 对象
        Role role = new Role();
        role.setRoleName(roleName);
        Set<Privilege> privileges = new HashSet<Privilege>();
        for (String privilegeId : privilegeIdList) {
            Privilege privilege = new Privilege();
            privilege.setPrivilegeId(privilegeId);
            privileges.add(privilege);
        }
        role.setPrivileges(privileges);
        LogicGroup logicGroup = new LogicGroup();
        logicGroup.setId(logicGroupId);
        role.setLogicGroup(logicGroup);
        save(role);
    }

    @Override
    public Role hasSameRole(int logicGroupId, String roleName) {
        String hql = " select r from Role r where r.logicGroup.id= ? and r.roleName= ? ";
        Query q = getSession().createQuery(hql);
        q.setParameter(0, logicGroupId);
        q.setParameter(1, roleName);
        return (Role) q.uniqueResult();
    }

    @Override
    public void deleteRoleById(int roleId) {
        delete(findById(roleId));
    }

    @Override
    public void updateRole(Role role, List<String> privilegeIdList) {
        Set<Privilege> privileges = new HashSet<Privilege>();
        for (String privilegeId : privilegeIdList) {
            Privilege privilege = new Privilege();
            privilege.setPrivilegeId(privilegeId);
            privileges.add(privilege);
        }
        role.setPrivileges(privileges);
        update(role);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Role> findRoleListByLogicGroupId(int logicGroupId,
                                                 Boolean isManger) {
        String hql;
        if (isManger == null) {
            hql = "from Role r where  r.logicGroup.id=?";
        } else {
            hql = "from Role r  where  r.logicGroup.id=? and r.manager=?";
        }
        Query q = getSession().createQuery(hql);
        q.setParameter(0, logicGroupId);
        if (isManger != null) {
            q.setParameter(1, isManger);
        }
        return q.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Role> findRoleListByUserId(int userId) {
        String hql = " Select u.roles From User u Where u.id = ? ";
        Query query = getSession().createQuery(hql);
        query.setParameter(0, userId);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Role> findRoleListByLogicGroupId(int logicGroupId,
                                                 String roleName, int stat, int max) {
        String hql;
        if (!Strings.isNullOrEmpty(roleName)) {
            hql = "select r from Role r where  r.logicGroup.id= ? and r.roleName like ?";
        } else {
            hql = "select r from Role r where  r.logicGroup.id= ?";
        }
        hql += " order by r.id desc ";
        Query q = getSession().createQuery(hql);
        q.setParameter(0, logicGroupId);
        if (!Strings.isNullOrEmpty(roleName)) {
            q.setParameter(1, "%" + roleName + "%");
        }
        q.setFirstResult((stat - 1) * max);
        q.setMaxResults(max);
        return q.list();
    }

    @Override
    public Integer findRoleCountByLogicGroupId(int logicGroupId, String roleName) {
        String hql;
        if (!Strings.isNullOrEmpty(roleName)) {
            hql = "select count(r.id) from Role r where  r.logicGroup.id=? and r.roleName like  ? ";
        } else {
            hql = "select count(r.id) from Role r where  r.logicGroup.id=? ";
        }
        Query q = getSession().createQuery(hql);
        q.setParameter(0, logicGroupId);
        if (!Strings.isNullOrEmpty(roleName)) {
            q.setParameter(1, "%" + roleName + "%");
        }
        return Integer.parseInt(q.uniqueResult().toString());
    }

    @Override
    public Role findAdminRoleByLogicGroupId(int logicGroupId) {
        String hql = "from  Role r where r.logicGroup.id = ? and r.manager=true";
        Query q = getSession().createQuery(hql);
        q.setParameter(0, logicGroupId);
        return (Role) q.uniqueResult();
    }

    @Override
    public Role findAnonymityRole() {
        Query q = getSession().createQuery("select r from Role r left join fetch r.privileges as p where r.state = 1");
        q.setMaxResults(1);
        return (Role) q.uniqueResult();
    }
}
