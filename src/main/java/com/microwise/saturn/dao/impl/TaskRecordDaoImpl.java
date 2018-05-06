package com.microwise.saturn.dao.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.saturn.bean.TaskRecordVO;
import com.microwise.saturn.dao.TaskRecordDao;
import com.microwise.saturn.sys.SaturnBaseDao;

import java.util.List;

/**
 * 任务记录dao实现
 */
@Beans.Dao
public class TaskRecordDaoImpl extends SaturnBaseDao implements TaskRecordDao {

    @Override
    public List<TaskRecordVO> findTaskRecords(String taskId) {
        return getSqlSession().selectList("saturn.TaskRecord.findTaskRecords", taskId);
    }

    @Override
    public TaskRecordVO findTaskRecord(String id) {
        return getSqlSession().selectOne("saturn.TaskRecord.findTaskRecord",id);
    }

    @Override
    public void insert(TaskRecordVO taskRecordVO) {
        getSqlSession().insert("saturn.TaskRecord.insert",taskRecordVO);
    }

    @Override
    public void delete(String id) {
        getSqlSession().delete("saturn.TaskRecord.delete",id);
    }

    @Override
    public void deleteByTaskId(String id) {
        getSqlSession().delete("saturn.TaskRecord.deleteByTaskId",id);
    }

    @Override
    public void updateRecord(TaskRecordVO taskRecordVO) {
        getSqlSession().update("saturn.TaskRecord.updateRecord",taskRecordVO);
    }

    @Override
    public void updateHandle(TaskRecordVO taskRecordVO) {
        getSqlSession().update("saturn.TaskRecord.updateHandle",taskRecordVO);
    }
}
