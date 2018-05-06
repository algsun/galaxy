package com.microwise.phoenix.service;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.phoenix.bean.vo.RelicBasicStats;

/**
 * 藏品管理：系统藏品统计service 测试
 * 
 * @author xu.baoji
 * @date 2013-7-16
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RelicStatServiceTest extends CleanDBTest {

	/*** 系统文物统计service */
	@Autowired
	private RelicStatService relicStatService;

	@BeforeClass
	public static void before() throws Exception {
		CleanDBTest.xmlInsert2("common/dbxml/phoenix/RelicStatServiceTest.xml");
	}

	/**查询系统文物统计测试*/
	@Test
	public void TestFindRelicStat() {
		RelicBasicStats  relicBasicStats = relicStatService.findRelicStatData("31010101");
		Assert.assertNotNull(relicBasicStats);
		Assert.assertNotNull(relicBasicStats.getEraStat());
		Assert.assertNotNull(relicBasicStats.getLevelStat());
		Assert.assertNotNull(relicBasicStats.getTextureStat());
		Assert.assertNotNull(relicBasicStats.getZoneStat());
	}
}
