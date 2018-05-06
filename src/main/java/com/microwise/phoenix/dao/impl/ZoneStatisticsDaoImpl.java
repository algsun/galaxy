package com.microwise.phoenix.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.phoenix.bean.po.ZoneData;
import com.microwise.phoenix.bean.vo.ZoneStatistics;
import com.microwise.phoenix.dao.ZoneStatisticsDao;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.sys.PhoenixBaseDao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 环境监控： 区域统计分析 dao 实现
 *
 * @author xu.baoji
 * @date 2013-7-4
 * @check 2013-07-08 许保吉 svn:4390
 * @check 2013-07-08 @gaohui #4417
 */
@Dao
@Phoenix
public class ZoneStatisticsDaoImpl extends PhoenixBaseDao implements ZoneStatisticsDao {

    @Override
    public List<Map<String, Object>> findAvgData(List<String> zoneIds, int sensorPhysicalid, Date startDate,
                                                 Date endDate, int type) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("zoneIds", zoneIds);
        paramMap.put("sensorPhysicalid", sensorPhysicalid);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("type", type);
        return getSqlSession().selectList("phoenix.mybatis.ZoneStatisticsDao.findAvgData", paramMap);
    }

    @Override
    public List<ZoneStatistics> findBaseData(List<String> zoneIds) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("zoneIds", zoneIds);
        return getSqlSession().selectList("phoenix.mybatis.ZoneStatisticsDao.findBaseData", paramMap);
    }

    @Override
    public List<ZoneData> findZoneData(List<String> zoneIds, int sensorPhysicalid, Date startDate, Date endDate) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("zoneIds", zoneIds);
        paramMap.put("sensorPhysicalid", sensorPhysicalid);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        return getSqlSession().selectList("phoenix.mybatis.ZoneStatisticsDao.findZoneData", paramMap);
    }

    @Override
    public List<ZoneData> findRangeStatsOfZones(String siteId, int sensorPhysicalId, Date start, Date end) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("siteId", siteId);
        params.put("sensorPhysicalId", sensorPhysicalId);
        params.put("start", start);
        params.put("end", end);
        return getSqlSession().selectList("phoenix.zoneStats.findRangeStatsOfZones", params);
    }
}
