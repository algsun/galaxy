package com.microwise.halley.dao;

import com.microwise.halley.bean.po.AlarmPO;

import java.util.Date;
import java.util.List;

/**
 * 报警记录 Dao
 *
 * @author li.jianfei
 * @date 2014-05-19
 */
public interface AlarmDao {

    /**
     * 查询全部报警记录
     *
     * @param exhibitionId 外展ID
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @return 报警记录集合
     */
    public int findAlarmListCount(int exhibitionId, Date beginTime, Date endTime);

    /**
     * 分页查询全部报警记录
     * 可查询全部报警记录，也可查询某段时间范围内的报警记录
     *
     * @param exhibitionId 外展ID
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @return 报警记录集合
     */
    public List<AlarmPO> findAlarmListByPage(int exhibitionId, Date beginTime, Date endTime, int startPage, int pageSize);
}
