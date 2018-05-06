package com.microwise.blueplanet.action.controlpanel;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.vo.ConditionActionVO;
import com.microwise.blueplanet.service.ActionService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author xuyuexi
 * @date 14-3-27
 */
@Beans.Action
@Blueplanet
@Route("/blueplanet")
public class ControlModuleChangeConditionsAction extends BlueplanetLoggerAction {
    private static final Logger logger = LoggerFactory.getLogger(ControlModuleChangeConditionsAction.class);

    @Autowired
    private ActionService actionService;

    /**
     * 条件动作id
     */
    private String deviceId;

    /**
     * 条件动作id
     */
    private int actionRoute;

    /**
     * 逻辑1与，2或'
     */
    private int logic;
    /**
     * 动作1开0关
     */
    private int action;

    @Route("control-panel/{deviceId}/editConditions")
    public String actions() {
        try {
            ConditionActionVO conditionActionVO = new ConditionActionVO();
            conditionActionVO.setLogic(logic);
            conditionActionVO.setDeviceId(deviceId);
            conditionActionVO.setRoute(actionRoute);
            conditionActionVO.setAction(action);
            conditionActionVO.setUpdateTime(new Date());
            actionService.updateSensorAction(conditionActionVO);
            log("控制面板", "编辑条件动作");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("控制面板编辑条件动作失败", e);
        }
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

    public int getLogic() {
        return logic;
    }

    public void setLogic(int logic) {
        this.logic = logic;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
