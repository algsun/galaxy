package com.microwise.saturn.action;

import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.util.GalaxyIdUtil;
import com.microwise.saturn.bean.TaskRecordVO;
import com.microwise.saturn.service.TaskRecordService;
import com.microwise.saturn.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 任务记录
 */
@Controller("taskRecordController")
public class TaskRecordController {
    public static final Logger log = LoggerFactory.getLogger(TaskRecordController.class);
    public static final String _pagePath = "task/task-records-index.ftl";


    @Autowired
    private TaskRecordService taskRecordService;

    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/task_records/{taskId}/index")
    public String index(Model model, @PathVariable String taskId) {
        try {
            model.addAttribute("_pagePath", _pagePath);
            model.addAttribute("taskId", taskId);
            model.addAttribute("task",taskService.findById(taskId));
        } catch (Exception e) {
            log.error("任务记录", e);
        }
        return "saturn/pages/manager/layout";
    }

    @RequestMapping(value = "/task_records/{taskId}/to_insert")
    public String toInsert(Model model, @PathVariable String taskId) {
        try {
            List<User> users = userService.findUserLists(Sessions.createByAction().currentLogicGroup().getId());
            int userId = Sessions.createByAction().currentUser().getId();
            model.addAttribute("_pagePath", "task/task-records-insert.ftl");
            model.addAttribute("recordDate", new Date());
            model.addAttribute("taskId", taskId);
            model.addAttribute("users", users);
            model.addAttribute("userId", userId);
        } catch (Exception e) {
            log.error("添加任务跳转", e);
        }

        return "saturn/pages/manager/layout";
    }

    @RequestMapping(value = "task_records/do_insert")
    public String doInsert(@ModelAttribute TaskRecordVO taskRecordVO) {
        try {
            taskRecordVO.setId(GalaxyIdUtil.get64UUID());
            taskRecordVO.setUpdateDate(new Date());
            taskRecordService.insert(taskRecordVO);
        } catch (Exception e) {
            log.error("添加任务记录", e);
        }
        return "redirect:/saturn/task_records/" + taskRecordVO.getTaskId() + "/index";
    }


    @RequestMapping(value = "/task_records/to_update/{id}")
    public String toUpdate(Model model, @PathVariable String id) {

        try {
            TaskRecordVO taskRecordVO = taskRecordService.findTaskRecord(id);
            List<User> users = userService.findUserLists(Sessions.createByAction().currentLogicGroup().getId());
            model.addAttribute("_pagePath", "task/task-records-update.ftl");
            model.addAttribute("taskRecordVO", taskRecordVO);
            model.addAttribute("users", users);
        } catch (Exception e) {
            log.error("更新任务记录跳转", e);
        }

        return "saturn/pages/manager/layout";
    }

    @RequestMapping(value = "task_records/do_update")
    public String doUpdate(@ModelAttribute TaskRecordVO taskRecordVO) {
        try {
            taskRecordVO.setUpdateDate(new Date());
            taskRecordService.updateRecord(taskRecordVO);
        } catch (Exception e) {
            log.error("更新任务记录", e);
        }
        return "redirect:/saturn/task_records/" + taskRecordVO.getTaskId() + "/index";
    }

    @RequestMapping(value = "/task_records/to_handle/{id}")
    public String toHandle(Model model, @PathVariable String id) {
        try {
            int userId = Sessions.createByAction().currentUser().getId();
            TaskRecordVO taskRecordVO = taskRecordService.findTaskRecord(id);
            List<User> users = userService.findUserLists(Sessions.createByAction().currentLogicGroup().getId());
            model.addAttribute("_pagePath", "task/task-records-handle.ftl");
            model.addAttribute("users", users);
            model.addAttribute("userId", userId);
            model.addAttribute("handleDate", new Date());
            model.addAttribute("taskRecordVO", taskRecordVO);
        } catch (Exception e) {
            log.error("处理任务记录跳转页面", e);
        }

        return "saturn/pages/manager/layout";
    }

    @RequestMapping(value = "task_records/do_handle")
    public String doHandle(@ModelAttribute TaskRecordVO taskRecordVO) {
        try {
            taskRecordVO.setUpdateDate(new Date());
            taskRecordService.updateHandle(taskRecordVO);
        } catch (Exception e) {
            log.error("处理任务记录", e);
        }
        return "redirect:/saturn/task_records/" + taskRecordVO.getTaskId() + "/index";
    }

    @RequestMapping(value = "/task_records/{taskId}")
    @ResponseBody
    public List<TaskRecordVO> findTaskRecords(@PathVariable String taskId) {
        try {
            return taskRecordService.findTaskRecords(taskId);
        } catch (Exception e) {
            log.error("查询任务记录", e);
            return null;
        }
    }

    @RequestMapping(value = "/task_records/{taskId}/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable String id, @PathVariable String taskId) {
        try {
            taskRecordService.delete(id);
        } catch (Exception e) {
            log.error("删除任务记录", e);
        }
        return "redirect:/saturn/task_records/" + taskId + "/index";
    }

}
