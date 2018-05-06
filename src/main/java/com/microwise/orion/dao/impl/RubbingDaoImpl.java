package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.orion.bean.Rubbing;
import com.microwise.orion.dao.RubbingDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Query;

/**
 * 文物拓扑 dao 实现
 * 
 * @author xubaoji
 * @date 2013-5-21
 *
 * @check 2013-06-04 zhangpeng svn:3608
 */
@Dao
@Orion
public class RubbingDaoImpl extends OrionBaseDao<Rubbing> implements RubbingDao {

	public RubbingDaoImpl() {
		super(Rubbing.class);
	}
	
	@Override
	public void deleteRubbing(Integer id) {
		String  hql = "delete from Rubbing where id =:id";
		Query query = getSession().createQuery(hql);
		query.setParameter("id", id);
		query.executeUpdate();
	}
}
