package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.orion.bean.InventoryTag;
import com.microwise.orion.dao.InventoryTagDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.util.List;

/**
 * 盘点 扫描有标签 文物 dao
 * 
 * @author xubaoji
 * @date 2013-5-27
 *
 * @check 2013-06-05 zhangpeng svn:3853
 */
@Dao
@Orion
public class InventoryTagDaoImpl extends OrionBaseDao<InventoryTag> implements
		InventoryTagDao {

	public InventoryTagDaoImpl() {
		super(InventoryTag.class);
	}

	@Override
	public void addInventoryTags(List<InventoryTag> inventoryTagList) {
		Session session = getSession();
		int i = 0;
		for (InventoryTag inventoryTag : inventoryTagList) {
			session.saveOrUpdate(inventoryTag);
			i++;
			if (i == 50) {
				session.flush();
				session.clear();
				i = 0;
			}
		}

	}

	@Override
	public void deleteInventoryTags(Integer inventoryId) {
		String hql = " delete from InventoryTag where inventoryId = :inventoryId ";
		Query query = getSession().createQuery(hql);
		query.setParameter("inventoryId", inventoryId);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> findNoScanAndIn(String siteId) {
		String sql = " select r.id from o_historical_relic r left join o_inventory_tag  it on r.id = it.relicId where r.hasTag = true and r.state in (0, 1, 3)  and r.siteId = :siteId and r.is_canceled=0 and  it.relicId is null ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter("siteId", siteId);
		return sqlQuery.list();
	}

    @SuppressWarnings("unchecked")
    @Override
    public Integer findNoScanAndInCount(String siteId){
        String sql = " select sum(r.count) from o_historical_relic r left join o_inventory_tag  it on r.id = it.relicId where r.hasTag = true and r.state in (0, 1, 3)  and r.siteId = :siteId and r.is_canceled=0 and  it.relicId is null ";
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        sqlQuery.setParameter("siteId", siteId);
        return sqlQuery.uniqueResult() == null ? 0 : Integer.parseInt(sqlQuery.uniqueResult().toString());
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> findScanAndOut(String siteId) {
		String sql = "  select r.id from o_historical_relic r inner join o_inventory_tag  it on r.id = it.relicId  where  r.state = 2 and r.hasTag = true and r.siteId = :siteId and r.is_canceled=0";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter("siteId", siteId);
		return sqlQuery.list();
	}

    @SuppressWarnings("unchecked")
    @Override
    public Integer findScanAndOutCount(String siteId){
        String sql = "  select sum(r.count) from o_historical_relic r inner join o_inventory_tag  it on r.id = it.relicId  where  r.state = 2 and r.hasTag = true and r.siteId = :siteId and r.is_canceled=0";
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        sqlQuery.setParameter("siteId", siteId);
        return sqlQuery.uniqueResult() == null ? 0 : Integer.parseInt(sqlQuery.uniqueResult().toString());
    }
}
