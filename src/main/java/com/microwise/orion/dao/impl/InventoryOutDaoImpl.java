package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.orion.bean.InventoryOut;
import com.microwise.orion.dao.InventoryOutDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Session;

import java.util.List;

/**
 * 盘点出库 dao 实现
 * 
 * @author xubaoji
 * @date 2013-5-28
 *
 * @check 2013-06-05 zhangpeng svn:4053
 */
@Dao
@Orion
public class InventoryOutDaoImpl extends OrionBaseDao<InventoryOut> implements
		InventoryOutDao {

	public InventoryOutDaoImpl() {
		super(InventoryOut.class);
	}

	@Override
	public void addInventoryOut(List<InventoryOut> inventoryOutList) {
		Session session = getSession();
		int i = 0;
		for (InventoryOut inventoryOut : inventoryOutList) {
			session.save(inventoryOut);
			i++;
			if (i == 50) {
				session.flush();
				session.clear();
				i = 0;
			}
		}
	}
}
