package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.orion.bean.StatusQuo;
import com.microwise.orion.dao.StatusQuoDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Query;

/**
 * 文物现状记录 dao 实现
 * 
 * @author xubaoji
 * @date  2013-5-16
 *
 * @check 2013-06-05 zhangpeng svn:4075
 */
@Dao
@Orion
public class StatusQuoDaoImpl extends OrionBaseDao<StatusQuo> implements StatusQuoDao {

	public StatusQuoDaoImpl() {
		super(StatusQuo.class);
	}

	@Override
	public StatusQuo findLatestStatusQuoDao(Integer relicId) {
		 String hql = "from StatusQuo  a  where  a.relic.id = :relicId order by a.quoDate desc";
		 Query  query = getSession().createQuery(hql);
		 query.setParameter("relicId", relicId);
		 query.setMaxResults(1);
		return (StatusQuo) query.uniqueResult();
	}

    @Override
    public void saveOrUpdateStatusQuo(StatusQuo statusQuo) {
        getSession().saveOrUpdate(statusQuo);
    }

    @Override
    public int updateStatusQuo(StatusQuo statusQuo) {
        String hql = "update StatusQuo st set st.quoPictures = :quoPictures where st.id = :id";
        Query  query = getSession().createQuery(hql);
        query.setParameter("quoPictures",statusQuo.getQuoPictures());
        query.setParameter("id",statusQuo.getId());
        return query.executeUpdate();
    }
}
