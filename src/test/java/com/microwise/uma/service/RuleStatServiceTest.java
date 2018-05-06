package com.microwise.uma.service;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.uma.bean.RuleStatBean;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author gaohui
 * @date 13-4-26 13:46
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RuleStatServiceTest extends CleanDBTest {
    @Autowired
    private RuleStatService ruleStatsService;

    @Before
    public void before() throws Exception {
        CleanDBTest.xmlInsert2("common/dbxml/uma/RuleStatsServiceTest.xml");
    }

    @Test
    public void testFindRuleStats() throws Exception {
        List<RuleStatBean> ruleStats = ruleStatsService.findRuleStats("31010101", 0, 1466954081646L);
        Assert.assertEquals(4, ruleStats.size());
        Assert.assertEquals(2, ruleStats.get(0).getCount());
        Assert.assertEquals(1, ruleStats.get(1).getCount());
        Assert.assertEquals(0, ruleStats.get(2).getCount());
        Assert.assertEquals(1, ruleStats.get(3).getCount());
    }
}
