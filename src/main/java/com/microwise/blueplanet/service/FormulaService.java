package com.microwise.blueplanet.service;

import com.microwise.blueplanet.bean.po.Formula;

import java.util.List;
import java.util.Map;

/**
 * 公式
 * @author gaohui
 * @date 14-1-7 10:20
 */
public interface FormulaService {
    Formula findBySensorId(int sensorId);

    /**
     * 返回多个监测指标的默认公式
     *
     * @param sensorIds
     * @return
     */
    Map<Integer, Formula> findBySensorIds(int... sensorIds);

    Formula findByDeviceId(String deviceId, int sensorId);

    List<Formula> findAllByDeviceId(String deviceId);

    Map<Integer, Map<String, String>> findParamsByDeviceId(String deviceId);

    /**
     * 保存或更新设备自定义公式系数
     *
     * @param deviceId
     * @param sensorId
     * @param params
     */
    void saveOrUpdateParamsByDeviceId(String deviceId, int sensorId, Map<String, String> params);

    void deleteParamsByDeviceId(String deviceId);

    void deleteParamsByDeviceIdAndSensorId(String deviceId, int sensorId);
}
