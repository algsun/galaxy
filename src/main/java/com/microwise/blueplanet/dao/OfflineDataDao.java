package com.microwise.blueplanet.dao;

import com.microwise.blueplanet.bean.po.LocationSensorPO;
import com.microwise.blueplanet.bean.vo.LocationDataVO;
import com.microwise.blueplanet.bean.vo.RecentDataVO;

import java.util.Date;
import java.util.List;

/**
 * 离线数据 数据 操作 dao
 *
 * @author liuzhu
 * @date 2016-4-20
 */
public interface OfflineDataDao {

    /**
     * 删除离线位置点监测指标数据
     *
     * @param locationId
     * @param date
     */
    public void deleteOfflineData(String locationId, Date date);

    /**
     * 添加位置点
     *
     * @param sensorList
     */
    public void insert(List<LocationSensorPO> sensorList);

    /**
     * 获得位置点的历史数据
     *
     * @param locationId 设备编号
     * @return List<SensorinfoVO> 监测指标数据信息列表
     */
    public List<LocationDataVO> findLocationHistoryOffline(String locationId);

    /**
     * 获得位置点的历史数据时间，并按照时间分组
     *
     * @param locationId 位置点id
     * @return List<RecentDataVO> 位置点历史数据时间戳
     */
    public List<RecentDataVO> findHistoryOfflineCreateTime(String locationId);


}
