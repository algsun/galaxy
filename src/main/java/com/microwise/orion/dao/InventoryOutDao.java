package com.microwise.orion.dao;

import com.microwise.common.sys.hibernate.BaseDao;
import com.microwise.orion.bean.InventoryOut;

import java.util.List;

/**
 * 盘点出库 dao
 *
 * @author xubaoji
 * @date 2013-5-28
 *
 * @check 2013-06-05 zhangpeng svn:4053
 */
public interface InventoryOutDao extends BaseDao<InventoryOut> {

    /**
     * 批量添加 盘点出库文物
     *
     * @param inventoryOutList 出库文物实体
     * @return void
     * @author 许保吉
     * @date 2013-5-28
     */
    public void addInventoryOut(List<InventoryOut> inventoryOutList);

}
