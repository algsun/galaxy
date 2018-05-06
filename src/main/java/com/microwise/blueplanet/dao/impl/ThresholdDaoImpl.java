package com.microwise.blueplanet.dao.impl;

import com.google.common.collect.Maps;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.bean.vo.ThresholdVO;
import com.microwise.blueplanet.bean.vo.ZoneThresholdVO;
import com.microwise.blueplanet.dao.ThresholdDao;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetBaseDao;
import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.common.sys.freemarker.tools.LocaleBundleTools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 阈值 dao 实现
 *
 * @author xu.baoji
 * @date 2013-8-26
 */
@Blueplanet
@Dao
public class ThresholdDaoImpl extends BlueplanetBaseDao implements ThresholdDao {

    @Override
    public void saveThreshold(ThresholdVO thresholdVO) {
        getSqlSession().insert("blueplanet.mybatis.ThresholdDao.saveThreshold", thresholdVO);
    }

    @Override
    public void deleteThreshold(String locationId, int thresholdType) {
        Map paramMap = new HashMap();
        paramMap.put("thresholdType", thresholdType);
        paramMap.put("locationId", locationId);
        getSqlSession().delete("blueplanet.mybatis.ThresholdDao.deleteThreshold", paramMap);
    }

    @Override
    public List<SensorinfoVO> findThresholdLocationData(String zoneId) {
        Map paramMap = new HashMap();
        paramMap.put("language", LocaleBundleTools.appLocale());
        paramMap.put("zoneId", zoneId);
        return getSqlSession().selectList("blueplanet.mybatis.ThresholdDao.findThresholdLocationData", paramMap);
    }

    @Override
    public String findParentIdByZoneId(String zoneId) {
        return getSqlSession().selectOne("blueplanet.mybatis.ThresholdDao.findParentIdByZoneId", zoneId);
    }

    public List<String> findNoZoneThresholdLocationIds(String zoneId) {
        return getSqlSession().selectList("blueplanet.mybatis.ThresholdDao.findNoZoneThresholdLocationIds", zoneId);
    }

    @Override
    public List<ZoneThresholdVO> findZoneThresholds(String zoneId) {
        return getSqlSession().selectList("blueplanet.mybatis.ThresholdDao.findZoneThresholds",
                zoneId);
    }

    public void saveZoneThreshold(ZoneThresholdVO zoneThresholdVO) {
        getSqlSession().insert("blueplanet.mybatis.ThresholdDao.saveZoneThreshold", zoneThresholdVO);
    }

    public void deleteZoneThreshold(String zoneId) {
        getSqlSession().delete("blueplanet.mybatis.ThresholdDao.deleteZoneThreshold", zoneId);
    }

    public List<ThresholdVO> findThresholdsByLocationId(String locationId, int thresholdType) {
        Map paramMap = new HashMap();
        paramMap.put("locationId", locationId);
        paramMap.put("thresholdType", thresholdType);
        return getSqlSession().selectList("blueplanet.mybatis.ThresholdDao.findThresholdsByLocationIdAndType",
                paramMap);
    }

//    public List<ThresholdVO> findThresholdsByLocationId(String locationId) {
//        return getSqlSession().selectList("blueplanet.mybatis.ThresholdDao.findByLocationId",
//                locationId);
//    }

    public List<ThresholdVO> findThresholds(String locationId) {
        return getSqlSession().selectList("blueplanet.mybatis.ThresholdDao.findThresholds", locationId);
    }

    public ThresholdVO findThreshold(String locationId, int sensorId) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("locationId", locationId);
        params.put("sensorId", sensorId);
        return getSqlSession().selectOne("blueplanet.mybatis.ThresholdDao.findThreshold", params);
    }

    public void delete(String locationId) {
        getSqlSession().delete("blueplanet.mybatis.ThresholdDao.delete", locationId);
    }

    public void delete(String locationId, int sensorId) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("locationId", locationId);
        params.put("sensorId", sensorId);
        getSqlSession().delete("blueplanet.mybatis.ThresholdDao.deleteBy", params);
    }

    public void save(ThresholdVO threshold) {
        getSqlSession().insert("blueplanet.mybatis.ThresholdDao.save", threshold);
    }

    public void update(ThresholdVO threshold) {
        getSqlSession().update("blueplanet.mybatis.ThresholdDao.update", threshold);
    }
}
