package com.microwise.orion.dao;

import com.microwise.common.sys.hibernate.BaseDao;
import com.microwise.orion.bean.InventoryZone;
import com.microwise.orion.vo.InventoryZoneVo;

import java.util.List;

/**
 * 按区域 统计盘点信息 dao
 *
 * @author xubaoji
 * @date 2013-6-3
 *
 * @check 2013-06-05 zhangpeng svn:4010
 */
public interface InventoryZoneDao extends BaseDao<InventoryZone> {

    /**
     * 查询 总数 按区域统计列表
     *
     * @param siteId 站点编号
     * @return List<InventoryZone> 按区域盘点统计实体列表
     * @author 许保吉
     * @date 2013-6-3
     */
    public List<InventoryZone> findCountList(String siteId);

    /**
     * 查询 实时总数 按区域统计列表
     *
     * @param siteId 站点编号
     * @return List<InventoryZoneVo>
     * @author wang.geng
     * @date 2014-3-11
     */
    public List<InventoryZoneVo> findRealtimeCountList(String siteId);

    /**
     * 查询 在库/出库 按区域统计盘点信息列表
     *
     * @param isOut  是否出库 true ：查询出库 false ：在库
     * @param siteId 站点编号
     * @return
     * @author 许保吉
     * @date 2013-6-3
     */
    public List<InventoryZone> findStockList(boolean isOut, String siteId);

    public List<InventoryZoneVo> findRealtimeStockList(boolean isOut,String siteId);

    /**
     * 查询 标签 盘点按区域统计信息列表
     *
     * @param siteId 站点编号
     * @return
     * @author 许保吉
     * @date 2013-6-3
     */
    public List<InventoryZone> findTagList(String siteId);

    public List<InventoryZoneVo> findRealtimeTagList(String siteId);

    /**
     * 查询 异常 盘点按区域统计信息列表
     *
     * @param inventoryId 盘点id
     * @param siteId      站点编号
     * @return
     * @author 许保吉
     * @date 2013-6-3
     */
    public List<InventoryZone> findErrorList(Integer inventoryId, String siteId);

    /**
     * 查询 实时异常 盘点按区域统计信息列表
     *
     * @param siteId 站点编号
     * @return List<InventoryZoneVo>
     * @author wang.geng
     * @date 2014-3-11
     */
    public List<InventoryZoneVo> findRealtimeErrorList(String siteId);

    /**
     * 查询 扫描 盘点按区域统计信息列表
     *
     * @param inventoryId 盘点id
     * @param siteId      站点编号
     * @return
     * @author 许保吉
     * @date 2013-6-3
     */
    public List<InventoryZone> findScanList(Integer inventoryId, String siteId);

    //todo
    public List<InventoryZoneVo> findRealtimeScanList(Integer inventoryId,String siteId);

    /**
     * 添加盘点 区域统计信息
     *
     * @param inventoryId    盘点id
     * @param statisticsType 统计类型
     * @param inventoryZones 按区域 统计 数据列表
     * @return void
     * @author 许保吉
     * @date 2013-6-3
     */
    public void addInventoryZone(Integer inventoryId, Integer statisticsType,
                                 List<InventoryZone> inventoryZones);

    /**
     * 查询一个盘点的所有 区域统计信息
     *
     * @param inventoryId 盘点id
     * @return  List<InventoryZoneVo> 盘点区域统计信息
     * @author 许保吉
     * @date 2013-6-3
     */
    public List<InventoryZoneVo> findAll(Integer inventoryId);

}
