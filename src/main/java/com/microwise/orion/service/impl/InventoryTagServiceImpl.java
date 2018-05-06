package com.microwise.orion.service.impl;

import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.orion.bean.InventoryTag;
import com.microwise.orion.dao.InventoryDao;
import com.microwise.orion.dao.InventoryTagDao;
import com.microwise.orion.service.InventoryTagService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 盘点扫描标签数据 service 实现
 * 
 * @author xubaoji
 * @date  2013-5-27 
 */
@Service
@Orion
@Transactional
public class InventoryTagServiceImpl implements InventoryTagService {

	/**盘点扫描标签数据 dao*/
	@Autowired
	private  InventoryTagDao inventoryTagDao;
	
	/**文物盘点 dao*/
	@Autowired
	private  InventoryDao inventoryDao;
	
	@Override
	public void addInventoryTags(String siteId, List<Integer> relicIdList) {
		// 获得正在进行的盘点记录的id 编号
		Integer inventoryId = inventoryDao.findStartInventory(siteId);
		List<InventoryTag> inventoryTags =  new ArrayList<InventoryTag>();
		for (Integer  relicId : relicIdList) {
			InventoryTag inventoryTag = new InventoryTag(relicId,inventoryId);
			inventoryTags.add(inventoryTag);
		}
		inventoryTagDao.addInventoryTags(inventoryTags);
	}

}
