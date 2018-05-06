package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.orion.bean.Restore;
import com.microwise.orion.dao.RestoreDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Query;

/**
 * 文物修复记录 dao 实现
 * 
 * @author xubaoji
 * @date  2013-5-16
 *
 * @check 2013-06-05 zhangpeng svn:4075
 */
@Dao
@Orion
public class RestoreDaoImpl extends OrionBaseDao<Restore> implements RestoreDao{

	public RestoreDaoImpl() {
		super(Restore.class);
	}

	@Override
	public Restore findLatestRestore(Integer relicID) {
		 String hql = "from Restore  a  where  a.relic.id = :relicId order by a.restoreDate desc";
		 Query  query = getSession().createQuery(hql);
		 query.setParameter("relicId", relicID);
		 query.setMaxResults(1);
		 return (Restore) query.uniqueResult();
	}
	
}
