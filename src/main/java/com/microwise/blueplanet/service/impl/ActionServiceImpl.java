package com.microwise.blueplanet.service.impl;

import com.microwise.blueplanet.bean.po.ConditionAction;
import com.microwise.blueplanet.bean.po.SwitchDailyAction;
import com.microwise.blueplanet.bean.po.SwitchIntervalAction;
import com.microwise.blueplanet.bean.vo.ConditionActionVO;
import com.microwise.blueplanet.bean.vo.SwitchActionVO;
import com.microwise.blueplanet.dao.ActionDao;
import com.microwise.blueplanet.dao.DeviceDao;
import com.microwise.blueplanet.dao.SensorinfoDao;
import com.microwise.blueplanet.service.ActionService;
import com.microwise.blueplanet.sys.Blueplanet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @author xuyuexi
 * @date 14-3-7
 */
@Service
@Transactional
@Blueplanet
public class ActionServiceImpl implements ActionService {

    @Autowired
    private ActionDao actionDao;
    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private SensorinfoDao sensorinfoDao;

    @Override
    public void insertDailyAction(SwitchDailyAction switchDailyAction) {
        switchDailyAction.setId(UUID.randomUUID().toString());
        actionDao.insertDailyAction(switchDailyAction);
    }

    @Override
    public void deleteDailyAction(String id) {
        actionDao.deleteDailyAction(id);
    }

    @Override
    public void updateDailyAction(SwitchDailyAction switchDailyAction) {
        actionDao.updateDailyAction(switchDailyAction);
    }

    @Override
    public SwitchDailyAction findDailyActionById(String id) {
        return actionDao.findDailyActionById(id);
    }

    @Override
    public List<SwitchDailyAction> findDailyActions(String deviceId, int route) {
        return actionDao.findDailyActions(deviceId, route);
    }

    @Override
    public void insertIntervalAction(SwitchIntervalAction switchIntervalAction) {
        switchIntervalAction.setId(UUID.randomUUID().toString());
        actionDao.insertIntervalAction(switchIntervalAction);
    }

    @Override
    public void deleteIntervalAction(String deviceId, int route) {
        actionDao.deleteIntervalAction(deviceId, route);
    }

    @Override
    public void deleteIntervalActionById(String id) {
        actionDao.deleteIntervalActionById(id);
    }

    @Override
    public void updateIntervalAction(SwitchIntervalAction switchIntervalAction) {
        actionDao.updateIntervalAction(switchIntervalAction);
    }

    @Override
    public SwitchIntervalAction findIntervalActionById(String id) {
        return actionDao.findIntervalActionById(id);
    }

    @Override
    public SwitchIntervalAction findIntervalActions(String deviceId, int route) {
        return actionDao.findIntervalActions(deviceId, route);
    }

    @Override
    public SwitchActionVO findIntervalAction(String deviceId, int route) {
        SwitchActionVO switchAction = new SwitchActionVO();
        switchAction.setDeviceId(deviceId);
        switchAction.setRoute(route);
        switchAction.setSwitchDailyActions(actionDao.findDailyActions(deviceId, route));
        switchAction.setSwitchIntervalAction(actionDao.findIntervalActions(deviceId, route));
        return switchAction;
    }

    @Override
    public void insertSensorCondition(ConditionAction conditionAction) {
        actionDao.insertSensorCondition(conditionAction);
    }

    @Override
    public void insertSensorAction(ConditionActionVO conditionActionVO) {
        actionDao.insertSensorAction(conditionActionVO);
    }

    @Override
    public void deleteSensorCondition(String id) {
        actionDao.deleteSensorCondition(id);
    }

    @Override
    public void deleteSensorAction(String deviceId, int route) {
        actionDao.deleteSensorAction(deviceId, route);
    }

    @Override
    public List<ConditionAction> findSensorCondition(String sensorActionId) {
        List<ConditionAction> conditionActions = actionDao.findSensorCondition(sensorActionId);
        for (ConditionAction c : conditionActions) {
            c.setDeviceVO(deviceDao.findDeviceById(c.getDeviceId()));
            c.setSensorinfoVO(sensorinfoDao.findByPhysicalid(c.getSensorId()));
        }
        return conditionActions;
    }

    @Override
    public ConditionActionVO findSensorAction(String deviceId, int route) {
        ConditionActionVO conditionActionVO = actionDao.findSensorAction(deviceId, route);
        if (conditionActionVO != null) {
            conditionActionVO.setConditionActions(findSensorCondition(conditionActionVO.getId()));
        }
        return conditionActionVO;
    }

    @Override
    public ConditionAction findSensorConditionById(String id) {
        return actionDao.findSensorConditionById(id);
    }

    @Override
    public void deleteSensorActionById(String id) {
        actionDao.deleteSensorActionById(id);
    }

    @Override
    public void updateSensorAction(ConditionActionVO conditionActionVO) {
        actionDao.updateSensorAction(conditionActionVO);
    }
}
