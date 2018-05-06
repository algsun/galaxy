package com.microwise.blueplanet.dao.impl;

import com.microwise.blueplanet.bean.vo.WindRoseVO;
import com.microwise.blueplanet.dao.ChartDao;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetBaseDao;
import com.microwise.common.sys.annotation.Beans.Dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 图表dao 实现
 *
 * @author xubaoji
 * @date 2013-2-27
 * @check 2013-03-08 zhangpeng svn:1991
 */
@Dao
@Blueplanet
public class ChartDaoImpl extends BlueplanetBaseDao implements ChartDao {

    @Override
    public List<Map<String, Object>> findBasicChart(String locationId,
                                                    int sensorPhysicalId, Date startTime, Date endTime, int a) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("sensorPhysicalId", sensorPhysicalId);
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);
        paramMap.put("a", a);
        return getSqlSession().selectList("blueplanet.mybatis.ChartDao.findBasicChart", paramMap);
    }

    @Override
    public Integer findBasicChartCount(String locationId, int sensorPhysicalId, Date startTime, Date endTime) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("sensorPhysicalId", sensorPhysicalId);
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);
        return getSqlSession().selectOne("blueplanet.mybatis.ChartDao.findBasicChartCount", paramMap);
    }

    @Override
    public List<Map<String, Object>> findDayRainfall(String locationId,
                                                     Date startTime, Date endTime) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);
        return getSqlSession().selectList(
                "blueplanet.mybatis.ChartDao.findDayRainfall", paramMap);
    }

    @Override
    public List<Map<String, Object>> findMonthRainfall(String locationId,
                                                       Date startTime, Date endTime) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);
        return getSqlSession().selectList(
                "blueplanet.mybatis.ChartDao.findMonthRainfall", paramMap);
    }

    @Override
    public List<Map<String, Object>> findYearRainfall(String locationId, Date startTime, Date endTime) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);
        return getSqlSession().selectList(
                "blueplanet.mybatis.ChartDao.findYearRainfall", paramMap);
    }

    @Override
    public List<Map<String, Object>> findBasicForDayRainfall(String locationId,
                                                             Integer sensorPhysicalid, Date startTime, Date endTime, Integer sensorPrecision) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("sensorPhysicalid", sensorPhysicalid);
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);
        paramMap.put("sensorPrecision", sensorPrecision);
        return getSqlSession().selectList(
                "blueplanet.mybatis.ChartDao.findBasicForDayRainfall",
                paramMap);
    }

    @Override
    public List<Map<String, Object>> findBasicForMonthRainfall(String locationId,
                                                               Integer sensorPhysicalid, Date startTime, Date endTime) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("sensorPhysicalid", sensorPhysicalid);
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);
        return getSqlSession()
                .selectList(
                        "blueplanet.mybatis.ChartDao.findBasicForMonthRainfall",
                        paramMap);
    }

    @Override
    public List<Map<String, Object>> findBasicForYearRainfall(String locationId,
                                                              Integer sensorPhysicalid, Date startTime, Date endTime, Integer sensorPrecision) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("sensorPhysicalid", sensorPhysicalid);
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);
        paramMap.put("sensorPrecision", sensorPrecision);
        return getSqlSession()
                .selectList(
                        "blueplanet.mybatis.ChartDao.findBasicForYearRainfall",
                        paramMap);
    }

    @Override
    public List<Map<String, Object>> findDayLight(String locationId, Date startTime, Date endTime) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);
        return getSqlSession().selectList(
                "blueplanet.mybatis.ChartDao.findDayLight", paramMap);
    }

    @Override
    public List<Map<String, Object>> findMonthLight(String locationId, Date startTime, Date endTime) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);
        return getSqlSession().selectList(
                "blueplanet.mybatis.ChartDao.findMonthLight", paramMap);
    }

    @Override
    public List<Map<String, Object>> findYearLight(String locationId, Date startTime, Date endTime) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);
        return getSqlSession().selectList(
                "blueplanet.mybatis.ChartDao.findYearLight", paramMap);
    }

    @Override
    public WindRoseVO findDayWindRose(String locationId, Date startDate, Date endDate) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        return getSqlSession().selectOne(
                "blueplanet.mybatis.ChartDao.findDayWindRose", paramMap);
    }

    @Override
    public WindRoseVO findYearWindRose(String nodeId, Date startTime, Date endTime) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", nodeId);
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);
        return getSqlSession().selectOne(
                "blueplanet.mybatis.ChartDao.findYearWindRose", paramMap);
    }


    @Override
    public List<Map<String, Object>> findMonthEvaporation(String locationId, Date startTime, Date endTime) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);
        return getSqlSession().selectList(
                "blueplanet.mybatis.ChartDao.findMonthEvaporation", paramMap);
    }

    @Override
    public List<Map<String, Object>> findYearEvaporation(String locationId, Date startTime, Date endTime) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);
        return getSqlSession().selectList(
                "blueplanet.mybatis.ChartDao.findYearEvaporation", paramMap);
    }

    @Override
    public Map<String, Object> findRecentEvaporation(String locationId, Date startTime, Date endTime) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);
        return getSqlSession().selectOne(
                "blueplanet.mybatis.ChartDao.findRecentEvaporation", paramMap);
    }

}
