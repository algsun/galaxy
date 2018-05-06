package com.microwise.uma.bean;

import java.util.List;

/**
 * 行为规则统计 bean
 *
 * @author gaohui
 * @date 13-4-26 11:10
 */
public class RuleStatBean extends RuleBean {
    // 匹配的次数
    private int count;

    // 子规则的统计(如果父规则类型是“往返”)
    List<RuleStatBean> childRuleStats;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<RuleStatBean> getChildRuleStats() {
        return childRuleStats;
    }

    public void setChildRuleStats(List<RuleStatBean> childRuleStats) {
        this.childRuleStats = childRuleStats;
    }
}
