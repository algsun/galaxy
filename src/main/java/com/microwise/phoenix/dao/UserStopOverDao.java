package com.microwise.phoenix.dao;

import com.microwise.phoenix.bean.po.uma.ZoneStat;

import java.util.Date;
import java.util.List;

/**
 * 人员管理：人员在区域活动时长统计dao
 *
 * @author xu.baoji
 * @date 2013-7-15
 * @check @duan.qixin 2013-7-18 @4535
 */
public interface UserStopOverDao {

    /***
     * 查询 人员在区域活动时长统计
     *
     * @param siteId    站点id
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return List<ZoneStat> 人员管理区域统计列表
     * @author xu.baoji
     * @date 2013-7-15
     */
    public List<ZoneStat> findZoneStat(String siteId, Date startDate, Date endDate);

}
