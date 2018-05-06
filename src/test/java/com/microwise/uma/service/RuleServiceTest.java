package com.microwise.uma.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.uma.bean.DeviceBean;
import com.microwise.uma.bean.RuleBean;

/**
 * @author xubaoji
 * @date 2013-4-18
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RuleServiceTest extends CleanDBTest {

    /**
     * 行为规则service
     */
    @Autowired
    private RuleService ruleService;

    @Before
    public void before() throws Exception {
        CleanDBTest.xmlInsert2("common/dbxml/uma/RuleServiceTest.xml");
    }

    /**
     * 行为规则分页查询测试
     *
     * @author 许保吉
     * @date 2013-4-18
     */
    @Test
    public void findRuleTest() {
        List<RuleBean> ruleList = ruleService.findRule(null, null, 2, 1, "aa");
        Assert.assertNotNull("分页查询行为规则出错", ruleList);
        Assert.assertTrue("分页查询行为规则出错", ruleList.size() > 0);
        ruleList = ruleService.findRule("1", null, 3, 1, "aa");
        Assert.assertNotNull("分页查询行为规则出错", ruleList);
        Assert.assertTrue("分页查询行为规则出错", ruleList.size() == 1);
        ruleList = ruleService.findRule("1", 2, 3, 1, "aa");
        Assert.assertTrue("分页查询行为规则出错", ruleList == null
                || ruleList.size() == 0);
    }

    /**
     * 查询行为规则数量测试
     *
     * @author 许保吉
     * @date 2013-4-18
     */
    @Test
    public void findRuleCountTest() {

        Integer count = ruleService.findRuleCount("", null, "aa");
        Assert.assertTrue("查询行为规则数量出错", count == 3);
        count = ruleService.findRuleCount("1", 1, "aa");
        Assert.assertTrue("查询行为规则数量出错", count == 1);

    }

    /**
     * 判断行为规则名称 是否可用 测试
     *
     * @author 许保吉
     * @date 2013-4-18
     */
    @Test
    public void isUsableTest() {
        boolean is = ruleService.isUsable("规则1", 1, "aa");
        Assert.assertTrue(is);
        boolean is1 = ruleService.isUsable("规则3", null, "aa");
        Assert.assertFalse(is1);

    }

}
