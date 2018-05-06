package com.microwise.orion.dao;

import com.microwise.orion.bean.InventoryError;

import java.util.List;

/**
 * 文物异常dao
 * 
 * @author xubaoji
 * @date 2013-5-28
 *
 * @check 2013-06-05 zhangpeng svn:3789
 */
public interface InventoryErrorDao {

	/**
	 * 批量添加文物异常 信息
	 * 
	 * @param inventoryErrors
	 *            文物异常信息 列表
	 * 
	 * @author 许保吉
	 * @date 2013-5-28
	 * 
	 * @return
	 */
	public void addInventoryErrorS(List<InventoryError> inventoryErrors);

}
