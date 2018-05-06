package com.microwise.uma.action.rule;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.uma.service.RuleService;
import com.microwise.uma.sys.Uma;
import com.microwise.uma.sys.UmaLoggerAction;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 验证行为规则 是否存在 action
 *
 * @author xubaoji
 * @date 2013-4-17
 *
 * @check @wang yunlong 2013-5-6 #3177
 * @check @hou.xiaocheng 2013-06-04 #3238
 */
@Beans.Action
@Uma
public class RuleExistAction  extends UmaLoggerAction{

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
     * 规则名称
     */
    private String ruleName;

    // Output
    /**
     * 名称是否可用
     */
    private boolean usable;

    /**
     * 验证行为规则 是否存在
     * @return
     */
    public String ruleExist() {

        try {
            // 获得siteId 当前站点编号
            String siteId = Sessions.createByAction().currentLogicGroup().getSite().getSiteId();

            usable = ruleService.isUsable(ruleName, id, siteId);
        } catch (Exception e) {
            logFailed("通过名称验证行为规则出错！","验证行为规则是否存在失败");
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public boolean isUsable() {
        return usable;
    }

    public void setUsable(boolean usable) {
        this.usable = usable;
    }
}
