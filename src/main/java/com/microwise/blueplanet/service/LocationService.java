package com.microwise.blueplanet.service;

import com.microwise.blueplanet.bean.po.AvgdataPO;
import com.microwise.blueplanet.bean.po.LocationDataPO;
import com.microwise.blueplanet.bean.po.LocationPO;
import com.microwise.blueplanet.bean.vo.*;
import com.microwise.orion.bean.Relic;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 位置点 Service
 *
 * @author li.jianfei
 * @date 2014-06-23
 */
public interface LocationService {

    /**
     * 根据位置点ID查询位置点信息
     *
     * @param locationId 位置点ID
     * @return 位置点
     */
    public LocationVO findLocationById(String locationId);

    /**
     * 查询位置点信息(携带位置点绑定文物信息)
     *
     * @param locationId 位置点Id
     * @return 位置点
     */
    public LocationVO findLocationWithRelics(String locationId);

    /**
     * 根据siteId和locationName查询站点下所有位置点信息(模糊查询)
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
     * 查询区域下的位置点(部署在指定区域下的位置点)
     *
     * @param zoneId 区域ID
     * @return 位置点集合
     */
    public List<LocationVO> findLocationsByZoneId(String zoneId, boolean withRelic);

    /**
     * 分页查询站点下所有位置点信息
     *
     * @param siteId 站点ID
     * @return 位置点集合
     */
    public List<LocationVO> findLocationsBySiteId(String siteId, int page, int pageSize);

    /**
     * 查询站点下所有位置点信息数目
     *
     * @param siteId 站点ID
     * @return 位置点集合
     */
    public int findLocationListCount(String siteId);

    /**
     * 分页根据位置点名称和区域Id查询位置点，全部为空查询全部
     *
     * @param locationName
     * @param zoneId
     * @param siteId
     * @return
     */
    public List<LocationVO> findLocationByNameAndZone(String locationName, String zoneId, String siteId, int page, int pageSize);

    /**
     * 分页根据位置点名称和区域Id查询位置点，全部为空查询全部
     * TODO 优化函数命名  @li.jianfei  2014-07-03
     *
     * @param locationName
     * @param zoneId
     * @param siteId
     * @return
     */
    public int findLocationByNameAndZoneCount(String locationName, String zoneId, String siteId);

    /**
     * 查询设备包含的所有监测指标
     *
     * @param locationId 位置点ID
     * @return 位置点监测指标集合
     */
    public List<SensorinfoVO> findSensorInfoList(String locationId);

    /**
     * 获取新的位置点编号
     *
     * @param siteId 站点编号
     * @return 位置点编号
     */
    public String getNewLocationId(String siteId);

    /**
     * 位置点名称是否存在
     * TODO 该方法不需要重载，只需要保留一个
     * TODO 为什么不需要重载?
     *
     * @param locationName 位置点名称
     */
    public Boolean isExistLocationName(String locationName, String siteId);

    /**
     * 位置点名称是否存在
     *
     * @param locationId   位置点编号
     * @param locationName 位置点名称
     */
    public Boolean isExistLocationName(String locationId, String locationName, String siteId);

    /**
     * 添加位置点信息
     *
     * @param locationPO 位置点信息
     */
    public void addLocation(LocationPO locationPO) throws Exception;


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
     * 根据id删除绑定关系
     *
     * @param id m_location_relic表的id
     * @author liuzhu
     * @date 2016-7-18
     */
    public void deleteLocationRelic(int id);

    /**
     * 删除位置点编号
     *
     * @param locationId 位置点编号
     */
    public void deleteLocation(String locationId) throws Exception;


    /**
     * 删除位置点数据表
     *
     * @param locationId 位置点ID
     */
    public void dropTable(String locationId);

    /**
     * 修改位置点信息
     *
     * @param locationPO
     * @return
     * @throws Exception
     */
    public List<String> updateLocation(LocationPO locationPO);

    /**
     * 部署位置点
     *
     * @param locationIds 位置点编号
     */
    public void deployLocation(String[] locationIds, String zoneId);

    /**
     * 解除部署位置点
     *
     * @param locationId 位置点
     */
    public void unDeployLocation(String locationId);

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
     * 通过位置点id获得设备最近N 包数据
     *
     * @param locationId 位置点id
     * @param dataCount  要查询的数据数量
     * @return 设备的实时数据vo对象集合
     * @author liuzhu
     * @date 2014-6-30
     */
    public List<RecentDataVO> findRecentDataList(String locationId, Integer dataCount, Date startTime);

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
     * 查询某监测指标的位置点的历史数据，距离时间参数最近的一包数据
     *
     * @param locationId 位置点ID
     * @param sensorId   监测指标ID
     * @param stamp      时间参数
     * @return 位置点时间参数数据
     */
    public LocationDataVO findLocationSensorNearBy(String locationId, int sensorId, Date stamp);


    /**
     * 通知中间件位置点发生变化
     *
     * @param deviceId 设备编号
     */
    public void notifyLocationChanged(String deviceId) throws Exception;

    /**
     * 通知中间件解析木卫一数据文件
     *
     * @param locationId 位置点编号
     * @throws Exception
     */
    public void notifyAnalysisDataFile(String locationId) throws Exception;

    /**
     * 获取设备的类型集合
     *
     * @param devices
     * @return
     */
    public List<Integer> getDeviceTypes(List<DeviceVO> devices);

    /**
     * 根据站点编号查询未绑定的设备信息
     *
     * @param siteId
     * @return
     */
    public List<DeviceVO> findUnbindDevices(String siteId);


