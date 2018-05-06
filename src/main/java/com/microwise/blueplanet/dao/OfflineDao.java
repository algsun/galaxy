package com.microwise.blueplanet.dao;

import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.bean.vo.ZoneVO;

import java.util.List;

/**
 * @author xu.yuexi
 * @date 13-12-31
 *
 * @change chen.yaoFei
 * @date 16-04-28
 */
public interface OfflineDao {

    /**
     * 添加位置点监测指标
     * */
    public void addLocationSensor(String locationId, List<Integer> checkedSensorInfoList);

    /**
     * 查询离线位置点
     * */
    public List<LocationVO> findLocationByNameAndZoneId(String siteId,String locationName,String zoneId,int page,int pageSize);

    /**
     * 查询位置点所在的区域
     * */
    public ZoneVO findZoneByZoneId(String zoneId);

    /**
     * 查询位置点总条目
     * */
    public int findAllLocationByNameAndZoneId(String siteId,String locationName,String zoneId);

    /**
     * 修改位置点信息
     * */
    public void updateLocation(LocationVO location);
}
