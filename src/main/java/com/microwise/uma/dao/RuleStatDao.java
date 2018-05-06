package com.microwise.uma.dao;

import com.microwise.uma.bean.RuleStatBean;

import java.util.List;

/**
 * 行为规则统计
 *
 * @author gaohui
 * @date 13-4-26 11:16
 * @check   li.jianfei 2013-5-6 #2977
 */
public interface RuleStatDao {
    /**
     * 查询规则统计信息
     *
     * @param siteId    站点 id
     * @param startTime 开始时间(毫秒)
     * @param endTime   结束时间(毫秒)
     * @param ruleName 规则名称
     * @return
     */
    List<RuleStatBean> findRuleStats(String siteId, long startTime, long endTime, String ruleName);
}
