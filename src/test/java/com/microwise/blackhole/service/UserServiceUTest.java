package com.microwise.blackhole.service;

import com.microwise.blackhole.bean.User;
import com.microwise.common.sys.test.CleanDBTest;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
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
public class UserServiceUTest extends CleanDBTest {

	/** 用户Service */
	@Autowired
	private UserService userService;

	/** 角色Service */
	@Autowired
	private RoleService roleService;

	/** 逻辑站点Service */
	@Autowired
	private LogicGroupService logicGroupService;

	@Before
	public void before() throws Exception {
		CleanDBTest.xmlInsert2("common/dbxml/blackhole/UserServiceTest.xml");
	}

	/**
	 * 测试 修改用户状态
	 * 
	 * @author zhangpeng
	 * @date 2012-11-19
	 */
	@Test
	public void testChangeUserDisableState() {
		userService.changeUserDisableState(101);
		Assert.assertTrue(userService.findUserById(101).isDisable());
	}

	/**
	 * 测试更新用户基本信息
	 * 
	 * @author zhangpeng
	 * @date 2012-11-19
	 */
	@Test
	public void testUpdateUser() {
		userService.updateUser(101, "新名字", 1, "15832641256");
		Assert.assertEquals("新名字", userService.findUserById(101).getUserName());
	}

	/**
	 * 测试更新用户
	 * 
	 * @author zhangpeng
	 * @date 2012-11-19
	 */
	@Test
	public void testUpdateUser2() {
		User user = userService.findUserById(101);
		user.setUserName("测试添加用户");
		List<Integer> roleIdList = new ArrayList<Integer>();
		roleIdList.add(2);
		roleIdList.add(3);
		userService.updateUser(user, roleIdList);
		Assert.assertEquals("测试添加用户", userService.findUserById(101)
				.getUserName());
	}

	/**
	 * 测试给用户分配角色（一个或多个）
	 * 
	 * @author zhangpeng
	 * @date 2012-11-20
	 */
	@Test
	public void testAssignRoleToUser() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(2);
		list.add(3);
		userService.assignRoleToUser(101, list);
		Assert.assertEquals(2, roleService.findRoleListByUserId(101).size());
	}

	/**
	 * 测试给用户指定分组（一个或多个，可复合）
	 * 
	 * @author zhangpeng
	 * @date 2012-11-20
	 */
	@Test
	public void testAssignLogicGroupToUser() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		userService.assignLogicGroupToUser(100, list);
		Assert.assertEquals(1, logicGroupService.findUserLogicGroups(100)
				.size());
	}

	/**
	 * 测试更新用户密码
	 * 
	 * @author zhangpeng
	 * @date 2012-11-19
	 */
	@Test
	public void testUpdateUserPassword() {
		userService.updateUserPassword(100, "1111111111112222");
		Assert.assertTrue(BCrypt.checkpw("1111111111112222", userService
				.findUserById(100).getPassword()));

	}

	/**
	 * 测试根据用户id更新用户photo
	 * 
	 * @author zhangpeng
	 * @date 2012-11-20
	 */
	@Test
	public void testUpdateUserPhoutById() {
		userService.updateUserPhotoById(100, "测试头像");
		User user = userService.findUserById(100);
		Assert.assertEquals("测试头像", user.getPhoto());
	}

	/**
	 * 测试激活用户
	 * 
	 * @author zhangpeng
	 * @date 2012-12-03
	 */
	@Test
	public void testActiveUser() {
		userService.activeUser(101, "id55用户", "123", 1, "13511111111");
		User user = userService.findUserById(100);
		Assert.assertEquals(true, user.isActive());
	}

    @Test
    public void testFindUserByLogicGroupIdAndPrivilegeId(){
        List<String> userList = userService.findUserByLogicGroupIdAndPrivilegeId(1,"blackhole:currentLogicGroup");
        String userId = userList.get(0);
        String expectedValue = "100";
        Assert.assertEquals(expectedValue ,userId);
    }

}
