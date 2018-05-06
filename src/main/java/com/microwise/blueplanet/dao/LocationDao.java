package com.microwise.blueplanet.dao;

import com.microwise.blueplanet.bean.po.*;
import com.microwise.blueplanet.bean.vo.*;
import com.microwise.orion.bean.Relic;
import com.microwise.phoenix.bean.po.LocationDate;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 位置点 Dao
 *
 * @author li.jianfei
 * @date 2014-06-20
 */
public interface LocationDao {

    /**
     * 根据位置点ID查询位置点信息
     *
     * @param locationId 位置点ID
     * @return 位置点
     */
    public LocationVO findLocationById(String locationId);

    /**
     * 查询位置点集合
     *
     * @param zoneId   区域ID
     * @param sensorId 监测指标ID
     * @param begin    开始时间
     * @param end      结束时间
     *                 开始时间、结束时间用于限制位置点的创建时间
     * @return 位置点集合
     */
    public List<LocationPO> findLocations(String zoneId, int sensorId, Date begin, Date end);

    /**
     * 查询位置点指定监测指标历史数据
     *
     * @param locationId 位置点ID
     * @param sensorId   监测指标ID
     * @param begin      开始时间
     * @param end        结束时间
     * @return 监测指标数据集合
     */
    public List<LocationDataPO> findLocationSensorData(String locationId, int sensorId, Date begin, Date end);


    /**
     * 根据siteId和locationName查询站点下所有位置点信息
     *
     * @param siteId 站点ID
     * @return 位置点集合
     */
    public List<LocationVO> findLocationsBySiteIdAndLocationName(String siteId, String locationName);

    /**
     * 精确查询位置点
     *
     * @param siteId
     * @param locationName
     * @return
     */
    public LocationVO findOneLocationsBySiteIdAndLocationName(String siteId, String locationName);


    /**
     * 查询区域下的位置点
     *
     * @param zoneId 区域ID
     * @return 位置点集合
     */
    public List<LocationVO> findLocationsByZoneId(String zoneId);

    /**
     * 分页查询站点下的所有位置点信息
     *
     * @param siteId 站点ID
     * @return 位置点集合
     */
    public List<LocationVO> findLocationsBySiteId(String siteId, int page, int pageSize);

    /**
     * 根据位置点名称和区域Id查询位置点，全部为空查询全部
     *
     * @param locationName
     * @param zoneId
     * @param siteId
     * @return
     */
    public List<LocationVO> findLocationByNameAndZone(String locationName, String zoneId, String siteId, int page, int pageSize);

    /**
     * 分页根据位置点名称和区域Id查询位置点，全部为空查询全部
     *
     * @param locationName
     * @param zoneId
     * @param siteId
     * @return
     */
    public int findLocationByNameAndZoneCount(String locationName, String zoneId, String siteId);

    /**
     * 查询站点下所有位置点信息数目
     *
     * @param siteId 站点ID
     * @return 位置点集合
     */
    public int findLocationListCount(String siteId);

    /**
     * 查询位置点包含的所有监测指标
     *
     * @param locationId 位置点ID
     * @return 位置点监测指标集合
     */
    public List<Integer> findLocationSensorIdList(String locationId);

    /**
     * 查询设备包含的所有监测指标
     *
     * @param locationId 位置点ID
     * @return 位置点监测指标集合
     */
    public List<SensorinfoVO> findSensorInfoList(String locationId);


    /**
     * 获取站点下最大的 位置点编号
     *
     * @param siteId 站点编号
     * @return 位置点编号
     */
    public String getMaxLocationId(String siteId);

    /**
     * 添加位置点信息
     *
     * @param locationPO 位置点信息
     */
    public void addLocation(LocationPO locationPO);

    /**
     * 添加位置点文物绑定信息
     *
     * @param relicId    relicId
     * @param locationId 位置点id
     * @author liuzhu
     * @date 2016-7-18
     */
    public void addLocationRelic(int relicId, String locationId);

    /**
     * 根据位置点id查找已绑定文物
     *
     * @param locationId 位置点id
     * @return 文物list
     * @author liuzhu
     * @date 2016-7-18
     */
    public List<Relic> findRelics(String locationId);

