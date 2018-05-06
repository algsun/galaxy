package com.microwise.blackhole.dao;

import com.microwise.blackhole.bean.Task;
import com.microwise.common.sys.hibernate.BaseDao;

import java.util.List;

/**
 * 任务dao
 *
 * @author xubaoji
 * @date 2013-6-8
 * @check @duan.qixin 2013年7月29日 #4700
 */
public interface TaskDao extends BaseDao<Task> {

    /**
     * 分页查询任务
     *
     * @param logicGroupId  站点id
     * @param userId  任务发布人
     * @param taskIds 包含莫个接受人的任务列表
     * @param index   当前页码
     * @param size    分页单位
     * @param state   任务状态0 未结束，-1 全部
     * @return List<Task> 任务列表（不携带回复记录列表）
     * @author 许保吉
     * @date 2013-6-8
     */
    public List<Task> findTasks(int logicGroupId, int userId, List<Integer> taskIds, Integer index,
                                Integer size, int state);

    /**
     * 查询数量
     *
     * @param logicGroupId     站点id
     * @param releaserId 任务发布人
     * @param taskIds    包含莫个接受人的任务列表
     * @param state      任务接受状态 0 未结束 -1 全部
     * @return Integer
     * @author 许保吉
     * @date 2013-6-8
     */
    public Integer findTaskCount(int logicGroupId, int releaserId, List<Integer> taskIds, int state);

    /**
     * 通过任务id查询一个任务
     *
     * @param taskId 任务id编号
     * @return Task 任务实体
     * @author 许保吉
     * @date 2013-6-8
     */
    public Task findById(Integer taskId);

    /**
     * 关闭任务 将任务置为结束状态
     *
     * @param taskId 任务id编号
     * @param state  true :关闭任务   false  开启任务
     * @author 许保吉
     * @date 2013-6-8
     */
    public void closeOrOpenTask(Integer taskId, boolean state);

    /**
     * 通过id删除任务
     *
     * @param taskId 任务id编号
     * @author 许保吉
     * @date 2013-6-8
     */
    public void deleteTask(Integer taskId);

    /**
     * 修改任务
     *
     * @param completeStatus 完成度 如果没有修改 传null
     * @param state          任务状态 0 未结束， 1 已结束 默认为0
     * @param taskId         任务编号
     * @author xu.baoji
     * @date 2013-7-26
     */
    public void updateTask(Integer completeStatus, int state, int taskId);

    /**
     * 通过任务接受人查询任务id
     *
     * @param designee 任务接受人
     * @return List<Integer> 任务id 列表
     * @author xu.baoji
     * @date 2013-7-30
     */
    public List<Integer> findTaskByDesignee(Integer designee);

    /**
     * 批量添加任务接受人
     *
     * @param taskId    任务id
     * @param designees 任务接受人id
     * @author xu.baoji
     * @date 2013-7-30
     */
    public void addTaskDesignees(Integer taskId, List<Integer> designees);

    /**
     * 批量删除任务接受人
     *
     * @param taskId 任务id
     * @author xu.baoji
     * @date 2013-7-30
     */
    public void deleteTaskDesignees(Integer taskId);


}
