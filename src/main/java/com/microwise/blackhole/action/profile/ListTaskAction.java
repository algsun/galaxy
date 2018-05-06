package com.microwise.blackhole.action.profile;

import com.microwise.blackhole.bean.Task;
import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.TaskService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeLoggerUtil;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的任务列表Action
 *
 * @author: wanggeng
 * @date: 13-7-24 上午9:13
 * @check @duan.qixin 2013年7月29日 #4720
 */
@Beans.Action
@Blackhole
public class ListTaskAction {

    /**
     * 每页多少条数据
     */
    private static final int TASK_PAGE_SIZE = 10;

    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(AddTaskAction.class);

    /**
     * 我的任务Service
     */
    @Autowired
    private TaskService taskService;
    @Autowired
    private BlackholeLoggerUtil logger;


    /**
     * 获得siteId 当前站点编号
     */
    private String siteId = Sessions.createByAction().currentSiteId();
    private int logicGroupId= Sessions.createByAction().currentLogicGroup().getId();

    private User currentUser = Sessions.createByAction().currentUser();

    //input
    /**
     * 当前页码
     */
    private int index = 1;

    /**
     * 任务状态
     * 1：全部任务
     * 2：未结束
     */
    private int taskState;

    //output
    /**
     * 任务列表
     */
    private List<Task> taskList = new ArrayList<Task>();

    /**
     * 首页显示Task
     */
    private List<Map<String, Object>> tasks = null;

    /**
     * 总页数
     */
    private int pageCount;

    /**
     * 首页我的任务显示列表Action
     *
     * @return EventAction.SUCCESS
     */
    public String indexListTasks() {
        ConfigFactory.Configs appConfig = ConfigFactory.getInstance().getConfig(Constants.Config.CONFIG_PROPERTIES_URL);
        boolean taskPrivilegeEnable = Boolean.parseBoolean(appConfig.get("blackhole.myTask.enable"));

        tasks = new ArrayList<Map<String, Object>>();
        taskList = taskService.findTasks(logicGroupId, currentUser.getId(), index, 5, 0);
        for (Task t : taskList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", t.getTaskTitle());
            map.put("releaseDate", t.getReleaseDate());
            map.put("id", t.getId());
            map.put("privilegeEnable", taskPrivilegeEnable);
            tasks.add(map);
        }
        return Action.SUCCESS;
    }

    /**
     * 我的任务列表
     *
     * @return EventAction.SUCCESS
     */
    public String execute() {
        ActionMessage.createByAction().consume();
        try {
            int totalCount;
            if (taskState == 1) {
                taskList = taskService.findTasks(logicGroupId, currentUser.getId(), index, TASK_PAGE_SIZE, -1);
                totalCount = taskService.findTaskCount(logicGroupId, currentUser.getId(), -1);
            } else {
                taskList = taskService.findTasks(logicGroupId, currentUser.getId(), index, TASK_PAGE_SIZE, 0);
                totalCount = taskService.findTaskCount(logicGroupId, currentUser.getId(), 0);
            }

            pageCount = (totalCount % TASK_PAGE_SIZE) == 0 ? (totalCount / TASK_PAGE_SIZE) : (totalCount / TASK_PAGE_SIZE) + 1;
            logger.log("我的任务", "查询我的任务");

        } catch (Exception e) {
            logger.logFailed("我的任务", "查询我的任务");
            log.error("我的任务列表查询失败", e);
        }
        return Action.SUCCESS;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public List<Map<String, Object>> getTasks() {
        return tasks;
    }

    public void setTasks(List<Map<String, Object>> tasks) {
        this.tasks = tasks;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getTaskState() {
        return taskState;
    }

    public void setTaskState(int taskState) {
        this.taskState = taskState;
    }
}
