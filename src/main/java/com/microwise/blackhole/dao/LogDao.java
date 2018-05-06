package com.microwise.blackhole.dao;

import com.microwise.blackhole.bean.SysLog;

import java.util.Date;
import java.util.List;

/**
 * 日志Dao
 *
 * @author zhangpeng
 * @date 2012-11-15
 * @check 2012-11-27  xubaoji  svn:381
 */
public interface LogDao {

    /**
     * 添加日志
     *
     * @param sysLog 日志对象
     * @author zhangpeng
     * @date 2012-11-15
     */
    public void saveLog(SysLog sysLog);

    /**
     * 查询日志，无时间条件
     *
     * @param sysLog 日志对象
     * @param start  开始页面
     * @param max    一页显示条数
     * @return List<SysLog> 日志列表
     * @author zhangpeng
     * @date 2012-11-15
     */
    public List<SysLog> findLogList(SysLog sysLog, int start, int max);

    /**
     * 根据条件查询日志总数（无时间条件）
     *
     * @param sysLog 日志对象
     * @return int 日志数量
     * @author zhangpeng
     * @date 2012-11-15
     */
    public int findLogListCount(SysLog sysLog);

    /**
     * 查询日志，有时间条件
     *
     * @param sysLog    日志对象
     * @param startTime 查询开始时间
     * @param endTime   查询结束时间
     * @param start     开始页面
     * @param max       一页显示条数
     * @return List<SysLog> 日志列表
     * @author zhangpeng
     * @date 2012-11-15
     */
    public List<SysLog> findLogList(SysLog sysLog, Date startTime,
                                    Date endTime, int start, int max);

    /**
     * 根据条件查询日志总数（有时间条件）
     *
     * @param sysLog    日志对象
     * @param startTime 查询开始时间
     * @param endTime   查询结束时间
     * @return int 日志数量
     * @author zhangpeng
     * @date 2012-11-15
     */
    public int findLogListCount(SysLog sysLog, Date startTime, Date endTime);

}
