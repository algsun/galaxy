package com.microwise.orion.dao.impl;

import com.microwise.blackhole.bean.User;
import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.orion.bean.UserZone;
import com.microwise.orion.dao.UserZoneDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import com.microwise.proxima.bean.Zone;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

import java.util.List;

/**
 * 用户和区域对应关系 dao 实现
 * 
 * @author xubaoji
 * @date 2013-6-17
 */
@Dao
@Orion
public class UserZoneDaoImpl extends OrionBaseDao<UserZone> implements
		UserZoneDao {

	public UserZoneDaoImpl() {
		super(UserZone.class);
	}

	@Override
	public void deleteUserZone(Integer userId, String zoneId) {
		String hql = " delete from UserZone  where userId = :userId and zoneId = :zoneId ";
		Query query = getSession().createQuery(hql);
		query.setParameter("userId", userId);
		query.setParameter("zoneId", zoneId);
		query.executeUpdate();

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Zone> findZones(String siteId) {
		String sql = "SELECT  DISTINCT z.*  FROM o_user_zone uz INNER JOIN  t_zone  z ON uz.zoneId = z.zoneId WHERE z.siteId = :siteId ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter("siteId", siteId);
		sqlQuery.addEntity(Zone.class);
		return sqlQuery.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> findZoneUser(String zoneId) {
		String sql = " SELECT DISTINCT u.* FROM  o_user_zone  uz  INNER JOIN  t_users u ON uz.userId = u.id  WHERE uz.zoneId = :zoneId ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter("zoneId", zoneId);
		sqlQuery.addEntity(User.class);
		return sqlQuery.list();
	}

}
