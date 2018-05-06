package com.microwise.blueplanet.service;

import com.microwise.blueplanet.bean.po.ConditionRefl;

import java.util.List;

/**
 * @author gaohui
 * @date 14-2-19 11:32
 */
public interface ConditionReflService {

    /**
     * 查询一个设备的条件反射参数
     *
     * @param deviceId
     * @return
     */
    List<ConditionRefl> findByDeviceId(String deviceId);
}
