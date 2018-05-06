package com.microwise.phoenix.dao;

import com.microwise.phoenix.bean.po.ZoneData;
import com.microwise.phoenix.bean.vo.ZoneStatistics;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 环境监控： 区域统计分析 dao
 *
 * @author xu.baoji
 * @date 2013-7-4
 * @check 2013-07-08 许保吉  svn:4388
 * @check 2013-07-08 @gaohui #4417
 */
public interface ZoneStatisticsDao {

    /***
     * 查询 图表基本数据
     *
     * @param zoneIds
     * @return
     * @author xu.baoji
     * @date 2013-7-4
     */
    public List<ZoneStatistics> findBaseData(List<String> zoneIds);

    /**
     * 查询 一个监测指标的图表数据
     *
     * @param zoneIds
     * @param sensorPhysicalid
     * @param startDate
     * @param endDate
     * @return
     * @author xu.baoji
     * @date 2013-7-4
     */
    public List<ZoneData> findZoneData(List<String> zoneIds,
                                       int sensorPhysicalid, Date startDate, Date endDate);

    /***
     * 查询 图表 平均值数据
     *
     * @param zoneIds
     * @param sensorPhysicalid
     * @param startDate
     * @param endDate
     * @param type             类型  1 年 2  月
     * @return
     * @author xu.baoji
     * @date 2013-7-4
     */
    public List<Map<String, Object>> findAvgData(List<String> zoneIds,
                                                 int sensorPhysicalid, Date startDate, Date endDate, int type);


    /**
     * 根据某个监测指标，某个时间段，返回各个区域数据范围(最小值，最大值，波动值)
     *
     * @param siteId           站点编号
     * @param sensorPhysicalId 监测指标标识
     * @param start            开始时间
     * @param end              结果时间
     * @return
     * @author gaohui
     * @date 2013-07-04
     */
    List<ZoneData> findRangeStatsOfZones(String siteId, int sensorPhysicalId, Date start, Date end);
}
