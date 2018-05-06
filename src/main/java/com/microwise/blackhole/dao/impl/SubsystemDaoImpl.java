package com.microwise.blackhole.dao.impl;

import com.microwise.blackhole.bean.Subsystem;
import com.microwise.blackhole.dao.SubsystemDao;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeBaseDao;
import com.microwise.common.sys.annotation.Beans.Dao;

import java.util.List;

/**
 * 业务系统Service实现
 *
 * @author zhangpeng
 * @date 2012-11-21
 * @check 2012-11-27 xubaoji svn:381
 */
@Dao
@Blackhole
public class SubsystemDaoImpl extends BlackholeBaseDao<Subsystem> implements
        SubsystemDao {

    public SubsystemDaoImpl() {
        super(Subsystem.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Subsystem> findAllSubsystems() {
        String sql = " select * from t_subsystem ";
        return getSession().createSQLQuery(sql).addEntity(Subsystem.class)
                .list();
    }
    public List<Subsystem> findAllSubsystemsByLanguage(String language) {
        String sql = "select subsystemId,subsystemName,subsystemName_"+language+" as subsystemName_zh_CN ,subsystemName_en_US,subsystemCode,remark,enable from t_subsystem ";
        return getSession().createSQLQuery(sql).addEntity(Subsystem.class)
                .list();
    }

}
