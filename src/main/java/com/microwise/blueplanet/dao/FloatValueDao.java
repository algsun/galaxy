package com.microwise.blueplanet.dao;

import com.microwise.blueplanet.bean.po.FloatValue;

import java.util.List;

/**
 * @author chenyaofei
 * @date 16-5-17
 */
public interface FloatValueDao {
    /**
     * 获取监测指标默认浮动值
     * @param sensorId
     * @return
     */
    public FloatValue findBySensorId(int sensorId);

    /**
     *获取自定义监测指标
     * @param deviceId
     * @return
     */
    public List<FloatValue> findCustomByDeviceId(String deviceId);

    /**
     * 保存浮动值
     * @param floatValue
     */
    public void save(FloatValue floatValue);

    /**
     * 删除浮动值
     * @param deviceId
     * @param sensorId
     */
    public void delete(String deviceId, int sensorId);
}
