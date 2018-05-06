package com.microwise.blueplanet.service;

import com.microwise.blueplanet.bean.po.ConditionAction;
import com.microwise.blueplanet.bean.po.SwitchDailyAction;
import com.microwise.blueplanet.bean.po.SwitchIntervalAction;
import com.microwise.blueplanet.bean.vo.ConditionActionVO;
import com.microwise.blueplanet.bean.vo.SwitchActionVO;

import java.util.List;

/**
 * 控制模块端口开关状态
 *
 * @author xuyuexi
 * @date 14-3-7
 */
public interface ActionService {

    /**
     * 给控制模块定时动作表插入动作
     *
     * @param switchDailyAction
     */
    public void insertDailyAction(SwitchDailyAction switchDailyAction);

    /**
     * 控制模块定时动作表删除动作
     *
     * @param id 动作id
     */
    public void deleteDailyAction(String id);


    /**
     * 控制模块定时动作表更新动作
     *
     * @param switchDailyAction
     */
    public void updateDailyAction(SwitchDailyAction switchDailyAction);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    SwitchDailyAction findDailyActionById(String id);

    /**
     * 查询定时动作
     *
     * @param deviceId
     * @param route
     * @return
     */
    public List<SwitchDailyAction> findDailyActions(String deviceId, int route);

    /**
     * 给控制模块周期动作表插入动作
     *
     * @param switchIntervalAction
     */
    public void insertIntervalAction(SwitchIntervalAction switchIntervalAction);

    /**
     * 控制模块周期动作表删除动作
     *
     * @param deviceId,route
     */
    public void deleteIntervalAction(String deviceId, int route);

    /**
     * 控制模块周期动作表删除动作
     *
     * @param id
     */
    public void deleteIntervalActionById(String id);

    /**
     * 控制模块周期动作表更新动作
     *
     * @param switchIntervalAction
     */
    public void updateIntervalAction(SwitchIntervalAction switchIntervalAction);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    SwitchIntervalAction findIntervalActionById(String id);

    /**
     * 查询周期动作
     *
     * @param deviceId
     * @param route
     * @return
     */
    public SwitchIntervalAction findIntervalActions(String deviceId, int route);

    /**
     * 查询所有动作
     *
     * @param deviceId
     * @param route
     * @return
     */
    public SwitchActionVO findIntervalAction(String deviceId, int route);


    /**
     * 给控制模块条件动作表插入动作
     *
     * @param conditionAction
     */
    public void insertSensorCondition(ConditionAction conditionAction);

    /**
     * 给控制模块条件动作表插入动作
     *
     * @param conditionActionVO
     */
    public void insertSensorAction(ConditionActionVO conditionActionVO);

    /**
     * 控制模块条件动作表删除动作
     *
     * @param id 动作id
     */
    public void deleteSensorCondition(String id);

    /**
     * 控制模块条件动作表开关以及逻辑
     *
     */
    public void deleteSensorAction(String deviceId, int route);

    /**
     * 删除控制模块条件动作表开关逻辑
     *
     */
    public void deleteSensorActionById(String id);

    /**
     * 查询条件动作
     *
     * @param sensorActionId
     * @return
     */
    public List<ConditionAction> findSensorCondition(String sensorActionId);

    /**
     * 查询条件动作
     *
     * @param id
     * @return
     */
    public ConditionAction findSensorConditionById(String id);

    /**
     * 查询逻辑
     *
     * @param deviceId
     * @param route
     * @return
     */
    public ConditionActionVO findSensorAction(String deviceId, int route);

    /**
     * 控制模块条件动作表更新
     *
     * @param conditionActionVO
     */
    public void updateSensorAction(ConditionActionVO conditionActionVO);
}
