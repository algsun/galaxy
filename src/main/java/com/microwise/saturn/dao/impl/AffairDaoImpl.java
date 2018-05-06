package com.microwise.saturn.dao.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.saturn.bean.Affair;
import com.microwise.saturn.dao.AffairDao;
import com.microwise.saturn.sys.Saturn;
import com.microwise.saturn.sys.SaturnBaseDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lijianfei on 15-3-19.
 */
@Beans.Dao
@Saturn
public class AffairDaoImpl extends SaturnBaseDao implements AffairDao {
    @Override
    public void save(Affair affair) {
        getSqlSession().insert("saturn.mybatis.AffairDao.save", affair);
    }

    @Override
    public void update(Affair affair) {
        getSqlSession().update("saturn.mybatis.AffairDao.update", affair);
    }

    @Override
    public List<Affair> findAll(String siteId) {
        return getSqlSession().selectList("saturn.mybatis.AffairDao.findAll", siteId);
    }

    @Override
    public Affair findById(int affairId) {
        return getSqlSession().selectOne("saturn.mybatis.AffairDao.findById", affairId);
    }

    @Override
    public void delete(int affairId) {
        getSqlSession().delete("saturn.mybatis.AffairDao.delete", affairId);
    }

    @Override
    public List<Affair> findAllByTypeAndYear(Integer type, Integer year, Integer quarterNum) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("type", type);
        param.put("year", year);
        param.put("quarterNum", quarterNum);
        return getSqlSession().selectList("saturn.mybatis.AffairDao.findAllByYear", param);
    }

    @Override
    public Integer findMaxSizeType(String siteId, Integer year, Integer quarterNum) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("siteId", siteId);
        param.put("year", year);
        param.put("quarterNum", quarterNum);
        return getSqlSession().selectOne("saturn.mybatis.AffairDao.findMaxSizeType", param);
    }

    @Override
    public List<Affair> findAffairType(String siteId, Integer year, Integer quarterNum) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("siteId", siteId);
        param.put("year", year);
        param.put("quarterNum", quarterNum);
        return getSqlSession().selectList("saturn.mybatis.AffairDao.findAffairType", param);
    }

    @Override
    public Integer findAffairTypeCount(String siteId, Integer type, Integer year, Integer quarterNum) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("siteId", siteId);
        param.put("type", type);
        param.put("year", year);
        param.put("quarterNum", quarterNum);
        return getSqlSession().selectOne("saturn.mybatis.AffairDao.findAffairTypeCount", param);
    }
}
