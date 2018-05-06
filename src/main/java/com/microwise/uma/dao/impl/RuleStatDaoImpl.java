package com.microwise.uma.dao.impl;

import com.google.common.collect.Lists;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.uma.bean.RuleBean;
import com.microwise.uma.bean.RuleStatBean;
import com.microwise.uma.dao.RuleStatDao;
import com.microwise.uma.sys.Uma;
import com.microwise.uma.sys.UmaBaseDao;

import java.util.*;

/**
 * 行为规则统计
 *
 * @author gaohui
 * @date 13-4-26 11:17
 * @check   li.jianfei 2013-5-6 #3139
 */
@Beans.Dao
@Uma
public class RuleStatDaoImpl extends UmaBaseDao implements RuleStatDao {

    @Override
    public List<RuleStatBean> findRuleStats(String siteId, long startTime, long endTime, String ruleName) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("siteId", siteId);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("ruleName", ruleName);

        List<RuleStatBean> ruleStats = getSqlSession().selectList("uma.mybatis.RuleStatDao.findRuleStats2", params);
        Map<Integer, RuleStatBean> ruleStatsMap = new LinkedHashMap<Integer, RuleStatBean>();
        // 将单程与往返 按 id 放入 map
        for (RuleStatBean ruleStat : ruleStats) {
            if (ruleStat.getType() == RuleBean.TYPE_SINGLE) {
                ruleStatsMap.put(ruleStat.getId(), ruleStat);
            } else if (ruleStat.getType() == RuleBean.TYPE_CENSUS) {
                ruleStat.setChildRuleStats(new ArrayList<RuleStatBean>());
                ruleStatsMap.put(ruleStat.getId(), ruleStat);
            }
        }

        // 将 "往" 和 "返" 归类到 "往返" 下
        for (RuleStatBean ruleStat : ruleStats) {
            if (ruleStat.getType() == RuleBean.TYPE_GO || ruleStat.getType() == RuleBean.TYPE_BACK) {
                RuleStatBean censusRule = ruleStatsMap.get(ruleStat.getParentId());
                censusRule.getChildRuleStats().add(ruleStat);
            }
        }

        for (RuleStatBean ruleStat : ruleStatsMap.values()) {
            if (ruleStat.getType() == RuleBean.TYPE_CENSUS) {
                // 子规则按类型排序
                Collections.sort(ruleStat.getChildRuleStats(), new Comparator<RuleStatBean>() {
                    @Override
                    public int compare(RuleStatBean o1, RuleStatBean o2) {
                        return o1.getType() - o2.getType();
                    }
                });
            }
        }

        return Lists.newArrayList(ruleStatsMap.values());
    }
}
