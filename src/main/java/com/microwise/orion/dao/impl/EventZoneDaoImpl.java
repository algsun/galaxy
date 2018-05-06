package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.orion.bean.EventZone;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.dao.EventZoneDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

import java.util.List;

/**
 * 出库文物 按区域确认 dao 实现
 *
 * @author xubaoji
 * @date 2013-6-19
 * @check li.jianfei 2013-06-20 #4288
 */
@Orion
@Dao
public class EventZoneDaoImpl extends OrionBaseDao<EventZone> implements
        EventZoneDao {

    public EventZoneDaoImpl() {
        super(EventZone.class);
    }

    @Override
    public void addEventZone(String eventId, List<String> zoneIdList) {
        String sql = " INSERT INTO o_event_zone  (eventId,zoneId,state) VALUES(:eventId,:zoneId,0)";
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        sqlQuery.setParameter("eventId", eventId);
        for (String zoneId : zoneIdList) {
            sqlQuery.setParameter("zoneId", zoneId);
            sqlQuery.executeUpdate();
        }

    }

    @Override
    public void addEventZoneRelic(int eventZoneId, List<Integer> relicIds) {
        String sql = " INSERT INTO o_event_zone_relic (eventZoneId,relicId) VALUES (:eventZoneId ,:relicId)";
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        sqlQuery.setParameter("eventZoneId", eventZoneId);
        for (Integer relicId : relicIds) {
            sqlQuery.setParameter("relicId", relicId);
            sqlQuery.executeUpdate();
        }

    }

    @Override
    public void deleteEventZone(String eventId) {
        String sql = " delete from o_event_zone where eventId = :eventId ";
        getSession().createSQLQuery(sql).setParameter("eventId", eventId)
                .executeUpdate();
    }

    @Override
    public void deleteEventZoneRelic(String eventId) {
        String sql = " DELETE FROM o_event_zone_relic WHERE eventZoneId IN (SELECT id FROM  o_event_zone WHERE eventId = :eventId )";
        getSession().createSQLQuery(sql).setParameter("eventId", eventId)
                .executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<EventZone> findEventZones(String eventId) {
        String hql = " from EventZone ez left outer join fetch ez.zone where ez.outEvent.id = :eventId ";
        Query query = getSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<EventZone> findEventZonesBeatchAll(String eventId) {
        String hql = " select DISTINCT ez from EventZone ez left outer join fetch ez.zone left outer join fetch ez.user  left outer join fetch ez.relics where ez.outEvent.id = :eventId ";
        Query query = getSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        List<EventZone> eventZones = query.list();
        if (eventZones != null && !eventZones.isEmpty()) {
            for (EventZone eventZone : eventZones) {
                if (eventZone.getRelics() != null && !eventZone.getRelics().isEmpty()) {
                    for (Relic relic : eventZone.getRelics()) {
                        Hibernate.initialize(relic.getZone());
                        Hibernate.initialize(relic.getLevel());
                        Hibernate.initialize(relic.getTexture());
                    }
                }
            }
        }
        return eventZones;
    }

    @Override
    public void updateEventZoneUserAndState(String eventId, Integer userId,
                                            int state, String zoneId) {
        String sql = " UPDATE o_event_zone  SET userId = :userId ,state = :state WHERE eventId = :eventId ";
        if (zoneId == null) {
			sql = sql +" and zoneId is null";
		}else {
			sql = sql +" and zoneId = :zoneId";
		}
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        sqlQuery.setParameter("userId", userId);
        sqlQuery.setParameter("eventId", eventId);
        sqlQuery.setParameter("state", state);
        if (zoneId!=null) {
        	sqlQuery.setParameter("zoneId", zoneId);
		}
        sqlQuery.executeUpdate();

    }


}
