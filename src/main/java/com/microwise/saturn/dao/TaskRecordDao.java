package com.microwise.saturn.dao;

import com.microwise.saturn.bean.TaskRecordVO;

import java.util.List;

/**
 * 任务记录dao
 */
public interface TaskRecordDao {

    /**
     * 根据任务id查询任务记录
     *
     * @param taskId
     * @return
     */
    public List<TaskRecordVO> findTaskRecords(String taskId);

    /**
     * 根据id查询任务记录
     *
     * @param id
     * @return
     */
    public TaskRecordVO findTaskRecord(String id);

    /**
     * 添加任务记录
     *
     * @param taskRecordVO
     */
    public void insert(TaskRecordVO taskRecordVO);

    /**
     * 删除任务记录
     *
     * @param id
     */
    public void delete(String id);

    /**
     * 根据id删除所以任务记录
     *
     * @param id
     */
    public void deleteByTaskId(String id);

    /**
     * 更新任务记录
     *
     * @param taskRecordVO
     */
    public void updateRecord(TaskRecordVO taskRecordVO);

    /**
     * 更新处理
     *
     * @param taskRecordVO
     */
    public void updateHandle(TaskRecordVO taskRecordVO);

}
