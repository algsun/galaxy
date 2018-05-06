package com.microwise.orion.dao;

import com.microwise.common.sys.hibernate.BaseDao;
import com.microwise.orion.bean.Inventory;
import com.microwise.orion.bean.InventoryError;
import com.microwise.orion.bean.InventoryOut;

import java.util.List;

/**
 * 库存盘点Dao层
 *
 * @author xubaoji
 * @date 2013-5-27
 *
 * @check 2013-06-05 zhangpeng svn:4053
 */
public interface InventoryDao extends BaseDao<Inventory> {

    /**
     * 分页查询盘点记录
     *
     * @param siteId 站点编号
     * @param index  当前页码
     * @param size   分页单位
     * @return List<Inventory> 盘点记录实体列表 （只包含列表展现需要的信息）
     * @author 许保吉
     * @date 2013-5-27
     */
    public List<Inventory> findInventorys(String siteId, Integer index,
                                          Integer size);

    /**
     * 查询站点下盘点记录数量
     *
     * @param siteId 站点编号
     * @return count 站点下盘点记录数量
     * @author 许保吉
     * @date 2013-5-27
     */
    public Integer findInventoryCount(String siteId);

    /**
     * 查询盘点出库文物信息列表
     *
     * @param id 盘点id 编号
     * @return  List<InventoryOut> 盘点出库文物列表
     * @author 许保吉
     * @date 2013-5-27
     */
    public List<InventoryOut> findInventoryOuts(Integer id);

    /**
     * 查询盘点 异常文物信息列表
     *
     * @param id 盘点id 编号
     * @return List<InventoryError> 盘点异常文物列表
     * @author 许保吉
     * @date 2013-5-27
     */
    public List<InventoryError> findInventoryErrors(Integer id);

    /**
     * 查询正在进行盘点记录id
     *
     * @param siteId 站点编号
     * @return Integer 正在进行的盘点记录的id编号
     * @author 许保吉
     * @date 2013-5-27
     */
    public Integer findStartInventory(String siteId);

    /**
     * 修改 盘点记录 的备注信息
     *
     * @param id     盘点id编号
     * @param remark 盘点备注
     * @return void
     * @author 许保吉
     * @date 2013-5-27
     */
    public void updateInventoryRemark(Integer id, String remark);

    /**
     * 查询 盘点 总数量统计
     *
     * @return sumCount 总数量
     * @author 许保吉
     * @date 2013-5-28
     */
    public Integer findSumCount(String siteId);

    /**
     * 查询 盘点 总件数统计
     *
     * @return sumNumber 总件数
     * @author 许保吉
     * @date 2013-5-28
     */
    public Integer findSumNumber(String siteId);

    /**
     * 查询 盘点 在库/出库 数量
     *
     * @param isOut 是否出库
     * @return Count 在库/出库 数量
     * @author 许保吉
     * @date 2013-5-28
     */
    public Integer findStockCount(boolean isOut, String siteId);

    /**
     * 查询 盘点 在库/出库 件数
     *
     * @param isOut 是否出库
     * @return number 在库件数
     * @author 许保吉
     * @date 2013-5-28
     */
    public Integer findStockNumber(boolean isOut, String siteId);

    /**
     * 查询盘点 含有标签的文物数量
     *
     * @return tagCount 含有标签的文物数量
     * @author 许保吉
     * @date 2013-5-28
     */
    public Integer findTagCount(String siteId);

    /**
     * 查询盘点 含有标签的文物件数
     *
     * @return tagCount 含有标签的文物件数
     * @author 许保吉
     * @date 2013-5-28
     */
    public Integer findTagNumber(String siteId);

    /**
     * 查询盘点 扫描到的文物数量
     *
     * @param inventoryId 盘点id
     * @return tagCount 含有标签的文物数量
     * @author 许保吉
     * @date 2013-5-28
     */
    public Integer findScanCount(Integer inventoryId, String siteId);

    /**
     * 查询盘点 扫描到的文物件数
     *
     * @param inventoryId 盘点id
     * @return tagCount 含有标签的文物件数
     * @author 许保吉
     * @date 2013-5-28
     */
    public Integer findScanNumber(Integer inventoryId, String siteId);

    /**
     * 查询盘点 异常 文物数量
     *
     * @param inventoryId 盘点id
     * @return tagCount 含有标签的文物数量
     * @author 许保吉
     * @date 2013-5-28
     */
    public Integer findErrorCount(Integer inventoryId, String siteId);

    /**
     * 查询盘点 异常文物件数
     *
     * @param inventoryId 盘点id
     * @return tagCount 含有标签的文物件数
     * @author 许保吉
     * @date 2013-5-28
     */
    public Integer findErrorNumber(Integer inventoryId, String siteId);
    
    /***
     * 查询最后一个关闭的盘点
     * 
     * @author xu.baoji
     * @date 2013-7-25
     *
     * @param siteId 站点编号
     * @return inventory 盘点信息
     */
    public Inventory findLastInventory(String siteId);

}