    /**
     * 根据m_location_relic表的id删除绑定关系
     *
     * @param id m_location_relic表的id
     * @author liuzhu
     * @date 2016-7-18
     */
    public void deleteLocationRelic(int id);

    /**
     * 根据id删除绑定关系
     *
     * @param locationId 位置点id
     * @author liuzhu
     * @date 2016-7-18
     */
    public void deleteLocationRelic(String locationId);

    /**
     * 添加位置点历史信息
     *
     * @param locationPO 位置点信息
     */
    public void addLocationHistory(LocationPO locationPO);

    /**
     * 删除 位置点信息
     *
     * @param locationId 位置点编号
     */
    public void deleteLocation(String locationId);

    /**
     * 删除 位置点历史信息
     *
     * @param locationId 位置点编号
     */
    public void deleteLocationHistory(String locationId);

    /**
     * 修改 位置点历史信息结束时间
     *
     * @param locationId 位置点信息
     * @param nodeId     设备编号
     */
    public void updateLocationHistoryEndTime(String locationId, String nodeId);

    /**
     * 修改 位置点信息
     *
     * @param locationPO 位置点信息
     */
    public void updateLocation(LocationPO locationPO);

    /**
     * 创建位置点表
     *
     * @param locationId 位置点
     */
    public void createLocationTable(String locationId);

    /**
     * 根据位置点id查找位置点绑定历史
     *
     * @param locationId 位置点id
     * @return 位置点绑定历史vo
     * @author liuzhu
     * @date 2014-6-26
     */
    public List<LocationHistoryVO> findLocationHistoryList(String locationId);

    /**
     * 查询位置点最近12包数据的时间戳
     * <p/>
     * /**
     * 查看位置点名称是否存在
     *
     * @param locationName 位置点名称
     * @return
     */
    public Boolean isExistLocationName(String locationName, String siteId);

    /**
     * 查看位置点名称是否存在
     *
     * @param locationId   位置点编号
     * @param locationName 位置点名称
     * @return
     */
    public Boolean isExistLocationName(String locationId, String locationName, String siteId);

    /**
     * 部署位置点
     *
     * @param locationIds 位置点编号
     */
    public void deployLocation(String[] locationIds, String zoneId);

    /**
     * 解除部署位置点
     *
     * @param locationId 位置点编号
     */
    public void unDeployLocation(String locationId);

    /**
     * 删除位置点表
     *
     * @param locationId 位置点编号
     */
    public void deleteLocationTable(String locationId);

    /**
     * 删除位置点的坐标信息
     *
     * @param locationId
     */
    public void deleteCoordinate(String locationId);

    /**
     * 删除位置点实时数据
     *
     * @param locationId 位置点编号
     */
    public void deleteLocationSensor(String locationId);

    /**
     * 删除位置点kdj数据
     *
     * @param locationId 位置点编号
     */
    public void deleteLocationStock(String locationId);

    /**
     * 查询位置点历史表中最近N包
     *
     * @param locationId 位置点编号
     * @param dataCount  要查询的数量
     * @return List<RecentDataVO> 近期数据VO 对象 此处未封装设备监测指标数据
     * @author liuzhu
     * @date 2014-6-30
     */
    public List<RecentDataVO> findRecentDataList(String locationId, Integer dataCount);


    /**
     * 查询设备历史表中一个时间点之后的 设备信息
     *
     * @param locationId 位置点编号
     * @param startTime  时间点
     * @return List<RealtimeDataVO> 实时数据VO 对象 此处未封装设备监测指标数据
     * @author liuzhu
     * @date 2014-7-1
     */
    public List<RecentDataVO> findRecentDataList(String locationId, Date startTime);

    /**
     * 查询位置点历史数据，某一位置点最近的一个设备
     *
     * @param locationId 位置点ID
     * @return 历史位置点绑定实体
     * @author wang.geng
     * @date 2014-7-10
     */
    public LocationHistoryPO findRecentlyLocation(String locationId);

    /**
     * 获得位置点监测指标实时数据
     *
     * @param locationId           位置点id
     * @param sensorPhysicalidList 筛选条件监测指标集合
     * @return List<SensorinfoVO> 监测指标数据信息列表
     * @author wang.geng
     * @date 2014-7-10
     */
    public List<LocationDataVO> findLocationSensor(String locationId, List<Integer> sensorPhysicalidList);

