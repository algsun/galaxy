package com.microwise.phoenix.service;

import com.microwise.phoenix.bean.vo.UserStopOver;

import java.util.Date;

/**
 * 人员管理：人员在区域活动时长统计service
 *
 * @author xu.baoji
 * @date 2013-7-15
 * @check @duan.qixin	2013-7-18 #4535
 */
public interface UserStopOverService {

    /***
     * 查询人员在区域活动时长统计
     * 注意：如果无数据返回对象不为null ，请通过UserStopOver 对象中hasData 属性判断:true
     * 有数据，false：无数据
     *
     * @param siteId 当前站点编号
     * @param date   查询时间
     * @param type   查询类型
     * @return UserStopOver 人员在区域活动时长统计实体
     * @author xu.baoji
     * @date 2013-7-15
     */
    public UserStopOver findUserStopOver(String siteId, Date date, int type);

}
