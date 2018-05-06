package com.microwise.blueplanet.service;

import com.microwise.blueplanet.bean.vo.ChartVO;
import com.microwise.blueplanet.bean.vo.WindRoseVO;

import java.util.Date;
import java.util.List;

/**
 * 图表Service
 *
 * @author zhangpeng
 * @date 2013-2-27
 * @check 2013-03-08 zhangpeng svn:1991
 */
public interface ChartService {

    /**
     * 获得一个位置点基础曲线图数据
     *
     * @param locationId     位置点ID
     * @param sensorInfoList 监测指标列表
     * @param startTime      开始时间
     * @param endTime        结束时间
     * @return List<ChartVO> 图表vo对象列表
     * @author xubaoji
     * @date 2013-2-27
     */
    public List<ChartVO> findBasicChart(String locationId,
                                        List<Integer> sensorInfoList, Date startTime, Date endTime);

    /**
     * 获得一个设备均值曲线图数据
     *
     * @param locationId     位置点编号
     * @param sensorinfoList 监测指标列表
     * @param startTime      开始时间
     * @param endTime        结束时间
     * @return List<ChartVO> 图表vo对象列表
     * @author xuyuexi
     * @date 2014-7-11
     */
    public List<ChartVO> findAverageChart(String locationId,
                                          List<Integer> sensorinfoList, Date startTime, Date endTime);

    /**
     * 获得一个位置点的降雨量柱状图数据（可携带其他指标）
     *
     * @param locationId     位置点id
     * @param sensorinfoList 监测指标列表
     * @param dateType       时间类型
     * @param time           时间
     * @return List<ChartVO> 图表vo对象列表
     * @author liuzhu
     * @date 2014-7-1
     */
    public List<ChartVO> findRainfall(String locationId,
                                      List<Integer> sensorinfoList, Integer dateType, Date time);

    /**
     * 获得一个设备的蒸发量柱状图数据（不携带其他指标）
     * <p/>
     * dateType==FIND_TYPE_DAY传入开始时间结束时间
     * dateType==FIND_TYPE_MONTH OR FIND_TYPE_YEAR传入结束时间，开始时间传null
     *
     * @param locationId 位置点id
     * @param dateType   时间类型
     * @return ChartVO 图表vo对象列表
     * @author xuyuexi
     * @date 2014-6-16
     */
    public ChartVO findEvaporation(String locationId, Integer dateType, Date startTime, Date endTime);

    /**
     * 获得一个位置点的累计光照阴影图数据
     *
     * @param locationId 位置点id
     * @param dateType   时间类型
     * @param time       时间
     * @return 图表vo对象
     * @author liuzhu
     * @date 2014-7-3
     */
    public ChartVO findLight(String locationId, int dateType, Date time);

    /**
     * 获得一个位置点的风向玫瑰图数据
     *
     * @param locationId 位置点编号
     * @param dateType   时间类型
     * @param time       时间
     * @return WindRoseVO 风向玫瑰图vo对象
     * @author xuyuexi
     * @date 2014-7-14
     */
    public WindRoseVO findWindRose(String locationId,int dateType,Date time, Date startDate, Date endDate);

    /**
     * 查询近一天 蒸发量图表 蒸发量数据
     *
     * @param locationId 位置点id
     * @return list<Map<Date, String>> 蒸发量图表 蒸发量数据
     * @author 许月希
     * @date 2014-7-14
     */
    public ChartVO findEvaporationRecentDay(String locationId, int dayCount);

    /**
     * 查询近n月 蒸发量图表 蒸发量数据
     *
     * @param locationId 位置点id
     * @param monthCount 最近几个月
     * @return list<Map<Date, String>> 蒸发量图表 蒸发量数据
     * @author 许月希
     * @date 2014-6-16
     */
    public ChartVO findEvaporationRecentMonth(String locationId, int monthCount);

}
