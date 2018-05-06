package com.microwise.blueplanet.dao;

import com.microwise.blueplanet.bean.po.DeviceState;
import com.microwise.blueplanet.bean.vo.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 设备 dao
 *
 * @author xubaoji
 * @date 2013-1-21
 * @check 2013-02-25 zhangpeng svn:1747
 * @check 2013-04-22 xubaoji svn:2629
 */
public interface DeviceDao {

    /**
     * 根据主模块设备id查询从模块列表
     *
     * @param masterModuleDeviceId 主模块设备id
     * @return List<DeviceVO> 从模块设备vo对象列表
     * @author zhangpeng
     * @date 2013-1-29
     */
    public List<DeviceVO> findSlaveModule(String masterModuleDeviceId);

    /**
     * 获得一个设备的详细信息
     *
     * @param deviceId 设备编号
     * @return deviceVO 设备vo对象
     * @author zhangpeng
     * @date 2013-1-23
     */
    public DeviceVO findDeviceById(String deviceId);

    /**
     * 获得一个设备的数据版本号
     *
     * @param deviceId 设备编号
     * @return long 设备数据版号
     * @author zhangpeng
     * @date 2013-1-30
     */
    public long findDataVersion(String deviceId);

    /**
     * 分页获得站点下绑定的设备列表
     *
     * @param siteId     站点编号
     * @param deviceType 设备类型 1节点、2中继、7网关，
     * @param index      当前页面
     * @param pageSize   分页单位（一页查询的条数）
     * @return List<DeviceVO> 设备VO 对象列表
     */
    public List<DeviceVO> findDeviceList(String siteId, String deviceId, int deviceType, int index, int pageSize);

    /**
     * 获得站点下绑定设备的数量
     *
     * @param siteId     站点编号
     * @param deviceType 设备类型 1节点、2中继、7网关
     * @return int 查询设备总数量
     * @author zhangpeng
     * @date 2013-1-28
     */
    public int findDeviceListCount(String siteId, String deviceId, int deviceType);

    /**
     * 通过设备编号获得设备所拥有的监测指标列表（激活的）
     *
     * @param deviceId    设备id编号
     * @param activeState 激活状态：1激活的，null全部
     * @return List<SensorinfoVO> 监测指标vo对象列表
     * @author zhangpeng
     * @date 2013-1-22
     */
    public List<SensorinfoVO> findSensorinfo(String deviceId,
                                             Integer activeState, String language);

    /**
     * 根据设备id查询设备对应的监测指标标识集合
     *
     * @param deviceId    设备id
     * @param activeState 监测指标激活状态：1激活的，null全部
     * @return List<Integer> 监测指标标识列表
     * @author zhangpeng
     * @date 2013-1-22
     */
    public List<Integer> findSensorPhysicalid(String deviceId,
                                              Integer activeState);

    /**
     * 获得设备监测指标实时数据
     *
     * @param nodeId               设备编号
     * @param sensorPhysicalidList 筛选条件监测指标集合
     * @return List<SensorinfoVO> 监测指标数据信息列表
     * @author 许保吉
     * @date 2013-1-23
     */
    public List<DeviceDataVO> findNodesensor(String nodeId,
                                             List<Integer> sensorPhysicalidList);

    /**
     * 获得设备监测指标历史数据
     *
     * @param nodeId   设备编号
     * @param statTime 开始时间
     * @param endTime  结束时间
     * @return List<SensorinfoVO> 监测指标数据信息列表
     * @author 许保吉
     * @date 2013-1-23
     */
    public List<DeviceDataVO> findHistoryNodesensor(String nodeId,
                                                    Date statTime, Date endTime);

    /**
     * 查询最大和最小时间（为查询设备历史数据服务）
     *
     * @param nodeId    设备编号
     * @param startTime 开始时间 （可为null）
     * @param endTime   结束时间 （可为null）
     * @param index     当前页码
     * @param pageSize  分页单位
     * @return Map<String, Date> key:常量，对应最大最小时间（value）
     * @author 许保吉
     * @date 2013-1-29
     */
    public Map<String, Date> findMaxAndMinTime(String nodeId, Date startTime,
                                               Date endTime, Integer index, Integer pageSize);

