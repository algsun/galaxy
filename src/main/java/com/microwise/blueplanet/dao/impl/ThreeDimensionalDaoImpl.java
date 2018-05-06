package com.microwise.blueplanet.dao.impl;

import com.microwise.blueplanet.bean.po.DimensionalLocationPO;
import com.microwise.blueplanet.bean.po.ThreeDimensionalPO;
import com.microwise.blueplanet.dao.ThreeDimensionalDao;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetBaseDao;
import com.microwise.common.sys.annotation.Beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 王耕
 * @date 15-6-10
 */
@Beans.Dao
@Blueplanet
public class ThreeDimensionalDaoImpl extends BlueplanetBaseDao implements ThreeDimensionalDao {

    @Override
    public void saveDimensional(ThreeDimensionalPO threeDimensionalPO) {//TODO
        getSqlSession().insert("blueplanet.mybatis.ThreeDimensionalDao.insertDimensionalFile", threeDimensionalPO);
    }

    @Override
    public List<ThreeDimensionalPO> findThreeDimensionals(String siteId) {
        return getSqlSession().selectList("blueplanet.mybatis.ThreeDimensionalDao.findDimensionalFiles",siteId);
    }

    @Override
    public ThreeDimensionalPO findThreeDimenById(int dimensionalId) {
        return getSqlSession().selectOne("blueplanet.mybatis.ThreeDimensionalDao.findThreeDimenById",dimensionalId);
    }

    @Override
    public ThreeDimensionalPO findThreeDimenByPath(String path) {
        return getSqlSession().selectOne("blueplanet.mybatis.ThreeDimensionalDao.findThreeDimenByPath",path);
    }

    @Override
    public void deleteDimensionalById(int dimensionalId) {
        getSqlSession().delete("blueplanet.mybatis.ThreeDimensionalDao.deleteDimensionalById",dimensionalId);
    }

    @Override
    public void updateDimensionalRemark(String remark,int dimensionalId) {
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("remark",remark);
        paramMap.put("dimensionalId",dimensionalId);
        getSqlSession().update("blueplanet.mybatis.ThreeDimensionalDao.updateDimensionalRemark",paramMap);
    }

    @Override
    public void saveDimensionalLocationRelation(String siteId, int dimensionalId, String[] locationIds) {
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId",siteId);
        paramMap.put("dimensionalId",dimensionalId);
        paramMap.put("locationIds",locationIds);
        getSqlSession().insert("blueplanet.mybatis.ThreeDimensionalDao.saveDimensionalLocationRelation",paramMap);
    }

    @Override
    public List<DimensionalLocationPO> findDimensionalLocationRelations(String siteId, int dimensionalId) {
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId",siteId);
        paramMap.put("dimensionalId",dimensionalId);
        return getSqlSession().selectList("blueplanet.mybatis.ThreeDimensionalDao.findDimensionalLocationRelations",paramMap);
    }

    @Override
    public void deleteDimensionalLocationRelation(int dimensionalId) {
        getSqlSession().delete("blueplanet.mybatis.ThreeDimensionalDao.deleteDimensionalLocationRelation",dimensionalId);
    }
}
