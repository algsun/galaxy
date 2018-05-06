package com.microwise.blueplanet.dao;

import com.microwise.blueplanet.bean.vo.WindRoseVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 图表dao
 *
 * @author xubaoji
 * @date 2013-2-27
 * @check 2013-03-08 zhangpeng svn:1991
 */
public interface ChartDao {

    /**
     * 获得一个设备指定监测指标的 基础曲线图数据
     *
     * @param locationId       设备编号
     * @param sensorPhysicalId 监测指标标识
     * @param startTime        开始时间
     * @param endTime          结束时间
     * @param a                用于数据过滤取模
     * @return list<Map<Date, String>> 基础曲线图 图形数据
     */
    public List<Map<String, Object>> findBasicChart(String locationId,
                                                    int sensorPhysicalId, Date startTime, Date endTime, int a);

    /**
     * 查询基本曲线图数据的总数量
     *
     * @param locationId       位置点ID
     * @param sensorPhysicalId 监测指标标识
     * @param startTime        开始时间
     * @param endTime          结束时间
     * @return 基础曲线图数据的总数量
     */
    public Integer findBasicChartCount(String locationId, int sensorPhysicalId, Date startTime, Date endTime);

    /**
     * 查询日类型 降雨量图表 降雨量数据
     *
     * @param locationId 位置点id
     * @param startTime  开始时间 格式如：2013-01-12 8:00:00
     * @param endTime    结束时间 格式如：2013-01-13 8:00:00
     * @return list<Map<Date, String>> 降雨量图表 降雨量数据
     * @author liuzhu
     * @date 2014-7-1
     */
    public List<Map<String, Object>> findDayRainfall(String locationId,
                                                     Date startTime, Date endTime);


    /**
     * 查询月类型 降雨量图表 降雨量数据
     *
     * @param locationId 位置点id
     * @param startTime  开始时间 格式如：2013-01-01 00:00:00
     * @param endTime    结束时间 格式如：2013-01-31 00:00:00
     * @return list<Map<Date, String>> 降雨量图表 降雨量数据
     * @author liuzhu
     * @date 2014-7-1
     */
    public List<Map<String, Object>> findMonthRainfall(String locationId,
                                                       Date startTime, Date endTime);

    /**
     * 查询月类型 蒸发量图表 蒸发量数据
     *
     * @param locationId 位置点id
     * @param startTime  开始时间 格式如：2013-01-01 00:00:00
     * @param endTime    结束时间 格式如：2013-01-31 00:00:00
     * @return list<Map<Date, String>> 蒸发量图表 蒸发量数据
     * @author 许月希
     * @date 2014-6-16
     */
    public List<Map<String, Object>> findMonthEvaporation(String locationId,
                                                          Date startTime, Date endTime);

    /**
     * 查询年类型 蒸发量图表 蒸发量数据
     *
     * @param locationId 位置点id
     * @param startTime  开始时间 格式如：2013-01-01 00:00:00
     * @param endTime    结束时间 格式如：2013-01-31 00:00:00
     * @return list<Map<Date, String>> 蒸发量图表 蒸发量数据
     * @author 许月希
     * @date 2014-6-16
     */
    public List<Map<String, Object>> findYearEvaporation(String locationId,
                                                         Date startTime, Date endTime);

    /**
     * 查询近一天或近一月等累计的 蒸发量图表 蒸发量数据
     *
     * @param locationId 位置点id
     * @return list<Map<Date, String>> 蒸发量图表 蒸发量数据
     * @author 许月希
     * @date 2014-7-14
     */
    public Map<String, Object> findRecentEvaporation(String locationId,
                                                     Date startTime, Date endTime);


    /**
     * 查询年类型 降雨量图表 降雨量数据
     *
     * @param locationId 位置点id
     * @param startTime  初始日期
     * @param endTime    截止日期
     * @return list<Map<Date, String>> 降雨量图表 降雨量数据
     * @author liuzhu
     * @date 2014-7-1
     */
    public List<Map<String, Object>> findYearRainfall(String locationId, Date startTime, Date endTime);

