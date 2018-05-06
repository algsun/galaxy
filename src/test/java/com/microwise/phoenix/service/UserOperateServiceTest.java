package com.microwise.phoenix.service;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.phoenix.bean.vo.SubsystemOperate;
import com.microwise.phoenix.bean.vo.UserOperate;
import com.microwise.common.sys.Constants;

/**
 * 用户 操作记录统计 service 测试
 * 
 * @author xu.baoji
 * @date 2013-7-17
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserOperateServiceTest extends CleanDBTest {

	/*** 用户操作记录统计 */
	@Autowired
	private UserOperateService userOperateService;

	@Before
	public void before() throws Exception {
		CleanDBTest.xmlInsert2("common/dbxml/phoenix/UserOperateServiceTest.xml");
	}

	/** 用户操作记录统计 */
	@Test
	public void TestFindUserOperateService() {
        Date date = new DateTime().withYear(2013).toDate();
		List<UserOperate> userOperates = userOperateService.findUserOperates("a@qq.com",
				date, Constants.FIND_TYPE_YEAR, 10);
		Assert.assertNotNull(userOperates);
		Assert.assertEquals(2, userOperates.size());
	}

	/**业务系统操作统计*/
	@Test
	public void TestFindSubsystemOperate() {
		List<SubsystemOperate> subsystemOperates = userOperateService.findSubsystemOperate(1,
				new Date(), Constants.FIND_TYPE_YEAR, 10);
		Assert.assertNotNull(subsystemOperates);
	}
}
