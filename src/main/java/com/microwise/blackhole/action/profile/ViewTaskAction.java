package com.microwise.blackhole.action.profile;

import com.microwise.blackhole.bean.Department;
import com.microwise.blackhole.bean.Task;
import com.microwise.blackhole.bean.TaskRecord;
import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.TaskService;
import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeLoggerUtil;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blackhole.util.BlackholeUtil;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 我的任务详情Action
 *
 * @author: wanggeng
 * @date: 13-7-24 下午1:30
 * @check @duan.qixin 2013年7月29日 #4720
 */

@Beans.Action
@Blackhole
public class ViewTaskAction {

    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(AddTaskAction.class);

    /**
     * 我的任务Service
     */
    @Autowired
    private TaskService taskService;

    /**
     * 用户信息 service
     */
    @Autowired
    private UserService userService;

    @Autowired
    private BlackholeLoggerUtil logger;

    // 资源地址
    private String resourcesPath;
    // 默认头像
    private String defaultPhoto = UpdateProfileAction.DEFAULT_HEAD_PORTRAIT_URL;

    //input
    /**
     * 任务id
     */
    private int id;
    /**
     * 任务完成进度
     */
    private int completeStatus;
    /**
     * 任务是否完成
     */
    private boolean state;
    /**
     * 任务接收人用户ID
     */
    private String userIds;
    /**
     * 回复信息描述
     */
    private String recordInfo;

    //output

    /**
     * 我的任务对象
     */
    private Task task;

    /**
     * 用户列表Map,用来分用户部门
     */
    private Map<String, List<User>> userMap;

    {
        resourcesPath = Sessions.createByAction().getGalaxyResourceURL() + "/blackhole/images";
    }


    /**
     * 跳转到任务详情页面
     *
     * @return EventAction.SUCCESS
     */
    public String view() {
        ActionMessage.createByAction().consume();
        try {
            User user = Sessions.createByAction().currentUser();
            Integer departmentId = null;
            Department department = user.getDepartment();
            if (department != null) {
                departmentId = user.getDepartment().getId();
            }
            task = taskService.findById(id);
            List<User> userList = userService.findUserListsByDepartment(Sessions.createByAction().currentLogicGroup().getId(), departmentId);
            userMap = BlackholeUtil.getSelectionList(userList);
            logger.log("我的任务", "查询任务详情");
        } catch (Exception e) {
            logger.logFailed("我的任务", "查询任务详情");
            log.error("我的任务查询失败", e);
        }
        return Action.SUCCESS;
    }

    /**
     * 添加描述信息
     *
     * @return EventAction.SUCCESS
     */
    public String execute() {
        try {
            Task task = taskService.findById(id);

            List<Integer> idList = getDesigneeIdList(userIds);
            taskService.updateTask(idList, completeStatus, state ? 1 : 0, id);
            TaskRecord taskRecord = new TaskRecord();
            taskRecord.setReplier(Sessions.createByAction().currentUser());
            taskRecord.setRecordInfo(recordInfo);
            taskRecord.setRecordDate(new Date(System.currentTimeMillis()));
            taskRecord.setEndDate(task.getEndDate());
            taskRecord.setTask(task);
            taskRecord.setCompleteStatus(completeStatus);
            taskRecord.setState(state);
            taskService.addTaskRecord(taskRecord);
            ActionMessage.createByAction().success("回复记录添加成功.");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("回复记录添加失败.");
            log.error("服务器端异常", e);
        }
        return Action.SUCCESS;
    }

    /**
     * 获得传入Action的所有任务指派人的id
     *
     * @param userIds 用户id
     * @return designeeIds idList
     */
    private List<Integer> getDesigneeIdList(String userIds) {
        if (userIds == null) {
            return null;
        }
        List<Integer> designeeIds = new ArrayList<Integer>();
        String ids[] = userIds.split(",");
        for (String id : ids) {
            if (!"none".equals(id.trim())) {
                designeeIds.add(Integer.parseInt(id.trim()));
            }
        }
        return designeeIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public int getCompleteStatus() {
        return completeStatus;
    }

    public void setCompleteStatus(int completeStatus) {
        this.completeStatus = completeStatus;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getRecordInfo() {
        return recordInfo;
    }

    public void setRecordInfo(String recordInfo) {
        this.recordInfo = recordInfo;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public Map<String, List<User>> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<String, List<User>> userMap) {
        this.userMap = userMap;
    }

    public String getResourcesPath() {
        return resourcesPath;
    }

    public String getDefaultPhoto() {
        return defaultPhoto;
    }
}

