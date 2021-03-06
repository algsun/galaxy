package com.microwise.biela.dao;

import com.microwise.biela.bean.po.AreaCodePO;
import com.microwise.biela.bean.po.LogicGroupPO;
import com.microwise.biela.bean.po.SensorInfoPO;
import com.microwise.biela.bean.vo.CustomizeVO;
import com.microwise.biela.bean.vo.LocationInfoVO;
import com.microwise.biela.bean.vo.NodeSensorInfoVO;
import com.microwise.blueplanet.bean.po.ZonePO;
import com.microwise.blueplanet.bean.vo.LocationVO;

import java.util.Date;
import java.util.List;

/**
 * 站点地图dao接口
 *
 * @author liuzhu
 * @date 14-1-2
 * @check @wang.geng 2014-1-17 #7710
 */
public interface MapIndexDao {

    /**
     * 获取站点检测指标平均值
     *
     * @return 平均值对象List
     * @author wang.geng
     * @date 2014-1-11
     */
    public List<LocationInfoVO> findGeneralLocationInfoBySite(String siteId);

    /**
     * 根据站点id获取地区名称（递归找到到省级名称）
     *
     * @param siteId
     * @return 地区编码
     * @author liuzhu
     * @date 2013-1-3
     */
    public Integer findAreaNameBySiteId(String siteId);

    /**
     * 根据areaCode获取AreaCodePO
     *
     * @param areaCode 区域编码
     * @return AreaCodePO po类
     * @author liuzhu
     * @date 2013-1-3
     */
    public AreaCodePO findAreaCodePOByAreaCode(int areaCode);

    /**
     * 获取站点下所有设备id
     *
     * @param siteId 站点id
     * @return deviceId 的list集合
     * @author liuzhu
     * @date 2014-1-3
     */
    public List<String> findLocationIdsBySite(String siteId);

    /**
     * 根据设备id,监测指标id，开始时间，结束时间获取方差
     *
     * @param locationId 位置点id
     * @param sensorId   监测指标id
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @return 方差
     * @author liuzhu
     * @date 2013-1-3
     */
    public Float findVariances(String locationId, int sensorId, Date startDate, Date endDate);

    /**
     * 根据站点获取检测指标
     *
     * @param siteId
     * @return sensorList 监测指标集合
     * @author liuzhu
     * @date 2014-1-7
     */
    public List<SensorInfoPO> findSensorInfo(String siteId);

    /**
     * 根据站点名称获取站点组VO
     *
     * @param siteId 站点id
     * @return 站点VO
     * @author liuzhu
     * @date 2013-1-9
     */
    public LogicGroupPO findLogicGroupBySiteId(String siteId);

    /**
     * 根据区域id获取区域PO
     *
     * @param zoneId 区域id
     * @return 区域PO
     * @author liuzhu
     * @date 2014-1-9
     */
    public ZonePO findZoneById(String zoneId);

    /**
     * 保存定制信息
     *
     * @param siteId          站点id
     * @param locationId      位置点id
     * @param sensorId        监测指标id
     * @param customizeRemark 定制备注
     * @return void
     * @author liuzhu
     * @date 2014-1-9
     */
    public void saveCustomize(String siteId, String locationId, Integer sensorId, String customizeRemark);

    /**
     * 根据siteId获取定制信息
     *
     * @param siteId 站点id
     * @return CustomizeVO 集合
     * @author liuzhu
     * @date 2014-1-10
     */
    public List<CustomizeVO> findCustomizeVOList(String siteId);

    /**
     * 根据监测指标id获取监测指标name
     *
     * @param sensorId 监测指标id
     * @return sensorName 监测指标 name
     * @author liuzhu
     * @date 2014-1-10
     */
    public String findSensorNameBySensorId(Integer sensorId);

    /**
     * 根据id删除一个定制信息
     *
     * @param id
     * @author liuzhu
     * @date 2014-1-10
     */
    public void deleteCustomizeById(Integer id);

    /**
     * 验证该监测指标是否已经定制
     *
     * @param siteId     站点id
     * @param locationId 位置点id
     * @param sensorId   监测指标id
     * @return Integer 数量 1,0
     * @author liuzhu
     * @date 2014-1-11
     */
    public Integer verifyCustomize(String siteId, String locationId, Integer sensorId);

    /**
     * ajax查看定制监测指标的数量
     *
     * @param siteId 站点id
     * @return 数量
     * @author liuzhu
     * @date 2014-1-11
     */
    public Integer customizeCount(String siteId);

    /**
     * 根据设备id、监测指标id查找NodeSensorInfoVO
     *
     * @param locationId 位置点id
     * @param sensorId   监测指标id
     * @return LocationInfoVO
     * @author liuzhu
     * @date 2014-1-14
     */
    public LocationInfoVO findNodeSensorInfoVOByDeviceSensorId(String locationId, Integer sensorId);

    /**
     * 根据区域ID，监测指标ID查询位置点
     *
     * @param zoneId   区域ID
     * @param sensorId 检测指标ID
     * @return 位置点集合
     * @author wang.geng
     * @date 2014-8-14
     */
    public List<LocationVO> findZoneLocationByZoneIdSensorId(String siteId, String zoneId, int sensorId);

    /**
     * 查询指点监测指标的各站点的实时数据的平均值
     *
     * @param sensorIds 指定的监测指标
     * @return 结果对象集
     * @author 王耕
     * @date 2014-11-6
     */
    public List<NodeSensorInfoVO> findSiteRealtimeAvg(List<Integer> sensorIds, int logicGroupId);
}
