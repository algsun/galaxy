package com.microwise.phoenix.service;

import com.microwise.phoenix.bean.po.uma.ActionStat;

import java.util.Date;
import java.util.List;

/**
 * 人员管理：规则统计service 接口
 *
 * @author xu.baoji
 * @date 2013-7-17
 * @check @duan.qixin 2013-7-18 4567
 */
public interface ActionStatService {

    /***
     * 查询规则统计数据
     *
     * @param siteId 站点编号
     * @param date   查询日期
     * @param type   查询类型
     * @return List<ActionStat> 规则统计实体列表
     * @author xu.baoji
     * @date 2013-7-17
     */
    public List<ActionStat> findActionStat(String siteId, Date date, int type);

}
