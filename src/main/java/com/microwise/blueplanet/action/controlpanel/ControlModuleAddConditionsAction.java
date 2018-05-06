package com.microwise.blueplanet.action.controlpanel;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.po.ConditionAction;
import com.microwise.blueplanet.bean.vo.ConditionActionVO;
import com.microwise.blueplanet.service.ActionService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.UUID;

/**
 * @author xuyuexi
 * @date 14-3-24
 */
@Beans.Action
@Blueplanet
@Route("/blueplanet")
public class ControlModuleAddConditionsAction extends BlueplanetLoggerAction {
    private static final Logger logger = LoggerFactory.getLogger(ControlModuleAddConditionsAction.class);

    @Autowired
    private ActionService actionService;

    /**
     * 控制模块
     */
    private String deviceId;

    /**
     * 添加动作的路数
     */
    private int actionRoute;

    /**
     * 添加提那件动作的设备
     */
    private String subNodeId;

    /**
     * 检测指标Id
     */
    private int sensorId;

    /**
     * 逻辑1与，2或'
     */
    private int logic;

    /**
     * 条件值
     */
    private double conditionValue;

    /**
     * 条件 1>,2<,3=
     */
    private int operator;
    /**
     * 动作1开0关
     */
    private int action;

    @Route("control-panel/{deviceId}/addCondition")
    public String actions() {
        ConditionAction conditionAction = new ConditionAction();
        ConditionActionVO conditionActionVO = new ConditionActionVO();
        String id = UUID.randomUUID().toString();
        String conditionId = UUID.randomUUID().toString();
        //添加条件动作，如果是第一次添加，则可以选择逻辑，否则logic=-1
        if (logic >= 0) {
            //保存逻辑和动作
            conditionActionVO.setId(id);
            conditionActionVO.setLogic(logic);
            conditionActionVO.setAction(action);
            conditionActionVO.setDeviceId(deviceId);
            conditionActionVO.setRoute(actionRoute);
            conditionActionVO.setUpdateTime(new Date());
            actionService.insertSensorAction(conditionActionVO);
            conditionAction.setSensorActionId(id);
        } else {
            //查询本设备路数的id
            conditionAction.setSensorActionId(actionService.findSensorAction(deviceId, actionRoute).getId());
        }
        conditionAction.setId(conditionId);
        conditionAction.setDeviceId(subNodeId);
        conditionAction.setSensorId(sensorId);
        conditionAction.setOperator(operator);
        conditionAction.setUpdateTime(new Date());
        conditionAction.setConditionValue(conditionValue);
        actionService.insertSensorCondition(conditionAction);
        return Results.redirect("/blueplanet/control-panel/" + deviceId + "/actions");
    }


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getActionRoute() {
        return actionRoute;
    }

    public void setActionRoute(int actionRoute) {
        this.actionRoute = actionRoute;
    }

    public String getSubNodeId() {
        return subNodeId;
    }

    public void setSubNodeId(String subNodeId) {
        this.subNodeId = subNodeId;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public int getLogic() {
        return logic;
    }

    public void setLogic(int logic) {
        this.logic = logic;
    }

    public double getConditionValue() {
        return conditionValue;
    }

    public void setConditionValue(double conditionValue) {
        this.conditionValue = conditionValue;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getOperator() {
        return operator;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }
}
