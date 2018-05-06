package com.microwise.blackhole.service;

import java.util.List;


import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.blackhole.bean.User;
import com.microwise.common.sys.test.CleanDBTest;

/**
 * 测试用户Service
 * 
 * @author zhangpeng
 * @date 2012-11-19
 * 
 * @check 2012-12-19 xubaoji svn:877
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceRTest extends CleanDBTest {

	/** 用户Service */
	@Autowired
	private UserService userService;

	@Before
	public void before() throws Exception {
		CleanDBTest.xmlInsert2("common/dbxml/blackhole/UserServiceTest.xml");
	}

	/**
	 * 测试根据logicGroupId和用户名查询用户列表
	 * 
	 * @author zhangpeng
	 * @date 2012-11-19
	 */
	@Test
	public void testFindUserList() {
		List<User> userList = userService.findUserList(1, null, 1, 10);
		Assert.assertEquals(2, userList.size());
	}

	/**
	 * 测试根据logicGroupId和用户名查询用户数量
	 * 
	 * @author zhangpeng
	 * @date 2012-11-19
	 */
	@Test
	public void testFindUserListCount() {
		int count = userService.findUserListCount(1, null);
		Assert.assertEquals(2, count);
		int count2 = userService.findUserListCount(1, "大头");
		Assert.assertEquals(1, count2);
	}

	/**
	 * 测试根据用户id查询用户
	 * 
	 * @author zhangpeng
	 * @date 2012-11-19
	 */
	@Test
	public void testFindUserById() {
		User user = userService.findUserById(101);
		Assert.assertNotNull(user);
	}

	/**
	 * 测试根据邮箱token查询用户
	 * 
	 * @author zhangpeng
	 * @date 2012-11-26
	 */
	@Test
	public void testFindUserByToken() {
		User user = userService.findUserByToken("token1");
		Assert.assertEquals(100, user.getId());
	}

	/**
	 * 测试根据站点id查询管理员用户
	 * 
	 * @author zhangpeng
	 * @date 2012-11-19
	 */
	@Test
	public void testFindManagerByLogicGroupId() {
		User user = userService.findManagerByLogicGroupId(1);
		Assert.assertEquals(100, user.getId());
	}

	/**
	 * 根据email查询用户
	 * 
	 * @author zhangpeng
	 * @date 2012-11-19
	 */
	@Test
	public void testFindUserByEmail() {
		User user = userService.findUserByEmail("962097540@qq.com");
		Assert.assertEquals(101, user.getId());
	}
	
	/**测试 查询站点下所有已激活 可用的用户*/
	@Test
	public void testFindUserLists(){
		
		List<User> users = userService.findUserLists(1,null,1,3);
	    Assert.assertNotNull(users);
	    Assert.assertEquals(1, users.size());
		
	}

}
