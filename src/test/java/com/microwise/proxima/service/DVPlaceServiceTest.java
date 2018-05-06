package com.microwise.proxima.service;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.proxima.bean.DVPlaceBean;

/**
 * 摄像机点位Service
 * 
 * @author zhangpeng
 * @date 2013-3-25
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DVPlaceServiceTest extends CleanDBTest {

	/** 摄像机Service */
	@Autowired
	private DVPlaceService dvPlaceService;

	@Before
	public void before() throws Exception {
		CleanDBTest.xmlInsert2("common/dbxml/proxima/DVPlaceServiceTest.xml");
	}

	/**
	 * 测试根据id查询摄像机点位
	 * 
	 * @author zhangpeng
	 * @date 2013-3-25
	 */
	@Test
	public void testFindByid() {
		DVPlaceBean dv = dvPlaceService.findById("123");
		Assert.assertEquals("点位名称", dv.getPlaceName());
	}
	
	/**
	 * 测试修改摄像机启用禁用状态
	 * 
	 * @author zhangpeng
	 * @date 2013-3-26
	 */
	@Test
	public void testFindDvEnable() {
		boolean bool = dvPlaceService.findDvEnable("123");
		Assert.assertTrue(bool);
	}

	/**
	 * 测试修改摄像机启用禁用状态
	 * 
	 * @author zhangpeng
	 * @date 2013-3-26
	 */
	@Test
	public void testHasSameNameByAdd() {
		boolean bool = dvPlaceService.hasSameNameByAdd("333", "测试重名");
		Assert.assertTrue(bool);
	}

	/**
	 * 测试修改摄像机启用禁用状态
	 * 
	 * @author zhangpeng
	 * @date 2013-3-26
	 */
	@Test
	public void testHasSameNameByUpdate() {
		boolean bool = dvPlaceService.hasSameNameByUpdate("333", "测试重名", "124");
		Assert.assertFalse(bool);
	}

	/**
	 * 测试修改摄像机启用禁用状态
	 * 
	 * @author zhangpeng
	 * @date 2013-3-26
	 */
	@Test
	public void testHasSameName() {
		boolean bool = dvPlaceService.hasSameName("333", "测试重名", null);
		Assert.assertTrue(bool);
		boolean bool2 = dvPlaceService
				.hasSameNameByUpdate("333", "测试重名", "124");
		Assert.assertFalse(bool2);
	}
}
