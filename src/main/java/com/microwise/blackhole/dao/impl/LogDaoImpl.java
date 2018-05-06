package com.microwise.blackhole.dao.impl;

import com.microwise.blackhole.bean.SysLog;
import com.microwise.blackhole.dao.LogDao;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeBaseDao;
import com.microwise.common.sys.annotation.Beans.Dao;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;

import java.util.Date;
import java.util.List;

/**
 * 日志Dao实现
 *
 * @author zhangpeng
 * @date 2012-11-15
 * @check 2012-11-27 xubaoji svn:381
 */
@Dao
@Blackhole
public class LogDaoImpl extends BlackholeBaseDao<SysLog> implements LogDao {

    public LogDaoImpl() {
        super(SysLog.class);
    }

    @Override
    public void saveLog(SysLog sysLog) {
        save(sysLog);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SysLog> findLogList(SysLog sysLog, int start, int max) {
        Example example = Example.create(sysLog).enableLike(MatchMode.ANYWHERE)
                .excludeZeroes();
        Criteria criteria = getSession().createCriteria(SysLog.class).add(
                example);
        criteria.setFirstResult((start - 1) * max);
        criteria.setMaxResults(max);
        criteria.addOrder(Order.desc("logTime"));
        return criteria.list();
    }

    @Override
    public int findLogListCount(SysLog sysLog) {
        Example example = Example.create(sysLog).enableLike(MatchMode.ANYWHERE)
                .excludeZeroes();
        Criteria criteria = getSession().createCriteria(SysLog.class).add(
                example);
        criteria.setProjection(Projections.count("id"));
        return Integer.parseInt(criteria.uniqueResult().toString());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SysLog> findLogList(SysLog sysLog, Date startTime,
                                    Date endTime, int start, int max) {
        Example example = Example.create(sysLog).enableLike(MatchMode.ANYWHERE)
                .excludeZeroes();
        Criteria criteria = getSession().createCriteria(SysLog.class).add(
                example);
        criteria.setFirstResult((start - 1) * max);
        criteria.setMaxResults(max);
        criteria.add(Restrictions.between("logTime", startTime, endTime));
        criteria.addOrder(Order.desc("logTime"));
        return criteria.list();
    }

    @Override
    public int findLogListCount(SysLog sysLog, Date startTime, Date endTime) {
        Example example = Example.create(sysLog).enableLike(MatchMode.ANYWHERE)
                .excludeZeroes();
        Criteria criteria = getSession().createCriteria(SysLog.class).add(
                example);
        criteria.add(Restrictions.between("logTime", startTime, endTime));
        criteria.setProjection(Projections.count("id"));
        return Integer.parseInt(criteria.uniqueResult().toString());
    }

}
