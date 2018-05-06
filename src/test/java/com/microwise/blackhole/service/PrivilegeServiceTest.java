package com.microwise.blackhole.service;

import com.microwise.blackhole.bean.Privilege;
import com.microwise.common.sys.test.CleanDBTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 测试权限接口
 * 
 * @author zhangpeng
 * @date 2012-11-20
 * 
 * @check 2012-12-19 xubaoji svn:877
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PrivilegeServiceTest extends CleanDBTest {

	/** 权限Service */
	@Autowired
	private PrivilegeService privilegeService;

	@Before
	public void before() throws Exception {
		CleanDBTest.xmlInsert2("common/dbxml/blackhole/PrivilegeServiceTest.xml");
	}

	/**
	 * 测试获取除必选权限外的所有权限
	 * 
	 * @author zhangpeng
	 * @date 2012-11-20
	 */
	@Test
	public void testFindAll() {
		List<Privilege> list = privilegeService.findAll();
		Assert.assertEquals(254, list.size());
	}

	/**
	 * 根据角色id查询角色权限
	 * 
	 * @author zhangpeng
	 * @date 2012-11-20
	 */
	@Test
	public void testFindPrivilegeListByRoleId() {
		List<Privilege> list = privilegeService.findPrivilegeListByRoleId(1);
		Assert.assertEquals(5, list.size());
	}

	/**
	 * 根据业务系统id查询可选权限（已根据父权限id和分组序列排序，携带父权限id）
	 * 
	 * @author zhangpeng
	 * @date 2012-11-20
	 */
	@Test
	public void testFindPrivilegeListBySubId() {
		List<Privilege> list = privilegeService.findPrivilegeListBySubId(1);
		Assert.assertEquals(45, list.size());
	}

	/**
	 * 测试根据角色id查询角色拥有的权限列表（携带父权限信息）
	 * 
	 * @author zhangpeng
	 * @date 2012-11-26
	 */
	@Test
	public void testFindPrivilegesCarryParent() {
		List<Privilege> list = privilegeService.findPrivilegesCarryParent(1,"zh_CN");
		Assert.assertEquals(5, list.size());
	}

	/**
	 * 测试根据角色id查询角色拥有的叶子权限
	 * 
	 * @author zhangpeng
	 * @date 2012-11-26
	 */
	@Test
	public void testFindLeafPrivilegesByRoleId() {
		List<Privilege> list = privilegeService.findLeafPrivilegesByRoleId(1);
		Assert.assertEquals(3, list.size());
	}

	/**
	 * 查询超级管理员权限列表（已根据父权限id和分组序列排序，携带父权限id）
	 * 
	 * @author zhangpeng
	 * @date 2012-11-20
	 */
	@Test
	public void testFindSupermanPrivileges() {
		List<Privilege> list = privilegeService.findSupermanPrivileges("en_US");
		Assert.assertEquals(256, list.size());
	}

	/**
	 * 查询站点管理员权限列表（已根据父权限id和分组序列排序，携带父权限id）
	 * 
	 * @author zhangpeng
	 * @date 2012-11-20
	 */
	@Test
	public void testFindManagerPrivileges() {
		List<Privilege> list = privilegeService.findManagerPrivileges("en_US");
		Assert.assertEquals(253, list.size());
	}

	/**
	 * 查询访客权限列表（已根据父权限id和分组序列排序，携带父权限id）
	 * 
	 * @author zhangpeng
	 * @date 2012-11-20
	 */
	@Test
	public void testFindGuestPrivileges() {
		List<Privilege> list = privilegeService.findGuestPrivileges("en_US");
		Assert.assertEquals(116, list.size());
	}

	/**
	 * 获取所有必选权限
	 * 
	 * @author zhangpeng
	 * @date 2012-11-30
	 */
	@Test
	public void testFinRequiredPrivileges() {
		List<Privilege> list = privilegeService.findRequiredPrivileges("en_US");
		Assert.assertEquals(3, list.size());
	}

}
