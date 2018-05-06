package com.microwise.orion.service;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.orion.bean.Inventory;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 添加或者修改盘点记录
 * 
 * @author xubaoji
 * @date 2013-5-27
 *
 * @check 2013-06-04 zhangpeng svn:4055
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InventoryCloseTest extends CleanDBTest {

	/** 盘点记录service */
	@Autowired
	private InventoryService inventoryService;

	@Before
	public void setbefor() throws Exception {
		xmlInsert2("common/dbxml/orion/InventoryColseTest.xml");
	}

	/**关闭盘点测试*/
	@Test
	public  void  testCloseInvenoty(){
		inventoryService.closeInventory(1, "31010101");
        Inventory inventory = inventoryService.findInventoryReport(1);
        Assert.assertTrue(inventory.isState());
    }

}
