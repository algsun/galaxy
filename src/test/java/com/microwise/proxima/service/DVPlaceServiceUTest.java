package com.microwise.proxima.service;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.proxima.bean.DVPlaceBean;
import com.microwise.proxima.bean.OpticsDVPlaceBean;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 摄像机点位Service
 * 
 * @author zhangpeng
 * @date 2013-3-25
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DVPlaceServiceUTest extends CleanDBTest {

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
	public void testUpdate() {
		OpticsDVPlaceBean dv = (OpticsDVPlaceBean) dvPlaceService
				.findById("123");
		int ioPort = 5000;
		String name = "新名字";
		dv.setIoPort(ioPort);
		dv.setPlaceName(name);
		dvPlaceService.update(dv);
		OpticsDVPlaceBean dv2 = (OpticsDVPlaceBean) dvPlaceService
				.findById("123");
		Assert.assertEquals(name, dv2.getPlaceName());
		Assert.assertTrue(ioPort == dv2.getIoPort());
	}

	/**
	 * 测试修改摄像机启用禁用状态
	 * 
	 * @author zhangpeng
	 * @date 2013-3-26
	 */
	@Test
	public void testChangeEnable() {
		DVPlaceBean dv = dvPlaceService.findById("123");
		Assert.assertTrue(dv.isEnable());
		dvPlaceService.changeEnable("123");
		DVPlaceBean dv2 = dvPlaceService.findById("123");
		Assert.assertFalse(dv2.isEnable());
	}

}
