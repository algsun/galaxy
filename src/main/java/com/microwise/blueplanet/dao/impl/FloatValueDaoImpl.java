package com.microwise.blueplanet.dao.impl;

import com.microwise.blueplanet.bean.po.FloatValue;
import com.microwise.blueplanet.dao.FloatValueDao;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetBaseDao;
import com.microwise.common.sys.annotation.Beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenyaofei
 * @date 16-5-17
 */
@Beans.Dao
@Blueplanet
public class FloatValueDaoImpl extends BlueplanetBaseDao implements FloatValueDao {

    @Override
    public FloatValue findBySensorId(int sensorId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("sensorId", sensorId);
        return getSqlSession().selectOne("blueplanet.mybatis.FloatValueDao.findBySensorId", param);
    }

    @Override
    public List<FloatValue> findCustomByDeviceId(String deviceId) {
        return getSqlSession().selectList("blueplanet.mybatis.FloatValueDao.findCustomByDeviceId", deviceId);
    }

    @Override
    public void save(FloatValue floatValue) {
        getSqlSession().insert("blueplanet.mybatis.FloatValueDao.save", floatValue);
    }

    @Override
    public void delete(String deviceId, int sensorId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("deviceId", deviceId);
        params.put("sensorId", sensorId);
        getSqlSession().delete("blueplanet.mybatis.FloatValueDao.delete", params);

    }
}
