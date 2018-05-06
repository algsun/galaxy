package com.microwise.blackhole.dao.impl;

import com.microwise.blackhole.bean.PrivilegeOperate;
import com.microwise.blackhole.dao.PrivilegeOperateDao;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeBaseDao;
import com.microwise.common.sys.annotation.Beans;
import org.hibernate.Query;

import java.util.List;

/**
 * @author liuzhu
 * @date 2014/3/31
 */
@Beans.Dao
@Blackhole
public class PrivilegeOperateDapImpl extends BlackholeBaseDao<PrivilegeOperate> implements PrivilegeOperateDao {

    public PrivilegeOperateDapImpl() {
        super(PrivilegeOperate.class);
    }

    @Override
    public void disable(PrivilegeOperate privilegeOperate) {
        save(privilegeOperate);
    }

    @Override
    public void cleanDisable(int logicGroupId) {
        String sql = "DELETE FROM t_privilege_operate_disable WHERE logicGroupId=:logicGroupId";
        Query query = getSession().createSQLQuery(sql);
        query.setParameter("logicGroupId", logicGroupId);
        query.executeUpdate();
    }

    @Override
    public boolean isDisableById(String privilegeId, int logicGroupId) {
        String sql = "SELECT COUNT(*) FROM t_privilege_operate_disable WHERE privilegeId = :privilegeId AND logicGroupId = :logicGroupId";
        Query query = getSession().createSQLQuery(sql);
        query.setParameter("privilegeId", privilegeId);
        query.setParameter("logicGroupId", logicGroupId);
        int count = Integer.parseInt(query.uniqueResult().toString());
        if (count >= 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<String> findDisableSubsystemId(int logicGroupId) {
        String sql = "SELECT  s.subsystemCode FROM `t_subsystem` AS s LEFT JOIN `t_logicGroup_subsystem_disable` AS lsd ON s.subsystemId = lsd.subsystemId WHERE ENABLE = 0 OR lsd.id IS NOT NULL AND lsd.`logicGroupId`= :logicGroupId";
        Query query = getSession().createSQLQuery(sql);
        query.setParameter("logicGroupId", logicGroupId);
        return query.list();
    }

    @Override
    public List<String> findDisablePrivilegeId(int logicGroupId) {
        String sql = "SELECT privilegeId FROM t_privilege_operate_disable WHERE logicGroupId=:logicGroupId";
        Query query = getSession().createSQLQuery(sql);
        query.setParameter("logicGroupId", logicGroupId);
        return query.list();
    }
}
