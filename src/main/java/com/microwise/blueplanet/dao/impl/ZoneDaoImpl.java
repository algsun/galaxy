package com.microwise.blueplanet.dao.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.microwise.blueplanet.bean.po.AvgdataPO;
import com.microwise.blueplanet.bean.vo.*;
import com.microwise.blueplanet.dao.ZoneDao;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetBaseDao;
import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.common.sys.freemarker.tools.LocaleBundleTools;

import java.util.*;

/**
 * 区域Dao实现
 *
 * @author zhangpeng
 * @date 2013-1-21
 * @check 2013-02-25 zhangpeng svn:1741
 * @check 2013-04-22 xubaoji svn:261
 * @check 2013-11-14 xiedeng svn:6603
 */
@Dao
@Blueplanet
public class ZoneDaoImpl extends BlueplanetBaseDao implements ZoneDao {

    @Override
    public List<ZoneVO> findZonesBySiteId(String siteId) {
        return getSqlSession().selectList("blueplanet.mybatis.ZoneDao.findZonesBySiteId", siteId);
    }

    @Override
    public void deleteZone(String zoneId) {
        getSqlSession().delete("blueplanet.mybatis.ZoneDao.deleteZone", zoneId);
    }

    @Override
    public List<String> findChildrenIdList(String zoneId) {
        List<String> resultList = new ArrayList<String>();
        if (!Strings.isNullOrEmpty(zoneId)) {
            resultList = getSqlSession().selectList(
                    "blueplanet.mybatis.ZoneDao.findChildrenIdList", zoneId);
        }
        return resultList;
    }

    @Override
    public long findDataVersion(String zoneId) {
        return getSqlSession().<Long>selectOne(
                "blueplanet.mybatis.ZoneDao.findDataVersion", zoneId);
    }

