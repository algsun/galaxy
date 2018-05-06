package com.microwise.blackhole.service;

import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.blackhole.bean.AreaCodeCN;
import com.microwise.common.sys.test.BaseTest;

/**
 * 地区行政 service 测试
 * 
 * @author xubaoji
 * @date 2012-11-29
 * 
 * @check 2012-12-19 zhangpeng svn:885
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AreaCodeServiceTest extends BaseTest {

	/** 注入 地区行政 Service */   
	@Autowired
	private AreaCodeService areaCodeService;

	/**
	 * 测试 查询给定 地区编号 地区下的 直接 子地区（将市辖区和县行政单位过滤）
	 * 
	 * @author 许保吉
	 * @date 2012-11-29
	 */
	@Test
	public void testFindAreaListByAreaCode() {
		List<AreaCodeCN> areaCodeCNs = areaCodeService
				.findAreaListByAreaCode(130000);
		Assert.assertNotNull("地区编号为130000的地区下有子地区", areaCodeCNs);
		Assert.assertTrue("地区编号为130000的地区下的子地区数量大于0", areaCodeCNs.size() > 0);
		List<AreaCodeCN> areaCodeCNs2 = areaCodeService
				.findAreaListByAreaCode(0);
		Assert.assertTrue("获得所有省或直辖市地区失败", areaCodeCNs2.size() > 0);
	}

	/**
	 * 测试 查询所有地区 （将市辖区和县行政单位过滤）
	 * 
	 * @author 许保吉
	 * @date 2012-11-29
	 */
	@Test
	public void testFindAllArea() {
		List<AreaCodeCN> areaCodeCNs = areaCodeService.findAllArea();
		Assert.assertNotNull("获得所有地区失败", areaCodeCNs);

	}

}
