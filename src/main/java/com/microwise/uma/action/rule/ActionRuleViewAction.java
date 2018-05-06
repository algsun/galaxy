package com.microwise.uma.action.rule;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.uma.bean.RuleStatBean;
import com.microwise.uma.service.RuleStatService;
import com.microwise.uma.sys.Uma;
import com.microwise.uma.sys.UmaLoggerAction;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 行为统计 页面跳转 action
 *
 * @author wangrensong
 * @date 2013-4-26
 *
 * @check li.jianfei 2013-5-3 #3146
 */
@Beans.Action
@Uma
public class ActionRuleViewAction extends UmaLoggerAction {

    /**
     * 行为规则service
     */
    @Autowired
    private RuleStatService ruleStatService;


    // input&output
    /**
     * 按年月日类型查询 年：1 月：2 日：3
     */
    private int dateType;

    /**
     * 时间
     */
    private Date date;

    /**
     * 规则名称
     */
    private String ruleName;

    // output
    /**
     * 行为规则列表
     */
    private List<RuleStatBean> actionRulelist;

    /**
     * 行为统计
     * @return
     */
    public String execute() {
        try {

            date = (date == null) ? (new Date()) : date;

            // 获得siteId 当前站点编号
            String siteId = Sessions.createByAction().currentLogicGroup()
                    .getSite().getSiteId();

            // 默认查询当天数据
            dateType = (dateType == 0) ? 3 : dateType;

            actionRulelist = ruleStatService.findRuleStats(siteId, dateType,
                    date, ruleName);

            log("统计分析","查询行为统计");
        } catch (Exception ex) {
            logFailed("查询行为规则列表出错！", "查询行为统计失败");
            ex.printStackTrace();
        }
        return Action.SUCCESS;
    }

    public int getDateType() {
        return dateType;
    }

    public void setDateType(int dateType) {
        this.dateType = dateType;
    }

    public List<RuleStatBean> getActionRulelist() {
        return actionRulelist;
    }

    public void setActionRulelist(List<RuleStatBean> actionRulelist) {
        this.actionRulelist = actionRulelist;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

}
