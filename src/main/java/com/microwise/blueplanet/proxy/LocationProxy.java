package com.microwise.blueplanet.proxy;

import com.microwise.blueplanet.bean.vo.*;

import java.util.Date;
import java.util.List;

/**
 * 设备代理 （提供查询 设备实时数据 历史数据 的基本业务）
 *
 * @author xu.baoji
 * @date 2013-10-17
 */
public interface LocationProxy {
    /**
     * 根据位置点ID查询位置点信息
     *
     * @param locationId 位置点ID
     * @return 位置点
     */
    public LocationVO findLocationById(String locationId);

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
     * 通过位置点id获得设备最近一段时间的数据
     *
     * @param locationId 位置点id
     * @param startDate  要查询的数据数量
     * @param endDate    要查询的数据数量
     * @return 设备的实时数据vo对象集合
     * @author li.jianfei
     * @date 2015-08-06
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
     * 查询位置点的实时数据
     *
     * @param locationId 位置点ID
     * @return 位置点的实时数据实体
     * @author wang.geng
     * @date 2014-8-7
     */
    public RealtimeDataVO findLocationData(String locationId);

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
     * 根据位置点id查设备
     *
     * @param locationId 位置点id
     * @return
     * @author liuzhu
     * @date 2015-7-8
     */
    public DeviceVO findDeviceByLocationId(String locationId);

    /**
     * 根据位置点id监测指标id查询监测指标
     *
     * @param locationId 位置点id
     * @param sensorIds  监测指标id集合
     * @return
     * @author liuzhu
     * @date 2015-7-10
     */
    public List<LocationDataVO> findSensorByLocationId(String locationId, List<Integer> sensorIds);
}
