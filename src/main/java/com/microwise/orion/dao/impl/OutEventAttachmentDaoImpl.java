package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.orion.bean.OutEventAttachment;
import com.microwise.orion.dao.OutEventAttachmentDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

import java.util.Date;
import java.util.List;

/**
 * 出库事件文档记录dao 实现
 * 
 * @author xu.baoji
 * @date 2013-8-1
 */
@Orion
@Dao
public class OutEventAttachmentDaoImpl extends OrionBaseDao<OutEventAttachment> implements
		OutEventAttachmentDao {
	
	public OutEventAttachmentDaoImpl() {
		super(OutEventAttachment.class);
	}

	@Override
	public void addOutEventAttachment(String eventId, int userId, String path, Date date) {
		String sql = " INSERT  INTO o_out_event_attachment (eventId,userId,path,`date`) VALUES (:eventId,:userId,:path,:date)";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter("eventId", eventId);
		sqlQuery.setParameter("userId", userId);
		sqlQuery.setParameter("path", path);
		sqlQuery.setParameter("date", date);
		sqlQuery.executeUpdate();
	}

	@Override
	public void deleteOutEventAttachment(int id) {
		String sql = "DELETE FROM o_out_event_attachment WHERE id = :id";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter("id", id);
		sqlQuery.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OutEventAttachment> findByEventId(String eventId) {
		String hql = " from OutEventAttachment oea left outer join fetch oea.user left outer join fetch oea.outEvent where oea.outEvent.id = :eventId";
		Query query = getSession().createQuery(hql);
		query.setParameter("eventId", eventId);
		return query.list();
	}

	@Override
	public void deleteEventAttachmentByEventId(String eventId) {
		String sql = "DELETE FROM o_out_event_attachment WHERE eventId = :eventId";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter("eventId", eventId);
		sqlQuery.executeUpdate();
	}

}
