package com.microwise.saturn.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.saturn.bean.TaskVO;
import com.microwise.saturn.dao.TaskDao;
import com.microwise.saturn.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 15-3-16.
 */

@Beans.Service
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDao taskDao;

    @Override
    public List<TaskVO> findTasks(String siteId) {
        return taskDao.findTasks(siteId);
    }

    @Override
    public void deleteById(String id) {
        taskDao.deleteById(id);
    }

    @Override
    public void updateById(TaskVO taskVO) {
        taskDao.updateById(taskVO);
    }

    @Override
    public void updateStateById(String id, Integer state, int userId) {
        taskDao.updateStateById(id, state, userId);
    }

    @Override
    public void insert(TaskVO taskVO) {
        taskDao.insert(taskVO);
    }

    @Override
    public TaskVO findById(String id) {
        return taskDao.findById(id);
    }

    @Override
    public List<TaskVO> findTasks(String siteId, String title, int responsible, Date beginDate, Date endDate, int index, int pageSize) {
        return taskDao.findTasks(siteId, title, responsible, beginDate, endDate, index, pageSize);
    }

    @Override
    public int countTasks(String siteId, String title, int responsible, Date beginDate, Date endDate) {
        return taskDao.countTasks(siteId, title, responsible, beginDate, endDate);
    }
}
