package com.microwise.blueplanet.service;

import com.microwise.blueplanet.bean.vo.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 区域Service
 *
 * @author zhangpeng
 * @date 2013-1-17
 * @check 2013-02-25 zhangpeng svn:1731
 * @check 2013-04-22 xubaoji svn:262
 * @check 2013-11-14 xiedeng svn:6563
 */
public interface ZoneService {

    /**
     * 更改区域父子关系
     *
     * @param zoneId       区域id
     * @param parentZoneId 调整后的父级区域id，为null时调整为顶级区域
     * @author zhangpeng
     * @date 2013-1-25
     */
    public void changeParent(String zoneId, String parentZoneId);

    /**
     * 判断指定站点的区域下是否拥有该名称区域
     *
     * @param siteId       站点id
     * @param parentZoneId 所属区域id
     * @param zoneName     区域名称
     * @return true 拥有 false 没有
     * @author zhangpeng
     * @date 2013-1-18
     */
    public boolean containsName(String siteId, String parentZoneId,
                                String zoneName);

    /**
     * 查询站点下的所有区域
     *
     * @param siteId 站点id
     * @return List<ZoneVO> 区域vo对象列表
     * @author zhangpeng
     * @date 2013-1-21
     */
    public List<ZoneVO> findZonesBySiteId(String siteId);


    /**
     * 删除区域
     *
     * @param zoneId 区域id
     * @author zhangpeng
     * @date 2013-1-18
     */
    public void deleteZone(String zoneId);

    /**
     * 根据区域id获取所有子孙区域id列表
     *
     * @param zoneId 区域id（不能为null）
     * @return List<String> 区域的所有子孙的id集合. 如果无结果返回大小为零的集合，则不是 null
     * @author zhangpeng
     * @date 2013-2-5
     */
    public List<String> findChildrenIdList(String zoneId);

    /**
     * 获取区域数据版本号
     *
     * @param zoneId 区域id
     * @return 数据版本号
     * @author zhangpeng
     * @date 2013-1-23
     */
    public long findDataVersion(String zoneId);

    /**
     * 查询区域下的位置点列表（带监测指标）
     *
     * @param zoneId 区域ID
     * @return List<LocationVO> 区域下的设备列表
     * @author wang.geng
     * @date 2014-7-18
     */
    public List<LocationVO> findLocations(String zoneId);

    /**
     * 查询区域下的实时数据（位置点存储）
     *
     * @param zoneId 区域id
     * @return List<RealtimeDataVO> 区域及子孙区域实时数据VO对象列表
     * @author wang.geng
     * @date 2014-7-11
     */
    public List<RealtimeDataVO> findRealtimeDataLocation(String zoneId);

    /**
     * 查询区域下的实时数据（位置点存储）（带监测指标筛选条件）
     *
     * @param zoneId               区域id
     * @param sensorPhysicalidList 筛选条件监测指标集合
     * @return List<RealtimeDataVO> 区域及子孙区域实时数据VO对象列表
     * @author wang.geng
     * @date 2014-7-11
     */
    public List<RealtimeDataVO> findRealtimeDataLocation(String zoneId,
                                                         List<Integer> sensorPhysicalidList);

    /**
     * 查询区域一下某一个时刻(在某个近似范围内)某个监测指标各个位置点对应的一条历史数据
     *
     * @param zoneId           区域ID
     * @param sensorPhysicalId 监测指标ID
     * @param time             时刻
     * @param deltaMinute      时间范围(分钟)
     * @return List<LocationOnceDataVO>
     */
    public List<LocationOnceDataVO> findLocationHistoryData(String zoneId, int sensorPhysicalId, Date time, int deltaMinute);

    /**
     * 查询区域下设备所拥有的监测指标集合（激活的）
     *
     * @param zoneId 区域id
     * @return List<SensorinfoVO> 监测指标vo对象列表
     * @author zhangpeng
     * @date 2013-1-18
     */
    public List<SensorinfoVO> findSensorinfo(String zoneId);

    /**
     * 查询区域下设备所有拥有的监测指标集合[位置点]
     *
     * @param zoneId   区域id
     * @param showType 显示类型
     * @return List<SensorinfoVO>
     */
    public List<SensorinfoVO> findSensorInfos(String zoneId, int showType);

    /**
     * 根据id查询区域对象
     *
     * @param zoneId 区域id
     * @return ZoneVO 区域vo对象
     * @author zhangpeng
     * @date 2013-1-18
     */
    public ZoneVO findZoneById(String zoneId);

    /**
     * 查询区域的直接子区域列表
     *
     * @param zoneId 区域id
     * @return List<ZoneVO> 子区域vo对象列表
     * @author zhangpeng
     * @date 2013-04-12
     */
    public List<ZoneVO> findZones(String zoneId);

