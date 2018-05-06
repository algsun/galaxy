package com.microwise.blueplanet.proxy.impl;

import com.microwise.blueplanet.bean.vo.*;
import com.microwise.blueplanet.proxy.LocationProxy;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 设备代理实现
 *
 * @author xu.baoji
 * @date 2013-10-17
 */
@Beans.Bean
@Blueplanet
public class LocationProxyImpl implements LocationProxy {

    /**
     * 设备 service 实现
     */
    @Autowired
    private LocationService locationService;

    @Override
    public LocationVO findLocationById(String locationId) {
        return locationService.findLocationById(locationId);
    }

    @Override
    public List<RecentDataVO> findRecentDataList(String locationId, Date startDate, Date endDate, int page, int pageSize) {
        return locationService.findRecentDataList(locationId, startDate, endDate, page, pageSize);
    }

    @Override
    public List<RecentDataVO> findHistoryDataList(String locationId, Date startDate, Date endDate, int page, int pageSize) {
        return locationService.findHistoryDataList(locationId, startDate, endDate, page, pageSize);
    }

    @Override
    public int findRecentDataListCount(String locationId, Date startDate, Date endDate) {
        return locationService.findRecentDataListCount(locationId, startDate, endDate);
    }

    @Override
    public RealtimeDataVO findLocationData(String locationId) {
        return locationService.findLocationData(locationId);
    }

    @Override
    public List<RecentDataVO> findRecentDataList(String locationId, Date startTime, Date endTime) {
        return locationService.findRecentDataList(locationId, startTime, endTime);
    }

    @Override
    public DeviceVO findDeviceByLocationId(String locationId) {
        return locationService.findDeviceByLocationId(locationId);
    }

    @Override
    public List<LocationDataVO> findSensorByLocationId(String locationId, List<Integer> sensorIds) {
        return locationService.findLocationSensor(locationId, sensorIds);
    }
}
