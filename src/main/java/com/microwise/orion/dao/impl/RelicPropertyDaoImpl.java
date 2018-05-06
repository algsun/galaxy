package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.orion.bean.RelicProperty;
import com.microwise.orion.dao.RelicPropertyDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Query;

import java.util.Arrays;
import java.util.List;

/**
 * 文物属性信息dao 实现
 * 
 * @author xubaoji
 * @date 2013-5-16
 * 
 * @check 2013-06-04 zhangpeng svn:3684
 */
@Dao
@Orion
public class RelicPropertyDaoImpl extends OrionBaseDao<RelicProperty> implements
		RelicPropertyDao {

	public RelicPropertyDaoImpl() {
		super(RelicProperty.class);
	}

	public List<RelicProperty> findRelicPropertyList(Integer relicId, Integer... belongs) {
		String hql = "from RelicProperty  rp left outer join fetch rp.property where  rp.relic.id = :relicId and rp.property.belong in (:belong)";
		Query query = getSession().createQuery(hql);
		query.setParameter("relicId", relicId);
		query.setParameterList("belong", Arrays.asList(belongs));
		return query.list();
	}



	@Override
	public void saveOrUpdateRelicProperty(List<RelicProperty> relicPropertyList) {
		for (RelicProperty relicProperty : relicPropertyList) {
			getSession().saveOrUpdate(relicProperty);
		}
	}

	@Override
	public void deleteRelicProperty(Integer relicPropertyId) {
		String hql = "delete from RelicProperty where id = :id";
		Query query = getSession().createQuery(hql);
		query.setParameter("id", relicPropertyId);
		query.executeUpdate();
	}

    @Override
    public RelicProperty findByRelicIdAndPropertyId(int relicId, int propertyId){
        String hql = "from RelicProperty rp left outer join fetch rp.property where relicId = :relicId and propertyId = :propertyId";
        Query query = getSession().createQuery(hql);
        query.setParameter("relicId", relicId);
        query.setParameter("propertyId", propertyId);
        return (RelicProperty)query.uniqueResult();
    }
}
