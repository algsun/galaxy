package com.microwise.phoenix.service;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.phoenix.bean.po.uma.InventoryStat;

/**
 * 人员管理：规则统计service测试
 * 
 * @author xu.baoji
 * @date 2013-7-17
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InventoryStatServiceTest extends CleanDBTest {

	/*** 规则统计 */
	@Autowired
	private InventoryStatService inventoryStatService;

	@Before
	public void before() throws Exception {
		CleanDBTest.xmlInsert2("common/dbxml/phoenix/InventoryStatServiceTest.xml");
	}

	/** 规则统计查询接口测试 */
	@Test
	public void TestFindInventoryStat() {
        Date date = new DateTime().withYear(2013).toDate();
		List<InventoryStat> inventoryStats = inventoryStatService.findInventoryStats("31010101", date);

		Assert.assertNotNull(inventoryStats);
		Assert.assertEquals(2, inventoryStats.size());
		InventoryStat inventoryStat = inventoryStats.get(0);
		Assert.assertNotNull(inventoryStat);
		Assert.assertEquals(2, inventoryStat.getCount().intValue());
		InventoryStat inventoryStat2 = inventoryStats.get(1);
		Assert.assertNotNull(inventoryStat2);

	}

	/** 规则统计查询接口测试 */
	@Test
	public void TestFindInventoryStat1() {
		List<InventoryStat> inventoryStats = inventoryStatService.findInventoryStats("31010101",
				new DateTime(2012, 1, 1, 1, 1).toDate());

		Assert.assertNotNull(inventoryStats);
		Assert.assertEquals(2, inventoryStats.size());
		InventoryStat inventoryStat = inventoryStats.get(0);
		Assert.assertNotNull(inventoryStat);
		Assert.assertEquals(1, inventoryStat.getCount().intValue());
		InventoryStat inventoryStat2 = inventoryStats.get(1);
		Assert.assertNull(inventoryStat2);

	}
}
