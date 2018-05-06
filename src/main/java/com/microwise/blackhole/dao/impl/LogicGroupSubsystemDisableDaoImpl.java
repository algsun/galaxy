package com.microwise.blackhole.dao.impl;

import com.microwise.blackhole.bean.LogicGroupSubsystemDisable;
import com.microwise.blackhole.bean.Subsystem;
import com.microwise.blackhole.dao.LogicGroupSubsystemDisableDao;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeBaseDao;
import com.microwise.common.sys.annotation.Beans.Dao;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

import java.util.List;


/**
 * @author liuzhu
 * @date 2014/3/24
 * @check 2014.03.27 xiedeng  svn: 8232
 */
@Dao
@Blackhole
public class LogicGroupSubsystemDisableDaoImpl extends BlackholeBaseDao<Subsystem> implements LogicGroupSubsystemDisableDao {


    public LogicGroupSubsystemDisableDaoImpl() {
        super(Subsystem.class);
    }

    @Override
    public int changeState(int systemId, int state) {
        String hql = "UPDATE Subsystem SET ENABLE = :state WHERE subsystemId = :systemId ";
        Query query = getSession().createQuery(hql);
        query.setParameter("systemId", systemId);
        query.setParameter("state", state);
        return query.executeUpdate();
    }

    @Override
    public LogicGroupSubsystemDisable findSubsystemStateByLogicGroupId(int logicGroupId, int subsystemId) {
        String sql = "SELECT * FROM t_logicGroup_subsystem_disable WHERE logicGroupId = :logicGroupId AND subsystemId = :subsystemId";
        Query query = getSession().createSQLQuery(sql).addEntity(LogicGroupSubsystemDisable.class);
        query.setParameter("logicGroupId", logicGroupId);
        query.setParameter("subsystemId", subsystemId);
        return (LogicGroupSubsystemDisable) query.uniqueResult();
    }

    @Override
    public List<Subsystem> findSubsystems() {
        String sql = " select * from t_subsystem";
        return getSession().createSQLQuery(sql).addEntity(Subsystem.class)
                .list();
    }

    @Override
    public List<Subsystem> findAllSubsystems(String language) {
        String sql = "select subsystemId,subsystemName,subsystemName_"+language+" as subsystemName_zh_CN ,subsystemName_en_US,subsystemCode,remark,enable from t_subsystem where enable =1";
        return getSession().createSQLQuery(sql).addEntity(Subsystem.class)
                .list();
    }

    @Override
    public void able(int logicGroupId, int subsystemId) {
        String hql = "DELETE LogicGroupSubsystemDisable where logicGroupId = :logicGroupId and subsystemId = :subsystemId";
        Query query = getSession().createQuery(hql);
        query.setParameter("logicGroupId", logicGroupId);
        query.setParameter("subsystemId", subsystemId);
        query.executeUpdate();
    }

    @Override
    public void disable(String id, int logicGroupId, int subsystemId) {
        String sql = "INSERT INTO `t_logicGroup_subsystem_disable`(id,logicGroupId,subsystemId) VALUE(:id,:logicGroupId,:subsystemId)";
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        sqlQuery.setParameter("id", id);
        sqlQuery.setParameter("logicGroupId", logicGroupId);
        sqlQuery.setParameter("subsystemId", subsystemId);
        sqlQuery.executeUpdate();
    }

    @Override
    public List<Subsystem> findAllSubsystemsFromHome() {
        String sql = "select subsystemId,subsystemName,subsystemName_zh_CN,subsystemName_en_US,subsystemCode,remark,enable from t_subsystem where enable =1";
        return getSession().createSQLQuery(sql).addEntity(Subsystem.class)
                .list();
    }


}
