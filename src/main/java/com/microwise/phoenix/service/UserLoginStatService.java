package com.microwise.phoenix.service;

import com.microwise.phoenix.bean.vo.UserLogLength;
import com.microwise.phoenix.bean.vo.UserLoginStat;

import java.util.Date;
import java.util.List;

/**
 * 系统管理：用户登录习惯统计 service接口
 *
 * @author xu.baoji
 * @date 2013-7-22
 * @check @duan.qixin 2013年7月29日 #5001
 */
public interface UserLoginStatService {

    /***
     * 查询用户登录统计
     *
     * @param logicGroupId 逻辑站点编号
     * @param date         日期
     * @param type         查询类型
     * @return UserLoginStat 用户登录统计实体
     * @author xu.baoji
     * @date 2013-7-22
     */
    public UserLoginStat findUserLoginStat(int logicGroupId, Date date, int type);

    /****
     * 查询一个站点下 所有用户登录时长统计(倒序排序)
     *
     * @param logicGroupId 逻辑站点编号
     * @param date         日期
     * @param type         查询类型
     * @param size         要查询的个数
     * @return List<UserLogLength> 用户登录时长统计列表
     * @author xu.baoji
     * @date 2013-8-9
     */
    public List<UserLogLength> findUserLonLength(int logicGroupId, Date date, int type, int size);

}