    /**
     * 查询某监测指标的位置点的历史数据，比时间参数前一包的数据
     *
     * @param locationId 位置点ID
     * @param sensorId   监测指标ID
     * @param stamp      时间参数
     * @return 位置点时间参数数据
     */
    public LocationDataVO findLocationSensorPre(String locationId, int sensorId, Date stamp);

    /**
     * 查询某监测指标的位置点的历史数据，比时间参数后一包的数据
     *
     * @param locationId 位置点ID
     * @param sensorId   监测指标ID
     * @param stamp      时间参数
     * @return 位置点时间参数数据
     */
    public LocationDataVO findLocationSensorNext(String locationId, int sensorId, Date stamp);

    /**
     * 查询设备历史表中一段时间的之后的 设备信息
     *
     * @param locationId 位置点编号
     * @param startTime  时间点
     * @return List<RealtimeDataVO> 实时数据VO 对象 此处未封装设备监测指标数据
     * @author xuyuexi
     * @date 2014-7-7
     */
    public List<RecentDataVO> findRecentDataList(String locationId, Date startTime, Date endTime, int page, int pageSize);

    /**
     * 查询设备历史表中一段时间的之后的 位置点信息
     *
     * @param locationId 位置点编号
     * @param startTime  时间点
     * @return List<RealtimeDataVO> 实时数据VO 对象 此处未封装设备监测指标数据
     * @author xuyuexi
     * @date 2014-8-19
     */
    public List<RecentDataVO> findHistoryDataList(String locationId, Date startTime, Date endTime, int page, int pageSize);

    /**
     * 查询设备历史表中一段时间的之后的 设备信息
     *
     * @param locationId 位置点编号
     * @param startTime  时间点
     * @return List<RealtimeDataVO> 实时数据VO 对象 此处未封装设备监测指标数据
     * @author xuyuexi
     * @date 2014-8-12
     */
    public List<RecentDataVO> findRecentDataList(String locationId, Date startTime, Date endTime);

    /**
     * 查询设备历史表中一段时间的之后的 设备信息数目
     *
     * @param locationId 位置点编号
     * @param startTime  时间点
     * @author xuyuexi
     * @date 2014-7-7
     */
    public int findRecentDataListCount(String locationId, Date startTime, Date endTime);

    /**
     * 获得位置点的历史数据
     *
     * @param locationId 设备编号
     * @param date       时间
     * @return List<SensorinfoVO> 监测指标数据信息列表
     * @author liuzhu
     * @date 2016-5-5
     */
    public List<LocationDataVO> findLocationHistoryData(String locationId, Date date);

    /**
     * 查询某个位置点某个监测指标在指定时刻附近的一条数据
     *
     * @param locationId
     * @param sensorPhysicalId
     * @param time
     * @param deltaMinute
     * @return
     */
    public LocationDataVO findHistoryData(String locationId, int sensorPhysicalId, Date time, int deltaMinute);

    /**
     * 某设备的位置点实时数据
     *
     * @param locationId 位置点ID
     * @return 实时数据实体
     * @author wang.geng
     * @date 2014-8-7
     */
    public RealtimeDataVO findLocationData(String locationId);

    /**
     * 根据站点编号查询未绑定的设备信息
     *
     * @param siteId
     * @return
     */
    public List<DeviceVO> findUnbindDevices(String siteId);

    /**
     * 获得要生成的excel文件有数据的年份
     *
     * @param locationId 位置点Id
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return Map
     * @author 许月希
     * @date 2014-7-8
     */
    public List<Integer> getExcelSum(String locationId, Date startTime,
                                     Date endTime);

    /**
     * 查询最大和最小时间（为查询设备历史数据服务）
     *
     * @param locationId 设备编号
     * @param startTime  开始时间 （可为null）
     * @param endTime    结束时间 （可为null）
     * @param index      当前页码
     * @param pageSize   分页单位
     * @return Map<String, Date> key:常量，对应最大最小时间（value）
     * @author 许月希
     * @date 2014-7-8
     */
    public Map<String, Date> findMaxAndMinTime(String locationId, Date startTime,
                                               Date endTime, Integer index, Integer pageSize);

