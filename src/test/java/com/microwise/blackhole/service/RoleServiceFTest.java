package com.microwise.blackhole.service;

import com.google.common.base.Objects;
import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.bean.Role;
import com.microwise.common.sys.test.CleanDBTest;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 测试角色Service
 * 
 * @author 许保吉
 * @date 2012-11-19
 * 
 * @check 2012-12-19 zhangpeng svn:885
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoleServiceFTest extends CleanDBTest {

	/** 角色Service */
	@Autowired
	private RoleService roleService;

	@Before
	public void setbefor() throws Exception {
		xmlInsert2("common/dbxml/blackhole/RoleServiceTest.xml");
	}

	/**
	 * 通过分组编号获得该组下的 所有角色
	 * 
	 * @author 许保吉
	 * @date 2012-11-19
	 */
	@Test
	public void testFindRoleListByLogicGroupId() {
		List<Role> roles = roleService.findRoleListByLogicGroupId(1, null);
		Assert.assertEquals("通过logicGroup编号获得该站点下的所有角色出错", 3, roles.size());
		List<Role> roles1 = roleService.findRoleListByLogicGroupId(1, true);
		Assert.assertEquals("通过logicGroup编号获得该站点下管理员角色出错", 1, roles1.size());
		List<Role> roles2 = roleService.findRoleListByLogicGroupId(1, false);
		Assert.assertEquals("通过logicGroup编号获得该站点下非管理员角色出错", 2, roles2.size());
	}

	/**
	 * 通过分组编号获得该组下的 所有角色(分页查询)
	 * 
	 * @author 许保吉
	 * @date 2012-11-21
	 */
	@Test
	public void testFindRoleListByLogicGroupIdByPage() {
		List<Role> roles = roleService.findRoleListByLogicGroupId(1, "管理员1", 1, 3);
		Assert.assertEquals("分页查询角色出错", 1, roles.size());
		List<Role> roles1 = roleService.findRoleListByLogicGroupId(1, null, 2, 2);
		Assert.assertEquals("分页查询角色出错", 1, roles1.size());
	}

	/**
	 * 通过分组编号获得该组下角色的 总记录数
	 * 
	 * @author 许保吉
	 * @date 2012-11-21
	 */
	@Test
	public void testFindRoleCountByLogicGroupId() {
		int count = roleService.findRoleCountByLogicGroupId(1, "管理员");
		Assert.assertEquals("获得角色logicGroup编号为1的站点下的角色总数出错", 1, count);
		int count1 = roleService.findRoleCountByLogicGroupId(1, null);
		Assert.assertEquals("获得角色logicGroup编号为1的站点下的角色总数出错", 3, count1);
	}

	/**
	 * 根据用户id查询用户角色列表
	 * 
	 * @author zhangpeng
	 * @date 2012-11-19
	 */
	@Test
	public void testFindRoleListByUserId() {
		List<Role> roles = roleService.findRoleListByUserId(1);
		Assert.assertTrue("根据用户编号获得角色失败", roles.size() > 0);
	}

	/**
	 * 通过角色编号获得一个角色
	 * 
	 * @author 许保吉
	 * @date 2012-11-22
	 */
	@Test
	public void testFindRoleById() {
		Role role = roleService.findRoleById(1);
		Assert.assertNotNull("通过编号获得角色失败", role);
		Role role2 = roleService.findRoleById(1232);
		Assert.assertNull("通过编号获得角色出错", role2);

	}

}
