package com.microwise.orion.service;

import com.microwise.common.sys.test.BaseTest;
import com.microwise.orion.bean.Era;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 时代service 测试
 * 
 * @author xubaoji
 * @date 2013-5-17
 *
 * @check 2013-06-04 zhangpeng svn:3510
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EraServiceTest extends BaseTest {

	/** 时代service */
	@Autowired
	
	private EraService eraService;

	/**
	 * 查询所有年代信息测试
	 * 
	 * @author 许保吉
	 * @date   2013-5-17 
	 */
	@Test
	public void TestFindAllEra(){
		List<Era> eraList = eraService.findAllEra();
		Assert.assertNotNull(eraList);
		Assert.assertEquals(21, eraList.size());
	}
}
