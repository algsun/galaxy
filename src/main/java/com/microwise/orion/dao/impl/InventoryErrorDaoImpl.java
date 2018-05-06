package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.orion.bean.InventoryError;
import com.microwise.orion.dao.InventoryErrorDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import org.hibernate.Session;

import java.util.List;

/**
 * 文物 异常信息 dao 实现
 * 
 * @author xubaoji
 * @date 2013-5-28
 *
 * @check 2013-06-05 zhangpeng svn:3794
 */
@Dao
@Orion
public class InventoryErrorDaoImpl extends OrionBaseDao<InventoryError>
		implements InventoryErrorDao {

	public InventoryErrorDaoImpl() {
		super(InventoryError.class);
	}

	@Override
	public void addInventoryErrorS(List<InventoryError> inventoryErrors) {
		Session session = getSession();
		int i = 0;
		for (InventoryError inventoryError : inventoryErrors) {
			session.save(inventoryError);
			i++;
			if (i == 50) {
				session.flush();
				session.clear();
				i = 0;
			}
		}

	}
}