    /**
     * 查询均峰值实体
     *
     * @param locationId 位置点id
     * @param startDate  起始时间
     * @param endDate    结束时间
     * @return 均峰值实体List
     * @author liuzhu
     * @date 2014-7-9
     */
    public List<AvgdataPO> findAverageAndPeakValue(String locationId, Date startDate, Date endDate);

    /**
     * 根据位置点id查位置点监测指标
     *
     * @param locationId 位置点id
     * @return List<LocationAvgdataVO>
     * @author liuzhu
     * @date 2014-7-11
     */
    public List<LocationAvgdataVO> findLocationSensorInfo(String locationId);

    /**
     * 查询均峰值平均数据
     *
     * @param sensorPhysicalId 监测指标ID
     * @param startDate        起始时间
     * @param endDate          结束时间
     * @param locationId       设备ID
     * @return List<Map<String, Object>>
     * @author xuyuexi
     * @date 2014-7-11
     */
    public List<Map<String, Object>> findAvgData(int sensorPhysicalId, Date startDate, Date endDate, String locationId);

    /**
     * 添加文件上传记录
     */
    public void addUploadRecord(DataFilePO dataFile);

    /**
     * 修改文件上传记录
     *
     * @param dataFile 文件
     */
    public void updateUploadRecord(DataFilePO dataFile);

    /**
     * 是否存在文件记录
     *
     * @param filename 文件名称
     * @return
     */
    public boolean isExistFileRecord(String filename);

    /**
     * 根据设备ID查询位置点信息
     *
     * @param nodeId 设备ID
     * @return 位置点实体
     * @author wang.geng
     * @date 2014-8-7
     */
    public LocationVO findLocationByNodeId(String nodeId);

    /**
     * 根据检测指标查询位置点id
     *
     * @param sensorPhysicalId 监测指标id
     * @param siteId           站点编号
     * @return 位置点id
     */
    public String findLocationIdBySensorIdAndSiteId(int sensorPhysicalId, String siteId);

    /**
     * 根据检测指标查询位置点id
     *
     * @param sensorPhysicalId 监测指标id
     * @param zoneId           区域id
     * @return 位置点id
     * @author 王耕
     * @date 2014-12-30
     */
    public String findLocationIdbySensorIdAndZoneId(int sensorPhysicalId, String zoneId);

    /**
     * 根据位置点id查设备
     *
     * @param locationId 位置点id
     * @return deviceVO
     * @author liuzhu
     * @date 2015-7-8
     */
    public DeviceVO findDeviceByLocationId(String locationId);

    /**
     * 查询监测指标小时统计信息
     *
     * @param locationId 位置点id
     * @param sensorId   监测指标id
     * @param start      开始时间
     * @param end        结束时间
     * @return 小时统计数据
     * @author 王耕
     * @date 2015-8-27
     */
    public List<HourAvgDataPO> findHourAvgDatas(String locationId, int sensorId, Date start, Date end);

    /**
     * 查询某年的月平均数据
     *
     * @param year 年
     * @return 月平均数据
     */
    public List<YearAvgDataVO> findYearAvgData(String year, int sensorId, String locationId);

    /**
     * 查询站点下含有某监测指标的所有位置点的集合
     *
     * @param siteId   站点编号
     * @param sensorId 位置点编号
     * @return 位置点集合
     * @author 王耕
     * @date 2015-9-2
     */
    public List<LocationVO> findLocationsBySensorIdAndSiteId(String siteId, int sensorId);

    /**
     * 查询区域下位置点的监测指标数据
     * @author chen yao fei
     * @date 2016-10-27
     */
    public List<LocationDate> findLocationDataBySensor(String zoneId, int sensorId, Date startDate, Date endDate);

    /**
     * 获取位置点监测指标值集合
     * @author chen yao fei
     * @date 2016-10-27
     */
    public List<Double> findSensorValues(String locationId, int sensorId, Date startDate, Date endDate);

    /**
     * 查询站点下的所有位置点
     *
     * @param siteId
     * @return
     */
    List<LocationVO> findLocationsBySiteId(String siteId);
}
