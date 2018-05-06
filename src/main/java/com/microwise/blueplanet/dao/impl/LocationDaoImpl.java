package com.microwise.blueplanet.dao.impl;

import com.google.common.collect.Maps;
import com.microwise.blueplanet.bean.po.*;
import com.microwise.blueplanet.bean.vo.*;
import com.microwise.blueplanet.dao.LocationDao;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetBaseDao;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.sys.freemarker.tools.LocaleBundleTools;
import com.microwise.common.util.DateTimeUtil;
import com.microwise.orion.bean.Relic;
import com.microwise.phoenix.bean.po.LocationDate;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 位置点 Dao 实现
 *
 * @author li.jianfei
 * @date 2014-06-23
 */
@Beans.Dao
@Blueplanet
public class LocationDaoImpl extends BlueplanetBaseDao implements LocationDao {

    @Override
    public LocationVO findLocationById(String locationId) {
        return getSqlSession().selectOne("blueplanet.mybatis.LocationDao.findLocationById", locationId);
    }

    @Override
    public List<LocationPO> findLocations(String zoneId, int sensorId, Date begin, Date end) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("zoneId", zoneId);
        params.put("sensorId", sensorId);
        params.put("begin", begin);
        params.put("end", end);
        return getSqlSession().selectList("blueplanet.mybatis.LocationDao.findLocationsBySensorIdAndZoneId", params);
    }

    @Override
    public List<LocationDataPO> findLocationSensorData(String locationId, int sensorId, Date begin, Date end) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("locationId", locationId);
        params.put("sensorId", sensorId);
        params.put("begin", begin);
        params.put("end", end);
        return getSqlSession().selectList("blueplanet.mybatis.LocationDao.findLocationSensorData", params);
    }

    @Override
    public List<LocationVO> findLocationsByZoneId(String zoneId) {
        return getSqlSession().selectList("blueplanet.mybatis.LocationDao.findLocationsByZoneId", zoneId);
    }

    @Override
    public List<LocationVO> findLocationsBySiteIdAndLocationName(String siteId, String locationName) {
        Map map = new HashMap<String, Object>();
        map.put("siteId", siteId);
        map.put("locationName", locationName);
        return getSqlSession().selectList("blueplanet.mybatis.LocationDao.findLocationsBySiteIdAndLocationName", map);
    }

    @Override
    public LocationVO findOneLocationsBySiteIdAndLocationName(String siteId, String locationName) {
        Map map = new HashMap<String, Object>();
        map.put("siteId", siteId);
        map.put("locationName", locationName);
        return getSqlSession().selectOne("blueplanet.mybatis.LocationDao.findOneLocationsBySiteIdAndLocationName", map);
    }

    @Override
    public List<LocationVO> findLocationsBySiteId(String siteId, int page, int pageSize) {
        Map map = new HashMap<String, Object>();
        map.put("siteId", siteId);
        map.put("pageSize", pageSize);
        int begin = (page - 1) * pageSize;
        map.put("begin", begin);
        return getSqlSession().selectList("blueplanet.mybatis.LocationDao.findLocationList", map);
    }

    @Override
    public List<LocationVO> findLocationByNameAndZone(String locationName, String zoneId, String siteId, int page, int pageSize) {
        Map map = new HashMap<String, Object>();
        map.put("siteId", siteId);
        map.put("pageSize", pageSize);
        int begin = (page - 1) * pageSize;
        map.put("begin", begin);
        map.put("zoneId", zoneId);
        map.put("locationName", locationName);
        return getSqlSession().selectList("blueplanet.mybatis.LocationDao.findLocationByNameAndZone", map);
    }

    @Override
    public int findLocationByNameAndZoneCount(String locationName, String zoneId, String siteId) {
        Map map = new HashMap<String, Object>();
        map.put("siteId", siteId);
        map.put("zoneId", zoneId);
        map.put("locationName", locationName);
        return getSqlSession().<Integer>selectOne("blueplanet.mybatis.LocationDao.findLocationByNameAndZoneCount", map);
    }

    @Override
    public List<Integer> findLocationSensorIdList(String locationId) {
        return getSqlSession().selectList("blueplanet.mybatis.LocationDao.findLocationSensorIdList", locationId);
    }

    @Override
    public List<SensorinfoVO> findSensorInfoList(String locationId) {
        Map paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("language", LocaleBundleTools.appLocale());
        return getSqlSession().selectList("blueplanet.mybatis.LocationDao.findSensorInfoList", paramMap);
    }

    @Override
    public int findLocationListCount(String siteId) {
        return getSqlSession().<Integer>selectOne("blueplanet.mybatis.LocationDao.findLocationListCount", siteId);
    }

    @Override
    public String getMaxLocationId(String siteId) {
        return getSqlSession().selectOne("blueplanet.mybatis.LocationDao.getMaxLocationId", siteId);
    }

    @Override
    public void addLocation(LocationPO locationPO) {
        getSqlSession().insert("blueplanet.mybatis.LocationDao.addLocation", locationPO);
    }

    @Override
    public void addLocationRelic(int relicId, String locationId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("relicId", relicId);
        paramMap.put("locationId", locationId);
        getSqlSession().insert("blueplanet.mybatis.LocationDao.addLocationRelic", paramMap);
    }

    @Override
    public List<Relic> findRelics(String locationId) {
        return getSqlSession().selectList("blueplanet.mybatis.LocationDao.findRelics", locationId);
    }

    @Override
    public void deleteLocationRelic(int id) {
        getSqlSession().update("blueplanet.mybatis.LocationDao.deleteLocationRelic", id);
    }

    @Override
    public void deleteLocationRelic(String locationId) {
        getSqlSession().update("blueplanet.mybatis.LocationDao.deleteLocationRelicByLocationId", locationId);
    }

    @Override
    public void addLocationHistory(LocationPO locationPO) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("locationId", locationPO.getId());
        map.put("nodeId", locationPO.getNodeId());
        map.put("startTime", DateTimeUtil.formatFull(new Date()));
        getSqlSession().insert("blueplanet.mybatis.LocationDao.addLocationHistory", map);
    }

    @Override
    public void deleteLocation(String locationId) {
        getSqlSession().delete("blueplanet.mybatis.LocationDao.deleteLocation", locationId);
    }

    @Override
    public void deleteLocationHistory(String locationId) {
        getSqlSession().delete("blueplanet.mybatis.LocationDao.deleteLocationHistory", locationId);
    }

    @Override
    public void updateLocationHistoryEndTime(String locationId, String nodeId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("locationId", locationId);
        map.put("nodeId", nodeId);
        map.put("endTime", DateTimeUtil.formatFull(new Date()));
        getSqlSession().update("blueplanet.mybatis.LocationDao.updateLocationHistoryEndTime", map);
    }

    @Override
    public void updateLocation(LocationPO locationPO) {
        getSqlSession().update("blueplanet.mybatis.LocationDao.updateLocation", locationPO);
    }

    @Override
    public void createLocationTable(String locationId) {
        getSqlSession().update("blueplanet.mybatis.LocationDao.createLocationTable", locationId);
    }

    @Override
    public List<LocationHistoryVO> findLocationHistoryList(String locationId) {
        return getSqlSession().selectList("blueplanet.mybatis.LocationDao.findLocationHistoryList", locationId);
    }

    @Override
    public Boolean isExistLocationName(String locationName, String siteId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationName", locationName);
        paramMap.put("siteId", siteId);
        Integer count = getSqlSession().selectOne("blueplanet.mybatis.LocationDao.isExistLocationName", paramMap);
        return count > 0;
    }

    @Override
    public Boolean isExistLocationName(String locationId, String locationName, String siteId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("locationName", locationName);
        paramMap.put("siteId", siteId);
        Integer count = getSqlSession().selectOne("blueplanet.mybatis.LocationDao.isExistLocationNameExceptSelf", paramMap);
        return count > 0;
    }

    @Override
    public void deleteLocationTable(String locationId) {
        getSqlSession().update("blueplanet.mybatis.LocationDao.deleteLocationTable", locationId);
    }

    @Override
    public void deleteCoordinate(String locationId) {
        getSqlSession().delete("blueplanet.mybatis.LocationDao.deleteCoordinate", locationId);
    }

    public void deleteLocationSensor(String locationId) {
        getSqlSession().delete("blueplanet.mybatis.LocationDao.deleteLocationSensor", locationId);
    }

    @Override
    public void deleteLocationStock(String locationId) {
        getSqlSession().delete("blueplanet.mybatis.LocationDao.deleteLocationStock", locationId);
    }

    @Override
    public void deployLocation(String[] locationIds, String zoneId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("locationIds", locationIds);
        param.put("zoneId", zoneId);
        getSqlSession().update("blueplanet.mybatis.LocationDao.deployLocation", param);
    }

    @Override
    public void unDeployLocation(String locationId) {
        getSqlSession().update("blueplanet.mybatis.LocationDao.unDeployLocation", locationId);
    }

    @Override
    public List<RecentDataVO> findRecentDataList(String locationId, Integer dataCount) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("dataCount", dataCount);
        return getSqlSession().selectList(
                "blueplanet.mybatis.LocationDao.findRecentDataList",
                paramMap);
    }

    @Override
    public List<RecentDataVO> findRecentDataList(String locationId, Date startTime) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("startTime", startTime);
        return getSqlSession().selectList(
                "blueplanet.mybatis.LocationDao.findStampList_startTime",
                paramMap);
    }

    @Override
    public LocationHistoryPO findRecentlyLocation(String locationId) {
        return getSqlSession().selectOne("blueplanet.mybatis.LocationDao.findRecentlyLocation",
                locationId);
    }

    @Override
    public List<LocationDataVO> findLocationSensor(String locationId, List<Integer> sensorPhysicalidList) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("sensorPhysicalIdList", sensorPhysicalidList);
        paramMap.put("language", LocaleBundleTools.appLocale());
        return getSqlSession().selectList("blueplanet.mybatis.LocationDao.findLocationSensor", paramMap);
    }

    @Override
    public LocationDataVO findLocationSensorPre(String locationId, int sensorId, Date stamp) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("sensorId", sensorId);
        paramMap.put("stamp", stamp);

        return getSqlSession().selectOne("blueplanet.mybatis.LocationDao.findLocationSensorPre", paramMap);
    }

    @Override
    public LocationDataVO findLocationSensorNext(String locationId, int sensorId, Date stamp) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("sensorId", sensorId);
        paramMap.put("stamp", stamp);

        return getSqlSession().selectOne("blueplanet.mybatis.LocationDao.findLocationSensorNext", paramMap);
    }

    @Override
    public List<LocationDataVO> findLocationHistoryData(String locationId, Date date) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("date", date);
        paramMap.put("language", LocaleBundleTools.appLocale());
        return getSqlSession().selectList(
                "blueplanet.mybatis.LocationDao.findLocationHistoryData", paramMap);
    }

    @Override
    public List<DeviceVO> findUnbindDevices(String siteId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("siteId", siteId);
        return getSqlSession().selectList(
                "blueplanet.mybatis.LocationDao.findUnbindDevices", map);
    }

    @Override
    public List<RecentDataVO> findRecentDataList(String locationId, Date startTime, Date endTime, int page, int pageSize) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);
        int begin = (page - 1) * pageSize;
        paramMap.put("begin", begin);
        paramMap.put("pageSize", pageSize);
        return getSqlSession().selectList(
                "blueplanet.mybatis.LocationDao.findStampList_startTime_endTime", paramMap);
    }

    @Override
    public List<RecentDataVO> findHistoryDataList(String locationId, Date startTime, Date endTime, int page, int pageSize) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);
        int begin = (page - 1) * pageSize;
        paramMap.put("begin", begin);
        paramMap.put("pageSize", pageSize);
        return getSqlSession().selectList(
                "blueplanet.mybatis.LocationDao.findHistoryDataList_startTime_endTime_desc", paramMap);
    }

    @Override
    public List<RecentDataVO> findRecentDataList(String locationId, Date startTime, Date endTime) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);
        return getSqlSession().selectList(
                "blueplanet.mybatis.LocationDao.findRecentDataList_startTime_endTime", paramMap);
    }

    @Override
    public int findRecentDataListCount(String locationId, Date startTime, Date endTime) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);
        return getSqlSession().<Integer>selectOne(
                "blueplanet.mybatis.LocationDao.findStampList_startTime_endTime_count", paramMap);
    }

    @Override
    public List<Integer> getExcelSum(String locationId, Date startTime, Date endTime) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("endTime", endTime);
        paramMap.put("startTime", startTime);
        return getSqlSession().selectList(
                "blueplanet.mybatis.LocationDao.getExcelSum", paramMap);
    }

    @Override
    public Map<String, Date> findMaxAndMinTime(String locationId, Date startTime, Date endTime, Integer index, Integer pageSize) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("endTime", endTime);
        if (index != null && pageSize != null) {
            paramMap.put("start", (index - 1) * pageSize);
            paramMap.put("pageSize", pageSize);
        }
        paramMap.put("startTime", startTime);
        return getSqlSession().selectOne(
                "blueplanet.mybatis.LocationDao.findMaxAndMinTime", paramMap);
    }

    @Override
    public List<AvgdataPO> findAverageAndPeakValue(String locationId, Date startDate, Date endDate) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        return getSqlSession().selectList("blueplanet.mybatis.LocationDao.findAverageAndPeakValue", paramMap);
    }

    @Override
    public List<LocationAvgdataVO> findLocationSensorInfo(String locationId) {
        return getSqlSession().selectList("blueplanet.mybatis.LocationDao.findLocationSensorInfo", locationId);
    }

    @Override
    public List<Map<String, Object>> findAvgData(int sensorPhysicalId, Date startDate, Date endDate, String locationId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("sensorPhysicalId", sensorPhysicalId);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("locationId", locationId);
        return getSqlSession().selectList("blueplanet.mybatis.LocationDao.findAvgData", paramMap);
    }

    @Override
    public LocationDataVO findHistoryData(String locationId, int sensorPhysicalId, Date time, int deltaMinute) {
        Date startTime = new DateTime(time).minusMinutes(deltaMinute).toDate();
        Date endTime = new DateTime(time).plusMinutes(deltaMinute).toDate();

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("sensorPhysicalId", sensorPhysicalId);
        paramMap.put("timeSec", time.getTime() / 1000);
        paramMap.put("endTime", endTime);
        paramMap.put("startTime", startTime);
        return getSqlSession().selectOne("blueplanet.mybatis.LocationDao.findHistoryOnceData", paramMap);
    }

    @Override
    public void addUploadRecord(DataFilePO dataFile) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("filename", dataFile.getFilename());
        paramMap.put("uploadTime", dataFile.getUploadTime());
        getSqlSession().insert("blueplanet.mybatis.LocationDao.addUploadRecord", paramMap);
    }

    @Override
    public void updateUploadRecord(DataFilePO dataFile) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("filename", dataFile.getFilename());
        paramMap.put("uploadTime", dataFile.getUploadTime());
        paramMap.put("analysisSign", dataFile.getAnalysisSign());
        getSqlSession().insert("blueplanet.mybatis.LocationDao.updateUploadRecord", paramMap);
    }

    public boolean isExistFileRecord(String filename) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("filename", filename);
        return getSqlSession().selectOne("blueplanet.mybatis.LocationDao.isExistFileRecord", paramMap) != null;
    }

    @Override
    public RealtimeDataVO findLocationData(String locationId) {
        return getSqlSession().selectOne(
                "blueplanet.mybatis.LocationDao.findLocationData", locationId);
    }

    @Override
    public LocationVO findLocationByNodeId(String nodeId) {
        return getSqlSession().selectOne(
                "blueplanet.mybatis.LocationDao.findLocationByNodeId", nodeId);
    }

    @Override
    public String findLocationIdBySensorIdAndSiteId(int sensorPhysicalId, String siteId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("sensorPhysicalId", sensorPhysicalId);
        paramMap.put("siteId", siteId);
        return getSqlSession().selectOne("blueplanet.mybatis.LocationDao.findLocationIdBySensorIdAndSiteId", paramMap);
    }

    @Override
    public String findLocationIdbySensorIdAndZoneId(int sensorPhysicalId, String zoneId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("sensorPhysicalId", sensorPhysicalId);
        paramMap.put("zoneId", zoneId);
        return getSqlSession().selectOne("blueplanet.mybatis.LocationDao.findLocationIdbySensorIdAndZoneId", paramMap);
    }

    @Override
    public DeviceVO findDeviceByLocationId(String locationId) {
        return getSqlSession().selectOne("blueplanet.mybatis.LocationDao.findDeviceByLocationId", locationId);
    }

    @Override
    public List<HourAvgDataPO> findHourAvgDatas(String locationId, int sensorId, Date start, Date end) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("sensorId", sensorId);
        paramMap.put("start", start);
        paramMap.put("end", end);
        return getSqlSession().selectList("blueplanet.mybatis.LocationDao.findHourAvgDatas", paramMap);
    }

    @Override
    public List<YearAvgDataVO> findYearAvgData(String year, int sensorId, String locationId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("year", year);
        paramMap.put("sensorId", sensorId);
        paramMap.put("locationId", locationId);
        return getSqlSession().selectList("blueplanet.mybatis.LocationDao.findYearAvgData", paramMap);
    }

    @Override
    public List<LocationVO> findLocationsBySensorIdAndSiteId(String siteId, int sensorId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId", siteId);
        paramMap.put("sensorId", sensorId);
        return getSqlSession().selectList("blueplanet.mybatis.LocationDao.findLocationsBySensorIdAndSiteId", paramMap);
    }

    @Override
    public List<LocationDate> findLocationDataBySensor(String zoneId, int sensorId, Date startDate, Date endDate) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("zoneId", zoneId);
        map.put("sensorId", sensorId);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        return getSqlSession().selectList("blueplanet.mybatis.LocationDao.findLocationDataBySensor", map);
    }

    @Override
    public List<Double> findSensorValues(String locationId, int sensorId, Date startDate, Date endDate) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("locationId", locationId);
        map.put("sensorId", sensorId);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        return getSqlSession().selectList("blueplanet.mybatis.LocationDao.findSensorValues", map);
    }

    @Override
    public List<LocationVO> findLocationsBySiteId(String siteId) {
        return getSqlSession().selectList("blueplanet.mybatis.LocationDao.findLocationsBySiteId", siteId);
    }
}
