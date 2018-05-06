package com.microwise.blueplanet.service;

import com.microwise.blueplanet.bean.po.LocationSensorPO;
import com.microwise.blueplanet.bean.vo.RecentDataVO;

import java.util.Date;
import java.util.List;

/**
 * 离线数据 数据 操作 service
 *
 * @author xu.baoji
 * @date 14-1-2
 */
public interface OfflineDataService {

    /**
     * 删除离线位置点监测指标数据
     *
     * @param locationId
     * @param date
     */
    public void deleteOfflineData(String locationId, Date date);

    /**
     * 添加离线位置点数据
     *
     * @param sensorList
     */
    public void insert(List<LocationSensorPO> sensorList);

    /**
     * 离线位置点历史数据
     *
     * @param locationId
     * @return recentDataVOs
     */
    public List<RecentDataVO> findOfflineHistory(String locationId);

}
