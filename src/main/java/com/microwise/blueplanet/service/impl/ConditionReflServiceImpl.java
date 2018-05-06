package com.microwise.blueplanet.service.impl;

import com.microwise.blueplanet.bean.po.ConditionRefl;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.dao.ConditionReflDao;
import com.microwise.blueplanet.dao.SensorinfoDao;
import com.microwise.blueplanet.service.ConditionReflService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author gaohui
 * @date 14-2-19 11:32
 */
@Beans.Service
@Transactional
@Blueplanet
public class ConditionReflServiceImpl implements ConditionReflService {

    @Autowired
    private ConditionReflDao conditionReflDao;
    @Autowired
    private SensorinfoDao sensorinfoDao;

    @Override
    public List<ConditionRefl> findByDeviceId(String deviceId) {
        List<ConditionRefl> conditionRefls = conditionReflDao.findByDeviceId(deviceId);
        for (ConditionRefl conditionRefl : conditionRefls) {
            SensorinfoVO sensorinfoVO=sensorinfoDao.findByPhysicalid(conditionRefl.getSensorId());
            conditionRefl.setSensorinfoVO(sensorinfoVO);
        }
        return conditionRefls;
    }
}
