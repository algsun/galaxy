package com.microwise.blueplanet.dao.impl;

import com.microwise.blueplanet.bean.po.Formula;
import com.microwise.blueplanet.dao.FormulaDao;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetBaseDao;
import com.microwise.common.sys.annotation.Beans;

import java.util.*;

/**
 * @author gaohui
 * @date 13-12-31 14:23
 */
@Beans.Dao
@Blueplanet
public class FormulaDaoImpl extends BlueplanetBaseDao implements FormulaDao {
    // 默认设备ID
    public static final String DEFAULT_DEVICE_ID = "0";

    @Override
    public Formula findBySensorId(int sensorId) {
        return findByDeviceIdAndSensorId(DEFAULT_DEVICE_ID, sensorId);
    }

    @Override
    public Formula findByDeviceId(String deviceId, int sensorId) {
        return findByDeviceIdAndSensorId(deviceId, sensorId);
    }

    @Override
    public List<Map<String, String>> findParamsBySensorId(int sensorId) {
        return findParamsByDeviceIdAndSensorId(DEFAULT_DEVICE_ID, sensorId);
    }

    @Override
    public List<Map<String, String>> findParamsByDeviceId(String deviceId, int sensorId) {
        return findParamsByDeviceIdAndSensorId(deviceId, sensorId);
    }

    public Formula findByDeviceIdAndSensorId(String deviceId, int sensorId) {
        Map<String, Object> input = new HashMap<String, Object>();
        input.put("deviceId", deviceId);
        input.put("sensorId", sensorId);
        return getSqlSession().selectOne("blueplanet.Formula.findBySensorId", input);
    }

    public List<Map<String, String>> findParamsByDeviceIdAndSensorId(String deviceId, int sensorId) {
        Map<String, Object> input = new HashMap<String, Object>();
        input.put("deviceId", deviceId);
        input.put("sensorId", sensorId);
        return getSqlSession().selectList("blueplanet.Formula.findParamsBySensorId", input);
    }

    @Override
    public Map<Integer, Map<String, String>> findParamsByDeviceId(String deviceId) {
        List<Map<String, Object>> allParams = getSqlSession().selectList("blueplanet.Formula.findParamsByDeviceId", deviceId);
        // 性能优化: 大部分设备是没有自定义公式参数的
        if (allParams.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<Integer, Map<String, String>> sensorParams = new HashMap<Integer, Map<String, String>>();
        for (Map<String, Object> params : allParams) {
            Integer sensorId = (Integer) params.get("sensorId");
            Map<String, String> sensorParam = sensorParams.get(sensorId);
            if (sensorParam == null) {
                sensorParam = new LinkedHashMap<String, String>();
                sensorParams.put(sensorId, sensorParam);
            }
            sensorParam.put((String) params.get("name"), (String) params.get("value"));
        }

        return sensorParams;
    }

    @Override
    public List<Formula> findAllByDeviceId(String deviceId) {
        return getSqlSession().selectList("blueplanet.Formula.findAllByDeviceId", deviceId);
    }

    @Override
    public void deleteParamsByDeviceId(String deviceId) {
        Map<String, Object> input = new HashMap<String, Object>();
        input.put("deviceId", deviceId);
        getSqlSession().delete("blueplanet.Formula.deleteParamsByDeviceId", input);
    }

    @Override
    public void deleteParamsByDeviceIdAndSensorId(String deviceId, int sensorId) {
        Map<String, Object> input = new HashMap<String, Object>();
        input.put("deviceId", deviceId);
        input.put("sensorId", sensorId);
        getSqlSession().delete("blueplanet.Formula.deleteParamsByDeviceIdAndSensorId", input);
    }

    @Override
    public void saveCustomParam(String deviceId, int sensorId, String name, String value){
        Map<String, Object> input = new HashMap<String, Object>();
        input.put("deviceId", deviceId);
        input.put("sensorId", sensorId);
        input.put("name", name);
        input.put("value", value);
        getSqlSession().insert("blueplanet.Formula.saveCustomParam", input);
    }
}
