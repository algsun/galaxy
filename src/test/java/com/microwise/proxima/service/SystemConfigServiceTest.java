package com.microwise.proxima.service;

import junit.framework.Assert;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.common.sys.test.BaseTest;

/**
 * 系统配置Service测试类
 * 
 * @author zhangpeng
 * @date 2013-3-26
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SystemConfigServiceTest extends BaseTest {

	@Autowired
	private SystemConfigService systemConfigService;

	/**
	 * 测试查询数据库初始化的摄像机编码索引
	 * 
	 * @author zhangpeng
	 * @date 2013-3-26
	 */
	@Test
	public void testFindDvPlaceCodeIndex() {
		int i = systemConfigService.findDvPlaceCodeIndex();
		Assert.assertEquals(0, i);
	}

	/**
	 * 测试查询数据库初始化的摄像机编码索引
	 * 
	 * @author zhangpeng
	 * @date 2013-3-26
	 */
	@Test
	public void testUpdateDvPlaceCodeIndex() {
		systemConfigService.updateDvPlaceCodeIndex(1);
		int i = systemConfigService.findDvPlaceCodeIndex();
		Assert.assertEquals(1, i);
		systemConfigService.updateDvPlaceCodeIndex(0);
	}

}
