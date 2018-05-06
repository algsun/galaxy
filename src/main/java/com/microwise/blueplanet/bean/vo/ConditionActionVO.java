package com.microwise.blueplanet.bean.vo;

import com.microwise.blueplanet.bean.po.ConditionAction;
import com.microwise.blueplanet.bean.po.SwitchAction;

import java.util.List;

/**
 * 控制模块定时动作对象
 *
 * @date 2014-03-05
 */
public class ConditionActionVO extends SwitchAction {

    /**
     * 逻辑关系 1与，2或'
     */
    private int logic;

    /**
     * 动作1开0关
     */
    private int action;

    /**
     * 条件列表
     */
    private List<ConditionAction> conditionActions;

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

    public List<ConditionAction> getConditionActions() {
        return conditionActions;
    }

    public void setConditionActions(List<ConditionAction> conditionActions) {
        this.conditionActions = conditionActions;
    }
}