package com.microwise.blackhole.service;

import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.common.sys.test.CleanDBTest;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 测试 logicGroupSerice
 * 
 * @author xubaoji
 * @date 2012-11-20
 * 
 * @check 2012-12-19 zhangpeng svn:885
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LogicGroupServiceSaveTest extends CleanDBTest {

	/** 注入 logicGroupService */
	@Autowired
	private LogicGroupService logicGroupService;

	/** 注入用户service */
	@Autowired
	private UserService userService;

	@Before
	public void setbefor() throws Exception {
		xmlInsert2("common/dbxml/blackhole/LogicGroupServiceSaveTest.xml");
	}

	/**
	 * 后置方法，清库（排除字典表等关键信息表）
	 */
	@After
	public void setAfter() throws Exception {
		tearDownOperation();
	}

	/**
	 * 行政站点 没有对应site 的logicGroup添加
	 * 
	 * @author 许保吉
	 * @date 2012-11-20
	 */
    @Ignore
	@Test
	public void testSaveLogicGroup() {
		try {
			logicGroupService.saveLogicGroup("测试创建顶级站点无site",
					"no-reply-galaxy@microwise-system.com", null, "token",
					"url");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		Assert.assertNotNull("添加行政站点失败",
				logicGroupService.findAdmimLogicGroups(null));
	}

	/**
	 * 行政站点 没有对应site 的logicGroup添加
	 * 
	 * @author 许保吉
	 * @date 2012-11-20
	 */
    @Ignore
	@Test
	public void testSaveLogicGroup1() {
		try {
			logicGroupService.saveLogicGroup("测试创建行政站点",
					"no-reply-galaxy@microwise-system.com", 1, "token", "url");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(
				"添加一个行政站点下的子站点失败",
				logicGroupService.findSubLogicGroupList(
						logicGroupService.findAdmimLogicGroups(null).get(0)
								.getId()).size() > 0);
	}

	/**
	 * 有对应site 的logicGroup 添加
	 * 
	 * @author 许保吉
	 * @date 2012-11-20
	 */
    @Ignore
	@Test
	public void testSaveLogicGroup2() {
		try {
			logicGroupService.saveLogicGroup("添加测试顶级站点",
					"no-reply-galaxy@microwise-system.com", null, "11010101",
					"", "");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		Assert.assertTrue("添加顶级基层站点失败", logicGroupService
				.findSubLogicGroupList(null).size() > 0);
	}

	/**
	 * 有对应site 的logicGroup 添加
	 * 
	 * @author 许保吉
	 * @date 2012-11-20
	 */
    @Ignore
	@Test
	public void testSaveLogicGroup3() {
		try {
			logicGroupService.saveLogicGroup("添加测试顶级站点1",
					"no-reply-galaxy@microwise-system.com", 1, "11010102", "",
					"");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		Assert.assertTrue("在站点编号为1 的行政站点下添加基层站点失败", logicGroupService
				.findSubLogicGroupList(1).size() > 0);
	}

	/**
	 * 根据logicGroupId分页查询直接子站点列表（无分页）
	 * 
	 * @author 许保吉
	 * @date 2012-11-21
	 */
	@Test
	public void testFindSubLogicGroupList() {
		List<LogicGroup> logicGroups = logicGroupService
				.findSubLogicGroupList(null);
		Assert.assertTrue("获得站点编号为1的站点下的子站点失败", logicGroups.size() > 0);
	}

	/**
	 * 同步 logicGroup 的 管理员初始化同步
	 * 
	 * @author 许保吉
	 * @date 2012-11-27
	 */
    @Ignore
	@Test
	public void testInitAdminToLogicGroup() {
		try {
			logicGroupService.initAdminToLogicGroup(
					"no-reply-galaxy@microwise-system.com", 4, "", "");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		Assert.assertTrue("初始化同步站点管理失败",
				userService.findUserListCount(4, null) >= 1);
	}

	/**
	 * 同步 logicGroup 的 管理员初始化同步
	 * 
	 * @author 许保吉
	 * @date 2012-11-27
	 */
	@Test
	public void testFindUserLogicGroupsCarrySite() {
		List<LogicGroup> lList = logicGroupService
				.findUserLogicGroupsCarrySite(1);
		Assert.assertEquals(3, lList.size());
	}

}
