package com.microwise.proxima.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.proxima.bean.DVPlaceBean;
import com.microwise.proxima.dao.DVPlaceDao;
import com.microwise.proxima.dao.base.BaseDaoImpl;
import com.microwise.proxima.sys.Proxima;
import org.hibernate.Query;

import java.util.List;

/**
 * @author gaohui
 * @date 2012-7-6
 * @check @li.jianfei 2013.09.02 #5293
 */
@Dao
@Proxima
public class DVPlaceDaoImpl extends BaseDaoImpl<DVPlaceBean> implements
		DVPlaceDao {

	public DVPlaceDaoImpl() {
		super(DVPlaceBean.class);
	}

	@Override
	public void updateDvIP(int dvPlaceId, String dvIp) {
		String hql = "update DVPlaceBean set dvIp = :dvIp where id = :dvPlaceId";
		Query query = super.getSession().createQuery(hql);
		query.setParameter("dvIp", dvIp);
		query.setParameter("dvPlaceId", dvPlaceId);
		query.executeUpdate();

	}

	/**
	 * 函数功能说明 根据监测点id查询出绑定在监测点上的摄像机的集合
	 * 
	 * @author JinGang
	 * @date 2012-9-14 下午01:25:07
	 * @参数： @param monitorPointId 监测点id
	 * @参数： @return 绑定在监测点上的摄像机的集合
	 */
	@Override
	public List<DVPlaceBean> findByMonitorPointId(int monitorPointId) {
		String hql = "select dvPlace from DVPlaceBean dvPlace where monitorPointId = :monitorPointId ORDER BY dvPlace.createTime DESC";
		Query query = super.getSession().createQuery(hql);
		query.setParameter("monitorPointId", monitorPointId);
		return query.list();
	}

	/**
	 * 查询出所有摄像机点位
	 * 
	 * @author GuoTian
	 * @date 2012-9-11 下午16:13:08
	 * @参数： @param 无
	 * @参数： @return 摄像机点位信息
	 */
	@Override
	public List<DVPlaceBean> findAllDVPlace() {
		Query query = getSession()
				.createQuery(
						"select dvPlace from DVPlaceBean dvPlace INNER JOIN fetch dvPlace.monitorPoint where dvtype = 1 ORDER BY dvPlace.createTime DESC");
		return query.list();
	}

	@Override
	public DVPlaceBean findByName(String dvPlaceName) {
		Query query = getSession()
				.createQuery(
						"select dvPlace from DVPlaceBean dvPlace where dvPlace.placeName = :dvPlaceName");
		query.setParameter("dvPlaceName", dvPlaceName);
		query.setMaxResults(1);
		return (DVPlaceBean) query.uniqueResult();
	}

	@Override
	public boolean hasSameNameByAdd(String zoneId, String dvPlaceName) {
		Query query = getSession()
				.createQuery(
						"select count(dv.id) from DVPlaceBean dv where dv.zone.id=:zoneId and dv.placeName=:dvPlaceName");
		query.setParameter("zoneId", zoneId);
		query.setParameter("dvPlaceName", dvPlaceName);
		return ((Long) query.uniqueResult()).intValue() > 0;
	}

	@Override
	public boolean hasSameNameByUpdate(String zoneId, String dvPlaceName,
			String dvPlaceId) {
		Query query = getSession()
				.createQuery(
						"select count(dv.id) from DVPlaceBean dv where dv.zone.id=:zoneId and dv.placeName=:dvPlaceName and dv.id<>:dvPlaceId");
		query.setParameter("zoneId", zoneId);
		query.setParameter("dvPlaceName", dvPlaceName);
		query.setParameter("dvPlaceId", dvPlaceId);
		int dvCount = Integer.parseInt(String.valueOf(query.uniqueResult()));
		return dvCount > 0;
	}

	@Override
	public void updateDvEnable(String dvPlaceId, boolean enable) {
		Query query = getSession()
				.createQuery(
						"update DVPlaceBean dv set dv.enable=:enable where dv.id=:dvPlaceId");
		query.setParameter("enable", enable);
		query.setParameter("dvPlaceId", dvPlaceId);
		query.executeUpdate();
	}

	@Override
	public boolean findDvEnable(String dvPlaceId) {
		Query query = getSession().createQuery(
				"select dv.enable from DVPlaceBean dv where dv.id=:dvPlaceId");
		query.setParameter("dvPlaceId", dvPlaceId);
		return (Boolean) query.uniqueResult();
	}
	
	@Override
	public void updateRealmap(String dvId, String realmap) {
		Query query = getSession().createQuery(" update DVPlaceBean set realmap = :realmap where id = :dvId");
		query.setParameter("realmap", realmap);
		query.setParameter("dvId", dvId);
		query.executeUpdate();
	}

    @Override
    public List<DVPlaceBean> findDvPlacesByZoneId(String zoneId) {
        Query query = getSession().createQuery("select dvPlace from DVPlaceBean dvPlace where dvPlace.zone.id = :zoneId");
        query.setParameter("zoneId", zoneId);
        return query.list();
    }

    @Override
    public List<DVPlaceBean> findAll(String siteId, int pageNmb, int pageSize) {
        Query query = getSession().createQuery("select dv from DVPlaceBean dv inner JOIN fetch dv.zone where dv.zone.site.siteId = :siteId ");
        query.setFirstResult((pageNmb - 1) * pageSize);
        query.setMaxResults(pageSize);
        query.setParameter("siteId", siteId);
        return query.list();
    }

    @Override
    public List<DVPlaceBean> findDvPlacesByZoneId(String zoneId, int pageNmb, int pageSize) {
        Query query = getSession().createQuery("select dv from DVPlaceBean dv  inner JOIN fetch dv.zone where dv.zone.id = :zoneId ");
        query.setFirstResult((pageNmb - 1) * pageSize);
        query.setMaxResults(pageSize);
        query.setParameter("zoneId", zoneId);
        return query.list();
    }

    @Override
    public List<DVPlaceBean> findAll(String siteId) {
        Query query = getSession().createQuery("select dv from DVPlaceBean dv inner JOIN fetch dv.zone where dv.zone.site.siteId = :siteId ");
        query.setParameter("siteId", siteId);
        return query.list();
    }
}
