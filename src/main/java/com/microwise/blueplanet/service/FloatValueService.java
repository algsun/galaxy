package com.microwise.blueplanet.service;

import com.microwise.blueplanet.bean.po.FloatValue;

import java.util.Map;

/**
 * @author chenyaofei
 * @date 16-5-17
 */
public interface FloatValueService {

    /**
     * 获取监测指标默认浮动值
     * @param sensorIds
     * @return
     */
    public Map<Integer,FloatValue> findBySensorId(int... sensorIds);

    /**
     * 获取指定设备的自定义浮动值
     * @param deviceId
     * @return
     */
    public Map<Integer,FloatValue> findCustomByDeviceId(String deviceId);

    /**
     * 添加或修改自定义监测指标浮动值
     *
     */
    public void saveOrUpdate(FloatValue floatValue);

    /**
     * 恢复默认设置
     * @param deviceId
     * @param sensorId
     * @return
     */

    public FloatValue resetDefault(String deviceId ,int sensorId);
}
