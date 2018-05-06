package com.microwise.blueplanet.service;

import com.microwise.blueplanet.bean.po.DeviceState;
import com.microwise.blueplanet.bean.vo.*;

import java.util.Date;
import java.util.List;

/**
 * 设备Service
 *
 * @author li.jianfei
 * @date 2014-07-29
 */
public interface DeviceService {

    /**
     * 通过设备编号获得设备所拥有的所有监测指标列表（激活和未激活的都要）
     *
     * @param deviceId 设备id编号
     * @return List<SensorinfoVO> 监测指标vo列表
     * @author zhangpeng
     * @date 2013-1-31
     */
    public List<SensorinfoVO> findAllSensorinfo(String deviceId, String language);

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
     * 通过设备id编号获得设备实时数据
     *
     * @param locationId 设备id 编号
     * @return RealtimeDataVO 设备的实时数据vo对象
     * @author 许保吉
     * @date 2013-1-18
     */
    public RealtimeDataVO findRealtimeData(String locationId);

    /**
     * 通过设备编号获得设备所拥有的监测指标列表（激活的）
     *
     * @param deviceId 设备id编号
     * @return List<SensorinfoVO> 监测指标vo对象列表
     * @author zhangpeng
     * @date 2013-1-22
     */
    public List<SensorinfoVO> findSensorinfo(String deviceId, String language);

    /**
     * 根据设备id查询设备对应的监测指标标识集合
     *
     * @param deviceId    设备id
     * @param activeState 监测指标激活状态：1激活的，null全部
     * @return List<Integer> 设备id集合
     * @author zhangpeng
     * @date 2013-1-22
     */
    public List<Integer> findSensorPhysicalid(String deviceId,
                                              Integer activeState);

    /**
     * 分页获得设备历史记录
     *
     * @param deviceId  设备编号
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param index     起始页
     * @param pageSize  每页数据数目
     * @return List<HistoryDataVO> 历史数据vo对象列表
     * @author 许保吉
     * @date 2013-1-18
     */
    public List<HistoryDataVO> findHistoryData(String deviceId, Date startTime,
                                               Date endTime, Integer index, Integer pageSize);

    /**
     * 根据时间条件获得历史数据数量
     *
     * @param deviceId  设备编号
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return int 历史数据数量
     * @author 许保吉
     * @date 2013-1-18
     */
    public int findHistoryDataCount(String deviceId, Date startTime,
                                    Date endTime);

    /**
     * 查询设备一个时间段内的历史数据
     *
     * @param deviceId  设备 id
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return List<HistoryDataVO> 历史数据列表
     * @author xu.baoji
     * @date 2013-10-17
     */
    public List<HistoryDataVO> findHistoryData(String deviceId, Date startTime, Date endTime);

    /**
     * 分页获得站点下绑定的设备列表
     *
     * @param siteId     站点编号
     * @param deviceId   设备编号
     * @param deviceType 设备类型 1节点、2中继、7网关，
     * @param index      当前页面
     * @param pageSize   分页单位（一页查询的条数）
     * @return List<DeviceVO> 设备VO 对象列表
     */
    public List<DeviceVO> findDeviceList(String siteId, String deviceId, Integer deviceType, int index, int pageSize);

    /**
     * 根据参数查询绑定或未绑定的设备
     *
     * @param siteId   站点编号
     * @param isBinded 是否绑定
     * @return 设备列表
     */
    public List<DeviceVO> findDeviceList(String siteId, boolean isBinded);

    /**
     * 获得站点下绑定设备的数量
     *
     * @param siteId     站点编号
     * @param deviceType 设备类型 1节点、2中继、7网关
     * @return int 查询设备总数量
     */
    public int findDeviceListCount(String siteId, String deviceId, int deviceType);

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
     * 修改一个设备的信息
     *
     * @param deviceVO 设备vo对象
     * @author zhangpeng
     * @date 2013-1-29
     */
    public void updateDevice(DeviceVO deviceVO);

    /**
     * 修改工作周期
     *
     * @param deviceVO 设备vo对象
     * @author liuzhu
     * @date 2013-10-22
     */
    public void updateDeviceInterval(DeviceVO deviceVO);

    /**
     * 根据设备类型查询设备
     *
     * @param siteId
     * @param type   设备类型
     * @return
     */
    List<DeviceVO> findDevicesByType(String siteId, int type);

    /**
     * 返回设备下连接的子设备，比如中继，控制模块
     *
     * @param deviceId
     * @return
     */
    List<DeviceVO> findSubDevicesByDeviceId(String deviceId, String language);

    /**
     * 删除设备
     *
     * @param nodeId 设备编号
     * @author liuzhu
     * @date 2013-10-16
     */
    public void deleteDevice(String nodeId);


    //------------------- 按位置存储---

    /**
     * 查询站点下设备集合
     *
     * @param siteId 站点ID
     * @return 设备集合
     */
    public List<DeviceVO> findDevicesBySiteId(String siteId);

    /**
     * 查询酸雨设备状态
     *
     * @param deviceId   设备编号
     * @param createTime 创建时间
     * @return
     */
    public RainDeviceStateVO findRainDeviceState(String deviceId, Date createTime);


    /**
     * 查询历史电压状态+搜网次数+RSSI、LQI
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
     * @return
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
     * 查询最后一条设备状态信息
     *
     * @param deviceId
     * @return
     */
    public DevicePropertyVO findLastContent(String deviceId);

    /**
     * 查询设备最新的错误代码
     *
     * @param deviceId
     * @return
     */
    String findLastFaultCode(String deviceId);

    /**
     * 查询content
     *
     * @param deviceId
     * @param count
     * @return
     */
    public DevicePropertyVO findContents(String deviceId, int count);

    /**
     * 查询恒湿机设备id
     *
     * @param siteId
     * @return
     */
    public List<String> findHumidities(String siteId);

    /**
     *
     */
    public boolean isBuzzerSwitch(List<SensorinfoVO> sensorinfoes);

    /**
     * 查询空调设备
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
