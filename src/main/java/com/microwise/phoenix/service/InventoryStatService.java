package com.microwise.phoenix.service;

import com.microwise.phoenix.bean.po.uma.InventoryStat;
import com.microwise.phoenix.bean.po.uma.StockStat;

import java.util.Date;
import java.util.List;

/**
 * 藏品管理：文物盘点统计service接口
 *
 * @author xu.baoji
 * @date 2013-7-22
 * @check @duan.qixin 2013年7月29日 #4621
 */
public interface InventoryStatService {

    /***
     * 查询盘点统计数据
     * 返回数据说明：1、list 不为null，永远为两个元素，
     * 2、list 第一个元素为当前年数据，第二个元素为上一年的数据，
     * 3、如果当前年或者上一年无数据list 对应的元素为null。
     *
     * @param siteId 站点编号
     * @param date   查询时间参数
     * @return List<InventoryStat> 盘点统计实体列表
     * @author xu.baoji
     * @date 2013-7-22
     */
    public List<InventoryStat> findInventoryStats(String siteId, Date date);

    /***
     * 查询盘点统计数据
     * 返回数据说明：1、list 不为null，永远为两个元素，
     * 2、list 第一个元素为当前年数据，第二个元素为上一年的数据，
     * 3、如果当前年或者上一年无数据list 对应的元素为null。
     *
     * @param siteId 站点编号
     * @return List<InventoryStat> 盘点统计实体列表
     * @author xu.yuexi
     * @date 2014-9-18
     */
    public List<StockStat> findStockStats(String siteId);

}
