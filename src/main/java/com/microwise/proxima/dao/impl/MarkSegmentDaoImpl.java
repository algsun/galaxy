package com.microwise.proxima.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.proxima.bean.DVPlaceBean;
import com.microwise.proxima.bean.MarkSegmentBean;
import com.microwise.proxima.dao.MarkSegmentDao;
import com.microwise.proxima.dao.base.BaseDaoImpl;
import com.microwise.proxima.sys.Proxima;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * MarkSegmentDao implements
 * 
 * @author gaohui
 * @date 2012-7-13
 */
@Dao
@Proxima
public class MarkSegmentDaoImpl extends BaseDaoImpl<MarkSegmentBean> implements
		MarkSegmentDao {

	public MarkSegmentDaoImpl() {
		super(MarkSegmentBean.class);
	}

	@Override
	public List<MarkSegmentBean> findAllMarkSegmentByDvPlaceId(String dvPlaceId) {
		Session session = super.getSession();

		Query query = session
				.createQuery("select ms from MarkSegmentBean ms where ms.dvPlace.id  = :dvPlaceId order by ms.createTime");
		query.setParameter("dvPlaceId", dvPlaceId);
		return (List<MarkSegmentBean>) query.list();
	}

	@Override
	public MarkSegmentBean findMarkSegmentOfDVPlaceByName(int dvPlaceId,
			String markSegmentName) {
		Query query  = super.getSession().createQuery("select ms from MarkSegmentBean ms where ms.dvPlace.id = :dvPlaceId and ms.name = :markSegmentName");
		query.setParameter("dvPlaceId", dvPlaceId);
		query.setParameter("markSegmentName", markSegmentName);
		
		query.setMaxResults(1);
		
		return (MarkSegmentBean)query.uniqueResult();
	}
	
	@Override
	public List<MarkSegmentBean> findAllMarkSegment() {
		Query query=super.getSession().createQuery("select ms from MarkSegmentBean ms order by ms.dvPlace.id,ms.createTime");
		return query.list();
	}

    @Override
    public List<MarkSegmentBean> findAllBySiteId(String siteId){
        Query query = super.getSession()
                .createQuery("select ms from MarkSegmentBean ms inner join ms.dvPlace as dvPlace inner join dvPlace.zone as zone where zone.site.id = :siteId order by dvPlace.id, ms.createTime");
        query.setParameter("siteId", siteId);

        return query.list();
    }

	@Override
	public DVPlaceBean findDVPlaceByMarkSegmentId(String markSegmentId) {
		Query query = super.getSession().createQuery("select dvPlace from MarkSegmentBean mark join mark.dvPlace as dvPlace where mark.id = :markSegmentId");
		query.setParameter("markSegmentId", markSegmentId);
		query.setMaxResults(1);
		return (DVPlaceBean) query.uniqueResult();
	}
}
