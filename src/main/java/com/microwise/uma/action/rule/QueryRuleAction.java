package com.microwise.uma.action.rule;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.PagingUtil;
import com.microwise.uma.bean.RuleBean;
import com.microwise.uma.service.RuleService;
import com.microwise.uma.sys.Uma;
import com.microwise.uma.sys.UmaLoggerAction;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 查询行为规则 action
 *
 * @author xubaoji
 * @date 2013-4-11
 *
 * @check @wang yunlong 2013-5-6 #2959
 * @check @hou.xiaocheng 2013-06-04 #3291
 */
@Beans.Action
@Uma
public class QueryRuleAction extends UmaLoggerAction {

    /**
     * 行为规则service
     */
    @Autowired
    private RuleService ruleService;


    // Input
    /**
     * 行为规则名称
     */
    private String ruleName;

    /**
     * 行为规则类型
     */
    private Integer type;

    /**
     * 当前页码
     */
    private Integer index;

    // Output
    /**
     * 行为规则列表
     */
    private List<RuleBean> ruleList;

    /**
     * 行为规则总页数
     */
    private Integer pageCount;

    /**
     * 查询行为规则
     * @return
     */
    public String queryRule() {

        try {
            ActionMessage.createByAction().consume();

            // 获得siteId 当前站点编号
            String siteId = Sessions.createByAction().currentLogicGroup()
                    .getSite().getSiteId();
            index = (index == null ? 1 : index);

            ruleList = ruleService.findRule(ruleName, type, Constants.SIZE_PER_PAGE, index, siteId);

            int count = ruleService.findRuleCount(ruleName, type, siteId);
            pageCount = PagingUtil.pagesCount(count, Constants.SIZE_PER_PAGE);
            log("统计分析","查询人员行为");
        } catch (Exception e) {
            logFailed("查询行为规则列表出错！","查询人员行为失败");
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    public RuleService getRuleService() {
        return ruleService;
    }

    public void setRuleService(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<RuleBean> getRuleList() {
        return ruleList;
    }

    public void setRuleList(List<RuleBean> ruleList) {
        this.ruleList = ruleList;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

}
