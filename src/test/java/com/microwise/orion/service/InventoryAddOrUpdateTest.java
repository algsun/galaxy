package com.microwise.orion.service;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.orion.bean.Inventory;

/**
 * 添加或者修改盘点记录
 * 
 * @author xubaoji
 * @date 2013-5-27
 *
 * @check 2013-06-04 zhangpeng svn:3823
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InventoryAddOrUpdateTest extends CleanDBTest {

	/** 盘点记录service */
	@Autowired
	private InventoryService inventoryService;

	@Before
	public void setbefor() throws Exception {
		xmlInsert2("common/dbxml/orion/InventoryServiceTest.xml");
	}

	/** 创建盘点记录 */
	@Test
	public void testCreateInventory() {
		Inventory inventory = inventoryService.createInventory("1234");
		Assert.assertNotNull(inventory);
		Assert.assertNotNull(inventory.getId());
		Assert.assertFalse(inventory.isState());
	}

	/** 修改 盘点备注信息 */
	@Test
	public void testUpdateInventoryRemark() {
		inventoryService.updateInventoryRemark(1, "aaaa");
		Inventory inventory = inventoryService.findInventoryReport(1);
		Assert.assertNotNull(inventory);
		Assert.assertEquals("aaaa", inventory.getRemark());
	}

}
