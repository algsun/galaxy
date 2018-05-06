package com.microwise.blueplanet.dao.impl;

import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.bean.vo.ZoneVO;
import com.microwise.blueplanet.dao.OfflineDao;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetBaseDao;
import com.microwise.common.sys.annotation.Beans;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xu.yuexi
 * @date 13-12-31
 * @check @xubaoji 2014-1-14  #7610
 *
 * @change chen.yaoFei
 * @date 16-04-28
 */

@Beans.Dao
@Blueplanet
public class OfflineDaoImpl extends BlueplanetBaseDao implements OfflineDao {

    @Override
    public void addLocationSensor(String locationId, List<Integer> checkedSensorInfoList) {
          Map<String,Object> params = new HashMap<String, Object>();
          params.put("locationId", locationId);
          params.put("checkedSensorInfoList", checkedSensorInfoList);
          getSqlSession().insert("blueplanet.mybatis.OfflineDao.addLocationSensor",params);
    }

    @Override
    public List<LocationVO> findLocationByNameAndZoneId(String siteId,String locationName,String zoneId,int page,int pageSize) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("siteId", siteId);
        params.put("locationName", locationName);
        params.put("zoneId", zoneId);
        params.put("start", (page-1)*pageSize);
        params.put("pageSize", pageSize);
        return  getSqlSession().selectList("blueplanet.mybatis.OfflineDao.findLocationByNameAndZoneId", params);
    }

    @Override
    public ZoneVO findZoneByZoneId(String zoneId) {
        return getSqlSession().selectOne("blueplanet.mybatis.OfflineDao.findZoneByZoneId", zoneId);
    }

    @Override
    public int findAllLocationByNameAndZoneId(String siteId,String locationName,String zoneId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("siteId", siteId);
        params.put("locationName", locationName);
        params.put("zoneId", zoneId);
        return getSqlSession().selectOne("blueplanet.mybatis.OfflineDao.findAllLocationByNameAndZoneId", params);
    }

    @Override
    public void updateLocation(LocationVO location) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", location.getId());
        params.put("locationName", location.getLocationName());
        params.put("zoneId", location.getZoneId());
        getSqlSession().update("blueplanet.mybatis.OfflineDao.updateLocation",params);
    }

}
