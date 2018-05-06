package com.microwise.proxima.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.proxima.dao.SystemConfigDao;
import com.microwise.proxima.dao.base.BaseDaoImpl;
import com.microwise.proxima.sys.Proxima;
import org.hibernate.SQLQuery;

/**
 * 系统配置Dao实现类
 * 
 * @author zhangpeng
 * @date 2013-3-26
 */
@Dao
@Proxima
public class SystemConfigDaoImpl extends BaseDaoImpl<Object> implements
		SystemConfigDao {

	public SystemConfigDaoImpl() {
		super(Object.class);
	}

	@Override
	public int findDvPlaceCodeIndex() {
		String sql = " SELECT s.`dvPlaceCodeIndex` FROM `m_systemconfig` AS s  ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        sqlQuery.setMaxResults(1);
		return (Integer) sqlQuery.uniqueResult();
	}

	@Override
	public void updateDvPlaceCodeIndex(int index) {
		String sql = " UPDATE `m_systemconfig` AS s SET s.`dvPlaceCodeIndex`=:index ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter("index", index);
		sqlQuery.executeUpdate();
	}

}
