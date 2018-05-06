package com.microwise.uma.service;

import com.microwise.uma.bean.RuleStatBean;

import java.util.Date;
import java.util.List;

/**
 * 规则统计 service
 *
 * @author gaohui
 * @date 13-4-26 11:15
 * @check  li.jianfei 2013-5-6  #3129
 */
public interface RuleStatService {

    /**
     * 查询规则统计信息
     *
     * @param siteId    站点 id
     * @param startTime 开始时间(毫秒)
     * @param endTime   结束时间(毫秒)
     * @return
     */
    List<RuleStatBean> findRuleStats(String siteId, long startTime, long endTime);

    /**
     * 根据时间类型查询规则统计
     *
     *
     * @param siteId 站点 id
     * @param dateType 1.年 2.月 3.日
     * @param date 日期
     * @param ruleName 行为规则名称
     * @return
     */
    List<RuleStatBean> findRuleStats(String siteId, int dateType, Date date, String ruleName);
}
