package com.microwise.blueplanet.proxy;

import com.microwise.blueplanet.bean.vo.DeviceVO;
import com.microwise.blueplanet.bean.vo.HistoryDataVO;
import com.microwise.blueplanet.bean.vo.RealtimeDataVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;

import java.util.Date;
import java.util.List;

/**
 * 设备代理 （提供查询 设备实时数据 历史数据 的基本业务）
 *
 * @author xu.baoji
 * @date 2013-10-17
 */
public interface DeviceProxy {

    /**
     * 分页查询设备历史记录
     *
     * @param deviceId  设备id
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param index     页码
     * @param pageSize  分页单位
     * @return List<HistoryDataVO> 历史数据列表  没有数据 list 为 empty  不为null
     * @author xu.baoji
     * @date 2013-10-17
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
     * @date 2013-10-17
     */
    public int findHistoryDataCount(String deviceId, Date startTime,
                                    Date endTime);
    /**
     * 查询设备 实时数据
     *
     * @param deviceIds
     * @return List<RealtimeDataVO> 设备实时数据列表
     * @author xu.baoji
     * @date 2013-10-17
     */
    public List<RealtimeDataVO> findRealtimeData(List<String> deviceIds);

    /**
     * 获得设备 监测指标列表
     *
     * @param deviceIds 设备 id 列表
     * @return List<SensorinfoVO> 监测指标列表
     */
    public List<SensorinfoVO> findSensorinfo(List<String> deviceIds);

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
     * 通过设备编号获得设备所拥有的所有监测指标列表（激活和未激活的都要）
     *
     * @param deviceId 设备id编号
     * @return List<SensorinfoVO> 监测指标vo列表
     * @author zhangpeng
     * @date 2013-1-31
     */
    public List<SensorinfoVO> findAllSensorinfo(String deviceId);
}
