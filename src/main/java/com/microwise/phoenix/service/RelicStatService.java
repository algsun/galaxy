package com.microwise.phoenix.service;

import com.microwise.phoenix.bean.vo.RelicBasicStats;

/**
 * 藏品管理：系统文物统计
 *
 * @author xu.baoji
 * @date 2013-7-16
 * @check @duan.qixin	2013-7-18 #4563
 */
public interface RelicStatService {

    /***
     * 查询系统文物统计数据
     * 如果没有数据 对象不为null  属性 list 不为null 但size 为0
     *
     * @param siteId 站点编号
     * @return RelicBasicStats 文物基本信息统计
     * @author xu.baoji
     * @date 2013-7-16
     */
    public RelicBasicStats findRelicStatData(String siteId);

    /***
     * 查询文物总数量
     *
     * @param siteId 站点编号
     * @return int 文物总数量
     * @author xu.baoji
     * @date 2013-7-16
     */
    public int findRelicSum(String siteId);

}
