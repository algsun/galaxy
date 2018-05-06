package com.microwise.blackhole.service.impl;

import com.microwise.blackhole.bean.Task;
import com.microwise.blackhole.bean.TaskRecord;
import com.microwise.blackhole.dao.TaskDao;
import com.microwise.blackhole.dao.TaskRecordDao;
import com.microwise.blackhole.service.TaskService;
import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.phoenix.sys.Phoenix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;

/**
 * 我的任务 service接口实现类
 *
 * @author xu.baoji
 * @date 2013-7-23
 * @check @duan.qixin 2013年7月29日 #4700
 */
@Service
@Phoenix
@Transactional
public class TaskServiceImpl implements TaskService {

    /**
     * 任务dao
     */
    @Autowired
    private TaskDao taskDao;

    /**
     * 任务回复dao
     */
    @Autowired
    private TaskRecordDao taskRecordDao;

    @Transactional(readOnly = true)
    @Override
    public Task findById(Integer taskId) {
        Task task = taskDao.findById(taskId);
        if (task != null) {
            task.setTaskRecords(new LinkedHashSet<TaskRecord>(taskRecordDao
                    .findTaskRecordByTaskId(taskId)));
        }
        return task;
    }

    @Override
    public List<Task> findTasks(int logicGroupId, Integer userId, Integer index, Integer max, int state) {
        return taskDao.findTasks(logicGroupId, userId, taskDao.findTaskByDesignee(userId), index, max, state);
    }

    @Override
    public Integer findTaskCount(int logicGroupId, Integer userId, int state) {
        return taskDao.findTaskCount(logicGroupId, userId, taskDao.findTaskByDesignee(userId), state);
    }

    @Override
    public void addTaskRecord(TaskRecord taskRecord) {
        // 修该任务表中 任务状态
        taskDao.closeOrOpenTask(taskRecord.getTask().getId(), taskRecord.isState());

        // 添加任务回复
        taskRecordDao.save(taskRecord);
    }

    @Override
    public void createTask(Task task, List<Integer> designees) {
        taskDao.save(task);
        // 添加任务接受人
        taskDao.addTaskDesignees(task.getId(), designees);
    }

    @Override
    public void deleteTask(Integer taskId) {
        taskRecordDao.deleteTaskRecordByTaskId(taskId);
        taskDao.deleteTask(taskId);
    }

    @Override
    public void updateTask(List<Integer> designees, Integer completeStatus, int state, int taskId) {
        // 更新任务 基本 信息
        taskDao.updateTask(completeStatus, state, taskId);
        // 更新任务接受人信息
        // 先删
        if (designees != null) {
            taskDao.deleteTaskDesignees(taskId);
            taskDao.addTaskDesignees(taskId, designees);
        }
    }

}
