package com.microwise.phoenix.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.phoenix.bean.po.uma.UserStat;
import com.microwise.common.sys.Constants;

/**
 * 人员管理：人员统计service测试
 * 
 * @author xu.baoji
 * @date 2013-7-16
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserStatServiceTest extends CleanDBTest {

	/*** 人员统计接口 */
	@Autowired
	private UserStatService userStatService;

	@Before
	public void before() throws Exception {
		CleanDBTest.xmlInsert2("common/dbxml/phoenix/UserStatServiceTest.xml");
	}

	/** 查询人员区域活动时长统计测试 */
	@Test
	public void TestFindUserFrequencyOfActivitiesStat() {
        Date date = new DateTime().withYear(2013).toDate();
		List<UserStat> userStats = userStatService.findUserFrequencyOfActivitiesStat(1, date, Constants.FIND_TYPE_YEAR, 10, true);
		Assert.assertNotNull(userStats);
		Assert.assertTrue(userStats.size() > 0);

        date=new DateTime().withYear(2013).withMonthOfYear(4).toDate();
		userStats = userStatService.findUserFrequencyOfActivitiesStat(1, date, Constants.FIND_TYPE_MONTH, 10, true);
		Assert.assertNotNull(userStats);
		Assert.assertTrue(userStats.size() == 0);

		userStats = userStatService.findUserFrequencyOfActivitiesStat(1, date, Constants.FIND_TYPE_YEAR, 10, false);
		Assert.assertNotNull(userStats);
		Assert.assertTrue(userStats.size() > 0);
		userStats = userStatService.findUserFrequencyOfActivitiesStat(1, date, Constants.FIND_TYPE_MONTH, 10, false);
		Assert.assertNotNull(userStats);
		Assert.assertTrue(userStats.size() > 0);

	}

	/** 查询人员活动次数为n 的人员数量 */
	@Test
	public void testFindUserCountByActivityCount() {
        Date date = new DateTime().withYear(2013).toDate();
		int count = userStatService.findUserCountByActivityCount(1, date, Constants.FIND_TYPE_YEAR, 1);
		Assert.assertEquals(1, count);

        date=new DateTime().withYear(2013).withMonthOfYear(4).toDate();
		count = userStatService.findUserCountByActivityCount(1, date, Constants.FIND_TYPE_YEAR, 0);
		Assert.assertEquals(0, count);
	}

	/** 判断是否有数据 */
	@Test
	public void testHasData() {
        Date date = new DateTime().withYear(2013).toDate();
		boolean is = userStatService.hasData(1, date, Constants.FIND_TYPE_YEAR);
		Assert.assertTrue(is);
        
        date=new DateTime().withYear(2013).withMonthOfYear(4).toDate();
		is = userStatService.hasData(1, date, Constants.FIND_TYPE_MONTH);
		Assert.assertFalse(is);
	}

	/** 查询人员早晚时刻统计 */
	@Test
	public void testFindUserMorningAndEveningStat() {
        Date date = new DateTime().withYear(2013).toDate();
		Map<String, Integer> stat = userStatService.findUserMorningAndEveningStat(1, date, Constants.FIND_TYPE_YEAR, true,10);
		Assert.assertNotNull(stat);
	}

	/** 查询 早晚 人员 测试 */
	@Test
	public void testMoringAndEveningUser() {
        Date date = new DateTime().withYear(2013).toDate();
		Map<String, Date> map = userStatService.findMorningAndEveningUser(1, date, Constants.FIND_TYPE_YEAR, true);
		Assert.assertNotNull(map);
	}
}
