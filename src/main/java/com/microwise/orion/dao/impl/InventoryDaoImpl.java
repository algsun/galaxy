package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.orion.bean.Inventory;
import com.microwise.orion.bean.InventoryError;
import com.microwise.orion.bean.InventoryOut;
import com.microwise.orion.dao.InventoryDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

import java.util.List;

/**
 * 文物盘点 dao 实现
 * 
 * @author xubaoji
 * @date 2013-5-27
 * 
 * @check 2013-06-05 zhangpeng svn:3975
 */
@Dao
@Orion
public class InventoryDaoImpl extends OrionBaseDao<Inventory> implements InventoryDao {

	public InventoryDaoImpl() {
		super(Inventory.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Inventory> findInventorys(String siteId, Integer index, Integer size) {
		String hql = " from Inventory i where i.siteId = :siteId  order by i.deadlineDate  desc , i.id desc ";
		Query query = getSession().createQuery(hql);
		query.setParameter("siteId", siteId);
		query.setFirstResult((index - 1) * size);
		query.setMaxResults(size);
		return query.list();
	}

	@Override
	public Integer findInventoryCount(String siteId) {
		String hql = " select count(i.id) from Inventory i where i.siteId = :siteId ";
		Query query = getSession().createQuery(hql);
		query.setParameter("siteId", siteId);
		return Integer.parseInt(query.uniqueResult().toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InventoryError> findInventoryErrors(Integer id) {
		String hql = " from InventoryError ie  left outer join fetch ie.relic left outer join fetch ie.relic.zone where  ie.inventory.id = :id order by ie.relic.zone.id ,ie.relic.id";
		Query query = getSession().createQuery(hql);
		query.setParameter("id", id);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InventoryOut> findInventoryOuts(Integer id) {
		String hql = " from InventoryOut io  left outer join fetch io.relic left outer join fetch io.outEvent left outer join fetch io.relic.zone where  io.inventory.id = :id order by io.relic.zone.id, io.relic.id";
		Query query = getSession().createQuery(hql);
		query.setParameter("id", id);
		return query.list();
	}

	@Override
	public Integer findStartInventory(String siteId) {
		String hql = " select i.id from Inventory  i where i.state = false and siteId = :siteId ";
		Query query = getSession().createQuery(hql);
		query.setParameter("siteId", siteId);
		return query.uniqueResult() == null ? null : Integer.parseInt(query.uniqueResult()
				.toString());
	}

	@Override
	public void updateInventoryRemark(Integer id, String remark) {
		String hql = " update Inventory set remark = :remark where id = :id";
		Query query = getSession().createQuery(hql);
		query.setParameter("remark", remark);
		query.setParameter("id", id);
		query.executeUpdate();

	}

	@Override
	public Integer findSumCount(String siteId) {
		String hql = " select count(r.id) from Relic r where r.siteId = :siteId and r.iscanceled=0";
		Query query = getSession().createQuery(hql);
		query.setParameter("siteId", siteId);
		return Integer.parseInt(query.uniqueResult().toString());
	}

	@Override
	public Integer findSumNumber(String siteId) {
		String hql = " select sum(r.count) from Relic r where r.siteId = :siteId and r.iscanceled=0";
		Query query = getSession().createQuery(hql);
		query.setParameter("siteId", siteId);
		return query.uniqueResult() == null ? 0 : Integer.parseInt(query.uniqueResult().toString());
	}

	@Override
	public Integer findTagCount(String siteId) {
		String hql = " select count(r.id) from Relic r where r.hasTag = true and r.siteId = :siteId and r.iscanceled=0";
		Query query = getSession().createQuery(hql);
		query.setParameter("siteId", siteId);
		return Integer.parseInt(query.uniqueResult().toString());
	}

	@Override
	public Integer findTagNumber(String siteId) {
		String hql = " select sum(r.count)from Relic r where r.hasTag = true and r.siteId = :siteId and r.iscanceled=0";
		Query query = getSession().createQuery(hql);
		query.setParameter("siteId", siteId);
		return query.uniqueResult() == null ? 0 : Integer.parseInt(query.uniqueResult().toString());
	}

	@Override
	public Integer findScanCount(Integer inventoryId, String siteId) {
		String sql = "  SELECT count(r.id) FROM o_inventory_tag it LEFT JOIN o_historical_relic r ON it.relicId = r.id WHERE it.inventoryId = :inventoryId and r.siteId = :siteId and r.is_canceled=0";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("inventoryId", inventoryId);
		query.setParameter("siteId", siteId);
		return Integer.parseInt(query.uniqueResult().toString());
	}

	@Override
	public Integer findScanNumber(Integer inventoryId, String siteId) {
		String sql = "  SELECT SUM(r.count) FROM o_inventory_tag it LEFT JOIN o_historical_relic r ON it.relicId = r.id WHERE it.inventoryId = :inventoryId and r.siteId = :siteId and r.is_canceled=0";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("inventoryId", inventoryId);
		query.setParameter("siteId", siteId);
		return query.uniqueResult() == null ? 0 : Integer.parseInt(query.uniqueResult().toString());
	}

	@Override
	public Integer findErrorCount(Integer inventoryId, String siteId) {
		String hql = " select count(id) from InventoryError ie where ie.inventory.id = :inventoryId and ie.relic.siteId = :siteId";
		Query query = getSession().createQuery(hql);
		query.setParameter("inventoryId", inventoryId);
		query.setParameter("siteId", siteId);
		return Integer.parseInt(query.uniqueResult().toString());
	}

	@Override
	public Integer findErrorNumber(Integer inventoryId, String siteId) {
		String hql = " select sum(ie.relic.count) from InventoryError ie  where ie.inventory.id = :inventoryId and ie.relic.siteId = :siteId";
		Query query = getSession().createQuery(hql);
		query.setParameter("inventoryId", inventoryId);
		query.setParameter("siteId", siteId);
		return query.uniqueResult() == null ? 0 : Integer.parseInt(query.uniqueResult().toString());
	}

	@Override
	public Integer findStockCount(boolean isOut, String siteId) {
		StringBuilder hql = new StringBuilder(" select count(id) from Relic r  ");
		if (isOut) {
			hql.append(" where state = 2");
		} else {
			hql.append(" where state != 2");
		}
		hql.append(" and  r.siteId = :siteId and r.iscanceled=0");
		Query query = getSession().createQuery(hql.toString());
		query.setParameter("siteId", siteId);
		return Integer.parseInt(query.uniqueResult().toString());
	}

	@Override
	public Integer findStockNumber(boolean isOut, String siteId) {
		StringBuilder hql = new StringBuilder(" select sum(r.count) from Relic r  ");
		if (isOut) {
			hql.append(" where state = 2");
		} else {
			hql.append(" where state != 2");
		}
		hql.append(" and  r.siteId = :siteId and r.iscanceled=0");
		Query query = getSession().createQuery(hql.toString());
		query.setParameter("siteId", siteId);
		return query.uniqueResult() == null ? 0 : Integer.parseInt(query.uniqueResult().toString());
	}

	@Override
	public Inventory findLastInventory(String siteId) {
		String hql = " from Inventory i where i.siteId = :siteId and i.state=true  order by i.deadlineDate  desc  ";
		Query query = getSession().createQuery(hql);
		query.setParameter("siteId", siteId);
		query.setMaxResults(1);
		return (Inventory) query.uniqueResult();
	}

}
