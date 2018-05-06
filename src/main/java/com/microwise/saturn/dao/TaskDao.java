package com.microwise.saturn.dao;

import com.microwise.saturn.bean.TaskVO;

import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 15-3-16.
 */
public interface TaskDao {

    /**
     * 查询任务
     *
     * @param siteId
     * @return
     */
    public List<TaskVO> findTasks(String siteId);


    /**
     * 删除任务
     *
     * @param id
     */
    public void deleteById(String id);

    /**
     * 修改任务
     *
     * @param
     */
    public void updateById(TaskVO taskVO);

    /**
     * 更新状态
     *
     * @param id
     * @param state
     */
    public void updateStateById(String id, Integer state, int userId);

    /**
     * 添加
     *
     * @param taskVO
     */
    public void insert(TaskVO taskVO);

    /**
     * 查询某一个
     *
     * @param id
     * @return
     */
    public TaskVO findById(String id);

    /**
     * 查询任务列表
     *
     * @param siteId
     * @param title
     * @param responsible
     * @param beginDate
     * @param endDate
     * @return
     */
    public List<TaskVO> findTasks(String siteId, String title, int responsible, Date beginDate, Date endDate, int index, int pageSize);

    /**
     * 查询任务数量
     *
     * @param siteId
     * @param title
     * @param responsible
     * @param beginDate
     * @param endDate
     * @return
     */
    public int countTasks(String siteId, String title, int responsible, Date beginDate, Date endDate);
}
