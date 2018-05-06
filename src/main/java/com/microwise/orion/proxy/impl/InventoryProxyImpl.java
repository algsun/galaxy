package com.microwise.orion.proxy.impl;

import com.microwise.orion.bean.Inventory;
import com.microwise.orion.bean.InventoryError;
import com.microwise.orion.proxy.InventoryProxy;
import com.microwise.orion.service.InventoryService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xu.baoji
 * @date 2013-7-25
 * 
 * @check @duan.qixin 2013年7月29日 #4687
 */
@Component
@Scope("prototype")
@Orion
public class InventoryProxyImpl implements InventoryProxy{
	
	/**盘点service*/
	@Autowired
	private InventoryService inventoryService;
	
	@Override
	public List<InventoryError> findInventoryErrors(Integer id) {
		return inventoryService.findInventoryErrors(id);
	}
	
	@Override
	public Inventory findLastInventory(String siteId) {
		return inventoryService.findLastInventory(siteId);
	}

}
