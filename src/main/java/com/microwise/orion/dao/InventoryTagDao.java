package com.microwise.orion.dao;

import com.microwise.orion.bean.InventoryTag;

import java.util.List;

/**
 * 盘点扫描标签文物dao
 *
 * @author xubaoji
 * @date 2013-5-27
 *
 * @check 2013-06-05 zhangpeng svn:3853
 */
public interface InventoryTagDao {

    /**
     * 批量添加盘点扫描标签数据
     *
     * @param inventoryTagList 盘点扫描标签文物 实体列表
     * @return void
     * @author 许保吉
     * @date 2013-5-27
     */
    public void addInventoryTags(List<InventoryTag> inventoryTagList);

    /**
     * 批量删除盘点扫描标签数据
     *
     * @param inventoryId 盘点id 编号
     * @return void
     * @author 许保吉
     * @date 2013-5-27
     */
    public void deleteInventoryTags(Integer inventoryId);

    /**
     * 查询扫描到已 出库   异常文物 id编号
     *
     * @param siteId  站点编号id
     * @return List<relic>
     * @author 许保吉
     * @date 2013-5-28
     */
    public List<Integer> findScanAndOut(String siteId);

    /**
     * 查询扫描到已 出库   异常文物件数
     *
     * @param siteId 站点编号id
     * @return Integer 文物件数
     * @author wang.geng
     * @date 2014-3-10
     */
    public Integer findScanAndOutCount(String siteId);

    /**
     * 查询 未扫描到 在库 异常文物id 编号
     * 在库：0在库 3申请出库中 1 待出库，都是在库
     *
     * @param siteId 站点编号
     * @return
     * @author 许保吉
     * @date 2013-5-28
     */
    public List<Integer> findNoScanAndIn(String siteId);

    /**
     * 查询 未扫描到 在库 异常文物件数
     * 在库：0在库 3申请出库中 1 待出库，都是在库
     *
     * @param siteId 站点编号id
     * @return Integer 文物件数
     * @author wang.geng
     * @date 2014-3-10
     */
    public Integer findNoScanAndInCount(String siteId);

}
