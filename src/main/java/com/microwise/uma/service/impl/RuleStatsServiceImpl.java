package com.microwise.uma.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.uma.bean.RuleStatBean;
import com.microwise.uma.dao.RuleStatDao;
import com.microwise.uma.service.RuleStatService;
import com.microwise.uma.sys.Uma;
import com.microwise.uma.util.DateTypeGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 规则统计 service 实现
 *
 * @author gaohui
 * @date 13-4-26 11:16
 *
 * @check li.jianfei  2013-5-6 #3052
 */
@Beans.Service
@Uma
public class RuleStatsServiceImpl implements RuleStatService {

    @Autowired
    private RuleStatDao ruleStatDao;

    @Override
    public List<RuleStatBean> findRuleStats(String siteId, long startTime, long endTime) {
        return ruleStatDao.findRuleStats(siteId, startTime, endTime, null);
    }

    @Override
    public List<RuleStatBean> findRuleStats(String siteId, int dateType, Date date, String ruleName) {
        Date startTime = DateTypeGenerator.start(dateType, date);
        Date endTime = DateTypeGenerator.end(dateType, date);

        return ruleStatDao.findRuleStats(siteId, startTime.getTime(), endTime.getTime(), ruleName);
    }
}
