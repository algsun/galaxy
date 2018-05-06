package com.microwise.saturn.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.saturn.bean.TaskRecordVO;
import com.microwise.saturn.dao.TaskRecordDao;
import com.microwise.saturn.service.TaskRecordService;
import com.microwise.saturn.sys.Saturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 任务记录service实现
 */

@Beans.Service
@Saturn
@Transactional
public class TaskRecordServiceImpl implements TaskRecordService {

    @Autowired
    private TaskRecordDao taskRecordDao;

    @Override
    public List<TaskRecordVO> findTaskRecords(String taskId) {
         return taskRecordDao.findTaskRecords(taskId);
    }

    @Override
    public TaskRecordVO findTaskRecord(String id) {
        return taskRecordDao.findTaskRecord(id);
    }

    @Override
    public void insert(TaskRecordVO taskRecordVO) {
        taskRecordDao.insert(taskRecordVO);
    }

    @Override
    public void delete(String id) {
        taskRecordDao.delete(id);
    }

    @Override
    public void deleteByTaskId(String id) {
        taskRecordDao.deleteByTaskId(id);
    }

    @Override
    public void updateRecord(TaskRecordVO taskRecordVO) {
        taskRecordDao.updateRecord(taskRecordVO);
    }

    @Override
    public void updateHandle(TaskRecordVO taskRecordVO) {
        taskRecordDao.updateHandle(taskRecordVO);
    }
}
