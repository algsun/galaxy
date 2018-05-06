package com.microwise.blackhole.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;


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
public class UserServiceCDTest extends CleanDBTest {

	/** 用户Service */
	@Autowired
	private UserService userService;

	@BeforeClass
	public static void before() throws Exception {
		CleanDBTest.xmlInsert2("common/dbxml/blackhole/UserServiceTest.xml");
	}

	/**
	 * 测试添加用户
	 * 
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 * 
	 * @author zhangpeng
	 * @date 2012-11-19
	 */
    @Ignore
	@Test
	public void testSaveUser() throws UnsupportedEncodingException,
			MessagingException {
		User user = new User();
		user.setUserName("测试添加用户");
		user.setEmail("no-reply-galaxy@microwise-system.com");
		user.setPassword("21212121212121");
		user.setMobile("13542153626");
		user.setSex(1);
		user.setCreateTime(new Date());
		user.setToken("1212131342343");
		user.setDisable(true);
		user.setActive(false);
		List<Integer> roleIdList = new ArrayList<Integer>();
		userService.saveUser(user, 1,"添加测试部门", roleIdList, "url",true);
		Assert.assertNotNull(userService
				.findUserByEmail("no-reply-galaxy@microwise-system.com"));
	}

	/**
	 * 测试删除用户
	 * 
	 * @author zhangpeng
	 * @date 2012-11-20
	 */
	@Test
	public void testDeleteUserById() {
		userService.deleteUserById(101);
		Assert.assertNull(userService.findUserById(101));
	}
	
	@Test
	public void testUpdateUserDepartment(){
		userService.updateUserDepartment(101, 1, "aa");
		userService.updateUserDepartment(101, 2, "aa");
		userService.updateUserDepartment(101, 1, null);
		System.out.println();
	}
	

	/**
	 * 测试重置用户个性化设置
	 * 
	 * @author zhangpeng
	 * @date 2012-11-19
	 */
	@Test
	public void testResetDefalultSetting() {
		// TODO 没时间专门搞这个的测试
		userService.resetDefalultSetting(100);
	}

}
