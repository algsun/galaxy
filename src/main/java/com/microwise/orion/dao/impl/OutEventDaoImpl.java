package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.orion.bean.OutEvent;
import com.microwise.orion.dao.OutEventDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import com.microwise.orion.vo.OutEventVo;
import com.microwise.orion.vo.RelicVo;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 出库记录dao 实现
 *
 * @author xubaoji
 * @date 2013-5-29
 * @check duan qixin svn:4017
 */
@Dao
@Orion
public class OutEventDaoImpl extends OrionBaseDao<OutEvent> implements
        OutEventDao {

    public OutEventDaoImpl() {
        super(OutEvent.class);
    }

    @Override
    public void saveOutEventInfo(OutEvent outEvent) {
        getSession().save(outEvent);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<OutEvent> findOutEventsBySiteId(String siteId, int start, int size) {
        String hql = "select a from OutEvent as a where a.siteId = :siteId Order by a.id desc ";
        Query query = getSession().createQuery(hql)
                .setParameter("siteId", siteId)
                .setFirstResult((start - 1) * size)
                .setMaxResults(size);
        return query.list();
    }

    @Override
    public OutEvent findById(String id) {
        String hql = "select outEvent from OutEvent as outEvent join fetch outEvent.eventRelics as eventRelic join fetch eventRelic.relic as relic left join fetch relic.level left join fetch relic.texture left join fetch outEvent.user where outEvent.id = :id";
        Query query = getSession().createQuery(hql)
                .setParameter("id", id);
        return (OutEvent) query.uniqueResult();
    }

    @Override
    public int findOutEventCountBySiteId(String siteId) {
        String hql = "select count(a.id) from OutEvent as a where a.siteId = :siteId";
        Query query = getSession().createQuery(hql)
                .setParameter("siteId", siteId);
        return ((Long) query.uniqueResult()).intValue();
    }

    @Override
    public List<OutEvent> findExhibitions(String siteId, Date begin, Date end) {
        String hql = "select a from OutEvent as a where a.siteId = :siteId and a.eventType = 1 and a.outBound = 1 and a.beginDate between :begin and :end order by a.beginDate";
        Query query = getSession().createQuery(hql);
        query.setParameter("siteId", siteId);
        query.setParameter("begin", begin);
        query.setParameter("end", end);
        return query.list();
    }

    @Override
    public void updateOutEvent(OutEvent outEvent) {
        getSession().update(outEvent);
    }

    @SuppressWarnings("unchecked")
    @Override
    // TODO 还原SQL
    public List<RelicVo> findOutEventRelicVo(String eventId) {
        String sql = "select r.id,r.totalCode,r.tagCode,r.name,r.count,r.zoneId,r.siteId,r.state,r.hasTag ,ol.name level,ott.name texture from o_historical_relic r left join o_event_relic oer on r.id = oer.relicId left join o_level ol on r.level=ol.id left  join  o_texture ott on r.texture=ott.id where oer.eventId = :eventId";
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        sqlQuery.setParameter("eventId", eventId);
        // 将查询的数据映射为 relicVo 实体对象
        sqlQuery.setResultTransformer(Transformers.aliasToBean(RelicVo.class));
        return sqlQuery.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    // TODO 还原SQL
    public List<OutEventVo> findOutEventVo(List<String> eventIds) {
        if (eventIds == null || eventIds.isEmpty()) {
            return Collections.emptyList();
        }

        String sql = " select id ,useFor, state,beginDate,endDate,applicant,eventType,outBound from o_out_event oe where oe.id in (:eventIds)";
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        sqlQuery.setParameterList("eventIds", eventIds);
        // 将查询的数据映射为 OutEventVo 实体对象
        sqlQuery.setResultTransformer(Transformers.aliasToBean(OutEventVo.class));
        return sqlQuery.list();
    }

    @Override
    public List<OutEventVo> findOutEventVoByState(String siteId, int state) {
        String sql = "select id, useFor, state from o_out_event oe where oe.siteId = :siteId and oe.state = :state";
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        sqlQuery.setParameter("siteId", siteId);
        sqlQuery.setParameter("state", state);
        sqlQuery.setResultTransformer(Transformers.aliasToBean(OutEventVo.class));

        return sqlQuery.list();
    }
}