    @Override
    public ZoneVO findZoneByName(String siteId, String parentZoneId,
                                 String zoneName) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("siteId", siteId);
        map.put("parentZoneId", parentZoneId);
        map.put("zoneName", zoneName);
        return getSqlSession().selectOne(
                "blueplanet.mybatis.ZoneDao.findZoneByName", map);
    }

    @Override
    public List<SensorinfoVO> findSensorinfo(List<String> zoneIdList) {
        Map paramMap = new HashMap();
        paramMap.put("language", LocaleBundleTools.appLocale());
        paramMap.put("zoneIdList", zoneIdList);
        return getSqlSession().selectList(
                "blueplanet.mybatis.ZoneDao.findSensorinfo", paramMap);
    }

    @Override
    public ZoneVO findZoneById(String zoneId) {
        return getSqlSession().selectOne(
                "blueplanet.mybatis.ZoneDao.findZoneById", zoneId);
    }

    @Override
    public List<ZoneVO> findZoneList(String siteId, String parentZoneId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("siteId", siteId);
        map.put("parentZoneId", parentZoneId);
        return getSqlSession().selectList(
                "blueplanet.mybatis.ZoneDao.findZoneList", map);
    }

    @Override
    public List<RealtimeDataVO> findLocationInfo(List<String> zoneIds,
                                                 List<Integer> sensorPhysicalidList) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("zoneIds", zoneIds);
        paramMap.put("sensorPhysicalidList", sensorPhysicalidList);
        return getSqlSession().selectList("blueplanet.mybatis.ZoneDao.findLocationInfo", paramMap);
    }

    @Override
    public void saveZone(ZoneVO zone) {
        getSqlSession().insert("blueplanet.mybatis.ZoneDao.saveZone", zone);
    }

    @Override
    public List<ZoneVO> findZones(String zoneId) {
        return getSqlSession().selectList(
                "blueplanet.mybatis.ZoneDao.findZones", zoneId);
    }

    @Override
    public List<ZoneVO> findZoneLineList(String siteId) {
        return getSqlSession().selectList("blueplanet.mybatis.ZoneDao.findZoneLineList", siteId);
    }

    @Override
    public void updateZone(ZoneVO zone) {
        getSqlSession().update("blueplanet.mybatis.ZoneDao.updateZone", zone);
    }

    @Override
    public List<LocationDataVO> findGeneralLocationDataByRoomId(String zoneId, Date startDate, Date endDate) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("zoneId", zoneId);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("language", LocaleBundleTools.appLocale());

        return getSqlSession().selectList("blueplanet.mybatis.ZoneDao.findGeneralLocationDataByRoomId", paramMap);
    }

    @Override
    public List<LocationDataVO> findLocationMaxMinValue(int sensorPhysicalid, Date startDate, String siteId, String zoneId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("sensorPhysicalid", sensorPhysicalid);
        paramMap.put("startDate", startDate);
        paramMap.put("siteId", siteId);
        paramMap.put("zoneId", zoneId);
        return getSqlSession().selectList("blueplanet.mybatis.ZoneDao.findLocationMaxMinValue", paramMap);
    }

    public List<AvgdataPO> findLocationMaxMinValue(int sensorPhysicalid, String zoneId, Date start, Date end) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("sensorPhysicalid", sensorPhysicalid);
        paramMap.put("zoneId", zoneId);
        paramMap.put("start", start);
        paramMap.put("end", end);
        return getSqlSession().selectList("blueplanet.mybatis.ZoneDao.findLocationMaxMinValueByZoneId", paramMap);
    }

    @Override
    public List<LocationDataVO> findLocationDataByLocationId(String locationId, Date startDate, Date endDate) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("language", LocaleBundleTools.appLocale());
        return getSqlSession().selectList("blueplanet.mybatis.ZoneDao.findLocationDataByLocationId", paramMap);
    }

    @Override
    public void deletePlanImageCoordinate(String zoneId, String objectId) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("zoneId", zoneId);
        params.put("objectId", objectId);
        getSqlSession().update("blueplanet.mybatis.ZoneDao.deletePlanImageCoordinate", params);
    }

    @Override
    public void updatePlanImageCoordinate(PlanImageCoordinateVO planImageCoordinate) {
        getSqlSession().update("blueplanet.mybatis.ZoneDao.updatePlanImageCoordinate", planImageCoordinate);
    }

    @Override
    public void deleteAllCoordinates(String id, int flag) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("flag", flag);
        getSqlSession().update("blueplanet.mybatis.ZoneDao.deleteAllCoordinates", params);
    }

    @Override
    public void savePlanImageCoordinate(PlanImageCoordinateVO planImageCoordinate) {
        getSqlSession().update("blueplanet.mybatis.ZoneDao.savePlanImageCoordinate", planImageCoordinate);
    }

    @Override
    public boolean isCoordinateExist(String zoneId, String objectId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("zoneId", zoneId);
        paramMap.put("objectId", objectId);
        return getSqlSession().<Integer>selectOne("blueplanet.mybatis.ZoneDao.isCoordinateExist", paramMap) > 0;
    }

    @Override
    public String findParentId(String id) {
        return getSqlSession().selectOne("blueplanet.mybatis.ZoneDao.findParentId", id);
    }

    @Override
    public PlanImageCoordinateVO findPlanImageCoordinate(String zoneId, String objectId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("zoneId", zoneId);
        paramMap.put("objectId", objectId);
        return getSqlSession().selectOne("blueplanet.mybatis.ZoneDao.findPlanImageCoordinate", paramMap);
    }


    @Override
    public List<ZoneAvgDataVO> findZoneAvgData(String siteId, int sensorPhysicalId, Date begin, Date end) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("siteId", siteId);
        params.put("sensorPhysicalId", sensorPhysicalId);
        params.put("begin", begin);
        params.put("end", end);
        return getSqlSession().selectList("blueplanet.mybatis.ZoneDao.findZoneAvgData", params);
    }
}
