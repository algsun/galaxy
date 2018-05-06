package com.microwise.blueplanet.service;

import com.microwise.blueplanet.bean.po.AvgdataPO;
import com.microwise.blueplanet.bean.vo.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 站点Service
 *
 * @author zhangpeng
 * @date 2013-1-17
 * @check 2013-02-25 zhangpeng svn:1731
 * @check 2013-04-22 xubaoji svn:2568
 * @check 2013-11-14 xiedeng svn:6556
 */
public interface SiteService {

    /**
     * 查询站点的实时数据(位置点存储)
     *
     * @param siteId 站点id
     * @return List<RealtimeDataVO> 站点实时数据VO对象列表
     * @author wang.geng
     * @date 2014-7-10
     */
    public List<RealtimeDataVO> findRealtimeDataLocation(String siteId);

    /**
     * 查询站点实时数据（带监测指标筛选条件）
     *
     * @param siteId               站点id
     * @param sensorPhysicalidList 筛选条件监测指标集合
     * @return List<RealtimeDataVO> 站点实时数据VO对象列表
     */
    public List<RealtimeDataVO> findDeviceRealTimeData(String siteId,
                                                       List<Integer> sensorPhysicalidList);

    /**
     * 查询站点的实时数据(位置点存储)（带监测指标筛选条件）
     *
     * @param siteId 站点id
     * @return List<RealtimeDataVO> 站点实时数据VO对象列表
     * @author wang.geng
     * @date 2014-7-11
     */
    public List<RealtimeDataVO> findRealtimeDataLocation(String siteId,
                                                         List<Integer> sensorPhysicalidList);

    /**
     * 查询站点下设备所拥有的监测指标集合（激活的）
     *
     * @param siteId 站点id
     * @return List<SensorinfoVO> 监测指标vo对象列表
     * @author zhangpeng
     * @date 2013-1-18
     */
    public List<SensorinfoVO> findSensorinfo(String siteId);

    /**
     * 查询站点下设备所拥有的监测指标集合（设备的监测指标）
     *
     * @param siteId 站点id
     * @return List<SensorinfoVO> 监测指标vo对象列表
     * @author liuzhu
     * @date 2014-12-22
     */
    public List<SensorinfoVO> findDeviceSensorInfo(String siteId);


    /**
     * 根据站点id查询站点对象
     *
     * @author liuzhu
     * @date 2014-12-17
     */
    public List<SiteVO> findSite();

    /**
     * 根据站点id查询站点对象
     *
     * @param siteId 站点id
     * @return SiteVO 站点对象
     * @author zhangpeng
     * @date 2013-1-18
     */
    public SiteVO findSiteById(String siteId);

    /**
     * 查询有订阅用户的site
     *
     * @return List<SiteVO> 站点vo 实体列表
     * @author 许保吉
     * @date 2013-6-14
     */
    public List<SiteVO> findSiteHasSubscribeUser();

    /**
     * 获取所有检测指标近5天的数据(位置点)
     *
     * @param siteId 站点编号
     * @return List<LocationDataVO>
     * @author wang.geng
     * @date 2014-7-18
     */
    public List<LocationDataVO> findSiteChartData(String siteId);

    /**
     * 站点下所有监测指标实时的平均数据（位置点）
     *
     * @param siteId 站点编号
     * @return Map<String, List<LocationDataVO>>
     * @author wang.geng
     * @date 2014-7-18
     */
    public Map<String, List<LocationDataVO>> findSiteRealTimeData(String siteId);

    /**
     * 站点下所有监测指标实时的平均数据（位置点）
     *
     * @param siteId 站点编号
     * @return Map<String, List<LocationDataVO>>
     * @author wang.geng
     * @date 2014-7-18
     */
    public Map<String, List<LocationDataVO>> findSiteRealTimeData(String siteId, int sensorId);

    /**
     * 区域下所有监测指标实时的平均数据（位置点）
     *
     * @param zoneId 区域编号
     * @return Map<String, List<LocationDataVO>>
     * @author xuyuexi
     * @date 2014-12-31
     */
    public Map<String, List<LocationDataVO>> findSiteRealTimeDataByZone(String zoneId);

    /**
     * 查询站点下的所有设备,不受roomid限制
     *
     * @param siteId 站点id
     * @return List<DeviceVO> 设备vo对象列表
     * @author xuyuexi
     * @date 2014-3-24
     */
    public List<DeviceVO> findAllDevicesBySiteId(String siteId);

    /**
     * 获取站点总览设备运行状况饼图数据
     *
     * @param siteId 站点id
     * @return 饼图数据json字符串
     */
    public DeviceStatusPieData findDeviceStatusPieDataBySiteId(String siteId);

    /**
     * 获取站点总览设备运行状况饼图数据
     *
     * @param zoneId 区域id
     * @return 饼图数据json字符串
     */
    public DeviceStatusPieData findDeviceStatusPieDataByZoneId(String zoneId);


    /**
     * 查找所有站点
     *
     * @return products集合
     * @author liuzhu
     * @date 2015-1-21
     */
    public List<SiteVO> findAllSite();

    /**
     *查询站点下前一天区域湿度最大值、最小值、平均值
     *@return 组织区域名称、波动范围、平均值集合
     *@author chenyaofei
     *@date 2016-8-24
     */
    public Map<String, AvgdataPO> findZoneHumidity(List<ZoneVO> zoneVOList);

    /**
     * 查询站点下监测指标被应用的次数，次数越多越常用
     * @return 返回监测指标信息以及被使用的次数
     * @author chen.yaofei
     * @date 2016-8-26
     */
    public List<SensorUsedDataVO> findSensorUsedInfo(String siteId, String language);

    /**
     * <pre>
     * 获得要导出的历史数据报表的文件名
     * </pre>
     *
     * @param siteId
     * @param startTime
     * @param endTime
     * @return
     */
    String getFileName(String siteId, Date startTime, Date endTime);
}
