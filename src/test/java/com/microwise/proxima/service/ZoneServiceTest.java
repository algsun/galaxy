package com.microwise.proxima.service;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.proxima.bean.Zone;

/**
 * 区域Service测试
 * 
 * @author zhangpeng
 * @date 2013-3-25
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ZoneServiceTest extends CleanDBTest {

	/** 区域Service */
	@Autowired
	private ZoneService zoneService;

	@Before
	public void before() throws Exception {
		CleanDBTest.xmlInsert2("common/dbxml/proxima/ZoneServiceTest.xml");
	}

	/**
	 * 查询某个站点下的所有区域
	 * 
	 * @author zhangpeng
	 * @date 2013-3-25
	 */
	@Test
	public void testFind() {
		List<Zone> list = zoneService.find("31010101");
		Assert.assertEquals(3, list.size());
	}

	/**
	 * 查询某个站点下的所有区域 带有父区域
	 * 
	 * @author zhangpeng
	 * @date 2013-3-25
	 */
	@Test
	public void testFindAllZone() {
		List<Zone> list = zoneService.find("31010101");
		Assert.assertEquals(3, list.size());
	}
	
	/**
	 * 查询有光学摄像机的区域
	 * 
	 * @author zhangpeng
	 * @date 2013-3-25
	 */
	@Test
	public void testFindHasOptics() {
		List<Zone> list = zoneService.findHasOptics("31010101");
		Assert.assertEquals(1, list.size());
	}

	/**
	 * 查询有光学摄像机的区域
	 * 
	 * @author zhangpeng
	 * @date 2013-3-25
	 */
	@Test
	public void testFindById() {
		Zone zone = zoneService.findById("002");
		Assert.assertEquals("测试区域2", zone.getName());
	}

	/**
	 * 判断区域下是否有摄像机
	 * 
	 * @author zhangpeng
	 * @date 2013-3-25
	 */
	@Test
	public void testHasDV() {
		boolean bool = zoneService.hasDV("002");
		Assert.assertTrue(bool);
	}

}
