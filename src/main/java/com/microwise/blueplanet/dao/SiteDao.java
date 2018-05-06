package com.microwise.blueplanet.dao;

import com.microwise.blueplanet.bean.po.ZonePO;
import com.microwise.blueplanet.bean.vo.*;

import java.util.Date;
import java.util.List;

/**
 * 站点Dao层
 *
 * @author zhangpeng
 * @date 2013-1-21
 * @check 2013-02-25 zhangpeng svn:1731
 * @check 2013-04-22 xubaoji svn:2568
 * @check 2013-11-14 xiedeng svn:6621
 */
public interface SiteDao {

    /**
     * 查询站点下的所有设备 ,不受roomid限制
     *
     * @param siteId 站点id
     * @return List<DeviceVO> 设备vo对象列表
     * @author xuyuexi
     * @date 2014-3-24
     */
    public List<DeviceVO> findAllDevicesBySiteId(String siteId);

    /**
     * 查询设备实时数据基本信息
     *
     * @param siteId               站点编号
     * @param sensorPhysicalidList 筛选条件监测指标集合
     * @return List<RealtimeDataVO> 实时数据VO对象列表，此处未封装设备监测指标数据
     */
    public List<RealtimeDataVO> findNodeinfo(String siteId,
                                             List<Integer> sensorPhysicalidList);

    /**
     * 查询设备实时数据基本信息(位置点存储)
     *
     * @param siteId               站点编号
     * @param sensorPhysicalidList 筛选条件监测指标集合
     * @return List<RealtimeDataVO> 实时数据VO对象列表，此处未封装设备监测指标数据
     * @author wang.geng
     * @date 2014-7-10
     */
    public List<RealtimeDataVO> findLocationInfo(String siteId,
                                                 List<Integer> sensorPhysicalidList);

    /**
     * 根据站点id查询站点对象
     *
     * @param siteId 站点id
     * @return SiteVO 站点vo对象
     * @author zhangpeng
     * @date 2013-1-18
     */
    public SiteVO findSiteById(String siteId);

    /**
     * 根据站点id查询站点对象
     *
     * @return SiteVO 站点vo对象
     * @author liuzhu
     * @date 2014-12-17
     */
    public List<SiteVO> findSite();

    /**
     * 查询站点下设备所拥有的激活的监测指标
     *
     * @param siteId 站点id
     * @return List<SensorinfoVO> 监测指标vo对象列表
     * @author zhangpeng
     * @date 2013-1-21
     */
    public List<SensorinfoVO> findSensorinfo(String siteId);

    /**
     * 查询站点下设备所拥有的激活的监测指标（设备监测指标）
     *
     * @param siteId 站点id
     * @return List<SensorinfoVO> 监测指标vo对象列表
     * @author liuzhu
     * @date 2014-12-22
     */
    public List<SensorinfoVO> findDeviceSensorInfo(String siteId);

    /**
     * 查询有订阅用户的site
     *
     * @return List<SiteVO> 站点vo 实体列表
     * @author 许保吉
     * @date 2013-6-14
     */
    public List<SiteVO> findSiteHasSubscribeUser();

    /**
     * 查询站点下所有检测指标（位置点）
     *
     * @param startDate 开始时间
     * @param siteId    站点id
     * @param zoneId    区域id
     * @return List<LocationVO> 传感信息表
     * @author wang.geng
     * @date 2014-7-17
     */
    public List<LocationDataVO> findAvgLocationById(Date startDate, String siteId, String zoneId);

    /**
     * 根据站点id获取子区域（一级区域）
     *
     * @param siteId 站点id
     * @return ZoneVOList
     * @author liuzhu
     * @date 2013-10-31
     */
    public List<ZonePO> findZonePOList(String siteId);

    /**
     * 查找所有站点
     *
     * @return products集合
     * @author liuzhu
     * @date 2015-1-21
     */
    public List<SiteVO> findAllSite();

    /**
     * 查询站点下监测指标被应用的次数，次数越多越常用
     * @return 返回监测指标信息以及被使用的次数
     * @author chen.yaofei
     * @date 2016-8-26
     */
    public List<SensorUsedDataVO> findSensorUsedInfo(String siteId, String language);
}
