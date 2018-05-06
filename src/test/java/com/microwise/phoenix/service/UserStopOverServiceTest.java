package com.microwise.phoenix.service;

import java.util.Date;

import org.joda.time.DateTime;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.phoenix.bean.vo.UserStopOver;
import com.microwise.common.sys.Constants;

/**
 * 人员管理：人员区域活动时长统计service测试
 * 
 * @author xu.baoji
 * @date 2013-7-4
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserStopOverServiceTest extends CleanDBTest {

	@Autowired
	private UserStopOverService userStopOverService;

	@Before
	public void before() throws Exception {
		CleanDBTest.xmlInsert2("common/dbxml/phoenix/UserStopOverServiceTest.xml");
	}

	/** 查询人员区域活动时长统计测试 */
    @Ignore
    @Test
	public void TestFindUserStopOver() {
      Date date = new DateTime().withYear(2013).toDate();
	  UserStopOver userStopOver = userStopOverService.findUserStopOver("31010101", date, Constants.FIND_TYPE_YEAR);
	  Assert.assertTrue(userStopOver.isHasData());
	  Assert.assertTrue(userStopOver.getZoneNames().size()>0);
	  userStopOver = userStopOverService.findUserStopOver("31010101", new Date(), Constants.FIND_TYPE_MONTH);
	  Assert.assertFalse(userStopOver.isHasData());
	}

}
