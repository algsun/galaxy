package com.microwise.blueplanet.service.impl;

import com.google.common.base.Preconditions;
import com.microwise.blueplanet.bean.po.FloatValue;
import com.microwise.blueplanet.dao.FloatValueDao;
import com.microwise.blueplanet.service.FloatValueService;
import com.microwise.blueplanet.sys.Blueplanet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenyaofei
 * @date 16-5-17
 */
@Service
@Transactional
@Blueplanet
public class FloatValueServiceImpl implements FloatValueService {
    @Autowired
    FloatValueDao floatValueDao;

    @Override
    public Map<Integer, FloatValue> findBySensorId(int... sensorIds) {
        Preconditions.checkNotNull(sensorIds);

        Map<Integer, FloatValue> floatValueMap = new LinkedHashMap<Integer, FloatValue>();
        for (int sensorId : sensorIds) {
            //findbysensorId
            floatValueMap.put(sensorId, floatValueDao.findBySensorId(sensorId));
        }
        return floatValueMap;
    }

    @Override
    public Map<Integer, FloatValue> findCustomByDeviceId(String deviceId) {
        //获取所有设备所有监测指标的浮动值
        List<FloatValue> allFloatValue = floatValueDao.findCustomByDeviceId(deviceId);
        Map<Integer, FloatValue> customFloatValueMap = new LinkedHashMap<Integer, FloatValue>();
        for (FloatValue floatValue : allFloatValue) {
            customFloatValueMap.put(floatValue.getSensorId(), floatValue);
        }
        return customFloatValueMap;
    }

    @Override
    public void saveOrUpdate(FloatValue floatValue) {
        floatValueDao.delete(floatValue.getDeviceId(), floatValue.getSensorId());
        floatValueDao.save(floatValue);
    }

    @Override
    public FloatValue resetDefault(String deviceId, int sensorId) {
        floatValueDao.delete(deviceId, sensorId);
        return floatValueDao.findBySensorId(sensorId);
    }

}
