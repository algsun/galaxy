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
public class RoleServiceHTest extends CleanDBTest {

	/** 角色Service */
	@Autowired
	private RoleService roleService;

	@Before
	public void setbefor() throws Exception {
		xmlInsert2("common/dbxml/blackhole/RoleServiceTest.xml");
	}

	/**
	 * 判断角色是否存在
	 * 
	 * @author 许保吉
	 * @date 2012-11-19
	 */
	@Test
	public void testHasSameRole() {
		boolean is0 = roleService.hasSameRole(1, 0, "超级管理员");
		Assert.assertTrue("当前logicGroup下存在超级管理员角色", !is0);
		boolean is = roleService.hasSameRole(1, 0, "管理员1");
		Assert.assertTrue("当前logicGroup下不存在管理员1角色", is);
		boolean is1 = roleService.hasSameRole(1, 1, "管理员1");
		Assert.assertFalse("当前logicGroup下不存在管理员1角色",is1);
		boolean is2 = roleService.hasSameRole(1, 1, "神");
		Assert.assertFalse("当前logicGroup下不存在神",is2);
	}

}
