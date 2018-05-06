package com.microwise.phoenix.service;

import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.phoenix.bean.po.ZoneData;
import com.microwise.phoenix.bean.vo.ZoneStatistics;

import java.util.Date;
import java.util.List;
import java.util.Map;

/***
 * 环境监控： 区域统计分析 service
 *
 * @author xu.baoji
 * @date 2013-7-4
 * @check 2013-07-08 许保吉  svn:4386
 */
public interface ZoneStatisticsService {

    /**
     * 查询 区域统计分析 数据
     *
     * @param zoneId 区域id
     * @param date   查询日期
     * @param type   查询类型 月 2 ， 年 1
     * @return List<ZoneStatistics> 区域统计分析 实体列表
     * @author xu.baoji
     * @date 2013-7-4
     */
    public List<ZoneStatistics> findZoneStatistics(String zoneId, Date date,
                                                   int type);

    /**
     * 根据某个监测指标，某个时间段，返回各个区域数据范围(最小值，最大值，波动值)
     *
     * @param siteId           站点编号
     * @param sensorPhysicalId 监测指标标识
     * @param date             时间
     * @param type             时间类型 1.年 2,月 3.日
     * @return List<ZoneData> 区域统计实体
     * @author gaohui
     * @date 2013-07-04
     */
    public List<ZoneData> findRangStatsOfZones(String siteId, int sensorPhysicalId, Date date, int type);

    /**
     * 查询站点下某些监测指标，某个时间段，返回各个区域数据范围(最小值，最大值，波动值)
     * 外层list 中元素 为每个检测指标区域对比统计
     *
     * @param siteId 站点编号
     * @param date   时间
     * @param type   时间类型 1.年 2,月 3.日
     * @return List<Map<SensorinfoVO,List<ZoneData>>>
     * @author xu.baoji
     * @date 2013-07-26
     */
    public List<Map<SensorinfoVO, List<ZoneData>>> findRangStatsOfZones(String siteId, Date date, int type);

}
