package com.microwise.blackhole.service;

import com.google.common.base.Objects;
import com.microwise.blackhole.bean.User;
import com.microwise.common.sys.test.CleanDBTest;
import junit.framework.Assert;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 测试用户Service
 * 
 * @author zhangpeng
 * @date 2012-11-19
 * 
 * @check 2012-12-19 xubaoji svn:877
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceFTest extends CleanDBTest {

	/** 用户Service */
	@Autowired
	private UserService userService;

	@Before
	public void before() throws Exception {
		CleanDBTest.xmlInsert2("common/dbxml/blackhole/UserServiceTest2.xml");
	}

	/**
	 * 测试发送找回密码的 邮件
	 *
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 *
	 * @author zhangpeng
	 * @date 2012-11-19
	 */
    @Ignore
	@Test
	public void testSendResetPasswordEmail()
			throws UnsupportedEncodingException, MessagingException {
		User user = userService.findUserById(100);
		Assert.assertNotNull(user);
		userService.sendResetPasswordEmail(user.getEmail(), "token", "url");
		User user2 = userService.findUserById(100);
		Assert.assertFalse(Objects.equal(user.getToken(), user2.getToken()));
	}
    
    @Test
    public void testFind(){
    	List<User> users = userService.findUserListsByDepartment(1, null);
    	Assert.assertNotNull(users);
    }

}
