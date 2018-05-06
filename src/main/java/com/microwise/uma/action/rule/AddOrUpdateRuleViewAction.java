package com.microwise.uma.action.rule;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.uma.bean.RuleBean;
import com.microwise.uma.service.RuleService;
import com.microwise.uma.sys.Uma;
import com.microwise.uma.sys.UmaLoggerAction;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 添加/修改 行为规则 页面跳转 action
 *
 * @author xubaoji
 * @date 2013-4-11
 *
 * @check @wang yunlong 2013-5-6 #2861
 */
@Beans.Action
@Uma
public class AddOrUpdateRuleViewAction extends UmaLoggerAction {

    /**
     * 行为规则service
     */
    @Autowired
    private RuleService ruleService;

    // Input
    /**
     * 行为规则id编号
     */
    private Integer ruleId;

    // Output
    /**
     * 行为规则实体对象
     */
    private RuleBean rule;

    /**
     * 添加 行为规则 页面跳转
     * @return
     */
    public String addRuleView() {
        return Action.SUCCESS;
    }

    /**
     * 修改 行为规则 页面跳转
     * @return
     */
    public String updateRuleView() {
        try {
            // 获得要编辑的行为规则
            rule = ruleService.findRulesById(ruleId);
            log("行为规则管理","修改行为规则");
        } catch (Exception e) {
            logFailed("跳转添加行为规则页面出错！", "修改行为规则失败");
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public RuleBean getRule() {
        return rule;
    }

    public void setRule(RuleBean rule) {
        this.rule = rule;
    }
}
