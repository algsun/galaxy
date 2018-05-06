package com.microwise.saturn.dao.impl;

import com.google.common.collect.Maps;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.saturn.bean.TaskVO;
import com.microwise.saturn.dao.TaskDao;
import com.microwise.saturn.sys.SaturnBaseDao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 15-3-16.
 */

@Beans.Dao
public class TaskDaoImpl extends SaturnBaseDao implements TaskDao {

    @Override
    public List<TaskVO> findTasks(String siteId) {
        return getSqlSession().selectList("saturn.Task.findTasksBySiteId", siteId);
    }

    @Override
    public void deleteById(String id) {
        getSqlSession().delete("saturn.Task.deleteById", id);
    }

    @Override
    public void updateById(TaskVO taskVO) {
        getSqlSession().update("saturn.Task.updateById", taskVO);
    }

    @Override
    public void updateStateById(String id, Integer state, int userId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("state", state);
        params.put("userId", userId);
        params.put("date", new Date());
        getSqlSession().update("saturn.Task.updateStateById", params);

    }

    @Override
    public void insert(TaskVO taskVO) {
        getSqlSession().insert("saturn.Task.save", taskVO);
    }

    @Override
    public TaskVO findById(String id) {
        return getSqlSession().selectOne("saturn.Task.findById", id);
    }

    @Override
    public List<TaskVO> findTasks(String siteId, String title, int responsible, Date beginDate, Date endDate, int index, int pageSize) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("siteId", siteId);
        params.put("title", title);
        params.put("responsible", responsible);
        params.put("beginDate", beginDate);
        params.put("endDate", endDate);
        params.put("start", (index - 1) * pageSize);
        params.put("pageSize", pageSize);
        return getSqlSession().selectList("saturn.Task.findTasks", params);
    }

    @Override
    public int countTasks(String siteId, String title, int responsible, Date beginDate, Date endDate) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("siteId", siteId);
        params.put("title", title);
        params.put("responsible", responsible);
        params.put("beginDate", beginDate);
        params.put("endDate", endDate);
        return getSqlSession().<Integer>selectOne("saturn.Task.countTasks", params);
    }
}
