package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.orion.dao.ZoneDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import com.microwise.proxima.bean.Zone;
import org.hibernate.SQLQuery;

import java.util.List;

/**
 * 区域dao 实现类
 * 
 * @author xubaoji
 * @date 2013-5-21
 *
 * @check 2013-06-04 zhangpeng svn:3746
 */
@Dao
@Orion
public class ZoneDaoImpl extends OrionBaseDao<Zone> implements ZoneDao {

	public ZoneDaoImpl() {
		super(Zone.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Zone> findHasRelicZone(String siteId) {
		String sql = "SELECT * FROM t_zone  b  WHERE  b.zoneId  IN (SELECT  DISTINCT a.zoneId  FROM o_historical_relic a WHERE  a.siteId = :siteId)";
		SQLQuery sQuery = getSession().createSQLQuery(sql);
		sQuery.setParameter("siteId", siteId);
		sQuery.addEntity(Zone.class);
		return sQuery.list();
	}
}
