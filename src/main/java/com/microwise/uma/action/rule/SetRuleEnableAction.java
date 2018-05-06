package com.microwise.uma.action.rule;

import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.uma.service.RuleService;
import com.microwise.uma.sys.Uma;
import com.microwise.uma.sys.UmaDaemonApiClients;
import com.microwise.uma.sys.UmaLoggerAction;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 停用/启用规则 action
 *
 * @author xubaoji
 * @date 2013-4-11
 *
 * @check @wang yunlong 2013-5-6 #3209
 * @check @li.jianfei 2013-06-04 #3715
 */
@Beans.Action
@Uma
public class SetRuleEnableAction extends UmaLoggerAction {

    /**
     * 行为规则service
     */
    @Autowired
    private RuleService ruleService;

    // Input
    /**
     * 行为规则id
     */
    private Integer id;

    /**
     * 禁用或者启用
     */
    private boolean enable;

    /**
     * 停用/启用规则
     * @return
     */
    public String setRuleEnable() {
        try {
            String msg = enable ? "启用行为规则成功" : "停用行为规则成功";
            ruleService.setRuleEnable(id, enable);
            boolean success = UmaDaemonApiClients.getClient().ruleChanged();
            if (success) {
                ActionMessage.createByAction().success(msg);
            } else {
                ActionMessage.createByAction().fail(msg + "，通知后台任务失败！");
            }
            log("行为规则管理", "行为规则");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("服务端异常,操作失败！");
            logFailed("行为规则", "停用/启用行为规则");
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
