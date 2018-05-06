package com.microwise.orion.service;

import com.microwise.orion.bean.Inventory;
import com.microwise.orion.bean.InventoryError;
import com.microwise.orion.bean.InventoryOut;

import java.util.List;

/**
 * 盘点相关 接口 service
 *
 * @author xubaoji
 * @date 2013-5-27
 *
 * @check 2013-06-05 zhangpeng svn:4053
 */
public interface InventoryService {

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
     * @return Integer 站点下盘点记录数量
     * @author 许保吉
     * @date 2013-5-27
     */
    public Integer findInventoryCount(String siteId);

    /**
     * 创建一条盘点记录（返回盘点记录对象）
     *
     * @param siteId 站点编号
     * @return Inventory 盘点记录实体（带有id ）
     * @author 许保吉
     * @date 2013-5-27
     */
    public Inventory createInventory(String siteId);

    /**
     * 关闭一个盘点记录 （生成盘点报告，统计盘点信息）
     *
     * @param id     盘点记录id
     * @param siteId 站点编号
     * @return void
     * @author 许保吉
     * @date 2013-5-27
     */
    public void closeInventory(Integer id, String siteId);

    /**
     * 获得一个盘点记录的盘点报告
     *
     * @param id 盘底记录id
     * @return Inventory 盘点记录实体（包含所有统计信息，不含有出库和异常列表信息，包含区域下盘点统计信息）
     * @author 许保吉
     * @date 2013-5-27
     */
    public Inventory findInventoryReport(Integer id);

    /**
     * 查询盘点出库文物信息列表
     *
     * @param id 盘点id 编号
     * @return List<InventoryOut> 盘点出库文物信息列表
     * @author 许保吉
     * @date 2013-5-27
     */
    public List<InventoryOut> findInventoryOuts(Integer id);

    /**
     * 查询盘点 异常文物信息列表
     *
     * @param id 盘点id 编号
     * @return List<InventoryError> 盘点异常文物信息列表
     * @author 许保吉
     * @date 2013-5-27
     */
    public List<InventoryError> findInventoryErrors(Integer id);

    /**
     * 获得一个实时盘点记录的盘点报告
     *
     * @param id 盘点id 编号
     * @return Inventory 实时盘点记录实体（包含所有统计信息，不含有出库和异常列表信息，包含区域下盘点统计信息）
     * @author wang.geng
     * @date 2014-3-7
     */
    public Inventory findRealtimeInventoryReport(Integer id,String siteId);

    /**
     * 查询实时盘点出库文物信息列表
     *
     * @param id 盘点id 编号
     * @return List<InventoryOut> 盘点出库文物信息列表
     * @author wang.geng
     * @date 2014-3-7
     */
    public List<InventoryOut> findRealtimeInventoryOuts(Integer id,String siteId);

    /**
     * 查询实时盘点 异常文物信息列表
     * @param id 盘点id 编号
     * @return List<InventoryError> 盘点异常文物信息列表
     * @author wang.geng
     * @date 2014-3-7
     */
    public List<InventoryError> findRealtimeInventoryErrors(Integer id,String siteId);

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
     * 判断 一个站点下是否存在正在进行的盘点
     *
     * @param siteId 站点编号
     * @return true 有 false 没有
     * @author 许保吉
     * @date 2013-5-27
     */
    public Boolean isStartInventory(String siteId);
    
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
