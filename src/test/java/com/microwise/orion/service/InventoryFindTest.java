package com.microwise.orion.service;

import java.util.List;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.orion.bean.Inventory;
import com.microwise.orion.bean.InventoryError;
import com.microwise.orion.bean.InventoryOut;

/**
 * 与盘点记录查询有关 的测试
 *
 * @author xubaoji
 * @date 2013-5-27
 *
 * @check 2013-06-04 zhangpeng svn:4055
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InventoryFindTest extends CleanDBTest {

    /**
     * 盘点记录service
     */
    @Autowired
    private InventoryService inventoryService;

    @Before
    public void setbefor() throws Exception {
        xmlInsert2("common/dbxml/orion/InventoryServiceTest.xml");
    }

    /**
     * 查询盘点记录列表测试
     */
    @Test
    public void testFindInventorys() {
        List<Inventory> inventories = inventoryService.findInventorys("123", 1,
                4);
        Assert.assertNotNull(inventories);
        Assert.assertEquals(4, inventories.size());
        Assert.assertTrue(inventories.get(0).getSumCount() == null);
        Assert.assertFalse(inventories.get(0).isState());
        inventories = inventoryService.findInventorys("1234", 1, 3);
        Assert.assertNotNull(inventories);

    }

    /**
     * 查询盘点记录总数量
     */
    @Test
    public void testFindInventoryCount() {
        Integer count = inventoryService.findInventoryCount("123");
        Assert.assertEquals(4, count.intValue());
        count = inventoryService.findInventoryCount("1");
        Assert.assertEquals(0, count.intValue());

    }

    /**
     * 查询盘点记录报告
     */
    @Test
    public void testFindInventoryReport() {
        Inventory inventory = inventoryService.findInventoryReport(1);
        Assert.assertNotNull(inventory);
        Assert.assertEquals(4, inventory.getSumNumber().intValue());
        inventory = inventoryService.findInventoryReport(5);
        Assert.assertNull(inventory);
    }

    /**
     * 查询盘点异常信息列表测试
     */
    @Test
    public void testFindInventoryErrors() {

        List<InventoryError> inventoryErrors = inventoryService
                .findInventoryErrors(1);
        Assert.assertNotNull(inventoryErrors);
        Assert.assertEquals(2, inventoryErrors.size());
        Assert.assertNotNull(inventoryErrors.get(0).getRelic());
        inventoryErrors = inventoryService.findInventoryErrors(10);
        Assert.assertEquals(0, inventoryErrors.size());
    }

    /**
     * 查询盘点 出库信息列表
     */
    @Test
    public void testFindInventoryOuts() {
        List<InventoryOut> inventoryOuts = inventoryService
                .findInventoryOuts(1);
        Assert.assertNotNull(inventoryOuts);
        Assert.assertEquals(2, inventoryOuts.size());
        Assert.assertNotNull(inventoryOuts.get(0).getOutEvent());
        Assert.assertNotNull(inventoryOuts.get(0).getRelic());
        inventoryOuts = inventoryService.findInventoryOuts(10);
        Assert.assertEquals(0, inventoryOuts.size());

    }

    /**
     * 判断站点下是否有正在进行的盘点记录
     */
    @Test
    public void testIsStartInventory() {
        Boolean is = inventoryService.isStartInventory("123");
        Assert.assertTrue(is);
        boolean is1 = inventoryService.isStartInventory("12345cc");
        Assert.assertFalse(is1);
    }
    
    /**查询最后一次盘点测试*/
    @Test
    public void findLastInventory(){
    	Inventory inventory = inventoryService.findLastInventory("123");
    	Assert.assertNotNull(inventory);
    	Assert.assertEquals(1, inventory.getId().intValue());
    }

}
