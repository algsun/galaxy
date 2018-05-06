package com.microwise.orion.dao.impl;

import com.google.common.base.Strings;
import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.dao.RelicDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import com.microwise.orion.vo.RelicVo;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文物dao 实现类
 *
 * @author xubaoji
 * @date 2013-5-13
 * @check 2014-04-22 liuzhu xuyuexi #8429
 */
@Dao
@Orion
public class RelicDaoImpl extends OrionBaseDao<Relic> implements RelicDao {

    public RelicDaoImpl() {
        super(Relic.class);
    }

    @Override
    public List<String> findLocationsIdByRelicId(Integer relicId) {
        StringBuilder hql=new StringBuilder("select locationId from m_location_relic where relicId = ");
        hql.append(relicId);
        SQLQuery sqlQuery=getSession().createSQLQuery(hql.toString());
        return sqlQuery.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Relic> findRelics(String totalCode, String name, String zoneId, String catalogCode,
                                  String typeCode, Integer eraId, Integer levelId, Integer textureId, String siteId,
                                  Integer state, Integer index, Integer size, Boolean iscanceled) {
        // 查询文物列表 hql
        StringBuilder hql = new StringBuilder(
                "Select r from Relic as r left outer join fetch r.era left outer join fetch r.level left outer join fetch r.texture left outer join  fetch r.zone  where ");
        // 追加 查询条件信息
        assembleHql(totalCode, name, zoneId, catalogCode, typeCode, eraId, levelId, textureId,
                state, iscanceled, hql);
        // 添加排序
        hql.append(" order by r.id");
        Query query = getSession().createQuery(hql.toString());
        // 添加条件参数
        setParameterForQuery(query, totalCode, name, zoneId, catalogCode, typeCode, eraId, levelId,
                textureId, siteId, state, iscanceled);
        query.setFirstResult((index - 1) * size);
        query.setMaxResults(size);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public List<Relic> findRelics(String totalCode, String name, String zoneId, String catalogCode,
                                  String typeCode, Integer eraId, Integer levelId, Integer textureId, String siteId,
                                  Integer state, Boolean iscanceled) {
        // 查询文物列表 hql
        StringBuilder hql = new StringBuilder(
                "select r from Relic as r left outer join fetch r.era left outer join fetch r.level left outer join fetch r.texture left outer join  fetch r.zone  where ");
        // 追加 查询条件信息
        assembleHql(totalCode, name, zoneId, catalogCode, typeCode, eraId, levelId, textureId,
                state, iscanceled, hql);
        // 添加排序
        hql.append(" order by r.id");
        Query query = getSession().createQuery(hql.toString());
        // 添加条件参数
        setParameterForQuery(query, totalCode, name, zoneId, catalogCode, typeCode, eraId, levelId,
                textureId, siteId, state, iscanceled);
        List<Relic> relies = query.list();
        for (int i = 0; i < relies.size(); i++) {
            Hibernate.initialize(relies.get(i).getRelicPropertielist());
            Hibernate.initialize(relies.get(i).getStatusQuos());
            for (int j = 0; j < relies.get(i).getRelicPropertielist().size(); j++) {
                Hibernate.initialize(relies.get(i).getRelicPropertielist().get(j).getProperty());
            }
        }
        return relies;
    }

    @Override
    public List<Relic> findRelics(String totalCode, String name, String[] zoneIds, Integer[] labelIds, String catalogCode,
                                  String typeCode, Integer[] eraIds, Integer[] levelIds, Integer[] textureIds,
                                  String siteId, Integer[] states, Integer index, Integer size, Boolean isCanceled) {
        // 查询文物列表 hql
        StringBuilder hql = new StringBuilder(
                "SELECT r from Relic as r left outer join r.relicLabels as a left outer join fetch r.era left outer join fetch r.level left outer join fetch r.texture left outer join  fetch r.zone  where ");
        // 追加 查询条件信息
        assembleHql(totalCode, name, zoneIds, labelIds, catalogCode, typeCode, eraIds, levelIds, textureIds,
                states, isCanceled, hql);
        hql.append(" group by r.totalCode ");
        // 添加排序
        hql.append(" order by r.id");
        Query query = getSession().createQuery(hql.toString());
        // 添加条件参数
        setParameterListForQuery(query, totalCode, name, zoneIds, labelIds, catalogCode, typeCode, eraIds, levelIds,
                textureIds, siteId, states, isCanceled);
        if (index == null && size == null) {
            return query.list();
        }
        query.setFirstResult((index - 1) * size);
        query.setMaxResults(size);
        List<Relic> relies = query.list();
        for (int i = 0; i < relies.size(); i++) {
            Hibernate.initialize(relies.get(i).getRelicPropertielist());
            Hibernate.initialize(relies.get(i).getStatusQuos());
            for (int j = 0; j < relies.get(i).getRelicPropertielist().size(); j++) {
                Hibernate.initialize(relies.get(i).getRelicPropertielist().get(j).getProperty());
            }
        }
        return relies;
    }

    @Override
    public int findRelicsCount(String siteId) {
        // 查询总数量 hql
        StringBuilder hql = new StringBuilder("select count(r.id) from Relic as r where siteId=:siteId");
        Query query = getSession().createQuery(hql.toString());
        query.setParameter("siteId", siteId);
        return Integer.parseInt(query.uniqueResult().toString());
    }

    @Override
    public List<Relic> findRelics(String siteId, int index, int sizePerPage) {
        StringBuilder hql = new StringBuilder("FROM Relic AS r left outer join fetch r.relicLabels as a LEFT OUTER JOIN FETCH r.era LEFT OUTER JOIN FETCH r.level LEFT OUTER JOIN FETCH r.texture LEFT OUTER JOIN  FETCH r.zone where r.siteId=:siteId order by r.id");
        Query query = getSession().createQuery(hql.toString());
        query.setParameter("siteId", siteId);
        query.setFirstResult((index - 1) * sizePerPage);
        query.setMaxResults(sizePerPage);

        List<Relic> relics = query.list();
        for (int i = 0; i < relics.size(); i++) {
            Hibernate.initialize(relics.get(i).getRelicPropertielist());
            Hibernate.initialize(relics.get(i).getStatusQuos());
            for (int j = 0; j < relics.get(i).getRelicPropertielist().size(); j++) {
                Hibernate.initialize(relics.get(i).getRelicPropertielist().get(j).getProperty());
            }
            Hibernate.initialize(relics.get(i).getEra());
            Hibernate.initialize(relics.get(i).getLevel());
            Hibernate.initialize(relics.get(i).getTexture());
            Hibernate.initialize(relics.get(i).getRestores());
            Hibernate.initialize(relics.get(i).getAppraisals());
            Hibernate.initialize(relics.get(i).getAccidents());
            Hibernate.initialize(relics.get(i).getAttachments());
            Hibernate.initialize(relics.get(i).getInscriptions());
            Hibernate.initialize(relics.get(i).getPhotos());
            Hibernate.initialize(relics.get(i).getRubbings());
            Hibernate.initialize(relics.get(i).getRoves());
            Hibernate.initialize(relics.get(i).getRelicLabels());
        }
        return relics;
    }

    @Override
    public int findRelicsCount(String siteId, String relicNameTotalCode, List<Integer> ids) {
        StringBuilder hql;
        if (ids != null && ids.size() != 0) {
            hql = new StringBuilder("select count(r.id) FROM Relic AS r where r.id not in :ids and r.siteId=:siteId and r.name LIKE :param OR r.totalCode LIKE :param");
        } else {
            hql = new StringBuilder("select count(r.id) FROM Relic AS r where r.siteId=:siteId and r.name LIKE :param OR r.totalCode LIKE :param");
        }
        Query query = getSession().createQuery(hql.toString());
        query.setParameter("siteId", siteId);
        if (ids != null && ids.size() != 0) {
            query.setParameterList("ids", ids);
        }
        query.setString("param", "%" + relicNameTotalCode + "%");
        return Integer.parseInt(query.uniqueResult().toString());
    }

    @Override
    public List<Relic> findRelics(String siteId, String relicNameTotalCode, int pageIndex, int pageSize, List<Integer> ids) {

        StringBuilder hql = null;
        if (ids != null && ids.size() != 0) {
            hql = new StringBuilder("FROM Relic AS r left outer join fetch r.era where r.id not in :ids and r.siteId=:siteId and r.name LIKE :param OR r.totalCode LIKE :param order by r.id");
        } else {
            hql = new StringBuilder("FROM Relic AS r left outer join fetch r.era where r.siteId=:siteId and r.name LIKE :param OR r.totalCode LIKE :param order by r.id");
        }
        Query query = getSession().createQuery(hql.toString());
        query.setParameter("siteId", siteId);
        if (ids != null && ids.size() != 0) {
            query.setParameterList("ids", ids);
        }
        query.setString("param", "%" + relicNameTotalCode + "%");

        query.setFirstResult((pageIndex - 1) * pageSize);
        query.setMaxResults(pageSize);
        List<Relic> relics = query.list();
        for (int i = 0; i < relics.size(); i++) {
            Hibernate.initialize(relics.get(i).getEra());
            Hibernate.initialize(relics.get(i).getPhotos());
        }
        return relics;
    }

    @Override
    public Integer findRelicCount(String totalCode, String name, String zoneId, String catalogCode,
                                  String typeCode, Integer eraId, Integer levelId, Integer textureId, String siteId,
                                  Integer state, Boolean iscanceled) {
        // 查询总数量 hql
        StringBuilder hql = new StringBuilder("select count(r.id) from Relic as r where ");
        // 追加查询条件
        assembleHql(totalCode, name, zoneId, catalogCode, typeCode, eraId, levelId, textureId,
                state, iscanceled, hql);
        Query query = getSession().createQuery(hql.toString());
        // 添加条件参数
        setParameterForQuery(query, totalCode, name, zoneId, catalogCode, typeCode, eraId, levelId,
                textureId, siteId, state, iscanceled);
        return Integer.parseInt(query.uniqueResult().toString());
    }

    @Override
    public Integer findRelicCount(String totalCode, String name, String[] zoneIds, Integer[] labelIds, String catalogCode,
                                  String typeCode, Integer[] eraIds, Integer[] levelIds, Integer[] textureIds,
                                  String siteId, Integer[] states, Boolean isCanceled) {
        // 查询总数量 hql
        StringBuilder hql = new StringBuilder("select count(distinct r.id) from Relic as r left outer join r.relicLabels as a where ");
        // 追加查询条件
        assembleHql(totalCode, name, zoneIds, labelIds, catalogCode, typeCode, eraIds, levelIds, textureIds,
                states, isCanceled, hql);
        Query query = getSession().createQuery(hql.toString());
        // 添加条件参数
        setParameterListForQuery(query, totalCode, name, zoneIds, labelIds, catalogCode, typeCode, eraIds, levelIds,
                textureIds, siteId, states, isCanceled);
        return Integer.parseInt(query.uniqueResult().toString());
    }

    @Override
    public Integer findRelicAllCount(String totalCode, String name, String zoneId,
                                     String catalogCode, String typeCode, Integer eraId, Integer levelId, Integer textureId,
                                     String siteId, Integer state, Boolean iscanceled) {
        // 查询文物总件数hql
        StringBuilder hql = new StringBuilder("select sum(r.count) from Relic as r where ");
        // 添加查询条件
        assembleHql(totalCode, name, zoneId, catalogCode, typeCode, eraId, levelId, textureId,
                state, iscanceled, hql);
        Query query = getSession().createQuery(hql.toString());
        // 添加条件参数
        setParameterForQuery(query, totalCode, name, zoneId, catalogCode, typeCode, eraId, levelId,
                textureId, siteId, state, iscanceled);
        return query.uniqueResult() == null ? 0 : Integer.parseInt(query.uniqueResult().toString());
    }

    @Override
    public Integer findRelicAllCount(String totalCode, String name, String[] zoneIds, Integer[] labelIds,
                                     String catalogCode, String typeCode, Integer[] eraIds, Integer[] levelIds,
                                     Integer[] textureIds, String siteId, Integer[] states, Boolean isCanceled) {
        // 查询文物总件数hql
        StringBuilder hql = new StringBuilder(" from Relic as r left outer join fetch r.relicLabels as a where ");
        // 添加查询条件
        assembleHql(totalCode, name, zoneIds, labelIds, catalogCode, typeCode, eraIds, levelIds, textureIds,
                states, isCanceled, hql);
        hql.append(" group by r.id");
        Query query = getSession().createQuery(hql.toString());
        // 添加条件参数
        setParameterListForQuery(query, totalCode, name, zoneIds, labelIds, catalogCode, typeCode, eraIds, levelIds,
                textureIds, siteId, states, isCanceled);
        List<Relic> relics = query.list();
        return getAllRelicCount(relics);
    }


    /**
     * 获取文物的总件数
     *
     * @param relicList
     * @return
     */
    private Integer getAllRelicCount(List<Relic> relicList) {
        int count = 0;
        for (Relic relic : relicList) {
            count += relic.getCount();
        }
        return count;
    }

    @Override
    public Relic findRelic(String totalCode, String siteId) {
        String hql = " from Relic as r left outer join fetch r.zone  where r.totalCode = :totalCode and r.siteId = :siteId";
        Query query = getSession().createQuery(hql);
        query.setParameter("totalCode", totalCode);
        query.setParameter("siteId", siteId);
        Relic relic = (Relic) query.uniqueResult();
        // 强制加载文物relic实体中对象属性和集合属性
        if (relic != null) {
            Hibernate.initialize(relic.getPhotos());
            Hibernate.initialize(relic.getRubbings());
            Hibernate.initialize(relic.getRestores());
            Hibernate.initialize(relic.getAppraisals());
            Hibernate.initialize(relic.getAccidents());
            Hibernate.initialize(relic.getStatusQuos());
            Hibernate.initialize(relic.getInscriptions());
            Hibernate.initialize(relic.getAttachments());
            Hibernate.initialize(relic.getRoves());
            Hibernate.initialize(relic.getEra());
            Hibernate.initialize(relic.getLevel());
            Hibernate.initialize(relic.getTexture());
            Hibernate.initialize(relic.getRelicLabels());
        }
        return relic;
    }

    public Relic findRelic(int relicId, String siteId) {
        String hql = " from Relic as r left outer join fetch r.zone  where r.id = :relicId and r.siteId = :siteId";
        Query query = getSession().createQuery(hql);
        query.setParameter("relicId", relicId);
        query.setParameter("siteId", siteId);
        Relic relic = (Relic) query.uniqueResult();
        // 强制加载文物relic实体中对象属性和集合属性
        if (relic != null) {
            Hibernate.initialize(relic.getPhotos());
            Hibernate.initialize(relic.getRubbings());
            Hibernate.initialize(relic.getRestores());
            Hibernate.initialize(relic.getAppraisals());
            Hibernate.initialize(relic.getAccidents());
            Hibernate.initialize(relic.getStatusQuos());
            Hibernate.initialize(relic.getInscriptions());
            Hibernate.initialize(relic.getAttachments());
            Hibernate.initialize(relic.getRoves());
            Hibernate.initialize(relic.getEra());
            Hibernate.initialize(relic.getLevel());
            Hibernate.initialize(relic.getTexture());
            Hibernate.initialize(relic.getRelicLabels());
        }
        return relic;
    }

    @Override
    public Integer findRelicId(String totalCode, String siteId) {
        String hql = " select r.id  from Relic as r where r.totalCode = :totalCode and r.siteId = :siteId";
        Query query = getSession().createQuery(hql);
        query.setParameter("totalCode", totalCode);
        query.setParameter("siteId", siteId);
        return query.uniqueResult() == null ? null : Integer.parseInt(query.uniqueResult()
                .toString());
    }

    @Override
    public Relic findRelicCard(String totalCode, String siteId) {
        String hql = " from Relic as r left outer join fetch r.zone  where r.totalCode = :totalCode and r.siteId = :siteId";
        Query query = getSession().createQuery(hql);
        query.setParameter("totalCode", totalCode);
        query.setParameter("siteId", siteId);
        Relic relic = (Relic) query.uniqueResult();
        // 加载 文物实体对象中的 对象/set 属性
        if (relic != null) {
            Hibernate.initialize(relic.getPhotos());
            Hibernate.initialize(relic.getInscriptions());
            Hibernate.initialize(relic.getEra());
            Hibernate.initialize(relic.getLevel());
            Hibernate.initialize(relic.getTexture());
        }
        return relic;
    }

    public Relic findRelicCard(int relicId, String siteId) {
        String hql = " from Relic as r left outer join fetch r.zone  where r.id = :relicId and r.siteId = :siteId";
        Query query = getSession().createQuery(hql);
        query.setParameter("relicId", relicId);
        query.setParameter("siteId", siteId);
        Relic relic = (Relic) query.uniqueResult();
        // 加载 文物实体对象中的 对象/set 属性
        if (relic != null) {
            Hibernate.initialize(relic.getPhotos());
            Hibernate.initialize(relic.getInscriptions());
            Hibernate.initialize(relic.getEra());
            Hibernate.initialize(relic.getLevel());
            Hibernate.initialize(relic.getTexture());
        }
        return relic;
    }

    @Override
    public String findPreRelicTotalCode(Integer id, String siteId) {
        String hql = " select r.totalCode from Relic  r  where  r.id < :id  and r.siteId = :siteId order by r.id desc ";
        Query query = getSession().createQuery(hql);
        query.setParameter("id", id);
        query.setParameter("siteId", siteId);
        query.setMaxResults(1);
        return (String) query.uniqueResult();
    }

    @Override
    public String findNextRelicTotalCode(Integer id, String siteId) {
        String hql = " select r.totalCode from Relic  r  where  r.id > :id and r.siteId = :siteId order by r.id asc ";
        Query query = getSession().createQuery(hql);
        query.setParameter("id", id);
        query.setParameter("siteId", siteId);
        query.setMaxResults(1);
        return (String) query.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RelicVo> findHasTagRelics(String siteId) {
        String sql = " select id,totalCode,tagCode,name,zoneId,state,hasTag from o_historical_relic r where r.siteId = :siteId and r.hasTag = 1";
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        sqlQuery.setParameter("siteId", siteId);
        // 将查询的数据映射为 relicVo 实体对象
        sqlQuery.setResultTransformer(Transformers.aliasToBean(RelicVo.class));
        return sqlQuery.list();
    }

    @Override
    public void updateRelicTag(String siteId, List<String> tagCodeList) {
        String hql = " update Relic set hasTag = true where siteId = :siteId and tagCode = :tagCode";
        Query query = getSession().createQuery(hql);
        query.setParameter("siteId", siteId);
        for (String string : tagCodeList) {
            query.setParameter("tagCode", string);
            query.executeUpdate();
        }
    }

    /**
     * 组装 文物 查询hql 条件
     *
     * @param totalCode   文物总等级号
     * @param name        文物名称
     * @param zoneId      文物区域id
     * @param catalogCode 文物编目号
     * @param typeCode    文物分类号（模糊查询）
     * @param eraId       文物年代编号
     * @param levelId     文物级别编号
     * @param textureId   文物质地编号
     * @author 许保吉
     * @date 2013-5-15
     */
    private void assembleHql(String totalCode, String name, String zoneId, String catalogCode,
                             String typeCode, Integer eraId, Integer levelId, Integer textureId, Integer state,
                             Boolean iscanceled, StringBuilder hql) {
        // 添加siteId 条件参数
        hql.append(" r.siteId = :siteId ");

        if (state != null && state != -1) {
            hql.append(" and  r.state = :state ");
        }
        if (!Strings.isNullOrEmpty(totalCode)) {
            hql.append(" and r.totalCode like :totalCode");
        }
        if (!Strings.isNullOrEmpty(name)) {
            hql.append(" and r.name  like  :name");
        }
        if (!Strings.isNullOrEmpty(zoneId)) {
            hql.append(" and r.zone.id = :zoneId");
        }
        if (!Strings.isNullOrEmpty(catalogCode)) {
            hql.append(" and r.catalogCode like :catalogCode");
        }
        if (!Strings.isNullOrEmpty(typeCode)) {
            hql.append(" and r.typeCode like  :typeCode");
        }
        if (eraId != null && eraId != -1) {
            hql.append(" and r.era.id = :eraId");
        }
        if (levelId != null && levelId != -1) {
            hql.append(" and r.level.id = :levelId");
        }
        if (textureId != null && textureId != -1) {
            hql.append(" and r.texture.id = :textureId");
        }
        if (iscanceled != null) {
            hql.append(" and r.iscanceled = :iscanceled");
        }
    }

    /**
     * 为hibernate query对象组装参数
     *
     * @param query       hibernate query对象
     * @param totalCode   文物总等级号（模糊）
     * @param name        文物名称（模糊）
     * @param zoneId      文物区域id
     * @param catalogCode 文物编目号（模糊）
     * @param typeCode    文物分类号（模糊）
     * @param eraId       文物年代编号
     * @param levelId     文物级别编号
     * @param textureId   文物质地编号
     * @return hql 返回查询文物基本信息列表 的hql
     * @author 许保吉
     * @date 2013-5-15
     */
    private void setParameterForQuery(Query query, String totalCode, String name, String zoneId,
                                      String catalogCode, String typeCode, Integer eraId, Integer levelId, Integer textureId,
                                      String siteId, Integer state, Boolean iscanceled) {
        query.setParameter("siteId", siteId);

        if (state != null && state != -1) {
            query.setParameter("state", state);
        }
        if (!Strings.isNullOrEmpty(totalCode)) {
            query.setParameter("totalCode", "%" + totalCode + "%");
        }
        if (!Strings.isNullOrEmpty(name)) {
            query.setParameter("name", "%" + name + "%");
        }
        if (!Strings.isNullOrEmpty(zoneId)) {
            query.setParameter("zoneId", zoneId);
        }
        if (!Strings.isNullOrEmpty(catalogCode)) {
            query.setParameter("catalogCode", "%" + catalogCode + "%");
        }
        if (!Strings.isNullOrEmpty(typeCode)) {
            query.setParameter("typeCode", "%" + typeCode + "%");
        }
        if (eraId != null && eraId != -1) {
            query.setParameter("eraId", eraId);
        }
        if (levelId != null && levelId != -1) {
            query.setParameter("levelId", levelId);
        }
        if (textureId != null && textureId != -1) {
            query.setParameter("textureId", textureId);
        }
        if (iscanceled != null) {
            query.setParameter("iscanceled", iscanceled);
        }
    }

    /**
     * 组装 文物 查询hql 条件
     *
     * @param totalCode   文物总等级号
     * @param name        文物名称
     * @param zoneIds     文物区域id
     * @param labelIds    文物标签编号
     * @param catalogCode 文物编目号
     * @param typeCode    文物分类号（模糊查询）
     * @param eraIds      文物年代编号
     * @param levelIds    文物级别编号
     * @param textureIds  文物质地编号
     */
    private void assembleHql(String totalCode, String name, String[] zoneIds, Integer[] labelIds, String catalogCode,
                             String typeCode, Integer[] eraIds, Integer[] levelIds, Integer[] textureIds,
                             Integer[] states, Boolean iscanceled, StringBuilder hql) {
        // 添加siteId 条件参数
        hql.append(" r.siteId = :siteId ");

        if (states != null && states.length != 0) {
            hql.append(" and  r.state in (:state) ");
        }
        if (!Strings.isNullOrEmpty(totalCode)) {
            hql.append(" and r.totalCode like :totalCode");
        }
        if (!Strings.isNullOrEmpty(name)) {
            hql.append(" and r.name  like  :name");
        }
        if (zoneIds != null && zoneIds.length != 0) {
            hql.append(" and r.zone.id in (:zoneId)");
        }
        if (labelIds != null && labelIds.length != 0) {
            hql.append(" and a.id in (:labelId)");
        }
        if (!Strings.isNullOrEmpty(catalogCode)) {
            hql.append(" and r.catalogCode like :catalogCode");
        }
        if (!Strings.isNullOrEmpty(typeCode)) {
            hql.append(" and r.typeCode like  :typeCode");
        }
        if (eraIds != null && eraIds.length != 0) {
            hql.append(" and r.era.id in (:eraId)");
        }
        if (levelIds != null && levelIds.length != 0) {
            hql.append(" and r.level.id in (:levelId)");
        }
        if (textureIds != null && textureIds.length != 0) {
            hql.append(" and r.texture.id in (:textureId)");
        }
        if (iscanceled != null) {
            hql.append(" and r.iscanceled = :iscanceled");
        }
    }

    /**
     * 为hibernate query对象组装参数
     *
     * @param query       hibernate query对象
     * @param totalCode   文物总等级号（模糊）
     * @param name        文物名称（模糊）
     * @param zoneIds     文物区域id
     * @param labelIds    文物标签编号
     * @param catalogCode 文物编目号（模糊）
     * @param typeCode    文物分类号（模糊）
     * @param eraIds      文物年代编号
     * @param levelIds    文物级别编号
     * @param textureIds  文物质地编号
     * @return hql 返回查询文物基本信息列表 的hql
     */
    private void setParameterListForQuery(Query query, String totalCode, String name, String[] zoneIds, Integer[] labelIds,
                                          String catalogCode, String typeCode, Integer[] eraIds, Integer[] levelIds,
                                          Integer[] textureIds, String siteId, Integer[] states, Boolean isCanceled) {
        query.setParameter("siteId", siteId);
        if (states != null && states.length != 0) {
            query.setParameterList("state", states);
        }
        if (!Strings.isNullOrEmpty(totalCode)) {
            query.setParameter("totalCode", "%" + totalCode + "%");
        }
        if (!Strings.isNullOrEmpty(name)) {
            query.setParameter("name", "%" + name + "%");
        }
        if (zoneIds != null && zoneIds.length != 0) {
            query.setParameterList("zoneId", zoneIds);
        }
        if (labelIds != null && labelIds.length != 0) {
            query.setParameterList("labelId", labelIds);
        }
        if (!Strings.isNullOrEmpty(catalogCode)) {
            query.setParameter("catalogCode", "%" + catalogCode + "%");
        }
        if (!Strings.isNullOrEmpty(typeCode)) {
            query.setParameter("typeCode", "%" + typeCode + "%");
        }
        if (eraIds != null && eraIds.length != 0) {
            query.setParameterList("eraId", eraIds);
        }
        if (levelIds != null && levelIds.length != 0) {
            query.setParameterList("levelId", levelIds);
        }
        if (textureIds != null && textureIds.length != 0) {
            query.setParameterList("textureId", textureIds);
        }
        if (isCanceled != null) {
            query.setParameter("iscanceled", isCanceled);
        }
    }

    @Override
    public Integer findRelicCountBySiteIdAndTotalCode(String siteId, String totalCode,
                                                      int relicState) {
        String hql = "select count(a.id) from Relic as a where a.siteId = :siteId  and a.totalCode like :totalCode";
        if (relicState != -1) {
            hql += " and a.state = :state";
        }
        Query query = getSession().createQuery(hql).setParameter("siteId", siteId)
                .setParameter("totalCode", "%" + totalCode + "%");
        if (relicState != -1) {
            query.setParameter("state", relicState);
        }
        return ((Long) query.uniqueResult()).intValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Relic> findRelicBySiteIdAndTotalCode(String siteId, String totalCode, int start,
                                                     int size, int relicState) {
        String hql = "select a from Relic as a where a.siteId = :siteId and a.totalCode like :totalCode";
        if (relicState != -1) {
            hql += " and a.state = :state";
        }
        Query query = getSession().createQuery(hql).setParameter("siteId", siteId)
                .setParameter("totalCode", "%" + totalCode + "%")
                .setFirstResult((start - 1) * size).setMaxResults(size);
        if (relicState != -1) {
            query.setParameter("state", relicState);
        }
        return query.list();
    }

    @Override
    public void updateRelicState(List<Integer> relicIds, int state) {
        // TODO 将 relicId 改为 in (:relicIds) @gaohui 2014-04-01
        StringBuffer hql = new StringBuffer("update Relic as a set a.state = :state where a.id = ");
        for (Integer id : relicIds) {
            hql.append(id).append(" or a.id = ");
        }
        String tmp = hql.toString();
        tmp = tmp.substring(0, tmp.lastIndexOf("or"));
        getSession().createQuery(tmp).setParameter("state", state).executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Relic> findRelicByIds(List<Integer> relicIds) {
        String hql = " from Relic r left outer join fetch r.zone where r.id in (:relicIds)";
        Query query = getSession().createQuery(hql);
        query.setParameterList("relicIds", relicIds);
        return query.list();
    }

    @Override
    public void updateRelicCanceledState(String siteId, int relicId, boolean iscanceled) {
        String hql = "update Relic set iscanceled = :iscanceled where siteId = :siteId and id = :relicId  ";
        Query query = getSession().createQuery(hql);
        query.setParameter("iscanceled", iscanceled).setParameter("siteId", siteId)
                .setParameter("relicId", relicId);
        query.executeUpdate();
    }

    @Override
    public List<Relic> validateByTotalCode(String totalCode) {
        String hql = " from Relic r where r.totalCode = :totalCode";
        Query query = getSession().createQuery(hql);
        query.setParameter("totalCode", totalCode);
        return query.list();
    }
}
