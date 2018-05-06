package com.microwise.blueplanet.dao.impl;

import com.microwise.blueplanet.bean.po.LocationSensorPO;
import com.microwise.blueplanet.bean.vo.LocationDataVO;
import com.microwise.blueplanet.bean.vo.RecentDataVO;
import com.microwise.blueplanet.dao.OfflineDataDao;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetBaseDao;
import com.microwise.common.sys.annotation.Beans;
import org.springframework.dao.DataAccessException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 离线数据 数据 操作 dao
 *
 * @author xu.baoji
 * @date 14-1-2
 * @check @xuyuexi 2014.1.14 #7594
 */
@Beans.Dao
@Blueplanet
public class OfflineDataDaoImpl extends BlueplanetBaseDao implements OfflineDataDao {

    @Override
    public void deleteOfflineData(String locationId, Date date) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("locationId", locationId);
        params.put("date", date);
        getSqlSession().delete("blueplanet.mybatis.OfflineDataDao.deleteOfflineData", params);
    }

    @Override
    public void insert(List<LocationSensorPO> sensorList) {
        try {
            for (LocationSensorPO locationSensor : sensorList) {
                //插入历史数据
                getSqlSession().insert("blueplanet.mybatis.OfflineDataDao.initLocationSensorList", locationSensor);
                //更新实时数据
                getSqlSession().update("blueplanet.mybatis.OfflineDataDao.updateLocationSensor", locationSensor);
            }
        } catch (DataAccessException e) {
        }
    }

    @Override
    public List<LocationDataVO> findLocationHistoryOffline(String locationId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        return getSqlSession().selectList(
                "blueplanet.mybatis.OfflineDataDao.findLocationHistoryOffline", paramMap);
    }

    @Override
    public List<RecentDataVO> findHistoryOfflineCreateTime(String locationId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        return getSqlSession().selectList(
                "blueplanet.mybatis.OfflineDataDao.findHistoryOfflineCreateTime", paramMap);
    }
}
