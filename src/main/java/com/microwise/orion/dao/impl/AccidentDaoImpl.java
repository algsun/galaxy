package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.orion.bean.Accident;
import com.microwise.orion.dao.AccidentDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Query;

/**
 * 文物事故记录 dao 实现
 * 
 * @author xubaoji
 * @date 2013-5-16
 *
 * @check 2013-06-05 zhangpeng svn:4075
 */
@Dao
@Orion
public class AccidentDaoImpl extends OrionBaseDao<Accident> implements
		AccidentDao {

	public AccidentDaoImpl() {
		super(Accident.class);
	}

	@Override
	public Accident findLatestAccident(Integer relicId) {
		 String hql = "from Accident  a  where  a.relic.id = :relicId order by a.accidentDate desc";
		 Query  query = getSession().createQuery(hql);
		 query.setParameter("relicId", relicId);
		 query.setMaxResults(1);
		return (Accident)query.uniqueResult();
	}
	
}