    /**
     * 根据站点id及父级区域id查询区域列表
     *
     * @param siteId       站点id
     * @param parentZoneId 父区域id
     * @return List<ZoneVO> 区域vo对象列表
     * @author zhangpeng
     * @date 2013-1-18
     */
    public List<ZoneVO> findZoneList(String siteId, String parentZoneId);

    /**
     * 线型查询一个站点下的所有区域
     *
     * @param siteId 站点id
     * @return List<ZoneVO> 区域vo对象列表
     * @author wang.geng
     * @date 2014-6-30
     */
    public List<ZoneVO> findZoneLineList(String siteId);

    /**
     * 判断区域是否为空（是否拥有子区域或设备）
     *
     * @param zoneId 区域id
     * @return true 空 false 不为空
     * @author zhangpeng
     * @date 2013-1-18
     */
    public boolean isEmpty(String zoneId);

    /**
     * 能否在当前区域下用此名称（用于区域修改业务）
     *
     * @param siteId       站点id
     * @param parentZoneId 所属区域id
     * @param zoneId       当前修改区域id
     * @param zoneName     区域名称
     * @return true 能用 false 不能用
     * @author zhangpeng
     * @date 2013-1-18
     */
    public boolean isNameAvailable(String siteId, String parentZoneId,
                                   String zoneId, String zoneName);

    /**
     * 添加区域
     *
     * @param zone 区域vo对象
     * @return String 新添加的区域的id
     * @author zhangpeng
     * @date 2013-1-18
     */
    public String saveZone(ZoneVO zone);

    /**
     * 更新区域详细信息
     *
     * @param zoneId    区域id
     * @param zoneName  区域名称
     * @param planImage 区域实景图路径
     * @author zhangpeng
     * @date 2013-1-18
     */
    public void updateZone(String zoneId, String zoneName, String planImage, int position);

    /**
     * 删除区域在父区域平面地图的坐标
     *
     * @param zoneId   区域ID
     * @param objectId 部署对象的ID（区域/位置点/摄像机）
     */
    public void deletePlanImageCoordinate(String zoneId, String objectId);

    /**
     * 更新区域在父区域平面地图的坐标
     *
     * @param planImageCoordinate 平面图坐标信息
     */
    public void updatePlanImageCoordinate(PlanImageCoordinateVO planImageCoordinate);

    /**
     * 添加区域在父区域平面地图的坐标
     *
     * @param planImageCoordinate 平面图坐标信息
     */
    public void savePlanImageCoordinate(PlanImageCoordinateVO planImageCoordinate);

    /**
     * 获取所有检测指标位置点的近5天的数据
     *
     * @param zoneId 区域id
     * @param siteId 站点id
     * @return List<LocationVO>
     * @author wang.geng
     * @date 2014-7-17
     */
    public List<LocationDataVO> findZoneChartData(String zoneId, String siteId);

    /**
     * 根据检测指标id获取检测指标近5天的数据
     *
     * @param sensorId 检测指标id
     * @param siteId   站点id
     * @return key为时间，value为NodeSensorVO
     * @author wang.geng
     * @date 2014-7-17
     */
    public Map<Long, LocationDataVO> findLocationDataNearlyDayMap(int sensorId, String siteId, String zoneId, Date startDate);

    /**
     * 根据站点id，区域id获取设备实时的检测指标
     *
     * @param siteId 站点id
     * @param zoneId 区域id
     * @author wang.geng
     * @date 2014-7-17
     */
    public Map<LocationDataVO, List<LocationDataVO>> findZoneRealTimeData(String siteId, String zoneId);

    /**
     * 判断在该区域该节点是否存在坐标
     *
     * @param zoneId   区域编号
     * @param objectId id
     * @return boolean
     * @author xu.yuexi
     * @date 2014-2-19
     */
    public boolean isCoordinateExist(String zoneId, String objectId);

    /**
     * 查询坐标
     * TODO 添加测试
     *
     * @param zoneId   查询对象ID(区域/位置点/摄像机)
     * @param objectId 父区域ID
     * @return PlanImageCoordinateVO
     */
    public PlanImageCoordinateVO findPlanImageCoordinate(String zoneId, String objectId);

    /**
     * 查找本区域的父区域id
     *
     * @param id 区域id
     * @return 父区域id
     * @author xuyuexi
     * @date 2014-2-19
     */
    public String findParentId(String id);


    /**
     * 查找按照区域和时间分组后的平均数据
     *
     * @param siteId
     * @param sensorPhysicalId
     * @param begin
     * @param end
     * @return
     */
    public List<ZoneAvgDataVO> findZoneAvgData(String siteId, int sensorPhysicalId, Date begin, Date end);


    /**
     * <pre>
     * 获得要导出的历史数据报表的文件名
     * </pre>
     *
     * @param zoneId
     * @param startTime
     * @param endTime
     * @return
     */
    String getFileName(String zoneId, Date startTime, Date endTime);

}
