package com.microwise.phoenix.service;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.phoenix.bean.po.uma.ActionStat;
import com.microwise.common.sys.Constants;

/**
 * 人员管理：规则统计service测试
 * 
 * @author xu.baoji
 * @date 2013-7-17
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ActionStatServiceTest extends CleanDBTest {

	/*** 规则统计 */
	@Autowired
	private ActionStatService actionStatService;

	@Before
	public void before() throws Exception {
		CleanDBTest.xmlInsert2("common/dbxml/phoenix/ActionStatServiceTest.xml");
	}

	/** 规则统计查询接口测试 */
	@Test
	public void TestFindActionStat() {
        Date date = new DateTime().withYear(2013).toDate();
		List<ActionStat> actionStats = actionStatService.findActionStat("31010101", date,
				Constants.FIND_TYPE_YEAR);
		Assert.assertNotNull(actionStats);
		Assert.assertTrue(actionStats.size()>0);
	}
}
