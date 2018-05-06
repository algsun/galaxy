package com.microwise.blueplanet.dao.impl;

import com.microwise.blueplanet.bean.po.ConditionAction;
import com.microwise.blueplanet.bean.po.SwitchDailyAction;
import com.microwise.blueplanet.bean.po.SwitchIntervalAction;
import com.microwise.blueplanet.bean.vo.ConditionActionVO;
import com.microwise.blueplanet.dao.ActionDao;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetBaseDao;
import com.microwise.common.sys.annotation.Beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuyuexi
 * @date 14-3-5
 */
@Beans.Dao
@Blueplanet
public class ActionDaoImpl extends BlueplanetBaseDao implements ActionDao {

    @Override
    public void insertDailyAction(SwitchDailyAction switchDailyAction) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", switchDailyAction.getId());
        paramMap.put("deviceId", switchDailyAction.getDeviceId());
        paramMap.put("route", switchDailyAction.getRoute());
        paramMap.put("time", switchDailyAction.getTime());
        paramMap.put("action", switchDailyAction.getAction());
        getSqlSession().insert("blueplanet.ActionDao.insertDailyAction", paramMap);
    }

    @Override
    public void deleteDailyAction(String id) {
        getSqlSession().delete("blueplanet.ActionDao.deleteDailyAction", id);
    }

    @Override
    public void updateDailyAction(SwitchDailyAction switchDailyAction) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", switchDailyAction.getId());
        paramMap.put("deviceId", switchDailyAction.getDeviceId());
        paramMap.put("route", switchDailyAction.getRoute());
        paramMap.put("time", switchDailyAction.getTime());
        paramMap.put("action", switchDailyAction.getAction());
        getSqlSession().update("blueplanet.ActionDao.updateDailyAction", paramMap);
    }

    @Override
    public SwitchDailyAction findDailyActionById(String id){
        return getSqlSession().selectOne("blueplanet.ActionDao.findDailyActionById", id);
    }

    @Override
    public List<SwitchDailyAction> findDailyActions(String deviceId, int route) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("deviceId", deviceId);
        paramMap.put("route", route);
        return getSqlSession().selectList("blueplanet.ActionDao.findDailyActions", paramMap);
    }

    @Override
    public void insertIntervalAction(SwitchIntervalAction switchIntervalAction) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", switchIntervalAction.getId());
        paramMap.put("deviceId", switchIntervalAction.getDeviceId());
        paramMap.put("route", switchIntervalAction.getRoute());
        paramMap.put("intervalTime", switchIntervalAction.getIntervalTime());
        paramMap.put("executionTime", switchIntervalAction.getExecutionTime());
        paramMap.put("action", switchIntervalAction.getAction());
        getSqlSession().insert("blueplanet.ActionDao.insertIntervalAction", paramMap);
    }

    @Override
    public void deleteIntervalAction(String deviceId, int route) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("deviceId", deviceId);
        paramMap.put("route", route);
        getSqlSession().delete("blueplanet.ActionDao.deleteIntervalAction", paramMap);
    }

    @Override
    public void updateIntervalAction(SwitchIntervalAction switchIntervalAction) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", switchIntervalAction.getId());
        paramMap.put("deviceId", switchIntervalAction.getDeviceId());
        paramMap.put("route", switchIntervalAction.getRoute());
        paramMap.put("intervalTime", switchIntervalAction.getIntervalTime());
        paramMap.put("executionTime", switchIntervalAction.getExecutionTime());
        paramMap.put("action", switchIntervalAction.getAction());
        getSqlSession().update("blueplanet.ActionDao.updateRegularActionTime", paramMap);
    }

    @Override
    public SwitchIntervalAction findIntervalActionById(String id){
        return getSqlSession().selectOne("blueplanet.ActionDao.findIntervalActionById", id);
    }

    @Override
    public SwitchIntervalAction findIntervalActions(String deviceId, int route) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("deviceId", deviceId);
        paramMap.put("route", route);
        return getSqlSession().selectOne("blueplanet.ActionDao.findIntervalActions", paramMap);
    }

    @Override
    public void deleteIntervalActionById(String id) {
        getSqlSession().delete("blueplanet.ActionDao.deleteIntervalActionById", id);
    }

    @Override
    public void insertSensorCondition(ConditionAction conditionAction) {
        getSqlSession().insert("blueplanet.ActionDao.insertSensorCondition", conditionAction);
    }

    @Override
    public void insertSensorAction(ConditionActionVO conditionActionVO) {
        getSqlSession().insert("blueplanet.ActionDao.insertSensorAction", conditionActionVO);
    }

    @Override
    public void deleteSensorCondition(String id) {
        getSqlSession().delete("blueplanet.ActionDao.deleteSensorCondition", id);
    }

    @Override
    public void deleteSensorAction(String deviceId, int route) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("deviceId", deviceId);
        paramMap.put("route", route);
        getSqlSession().delete("blueplanet.ActionDao.deleteSensorAction",paramMap);
    }

    @Override
    public List<ConditionAction> findSensorCondition(String sensorActionId) {
        return getSqlSession().selectList("blueplanet.ActionDao.findSensorCondition", sensorActionId);
    }

    @Override
    public ConditionActionVO findSensorAction(String deviceId, int route) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("deviceId", deviceId);
        paramMap.put("route", route);
        return getSqlSession().selectOne("blueplanet.ActionDao.findSensorAction",paramMap);
    }

    @Override
    public ConditionAction findSensorConditionById(String id) {
        return getSqlSession().selectOne("blueplanet.ActionDao.findSensorConditionById",id);
    }

    @Override
    public void deleteSensorActionById(String id) {
        getSqlSession().delete("blueplanet.ActionDao.deleteSensorActionById",id);
    }

    @Override
    public void updateSensorAction(ConditionActionVO conditionActionVO) {
        getSqlSession().update("blueplanet.ActionDao.updateSensorAction",conditionActionVO);
    }
}
