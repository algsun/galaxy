package com.microwise.blueplanet.dao;

import com.microwise.blueplanet.bean.po.AvgdataPO;
import com.microwise.blueplanet.bean.vo.*;

import java.util.Date;
import java.util.List;

/**
 * 区域Dao
 *
 * @author zhangpeng
 * @date 2013-1-21
 * @check 2013-02-25 zhangpeng svn:1731
 * @check 2013-04-22 xubaoji svn:2614
 * @check 2013-11-14 xiedeng svn:6603
 */
public interface ZoneDao {

    /**
     * 查询站点下的所有区域
     *
     * @param siteId 站点id
     * @return List<ZoneVO> 区域vo对象列表
     */
    public List<ZoneVO> findZonesBySiteId(String siteId);

    /**
     * 删除区域
     *
     * @param zoneId 区域id
     * @return void
     * @author zhangpeng
     * @date 2013-1-25
     */
    public void deleteZone(String zoneId);

    /**
     * 根据区域id获取所有子孙区域id列表
     *
     * @param zoneId 区域id（不能为null）
     * @return List<String> 区域的所有子孙的id集合
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
     * @date 2013-1-18
     */
    public long findDataVersion(String zoneId);

    /**
     * 根据父区域id及子区域名称查询子区域
     *
     * @param siteId       站点id
     * @param parentZoneId 所属区域id
     * @param zoneName     区域名称
     * @return true 拥有 false 没有
     * @author zhangpeng
     * @date 2013-1-18
     */
    public ZoneVO findZoneByName(String siteId, String parentZoneId,
                                 String zoneName);

    /**
     * 查询区域下设备所拥有的监测指标集合（激活的）
     *
     * @param zoneIdList 及子孙区域id列表
     * @return List<SensorinfoVO> 监测指标vo对象列表
     * @author zhangpeng
     * @date 2013-1-18
     */
    public List<SensorinfoVO> findSensorinfo(List<String> zoneIdList);

    /**
     * 根据id查询区域
     *
     * @param zoneId 区域id
     * @return ZoneVO 区域vo对象
     * @author zhangpeng
     * @date 2013-1-24
     */
    public ZoneVO findZoneById(String zoneId);

    /**
     * 根据站点id及父区域id查询直接子区域
     *
     * @param siteId       站点id
     * @param parentZoneId 父区域id
     * @return List<ZoneVO> 区域的直接子区域对象vo对象列表
     * @author zhangpeng
     * @date 2013-1-18
     */
    public List<ZoneVO> findZoneList(String siteId, String parentZoneId);

    /**
     * 查询区域实时数据基本信息（位置点存储）
     *
     * @param zoneIds              区域编号 列表
     * @param sensorPhysicalidList 筛选条件监测指标集合
     * @return List<RealtimeDataVO> 实时数据VO对象列表，此处未封装设备监测指标数据
     * @author wang.geng
     * @date 2014-7-11
     */
    public List<RealtimeDataVO> findLocationInfo(List<String> zoneIds,
                                                 List<Integer> sensorPhysicalidList);

    /**
     * 添加区域
     *
     * @param zone 区域vo对象
     * @author zhangpeng
     * @date 2013-1-18
     */
    public void saveZone(ZoneVO zone);

    /**
     * 查询区域的直接子区域列表
     *
     * @param zoneId 区域id
     * @return List<ZoneVO> 子区域vo对象列表
     * @author zhangpeng
     * @date 2013-1-18
     */
    public List<ZoneVO> findZones(String zoneId);

    /**
     * 线型查询一个站点下的所有区域
     *
     * @param siteId 站点编号
     * @return List<ZoneVO> 区域列表
     * @author wang.geng
     * @date 2014-6-30
     */
    public List<ZoneVO> findZoneLineList(String siteId);

    /**
     * 更新区域
     *
     * @param zone 区域vo对象
     * @author zhangpeng
     * @date 2013-1-28
     */
    public void updateZone(ZoneVO zone);

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


    void deleteAllCoordinates(String id, int flag);

    /**
     * 添加区域在父区域平面地图的坐标
     *
     * @param planImageCoordinate 平面图坐标信息
     */
    public void savePlanImageCoordinate(PlanImageCoordinateVO planImageCoordinate);

    /**
     * 判断在该区域该节点是否存在坐标
     *
     * @param id
     * @param parentId
     * @return
     * @author xu.yuexi
     * @date 2014-2-19
     */
    public boolean isCoordinateExist(String id, String parentId);

    /**
     * 根据设备id查询坐标
     *
     * @param zoneId
     * @param objectId
     * @return
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
     * 根据检测指标id、开始时间、站点id 获取最大，最小值(位置点)
     *
     * @param sensorPhysicalid 检测指标id
     * @param startDate        开始时间
     * @param siteId           站点id
     * @return LocationDataVO 集合
     * @author wang.geng
     * @date 2014-7-17
     */
    public List<LocationDataVO> findLocationMaxMinValue(int sensorPhysicalid, Date startDate, String siteId, String zoneId);

    /**
     * 根据检测指标id、开始时间、结束时间、站点id 获取最大，最小值(位置点)
     *
     * @param sensorPhysicalid 检测指标id
     * @param start            开始时间
     * @param end              结束时间
     * @param zoneId           站点id
     * @return LocationDataVO 集合
     * @author chen.yaofei
     * @date 2016-10-21
     */
    public List<AvgdataPO> findLocationMaxMinValue(int sensorPhysicalid, String zoneId, Date start, Date end);

    /**
     * 根据roomId，开始时间，结束时间获取该区域下监测指标值（位置点）
     *
     * @param zoneId    区域id
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return list<LocationDataVO>
     * @author wang.geng
     * @date 2014-7-17
     */
    public List<LocationDataVO> findGeneralLocationDataByRoomId(String zoneId, Date startDate, Date endDate);

    /**
     * 获取一个位置点的检测指标（当天）
     *
     * @param locationId 位置点id
     * @param startDate  起始时间
     * @param endDate    结束时间
     * @return List<LocationDataVO>
     * @author wang.geng
     * @date 2014-7-18
     */
    public List<LocationDataVO> findLocationDataByLocationId(String locationId, Date startDate, Date endDate);


    /**
     * 查询指定时间范围内区域某监测指标均值
     *
     * @param sensorPhysicalId
     * @param siteId
     * @param begin
     * @param end
     * @return
     */
    public List<ZoneAvgDataVO> findZoneAvgData(String siteId, int sensorPhysicalId, Date begin, Date end);
}
