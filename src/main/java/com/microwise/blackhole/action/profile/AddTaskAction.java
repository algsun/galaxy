package com.microwise.blackhole.action.profile;

import com.microwise.blackhole.bean.Department;
import com.microwise.blackhole.bean.Task;
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

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 添加我的任务Action
 *
 * @author: wanggeng
 * @date: 13-7-24 下午1:33
 * @check @duan.qixin 2013年7月29日 #4695
 */
@Beans.Action
@Blackhole
public class AddTaskAction {

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

    //output

    /**
     * 用户列表Map,用来分用户部门
     */
    private Map<String, List<User>> userMap;

    //input
    /**
     * 任务标题
     */
    private String taskTitle;

    /**
     * 任务描述信息
     */
    private String taskInfo;

    /**
     * 截至时间
     */
    private Date endDate;

    /**
     * 发布人
     */
    private User releaser = Sessions.createByAction().currentUser();

    /**
     * 指派人id，多个id以逗号分隔
     */
    private String userIds;

    /**
     * 获得siteId 当前站点编号
     */
    private String siteId = Sessions.createByAction().currentSiteId();
    /**
     * 站点id
     */
    private int logicGroupId = Sessions.createByAction().currentLogicGroup().getId();

    /**
     * 跳转到添加我的任务界面
     *
     * @return EventAction.SUCCESS
     */
    public String view() {
        try {
            User user = Sessions.createByAction().currentUser();
            Integer departmentId = null;
            Department department = user.getDepartment();
            if (department != null) {
                departmentId = user.getDepartment().getId();
            }
            List<User> userList = userService.findUserListsByDepartment(logicGroupId, departmentId);
            userMap = BlackholeUtil.getSelectionList(userList);
        } catch (Exception e) {
            log.error("查询用户列表失败", e);
        }
        return Action.SUCCESS;
    }

    /**
     * 新增我的任务
     *
     * @return EventAction.SUCCESS
     */
    public String execute() {
        Task task = new Task();
        task.setTaskTitle(taskTitle);
        task.setTaskInfo(taskInfo);
        task.setReleaseDate(new Date());
        task.setEndDate(endDate);
        task.setReleaser(releaser);
        task.setCompleteStatus(0);
        task.setState(false);
        task.setLogicGroupId(logicGroupId);

        List<Integer> designeeIds = BlackholeUtil.getDesigneeIdList(userIds);

        try {
            taskService.createTask(task, designeeIds);
            ActionMessage.createByAction().success("我的任务添加成功");
            logger.log("我的任务", "添加我的任务");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("我的任务添加失败");
            logger.logFailed("我的任务", "添加我的任务");
            log.error("添加我的任务", e);
        }
        return Action.SUCCESS;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(String taskInfo) {
        this.taskInfo = taskInfo;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Map<String, List<User>> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<String, List<User>> userMap) {
        this.userMap = userMap;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }
}
