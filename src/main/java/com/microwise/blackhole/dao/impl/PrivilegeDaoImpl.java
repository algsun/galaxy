package com.microwise.blackhole.dao.impl;

import com.microwise.blackhole.bean.Privilege;
import com.microwise.blackhole.dao.PrivilegeDao;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeBaseDao;
import com.microwise.common.sys.annotation.Beans.Dao;
import org.hibernate.Query;

import java.util.List;
import java.util.Set;

/**
 * 权限Dao实现
 *
 * @author zhangpeng
 * @date 2012-11-15
 * @check 2012-11-28 xubaoji svn:440
 */
@Dao
@Blackhole
@SuppressWarnings("unchecked")
public class PrivilegeDaoImpl extends BlackholeBaseDao<PrivilegeDao> implements
        PrivilegeDao {

    public PrivilegeDaoImpl() {
        super(PrivilegeDao.class);
    }

    @Override
    public List<Privilege> findAll() {
        String hql = " select p from Privilege AS p order by p.parent.privilegeId,p.sequence ";
        Query query = getSession().createQuery(hql);
        return query.list();
    }

    @Override
    public List<Privilege> findPrivilegeListByRoleId(int roleId) {
        String hql = " select p from Role r join r.privileges AS p where r.id= ? order by p.parent.privilegeId,p.sequence ";
        Query query = getSession().createQuery(hql).setParameter(0, roleId);
        return query.list();
    }

    @Override
    public List<Privilege> findPrivilegeListBySubId(int id, Set<Integer> set) {
        String hql = " from Privilege p where p.subsystemId = :subsystemId and p.state in :set order by p.parent.privilegeId,p.sequence ";
        Query query = getSession().createQuery(hql);
        query.setParameter("subsystemId", id);
        query.setParameterList("set", set);
        return query.list();
    }

    @Override
    public List<Privilege> findPrivilegesCarryParent(int roleId,String language) {
        String sql = "SELECT tr.`privilegeId`,tsp.`parentPrivilegeId`,tsp.`subsystemId`,tsp.`privilegeName_en_US`," +
                "IF(tsp.privilegeName_" + language + " !='',tsp.privilegeName_" + language + ",privilegeName_zh_CN)AS privilegeName_zh_CN," +
                "tsp.`url`,tsp.`sequence`,tsp.`state`,tsp.`isNavigation`" +
                "FROM `t_role_privilege` AS tr INNER JOIN t_system_privilege AS tsp ON tr.`privilegeId` = tsp.`privilegeId` WHERE tr.roleId = ? ";
//        String hql = " select p from Role r join r.privileges AS p left outer join fetch p.parent where r.id= ? order by p.parent.privilegeId,p.sequence ";
//        Query query = getSession().createQuery(hql).setParameter(0, roleId);
        return getSession().createSQLQuery(sql).addEntity(Privilege.class).setParameter(0 ,roleId).list();
    }

    @Override
    public List<Privilege> findLeafPrivilegesByRoleId(int roleId) {
        String sql = "SELECT DISTINCT p.* FROM t_roles r INNER JOIN t_role_privilege rp ON r.id=rp.roleId INNER JOIN t_system_privilege p ON rp.privilegeId=p.privilegeId WHERE r.id=? AND ( p.privilegeId NOT IN  ( SELECT DISTINCT p.parentPrivilegeId FROM t_system_privilege p WHERE p.parentPrivilegeId IS NOT NULL)) order by p.parentPrivilegeId,p.sequence ";
        return getSession().createSQLQuery(sql).addEntity(Privilege.class)
                .setParameter(0, roleId).list();
    }

    @Override
    public List<Privilege> findPrivilegesByStateSet(Set<Integer> set) {
        String hql = " from Privilege p where p.state in :set order by p.parent.privilegeId,p.sequence ";
        Query query = getSession().createQuery(hql);
        query.setParameterList("set", set);
        return query.list();
    }

    @Override
    public List<Privilege> findPrivilegesByStateSetByLanguage(Set<Integer> set, String language) {
//        String sql = "select p.sequence,p.privilegeId,p.subsystemId,p.parentPrivilegeId,p.privilegeName_en_US,p.privilegeName_zh_CN " +
//                "as privilegeName_zh_CN,p.isNavigation,p.state,p.url from t_system_privilege p ";
//        if ("en_US".equals(language)) {
//            sql = "select p.sequence,p.privilegeId,p.subsystemId,p.parentPrivilegeId,p.privilegeName_en_US,IF(privilegeName_en_US!='',privilegeName_en_US,privilegeName_zh_CN)AS privilegeName_zh_CN," +
//                    "p.isNavigation,p.state,p.url from t_system_privilege p ";
//        }

        String sql = "select p.sequence,p.privilegeId,p.subsystemId,p.parentPrivilegeId,p.privilegeName_en_US," +
                "IF(privilegeName_" + language + " !='',privilegeName_" + language + ",privilegeName_zh_CN)AS privilegeName_zh_CN," +
                "p.isNavigation,p.state,p.url from t_system_privilege p ";
        String sets = "where p.state in (";
        for (int state : set) {
            sets = sets + state + ",";
        }
        sets = sets.substring(0, sets.length() - 2) + ") order by p.privilegeId,p.sequence ;";
        sql = sql + sets;
        Query query = getSession().createSQLQuery(sql).addEntity(Privilege.class);
        return query.list();
    }

}
