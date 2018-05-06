package com.microwise.proxima.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.proxima.bean.OpticsDVPlaceBean;
import com.microwise.proxima.dao.OpticsDVPlaceDao;
import com.microwise.proxima.dao.base.BaseDaoImpl;
import com.microwise.proxima.sys.Proxima;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * 光学摄像机 dao 实现
 * 
 * @author gaohui
 * @date 2012-7-10
 * 
 * @check zhang.licong 2012-07-14
 */
@Dao
@Proxima
public class OpticsDVPlaceDaoImpl extends BaseDaoImpl<OpticsDVPlaceBean>
		implements OpticsDVPlaceDao {

	public OpticsDVPlaceDaoImpl() {
		super(OpticsDVPlaceBean.class);
	}

	@Override
	public List<OpticsDVPlaceBean> findAll(String siteId) {
		Query query = getSession()
				.createQuery(
						"select dv from OpticsDVPlaceBean dv inner JOIN fetch dv.zone as zone where zone.site.siteId = :siteId order by zone.name, dv.createTime DESC");
		query.setParameter("siteId", siteId);
		return query.list();
	}

	public List<OpticsDVPlaceBean> findAll(String siteId, int pageNmb,
			int pageSize) {
		Query query = getSession()
				.createQuery(
						"select dv from OpticsDVPlaceBean dv inner JOIN fetch dv.zone where dv.zone.site.siteId = :siteId order by dv.createTime DESC");
		query.setParameter("siteId", siteId);
		query.setFirstResult((pageNmb - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.list();
	}

	@Override
	public int findAllCount(String siteId) {
		Query query = getSession()
				.createQuery(
						"select COUNT(dv.id) from OpticsDVPlaceBean dv where dv.zone.site.siteId = :siteId");
		query.setParameter("siteId", siteId);
		return ((Long) query.uniqueResult()).intValue();
	}

	@Override
	public List<OpticsDVPlaceBean> findByZoneId(String zoneId) {
		Query query = getSession()
				.createQuery(
						"select dv from OpticsDVPlaceBean dv inner JOIN fetch dv.zone where dv.zone.id = :zoneId order by dv.createTime DESC");
		query.setParameter("zoneId", zoneId);
		return query.list();
	}

	@Override
	public List<OpticsDVPlaceBean> findByZoneId(String zoneId, int pageNmb,
			int pageSize) {
		Query query = getSession()
				.createQuery(
						"select dv from OpticsDVPlaceBean dv inner JOIN fetch dv.zone where dv.zone.id = :zoneId order by dv.createTime DESC");
		query.setParameter("zoneId", zoneId);
		query.setFirstResult((pageNmb - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.list();
	}

	@Override
	public int findByZoneIdCount(String zoneId) {
		Query query = getSession()
				.createQuery(
						"select COUNT(dv.id) from OpticsDVPlaceBean dv where dv.zone.id = :zoneId");
		query.setParameter("zoneId", zoneId);
		return ((Long) query.uniqueResult()).intValue();
	}

	@Override
	public List<OpticsDVPlaceBean> findAllEnable() {
		Session session = super.getSession();

		Query query = session
				.createQuery("select dvPlace from OpticsDVPlaceBean dvPlace where dvPlace.enable = true");
		return (List<OpticsDVPlaceBean>) query.list();
	}

	@Override
	public List<OpticsDVPlaceBean> findByMonitorPointId(int monitorPointId) {
		Session session = getSession();
		String hql = "select dvPlace from OpticsDVPlaceBean dvPlace  where monitorPointId = :monitorPointId";
		Query query = session.createQuery(hql);
		query.setParameter("monitorPointId", monitorPointId);
		return (List<OpticsDVPlaceBean>) query.list();
	}

	@Override
	public List<OpticsDVPlaceBean> findAllWithMonitorPoint() {
		Query query = getSession()
				.createQuery(
						"select dvPlace from OpticsDVPlaceBean dvPlace INNER JOIN fetch dvPlace.monitorPoint ORDER BY dvPlace.createTime DESC");
		return query.list();
	}

	@Override
	public OpticsDVPlaceBean findById(String id) {
		Query query = getSession()
				.createQuery(
						"select dv from OpticsDVPlaceBean dv inner JOIN fetch dv.zone inner JOIN fetch dv.ftp where dv.id = :id");
		query.setParameter("id", id);
		return (OpticsDVPlaceBean) query.uniqueResult();
	}

	@Override
	public boolean isIoPortUsingByAdd(int ioPort) {
		Query query = getSession()
				.createQuery(
						"select count(dv.id) from OpticsDVPlaceBean dv where dv.ioPort = :ioPort");
		query.setParameter("ioPort", ioPort);
		long count = (Long) query.uniqueResult();
		return count > 0;
	}

	@Override
	public boolean isIoPortUsingByUpdate(String dvPlaceId, int ioPort) {
		Query query = getSession()
				.createQuery(
						"select count(dv.id) from OpticsDVPlaceBean dv where dv.id<>:dvPlaceId and dv.ioPort = :ioPort ");
		query.setParameter("dvPlaceId", dvPlaceId);
		query.setParameter("ioPort", ioPort);
		long count = (Long) query.uniqueResult();
		return count > 0;
	}

}
