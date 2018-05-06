package com.microwise.proxima.service;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.common.util.GalaxyIdUtil;
import com.microwise.proxima.bean.FTPProfile;
import com.microwise.proxima.bean.OpticsDVPlaceBean;
import com.microwise.proxima.bean.PhotographScheduleBean;
import com.microwise.proxima.bean.Zone;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 光学摄像机Service测试类
 * 
 * @author zhangpeng
 * @date 2013-3-25
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OpticsDVPlaceServiceTest extends CleanDBTest {

	/** 光学摄像机Service */
	@Autowired
	private OpticsDVPlaceService opticsDVPlaceService;

	/** 摄像机拍照计划service接口 */
	@Autowired
	private PhotographScheduleService photographScheduleService;

	@Before
	public void before() throws Exception {
		CleanDBTest.xmlInsert2("common/dbxml/proxima/OpticsDVPlaceServiceTest.xml");
	}

	/**
	 * 测试添加光学摄像机点位
	 * 
	 * @author zhangpeng
	 * @date 2012-3-25
	 */
	@Test
	public void testSave() {
		OpticsDVPlaceBean op = new OpticsDVPlaceBean();
		String id = GalaxyIdUtil.get64UUID();
		String name = "测试光学1号";
		Zone zone = new Zone();
		zone.setId("00003");
		op.setZone(zone);
		op.setId(id);
		op.setPlaceName(name);
		FTPProfile ftp = new FTPProfile();
		ftp.setId("aaaa");
		op.setFtp(ftp);
		opticsDVPlaceService.save(op);
		OpticsDVPlaceBean dv = opticsDVPlaceService.findById(id);
		Assert.assertEquals(name, dv.getPlaceName());
	}

	/**
	 * 测试添加光学摄像机点位
	 * 
	 * @author zhangpeng
	 * @throws Exception
	 * @date 2012-3-25
	 */
	@Test
	public void testSave2() throws Exception {
		OpticsDVPlaceBean op = new OpticsDVPlaceBean();
		String id = GalaxyIdUtil.get64UUID();
		String name = "测试光学2号";
		Zone zone = new Zone();
		zone.setId("00003");
		op.setZone(zone);
		op.setId(id);
		op.setPlaceName(name);
		op.setIoOn(true);
		op.setLightOn(true);
		FTPProfile ftp = new FTPProfile();
		ftp.setId("aaaa");
		op.setFtp(ftp);
		opticsDVPlaceService
				.save(op, "12:01,19:01,60", null, null, null, "day");
		OpticsDVPlaceBean dv = opticsDVPlaceService.findById(id);
		Assert.assertEquals(name, dv.getPlaceName());
		List<PhotographScheduleBean> list = photographScheduleService
				.findAllOfDVPlace(id);
		Assert.assertEquals(1, list.size());
	}

	/**
	 * 分页查询站点下所有光学摄像机，有分页
	 * 
	 * @author zhangpeng
	 * @date 2012-3-25
	 */
	@Test
	public void testFindAll() {
		List<OpticsDVPlaceBean> list = opticsDVPlaceService.findAll("11010101",
				1, 2);
		Assert.assertEquals(2, list.size());
	}

	/**
	 * 分页查询站点下所有光学摄像机，无分页
	 * 
	 * @author zhangpeng
	 * @date 2012-3-25
	 */
	@Test
	public void testFindAll2() {
		List<OpticsDVPlaceBean> list = opticsDVPlaceService.findAll("11010101");
		Assert.assertEquals(3, list.size());
	}

	/**
	 * 分页查询站点下所有光学摄像机数量
	 * 
	 * @author zhangpeng
	 * @date 2012-3-25
	 */
	@Test
	public void testFindAllCount() {
		int count = opticsDVPlaceService.findAllCount("11010101");
		Assert.assertEquals(3, count);
	}

	/**
	 * 分页查询区域下所有光学摄像机
	 * 
	 * @author zhangpeng
	 * @date 2012-3-25
	 */
	@Test
	public void testFindByZoneId2() {
		List<OpticsDVPlaceBean> list = opticsDVPlaceService
				.findByZoneId("00001");
		Assert.assertEquals(2, list.size());
	}

	/**
	 * 分页查询区域下所有光学摄像机
	 * 
	 * @author zhangpeng
	 * @date 2012-3-25
	 */
	@Test
	public void testFindByZoneId() {
		List<OpticsDVPlaceBean> list = opticsDVPlaceService.findByZoneId(
				"00001", 1, 1);
		Assert.assertEquals(1, list.size());
	}

	/**
	 * 分页查询区域下所有光学摄像机数量
	 * 
	 * @author zhangpeng
	 * @date 2012-3-25
	 */
	@Test
	public void testFindByZoneIdCount() {
		int count = opticsDVPlaceService.findByZoneIdCount("00001");
		Assert.assertEquals(2, count);
	}

	/**
	 * 分页查询区域下所有光学摄像机数量
	 * 
	 * @author zhangpeng
	 * @date 2012-3-25
	 */
	@Test
	public void testFindById() {
		OpticsDVPlaceBean op = opticsDVPlaceService.findById("1113");
		Assert.assertEquals("点位3", op.getPlaceName());
		Assert.assertNotNull(op.getZone());
		Assert.assertNotNull(op.getFtp());
	}

	/**
	 * 测试更新光学摄像机点位
	 * 
	 * @author zhangpeng
	 * @date 2012-3-25
	 */
	@Test
	public void testUpdate() {
		OpticsDVPlaceBean op = opticsDVPlaceService.findById("1113");
		String name = "改名的点3";
		op.setPlaceName(name);
		opticsDVPlaceService.update(op);
		OpticsDVPlaceBean op2 = opticsDVPlaceService.findById("1113");
		Assert.assertEquals(name, op2.getPlaceName());
	}

	/**
	 * 测试更新光学摄像机点位
	 * 
	 * @author zhangpeng
	 * @throws Exception
	 * @date 2012-3-25
	 */
	@Test
	public void testUpdate2() throws Exception {
		String id = "1113";
		OpticsDVPlaceBean op = opticsDVPlaceService.findById(id);
		String name = "改名的点3";
		op.setPlaceName(name);
		int count1 = photographScheduleService.findAllOfDVPlace(id).size();
		opticsDVPlaceService.update(op, "12:01,19:01,60", null, null, null,
				"day");
		OpticsDVPlaceBean op2 = opticsDVPlaceService.findById(id);
		int count2 = photographScheduleService.findAllOfDVPlace(id).size();
		Assert.assertEquals(name, op2.getPlaceName());
		Assert.assertFalse(count1 == count2);
	}

	/**
	 * 测试io端口号是否使用，添加时
	 * 
	 * @author zhangpeng
	 * @date 2012-3-25
	 */
	@Test
	public void testIsIoPortUingByAdd() {
		boolean bool = opticsDVPlaceService.isIoPortUsingByAdd(65534);
		Assert.assertTrue(bool);
	}

	/**
	 * 测试io端口号是否使用，修改时
	 * 
	 * @author zhangpeng
	 * @date 2012-3-25
	 */
	@Test
	public void testIsIoPortUingByUpdate() {
		boolean bool = opticsDVPlaceService
				.isIoPortUsingByUpdate("1111", 65534);
		Assert.assertFalse(bool);
	}

	/**
	 * 测试io端口号是否使用
	 * 
	 * @author zhangpeng
	 * @date 2012-3-25
	 */
	@Test
	public void testIsIoPortUing() {
		boolean bool = opticsDVPlaceService.isIoPortUsing(null, 65534);
		Assert.assertTrue(bool);
		boolean bool2 = opticsDVPlaceService.isIoPortUsing("1111", 65534);
		Assert.assertFalse(bool2);
	}

}
