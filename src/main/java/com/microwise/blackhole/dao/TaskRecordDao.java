package com.microwise.blackhole.dao;

import com.microwise.blackhole.bean.TaskRecord;
import com.microwise.common.sys.hibernate.BaseDao;

import java.util.List;

/**
 * 任务 回复记录dao
 *
 * @author xubaoji
 * @date 2013-6-8
 * @check @duan.qixin 2013年7月29日 #4661
 */
public interface TaskRecordDao extends BaseDao<TaskRecord> {

    /**
     * 查询任务回复记录列表
     *
     * @param taskId 任务id
     * @return List<TaskRecord>  任务回复列表
     * @author xu.baoji
     * @date 2013-7-23
     */
    public List<TaskRecord> findTaskRecordByTaskId(int taskId);

    /**
     * 通过任务id 删除任务回复记录
     *
     * @param taskId 任务id
     * @author xu.baoji
     * @date 2013-7-23
     */
    public void deleteTaskRecordByTaskId(int taskId);

}
