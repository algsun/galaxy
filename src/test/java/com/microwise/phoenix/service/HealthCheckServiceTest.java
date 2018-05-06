package com.microwise.phoenix.service;

import java.util.List;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.phoenix.bean.vo.HealthCheckItem;
import com.microwise.phoenix.bean.vo.healthCheck.Device;
import com.microwise.phoenix.bean.vo.healthCheck.FTP;
import com.microwise.uma.bean.PersonBean;

/**
 * 系统健康检测 service 测试
 * 
 * @author xu.baoji
 * @date 2013-7-25
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HealthCheckServiceTest extends CleanDBTest {

	/*** 系统健康检测service */
	@Autowired
	private HealthCheckService healthCheckService;

	@Before
	public void before() throws Exception {
		CleanDBTest.xmlInsert2("common/dbxml/phoenix/HealthCheckServiceTest.xml");
	}

	/** 查询系统健康检测项列表测试 */
	@Test
	public void testFindHealthCheckItems() {
		List<HealthCheckItem> healthCheckItems = healthCheckService.findHealthCheckItem();
		Assert.assertNotNull(healthCheckItems);
		Assert.assertEquals(2, healthCheckItems.size());
		HealthCheckItem healthCheckItem =healthCheckItems.get(0);
		Assert.assertNotNull(healthCheckItem);
		Assert.assertEquals(1, healthCheckItem.getCheckItems().size());
	}
	
	/** 查询系统健康检测项数量*/
	@Test
	public void testFindHealthCheckItemCount(){
		int count = healthCheckService.findHealthCheckItemCount();
		Assert.assertEquals(2, count);
	}
	
	/** ftp 检测*/
	@Test
	public void testProximaFtpCheck(){
		List<Integer>  result = healthCheckService.proximaFtpCheck("11010101", 100);
		Assert.assertNotNull(result);
		Assert.assertEquals(100, result.get(0).intValue());
	}
	
	/**查询异常ftp 实体列表*/
	@Test
	public void testProximaFindErrorFtps(){
		List<FTP> errorFtps = healthCheckService.proximaFindErrorFtps("11010101");
		Assert.assertNotNull(errorFtps);
		Assert.assertEquals(3, errorFtps.size());
	}
	
	/**环境监控：设备检测*/
	@Test
	public void testBlueplanetDeviceCheck(){
		List<Integer> result = healthCheckService.blueplanetDeviceCheck("31010101", 100);
		Assert.assertNotNull(result);
		Assert.assertEquals(2, result.get(1).intValue());
		
	    result = healthCheckService.blueplanetDeviceCheck("31010101c", 100);
		Assert.assertNotNull(result);
		Assert.assertEquals(0, result.get(1).intValue());
	}
	
	/**环境监控：异常设备列表*/
	@Test
	public void testBlueplanetFindErrorDevices(){
		List<Device> errorDevice = healthCheckService.blueplanetFindErrorDevices("31010101");
		Assert.assertNotNull(errorDevice);
		Assert.assertEquals(2, errorDevice.size());
	    errorDevice = healthCheckService.blueplanetFindErrorDevices("31010101c");
		Assert.assertNotNull(errorDevice);
		Assert.assertEquals(0, errorDevice.size());
	}
	
	/**人员管理：电子卡检测*/
	@Test
	public void testUmaUserCardCheck(){
		List<Integer> result = healthCheckService.umaUserCardCheck("aa", 50);
		Assert.assertEquals(1, result.get(1).intValue());
		result = healthCheckService.umaUserCardCheck("ac", 50);
		Assert.assertEquals(0, result.get(1).intValue());
	}
	
	/**人员管理：低电和无电的电子卡列表*/
	@Test
	public void testUmaFindErrorUserCard(){
		List<PersonBean> personBeans = healthCheckService.umaFindErrorUserCard("aa");
		Assert.assertNotNull(personBeans);
		Assert.assertEquals(1, personBeans.size());
		personBeans = healthCheckService.umaFindErrorUserCard("ac");
		Assert.assertNotNull(personBeans);
		Assert.assertEquals(0, personBeans.size());
	}
}
