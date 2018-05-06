package com.microwise.proxima.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.proxima.bean.Zone;
import com.microwise.proxima.dao.ZoneDao;
import com.microwise.proxima.dao.base.BaseDaoImpl;
import com.microwise.proxima.sys.Proxima;
import org.hibernate.Query;

import java.util.List;

/**
 * 区域Dao实现类
 * 
 * @author zhangpeng
 * @date 2013-3-25
 */
@Dao
@Proxima
public class ZoneDaoImpl extends BaseDaoImpl<Zone> implements ZoneDao {

	public ZoneDaoImpl() {
		super(Zone.class);
	}

	@Override
	public List<Zone> find(String siteId) {
		Query query = getSession()
				.createQuery(
						" select DISTINCT z from Zone as z where z.site.siteId = :siteId ");
		query.setParameter("siteId", siteId);
		return query.list();
	}

	@Override
	public List<Zone> findAllZone(String siteId) {

		Query query = getSession()
				.createQuery(
						" select DISTINCT z from Zone as z left outer join fetch z.parent where z.site.siteId = :siteId ");
		query.setParameter("siteId", siteId);
		return query.list();
	}

	@Override
	public List<Zone> findHasOptics(String siteId) {
		Query query = getSession()
				.createQuery(
						" select DISTINCT dv.zone from OpticsDVPlaceBean as dv where dv.zone.site.siteId = :siteId ");
		query.setParameter("siteId", siteId);
		return query.list();
	}

	@Override
	public int getDVCount(String zoneId) {
		Query query = getSession()
				.createQuery(
						" select count(dv.id) from DVPlaceBean as dv where dv.zone.id = :zoneId ");
		query.setParameter("zoneId", zoneId);
		return ((Long) query.uniqueResult()).intValue();
	}

    @Override
    public List<Zone> findHasInfrareds(String siteId) {
        Query query = getSession()
                .createQuery(
                        " select DISTINCT dv.zone from InfraredDVPlaceBean as dv where dv.zone.site.siteId = :siteId");
        query.setParameter("siteId", siteId);
        return query.list();
    }
}
