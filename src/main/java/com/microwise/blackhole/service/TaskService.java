package com.microwise.blackhole.service;

import com.microwise.blackhole.bean.Task;
import com.microwise.blackhole.bean.TaskRecord;

import java.util.List;

/**
 * 任务service
 *
 * @author xubaoji
 * @date 2013-6-8
 * @check duan.qixin 2013-7-29 #4700
 */
public interface TaskService {

    /**
     * 分页查询任务
     *
     * @param logicGroupId 站点id
     * @param userId 相关人员 （发布人或者任务接收人）
     * @param index  当前页码
     * @param size   分页单位
     * @param state  任务状态  0 未结束 ，-1 全部
     * @return List<Task> 任务列表（不携带回复记录列表）
     * @author 许保吉
     * @date 2013-6-8
     */
    public List<Task> findTasks(int logicGroupId, Integer userId, Integer index,
                                Integer size, int state);

    /**
     * 查询数量
     *
     * @param logicGroupId 站点id
     * @param userId 相关人员（指派人或者任务接受人）
     * @param state  任务状态  0 未结束 -1 全部
     * @return Integer
     * @author 许保吉
     * @date 2013-6-8
     */
    public Integer findTaskCount(int logicGroupId, Integer userId, int state);

    /**
     * 通过任务id查询一个任务（携带有回复记录列表）
     *
     * @param taskId 任务id编号
     * @return Task 任务实体（携带有回复记录列表）
     * @author 许保吉
     * @date 2013-6-8
     */
    public Task findById(Integer taskId);

    /**
     * 创建任务
     *
     * @param task      任务实体
     * @param designees 任务接收人id 列表
     * @author 许保吉
     * @date 2013-6-8
     */
    public void createTask(Task task, List<Integer> designees);

    /**
     * 添加一条任务回复记录
     *
     * @param taskRecord 任务回复记录实体
     * @author 许保吉
     * @date 2013-6-8
     */
    public void addTaskRecord(TaskRecord taskRecord);

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
     * @param designees      接受人列表 ，如果 接受人没有改变请传null
     * @param completeStatus 完成度 如果没有修改 传null
     * @param state          任务状态 0 未结束， 1 已结束 默认为0
     * @param taskId         任务id
     * @author xu.baoji
     * @date 2013-7-26
     */
    public void updateTask(List<Integer> designees, Integer completeStatus, int state, int taskId);

}
