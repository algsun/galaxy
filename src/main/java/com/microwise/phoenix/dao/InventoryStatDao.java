package com.microwise.phoenix.dao;

import com.microwise.phoenix.bean.po.uma.InventoryStat;
import com.microwise.phoenix.bean.po.uma.StockStat;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 藏品管理：盘点统计dao 接口
 *
 * @author xu.baoji
 * @date 2013-7-22
 * @check @duan.qixin 2013年7月29日 #4616
 */
public interface InventoryStatDao {

    /**
     * 查询 年盘点统计数据
     *
     * @param siteId 站点编号
     * @param year   查询年
     * @return InventoryStat 盘点统计实体
     * @author xu.baoji
     * @date 2013-7-22
     */
    public InventoryStat findInventoryStat(String siteId, int year);

    /**
     * 查询 年盘点统计数据
     *
     * @param siteId 站点编号
     * @return StockStat 盘点统计实体
     * @author xu.yuexi
     * @date 2014-9-18
     */
    public List<StockStat> findStockStat(String siteId);

    /**
     * 查询 单次盘点识别率
     *
     * @param siteId 站点编号
     * @return Map 盘点统计实体
     * @author xu.yuexi
     * @date 2014-9-18
     */
    public List<Map<Date, Object>> findSingleRecognition(String siteId, int year);

}
