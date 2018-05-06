package com.microwise.phoenix.dao;

import com.microwise.phoenix.bean.po.UserLogin;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统管理 ：用户登录习惯统计dao 接口
 *
 * @author xu.baoji
 * @date 2013-7-22
 * @check @duan.qixin 2013年7月29日 #4959
 */
public interface UserLoginStatDao {

    /**
     * 查询用户登录总次数
     *
     * @param logicGroupId 逻辑站点编号
     * @param startDate    开始时间
     * @param endDate      结束时间
     * @return Integer  用户登录总次数
     * @author xu.baoji
     * @date 2013-7-22
     */
    public Integer findUserLoginCount(int logicGroupId, Date startDate, Date endDate);

    /***
     * 查询用户登录统计 （按周统计）
     *
     * @param logicGroupId 逻辑站点
     * @param loginCount   登录总次数
     * @param startDate    开始时间
     * @param endDate      结束时间
     * @return List<Map<String, Object>> 用户登录习惯周统计数据 按周排序  周一，周二  ... 周天
     * @author xu.baoji
     * @date 2013-7-22
     */
    public List<Map<String, Object>> findUserLoginWeekStat(int logicGroupId, int loginCount, Date startDate, Date endDate);

    /***
     * 查询用户登录统计（按日统计）
     *
     * @param logicGroupId 逻辑站点
     * @param loginCount   登录总次数
     * @param startDate    开始时间
     * @param endDate      结束时间
     * @return List<Map<String, Object>> 用户登录习惯周统计数据 按周排序  凌晨，早上  ... 晚上
     * @author xu.baoji
     * @date 2013-7-22
     */
    public List<Map<String, Object>> findUserLoginDayStat(int logicGroupId, int loginCount, Date startDate, Date endDate);

    /***
     * 查询站点下用户
     *
     * @param logicGroupId 逻辑站点编号
     * @return List<UserLogin> 用户列表
     * @author xu.baoji
     * @date 2013-8-9
     */
    public List<UserLogin> findUserBySite(int logicGroupId);

    /***
     * 查询一个用户在一个时间段内在 指点站点下的登录情况
     *
     * @param logicGroupId 逻辑站点编号
     * @param email        用户email 用来表示唯一用户
     * @param startDate    开始时间
     * @param endDate      结束时间
     * @return List<UserLogin> 用户登录退出信息列表
     * @author xu.baoji
     * @date 2013-8-9
     */
    public List<UserLogin> findUserLoginByEmail(int logicGroupId, String email, Date startDate, Date endDate);
}
