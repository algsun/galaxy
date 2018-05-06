package com.microwise.blueplanet.action.controlpanel;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.po.ConditionAction;
import com.microwise.blueplanet.service.ActionService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuyuexi
 * @date 14-3-25
 */
@Beans.Action
@Blueplanet
@Route("/blueplanet")
public class ControlModuleDeleteConditionsAction extends BlueplanetLoggerAction {
    private static final Logger logger = LoggerFactory.getLogger(ControlModuleDeleteConditionsAction.class);

    @Autowired
    private ActionService actionService;

    /**
     * 条件动作id
     */
    private String conditionId;


    @Route("control-panel/{conditionId}/deleteConditions")
    public String actions() {
        Map<String, Object> success = new HashMap<String, Object>();
        try {
            ConditionAction conditionAction = actionService.findSensorConditionById(conditionId);
            String id = conditionAction.getSensorActionId();
            actionService.deleteSensorCondition(conditionId);
            List<ConditionAction> conditionActions = actionService.findSensorCondition(id);
          //如果删除的条件只有一个则删除对应的逻辑
            if (conditionActions.size() == 0) {
                actionService.deleteSensorActionById(id);
                success.put("unique", true);
            }
            success.put("success", true);
            log("控制面板", "删除条件动作");
        } catch (Exception e) {
            e.printStackTrace();
            success.put("success", false);
            logger.error("控制面板删除条件动作失败", e);
        }
        return Results.json().asRoot(success).done();
    }


    public String getConditionId() {
        return conditionId;
    }

    public void setConditionId(String conditionId) {
        this.conditionId = conditionId;
    }
}
