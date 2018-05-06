package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.orion.bean.EventRelic;
import com.microwise.orion.dao.EventRelicDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import com.microwise.orion.vo.OutOrInRelicVo;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

import java.util.Date;
import java.util.List;

/**
 * 出库文物dao 实现
 * 
 * @author xubaoji
 * @date 2013-5-29
 *
 * @check 2013-06-05 zhangpeng svn:4017
 */
@Dao
@Orion
public class EventRelicDaoImpl extends OrionBaseDao<EventRelic> implements EventRelicDao {
	
	
	public EventRelicDaoImpl() {
		super(EventRelic.class);
	}
	
	@Override
	public void updateInRelicForHttp(List<OutOrInRelicVo> outRelics) {
		String hql = " update EventRelic er set state = 1, inDate = :inDate where er.outEvent.id = :eventId and er.relic.id = :relicId";
		Query query = getSession().createQuery(hql);
		for (OutOrInRelicVo outRelic : outRelics) {
			query.setParameter("inDate", outRelic.getDate());
			query.setParameter("eventId", outRelic.getEventId());
			query.setParameter("relicId", outRelic.getRelicId());
			query.executeUpdate();
		}
	}
	
	@Override
	public void updateOutRelicForHttp(List<OutOrInRelicVo> outRelics) {
		String hql = " update EventRelic AS er set er.state = 1, er.outDate = :outDate where er.outEvent.id = :eventId and er.relic.id = :relicId";
		Query query = getSession().createQuery(hql);
		for (OutOrInRelicVo outRelic : outRelics) {
			query.setParameter("outDate", outRelic.getDate());
			query.setParameter("eventId", outRelic.getEventId());
            query.setParameter("relicId", outRelic.getRelicId());
			query.executeUpdate();
		}
		
	}

    public void updateRelicState(List<OutOrInRelicVo> outRelics, int stateToBe){
        String hql = "update Relic AS r set r.state = :stateToBe where r.id = :relicId";
        Query query = getSession().createQuery(hql);
        for(OutOrInRelicVo outRelic: outRelics){
            query.setParameter("relicId", outRelic.getRelicId());
            query.setParameter("stateToBe", stateToBe);
            query.executeUpdate();
        }
    }
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EventRelic> findInventoryOut(String siteId) {
		String hql = " from EventRelic er left outer join fetch er.relic left outer join fetch er.outEvent  where er.relic.state = 2 and er.inDate = null and er.relic.siteId = :siteId and er.relic.iscanceled=0";
		Query query = getSession().createQuery(hql);
		query.setParameter("siteId", siteId);
		return query.list();
	}

    @Override
    public void deleteEventRelicsByOutEventId(String outEventId){
        Query query = super.getSession().createQuery("delete EventRelic e where e.outEvent.id = :outEventId");
        query.setParameter("outEventId", outEventId);

        query.executeUpdate();
    }

    @Override
    public void updateStateById(int id, int state) {
        String hql = "update EventRelic ec set ec.state = :state where ec.id = :id";
        getSession().createQuery(hql)
                .setParameter("state", state)
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public void updateOutDateById(int id){
        String hql = "update EventRelic ec set ec.outDate = :outDate where ec.id = :id";
        getSession().createQuery(hql)
                .setParameter("outDate", new Date())
                .setParameter("id", id)
                .executeUpdate();
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<Integer> findNoUserZoneEventRelic(List<String> zoneIds,String eventId) {
    	String sql = " SELECT DISTINCT  r.id FROM o_historical_relic r INNER JOIN o_event_relic er on r.id = er.relicId WHERE er.eventId = :eventId  ";
    	if (!zoneIds.isEmpty()) {
			sql = sql+"  AND r.zoneId NOT IN (:zoneIds)";
		}
    	SQLQuery query = getSession().createSQLQuery(sql);
    	query.setParameter("eventId", eventId);
    	if (!zoneIds.isEmpty()) {
    		query.setParameterList("zoneIds", zoneIds);
		}
    	return query.list();
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<Integer> findNoZoneEventRelic(String eventId) {
    	String sql = " SELECT relicId FROM o_event_relic  er LEFT JOIN o_historical_relic  r ON er.`relicId`= r.`id` WHERE er.`eventId`= :eventId  AND r.`zoneId` IS NULL" ;
    	SQLQuery query = getSession().createSQLQuery(sql);
    	query.setParameter("eventId", eventId);
    	return query.list();
    }

}
