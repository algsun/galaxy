package com.microwise.blueplanet.service;

import com.microwise.blueplanet.bean.vo.LocationVO;

import java.util.List;

/**
 * @author chenyaofei
 * @date 16-4-20
 */
public interface OfflineService {

    /**
     * 添加位置点监测指标
     */
    public void addLocationSensor(String locationId, List<Integer> checkedSensorInfoList);

    /**
     * 查询离线位置点列表
     */
    public List<LocationVO> findLocationByNameAndZoneId(String siteId,String locationName,String zoneId,int page,int pageSize);

    /**
     * 查询离线位置点总数目
     * */
    public int findAllLocationByNameAndZoneId(String siteId,String locationName,String zoneId);

    /**
     *查询历史数据表是否有内容
     * */
    public boolean findLocationDataById(String locationId);

    /**
     * 修改位置点信息
     * */
    public void updateLocation(LocationVO location);
    /**
     * 修改位置点监测指标
     * */
    public void updateSensorById(String locationId, List<Integer> checkedSensorInfoList);
  }
