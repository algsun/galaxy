package com.microwise.orion.proxy;

import com.microwise.orion.bean.Inventory;
import com.microwise.orion.bean.InventoryError;

import java.util.List;

/**
 * 盘点代理层服务
 *
 * @author xu.baoji
 * @date 2013-7-25
 * @check @duan.qixin 2013年7月29日 #4687
 */
public interface InventoryProxy {

    /**
     * 查询盘点 异常文物信息列表
     *
     * @param id 盘点id 编号
     * @return List<InventoryError> 盘点异常文物信息列表
     * @author 许保吉
     * @date 2013-7-25
     */
     List<InventoryError> findInventoryErrors(Integer id);

    /**
     * 查询最后一个关闭的盘点
     *
     * @param siteId 站点编号
     * @return inventory 盘点信息
     * @author xu.baoji
     * @date 2013-7-25
     */
     Inventory findLastInventory(String siteId);

}