    /**
     * 通过位置点id获得设备最近一段时间的数据
     *
     * @param locationId 位置点id
     * @param startDate  要查询的数据数量
     * @param endDate    要查询的数据数量
     * @return 设备的实时数据vo对象集合
     * @author xuyuexi
     * @date 2014-8-12
     */
    public List<RecentDataVO> findRecentDataList(String locationId, Date startDate, Date endDate);

    /**
     * 通过位置点id获得设备最近一段时间的数据
     *
     * @param locationId 位置点id
     * @param startDate  要查询的数据数量
     * @param endDate    要查询的数据数量
     * @return 设备的实时数据vo对象集合
     * @author xuyuexi
     * @date 2014-7-7
     */
    public List<RecentDataVO> findRecentDataList(String locationId, Date startDate, Date endDate, int page, int pageSize);

    /**
     * 通过位置点id获得设备一段时间的历史数据
     *
     * @param locationId 位置点id
     * @param startDate  要查询的数据数量
     * @param endDate    要查询的数据数量
     * @return 设备的历史数据vo对象集合
     * @author xuyuexi
     * @date 2014-8-19
     */
    public List<RecentDataVO> findHistoryDataList(String locationId, Date startDate, Date endDate, int page, int pageSize);

    /**
     * 通过位置点id获得设备最近一段时间的数据数目
     *
     * @param locationId 位置点id
     * @param startDate  要查询的数据数量
     * @param endDate    要查询的数据数量
     * @return 设备的实时数据vo对象集合
     * @author xuyuexi
     * @date 2014-7-7
     */
    public int findRecentDataListCount(String locationId, Date startDate, Date endDate);

    /**
     * <pre>
     * 获得要导出的历史数据报表的文件名
     * 如果是一个excel则返回excel文件名 如果是多个excel文件则为zip包的文件名
     * </pre>
     *
     * @param locationId 位置点Id
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return fileName string的 文件名称
     * @author 许月希
     * @date 2014-07-08
     */
    public String getFileName(String locationId, Date startTime, Date endTime);

    /**
     * 导出一个位置点的历史数据到excel报表中
     *
     * @param locationId   位置点Id
     * @param startTime    开始时间
     * @param outputStream 输出流
     * @param endTime      结束时间
     * @throws Exception
     * @author 许月希
     * @date 2014-07-08
     */
    public String exportHistoryData(String locationId, Date startTime,
                                  Date endTime, OutputStream outputStream) throws Exception;

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
     * 组装数据
     *
     * @param locationId 位置点id
     * @return List<DeviceStatisticsVO>
     * @author liuzhu
     * @date 2014-7-9
     */
    public List<LocationAvgdataVO> assembleAverageAndPeakValue(String locationId, List<AvgdataPO> avgdataList);

    /**
     * 添加文件上传记录
     *
     * @param filename 文件名称
     */
    public void addUploadRecord(String filename);

    /**
     * 查询位置点的实时数据
     *
     * @param locationId 位置点ID
     * @return 位置点的实时数据实体
     * @author wang.geng
     * @date 2014-8-7
     */
    public RealtimeDataVO findLocationData(String locationId);

    /**
     * 查询区域下所有位置点指定监测指标历史数据
     *
     * @param zoneId   区域ID
     * @param sensorId 监测指标ID
     * @param begin    开始时间
     * @param end      结束时间
     * @return 位置点监测指标历史数据集合
     */
    public Map<LocationPO, List<LocationDataPO>> findLocationSensorData(String zoneId, int sensorId, Date begin, Date end);

    /**
     * 根据设备ID查询位置点，若没有绑定位置点，则返回NULL
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
     * 根据监测指标查询位置点id
     *
     * @param sensorPhysicalId 监测指标id
     * @param zoneId           区域id
     * @return 位置点id
     * @author 王耕
     * @date 2014-12-30
     */
    public String findLocationIdBySensorIdAndZoneId(int sensorPhysicalId, String zoneId);


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
     * 查询水流量相关统计数据
     *
     * @param date     日期
     * @param sensorId 监测指标
     * @param dateType 日期类型
     * @return 图表数据
     * <p>
     * Map<String,Object>为页面图表组织数据
     * <p>
     * 键值对应为:
     * name       -- 位置点名称
     * dateType   -- 日期类型
     * locationId -- 位置点ID
     * title      -- 页面图表标题
     * data       -- 图表数据
     * avgValue   -- 平均值
     * waveValue  -- 波动值
     */
    public List<Map<String, Object>> findWaterFlowChartData(String siteId, Date date, int sensorId, int dateType);

    /**
     * 查询站点下含有某监测指标的所有位置点的集合
     *
     * @param siteId   站点编号
     * @param sensorId 监测指标id
     * @return 位置点集合
     * @author 王耕
     * @date 2015-9-2
     */
    public List<LocationVO> findLocationsBySensorIdAndSiteId(String siteId, int sensorId);

    /**
     * 添加文物绑定位置点阈值融合文物阈值
     *
     * @param locationId 位置点Id
     * @param relicIds   文物Id集合
     * @author 陈耀飞
     * @date 2016-07-25
     */
    public void addTextureThreshold(String locationId, String[] relicIds);

    /**
     * 修改绑定位置点阈值融合文物阈值
     *
     * @param locationId 位置点Id
     * @param relicIds   文物Id集合
     * @author 陈耀飞
     * @date 2016-07-25
     */
    public void editTextureThreshold(String locationId, String[] relicIds);

    /**
     * 查询站点下所有位置点
     *
     * @param siteId
     * @return
     */
    List<LocationVO> findLocationsBySiteId(String siteId);
}
