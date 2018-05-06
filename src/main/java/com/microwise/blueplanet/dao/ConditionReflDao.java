package com.microwise.blueplanet.dao;

import com.microwise.blueplanet.bean.po.ConditionRefl;

import java.util.List;

/**
 * @author gaohui
 * @date 14-2-19 11:19
 */
public interface ConditionReflDao {
    /**
     * 查询一个设备的条件反射参数
     *
     * @param deviceId
     * @return
     */
    List<ConditionRefl> findByDeviceId(String deviceId);
}