    /**
     * 为日降雨量图表 查询 其他监测指标的曲线数据
     *
     * @param locationId       位置点id
     * @param sensorPhysicalid 监测指标标识
     * @param startTime        开始时间 格式如：2013-01-12 8:00:00
     * @param endTime          结束时间      格式如：2013-01-13 8:00:00
     * @param sensorPrecision  当前指标的精度
     * @return list<Map<Date, String>> 降雨量图表 监测指标的曲线数据
     * @author liuzhu
     * @date 2014-7-1
     */
    public List<Map<String, Object>> findBasicForDayRainfall(String locationId,
                                                             Integer sensorPhysicalid, Date startTime, Date endTime,
                                                             Integer sensorPrecision);

    /**
     * 为月降雨量图表 查询 其他监测指标的曲线数据
     *
     * @param locationId       位置点id
     * @param sensorPhysicalid 监测指标标识
     * @param startTime        开始时间 格式如：2013-01-01 00:00:00
     * @param endTime          结束时间 格式如：2013-01-31 00:00:00
     * @return list<Map<Date, String>> 降雨量图表 监测指标的曲线数据
     * @author liuzhu
     * @date 2014-7-1
     */
    public List<Map<String, Object>> findBasicForMonthRainfall(String locationId,
                                                               Integer sensorPhysicalid, Date startTime, Date endTime);

    /**
     * 为年降雨量图表 查询 其他监测指标的曲线数据
     *
     * @param locationId       位置点id
     * @param sensorPhysicalid 监测指标标识
     * @param startTime        初始日期
     * @param endTime          截止日期
     * @param sensorPrecision  当前指标的精度
     * @return list<Map<Date, String>> 降雨量图表 监测指标的曲线数据
     * @author liuzhu
     * @date 2014-7-1
     */
    public List<Map<String, Object>> findBasicForYearRainfall(String locationId,
                                                              Integer sensorPhysicalid, Date startTime, Date endTime, Integer sensorPrecision);

    /**
     * 获得 日类型光照图表数据
     *
     * @param locationId 设备编号
     * @param startTime  初始日期
     * @param endTime    截止日期
     * @return List<Map<String, Object>> 日类型 光照图表数据
     * @author liuzhu
     * @date 2014-7-3
     */
    public List<Map<String, Object>> findDayLight(String locationId, Date startTime, Date endTime);

    /**
     * 获得 月类型光照图表数据
     *
     * @param locationId 位置点id
     * @param startTime  起始日期
     * @param endTime
     * @return List<Map<String, Object>> 月类型 光照图表数据
     * @author liuzhu
     * @date 2014-7-3
     */
    public List<Map<String, Object>> findMonthLight(String locationId, Date startTime, Date endTime);

    /**
     * 获得 年类型光照图表数据
     *
     * @param locationId 位置点id
     * @param startTime  初始日期
     * @param endTime    截止日期
     * @return List<Map<String, Object>> 年类型 光照图表数据
     * @author liuzhu
     * @date 2014-7-3
     */
    public List<Map<String, Object>> findYearLight(String locationId, Date startTime, Date endTime);

    /**
     * 获得 日类型 风向图表数据
     *
     * @param locationId 位置点Id
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @return WindRoseVO 日类型 风向表数据
     * @author liuzhu
     * @date 2015-6-11
     */
    public WindRoseVO findDayWindRose(String locationId, Date startDate, Date endDate);

    /**
     * 获得 年类型光照图表数据
     *
     * @param nodeId    设备编号
     * @param startTime 初始日期
     * @param endTime   截止日期
     * @return WindRoseVO 年类型 光照图表数据
     * @author 许保吉
     * @date 2013-3-4
     */
    public WindRoseVO findYearWindRose(String nodeId, Date startTime, Date endTime);

}