    /**
     * 获得设备一个时间段的历史记录
     *
     * @param nodeId    设备编号
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return List<HistoryDataVO> 历史数据vo对象列表
     * @author 许保吉
     * @date 2013-1-31
     */
    public List<HistoryDataVO> findHistoryData(String nodeId, Date startTime,
                                               Date endTime);

    /**
     * 根据时间条件获得历史数据数量
     *
     * @param nodeId    设备编号
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return int 历史数据数量
     * @author 许保吉
     * @date 2013-1-31
     */
    public int findHistoryDataCount(String nodeId, Date startTime, Date endTime);


    /**
     * 批量修改设备的信息
     *
     * @param devices 传感器（设备）po对象列表
     * @author zhangpeng
     * @date 2013-1-29
     */
    public void updateDevices(List<DeviceVO> devices);

    /**
     * 删除失败
     *
     * @param nodeId 需要解除绑定的设备列表
     * @author liuzhu
     * @date 2013-10-16
     */
    public void deleteDevice(String nodeId);

    /**
     * 修改工作周期
     *
     * @param devices 设备vo类
     * @author liuzhu
     * @date 2013-10-22
     */
    public void updateDevicesInterval(DeviceVO devices);

    /**
     * 更新设备类型
     *
     * @param deviceId
     * @param deviceType
     */
    void updateDeviceType(String deviceId, int deviceType);

    /**
     * 根据设备类型查询设备
     *
     * @param siteId
     * @param type
     * @return
     */
    public List<DeviceVO> findDevicesByType(String siteId, int type);

    /**
     * 根据协议与 parentIP 查询匹配的设备
     *
     * @param siteId
     * @param nodeVersion
     * @param parentIP
     * @return
     */
    public List<DeviceVO> findDevices(String siteId, int nodeVersion, int parentIP);

    /**
     * 查找V1.3协议的设备
     *
     * @param siteId
     * @param subNet   子网 (0~99)
     * @param parentIP
     * @return
     */
    public List<DeviceVO> findV13Devices(String siteId, int subNet, int parentIP);

    //  --- 按位置存储

    /**
     * 查询站点下设备集合
     *
     * @param siteId 站点ID
     * @return 设备集合
     */
    public List<DeviceVO> findDevicesBySiteId(String siteId);

    /**
     * 查询区域下设备集合
     *
     * @param zoneId 区域ID
     * @return 设备集合
     */
    public List<DeviceVO> findDevicesByZoneId(String zoneId);

    /**
     * 查询酸雨设备状态
     *
     * @param deviceId   设备编号
     * @param createTime 创建时间
     * @return
     */
    public String findRainDeviceState(String deviceId, Date createTime);

    /**
     * 查询历史电压状态
     *
     * @param deviceId
     * @param startTime
     * @param endTime
     * @return
     */
    public List<DeviceState> findHistoryLowVoltage(String deviceId, Date startTime, Date endTime);

    /**
     * 查询设备状态信息
     *
     * @param siteId 站点id
     * @return
     * @author liuzhu
     * @date 2015-1-21
     */
    public List<ProductStateVO> findProductStateVO(String siteId);

    /**
     * 查询设备状态信息(无产品序列号)
     *
     * @param siteId 站点id
     * @return 序列号
     * @author liuzhu
     * @date 2015-3-9
     */
    public List<ProductStateVO> findProductStateVONoSn(String siteId);

    /**
     * 查询设备id
     *
     * @param sn 产品序列号
     * @return 序列号
     * @author liuzhu
     * @date 2015-1-21
     */
    public String findDeviceId(String sn);

    /**
     * 查询设备属性
     *
     * @param deviceId 设备编号
     * @return
     */
    public String findContent(String deviceId);

    /**
     * 查询设备最新的错误代码
     *
     * @param deviceId
     * @return
     */
    String findLastFaultCode(String deviceId);

    /**
     * 查询设备属性
     *
     * @param deviceId 设备编号
     * @return
     */
    public List<String> findContents(String deviceId, int count);

    /**
     * 查询恒湿机设备id
     *
     * @param siteId
     * @return
     */
    public List<String> findHumidities(String siteId);

    /**
     * 查询空调设备id
     *
     * @param siteId
     * @return
     */
    List<String> findAirConditioners(String siteId);

    /**
     * 查询所有控制设备nodeId
     *
     * @param deviceType 设备类型 0-其他 1-恒湿机 2-空调
     * @param siteId
     * @return
     */
    List<String> findControlDevices(String siteId, int deviceType);
}
