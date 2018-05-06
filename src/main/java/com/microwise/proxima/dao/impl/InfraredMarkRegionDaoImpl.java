package com.microwise.proxima.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.proxima.bean.DVPlaceBean;
import com.microwise.proxima.bean.InfraredMarkRegionBean;
import com.microwise.proxima.dao.InfraredMarkRegionDao;
import com.microwise.proxima.dao.base.BaseDaoImpl;
import com.microwise.proxima.sys.Proxima;
import org.hibernate.Query;

import java.util.List;

/**
 * 红外标记区域 dao 实现
 * 
 * @author gaohui
 * @date 2012-9-5
 */
@Dao
@Proxima
public class InfraredMarkRegionDaoImpl extends
		BaseDaoImpl<InfraredMarkRegionBean> implements InfraredMarkRegionDao {

	public InfraredMarkRegionDaoImpl() {
		super(InfraredMarkRegionBean.class);
	}

	@Override
	public List<InfraredMarkRegionBean> findAllByDVPlaceId(String dvPlaceId){
		 Query query = getSession().createQuery("select m from InfraredMarkRegionBean m where m.dvPlace.id = :dvPlaceId");
		 query.setParameter("dvPlaceId", dvPlaceId);
		 
		 return (List<InfraredMarkRegionBean>)query.list();
	}

	@Override
	public void updateById(String markRegionId, int x,int y,int width, int height) {
		Query query = getSession().createQuery("update InfraredMarkRegionBean m  set m.positionX = :x , m.positionY = :y, m.regionWidth = :width, m.regionHeight = :height where m.id = :markRegionId");
		query.setParameter("markRegionId", markRegionId);
		query.setParameter("x", x);
		query.setParameter("y", y);
		query.setParameter("width", width);
		query.setParameter("height", height);
		
		query.executeUpdate();
	}
	
	@Override
	public DVPlaceBean findDVPlaceByMarkRegionId(String markRegionId) {
		Query query = getSession().createQuery("select dvPlace from InfraredMarkRegionBean m join m.dvPlace dvPlace where m.id = :markRegionId");
		query.setParameter("markRegionId", markRegionId);
		query.setMaxResults(1);
		
		return (DVPlaceBean)query.uniqueResult();
	}

	@Override
	public InfraredMarkRegionBean findAt(String dvPlaceId, int x, int y) {
		Query query = super.getSession().createQuery("select markRegion from InfraredMarkRegionBean markRegion where markRegion.positionX <= :x and markRegion.positionY <= :y and :x <= (markRegion.positionX + markRegion.regionWidth) and :y <= (markRegion.positionY + markRegion.regionHeight) and markRegion.dvPlace.id = :dvPlaceId");
		query.setParameter("dvPlaceId", dvPlaceId);
		query.setParameter("x", x);
		query.setParameter("y", y);
		query.setMaxResults(1);
		return (InfraredMarkRegionBean) query.uniqueResult();
	}

}
