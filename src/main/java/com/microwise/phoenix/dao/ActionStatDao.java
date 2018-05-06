package com.microwise.phoenix.dao;

import com.microwise.phoenix.bean.po.uma.ActionStat;

import java.util.List;

/**
 * 人员管理：规则统计dao
 *
 * @author xu.baoji
 * @check @duan.qixin 2013-7-18 #4567
 * @date 2013-7-17
 */
public interface ActionStatDao {

    /***
     * 查询规则统计数据
     *
     * @param siteId    站点编号
     * @param startDate long型开始时间
     * @param endDate   long型结束时间
     * @return List<ActionStat> 规则统计实体列表
     * @author xu.baoji
     * @date 2013-7-17
     */
    public List<ActionStat> findActionStat(String siteId, long startDate, long endDate);
}
