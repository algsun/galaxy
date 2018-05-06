package com.microwise.phoenix.service;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.phoenix.bean.vo.UserLogLength;
import com.microwise.phoenix.bean.vo.UserLoginStat;
import com.microwise.common.sys.Constants;

/**
 * 系统管理：用户登录习惯统计测试
 * 
 * @author xu.baoji
 * @date 2013-7-17
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserLoginStatServiceTest extends CleanDBTest {

	/*** 用户登录习惯 */
	@Autowired
	private UserLoginStatService userLoginStatService;

	@Before
	public void before() throws Exception {
		CleanDBTest.xmlInsert2("common/dbxml/phoenix/UserLoginStatServiceTest.xml");
	}

	/** 规则统计查询接口测试 */
	@Test
	public void TestFindUserLoginStat() {

        Date date = new DateTime().withYear(2013).toDate();
		UserLoginStat userLoginStat = userLoginStatService.findUserLoginStat(1, date, Constants.FIND_TYPE_YEAR);
		Assert.assertNotNull(userLoginStat);
		Assert.assertTrue(userLoginStat.isHasData());
		Assert.assertNotNull(userLoginStat.getDayStat());
		Assert.assertNotNull(userLoginStat.getWeekStat());

		userLoginStat = userLoginStatService.findUserLoginStat(2, new Date(),
				Constants.FIND_TYPE_MONTH);
		Assert.assertNotNull(userLoginStat);
		Assert.assertFalse(userLoginStat.isHasData());

	}

	/** 用户登录时长统计测试 */
	@Test
	public void TestFindUserLonLength() {
		List<UserLogLength> userLogLengths = userLoginStatService.findUserLonLength(1, new Date(),
				Constants.FIND_TYPE_YEAR,20);
		Assert.assertNotNull(userLogLengths);
		
	}
}
