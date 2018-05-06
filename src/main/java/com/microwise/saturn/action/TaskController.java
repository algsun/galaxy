package com.microwise.saturn.action;

import com.google.gson.Gson;
import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.Constants;
import com.microwise.common.util.DateTimeUtil;
import com.microwise.common.util.GalaxyIdUtil;
import com.microwise.common.util.PagingUtil;
import com.microwise.saturn.bean.TaskRecordVO;
import com.microwise.saturn.bean.TaskVO;
import com.microwise.saturn.service.TaskRecordService;
import com.microwise.saturn.service.TaskService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 任务管理
 */
@Controller("taskController")
public class TaskController {

    public static final Logger log = LoggerFactory.getLogger(TaskController.class);

    public static final String _pagePath = "task/task-index.ftl";

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskRecordService taskRecordService;

    @RequestMapping(value = "/task/index", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("_pagePath", _pagePath);
        return "saturn/pages/manager/layout";
    }

    @RequestMapping(value = "/task/tasks", method = RequestMethod.GET)
    @ResponseBody
    public List<TaskVO> list() {
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            return taskService.findTasks(siteId);
        } catch (Exception e) {
            log.error("查询任务列表", e);
            return null;

        }
    }

    @RequestMapping(value = "/tasks")
    public String view(
            @RequestParam(defaultValue = "") String title,
            @RequestParam(defaultValue = "0") int responsible,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date beginDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam(defaultValue = "0") int index,
            Model model) {

        try {
            if (beginDate == null) beginDate = DateTime.now().minusYears(1).toDate();
            if (endDate == null) endDate = DateTime.now().toDate();
            String siteId = Sessions.createByAction().currentSiteId();
            int pageCount = PagingUtil.pagesCount(
                    taskService.countTasks(siteId, title, responsible, beginDate, endDate),
                    Constants.SIZE_PER_PAGE);
            index = (index == 0 ? 1 : index);
            List<TaskVO> tasks = taskService.findTasks(siteId, title, responsible, beginDate, endDate, index, Constants.SIZE_PER_PAGE);

            List<User> users = userService.findUserLists(Sessions.createByAction().currentLogicGroup().getId());
            model.addAttribute("_pagePath", "task-index.ftl");
            model.addAttribute("index", index);
            model.addAttribute("pageCount", pageCount);
            model.addAttribute("tasks", tasks);
            model.addAttribute("users", users);
            model.addAttribute("beginDate", beginDate);
            model.addAttribute("endDate", endDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "saturn/pages/layout";
    }

    @RequestMapping(value = "/task/to_add", method = RequestMethod.GET)
    public String to_add(Model model) {
        try {
            Date date = new Date();
            List<User> users = userService.findUserLists(Sessions.createByAction().currentLogicGroup().getId());
            model.addAttribute("_pagePath", "task/task-add.ftl");
            model.addAttribute("predictStart", DateTimeUtil.startOfDay(date));
            model.addAttribute("predictEnd", date);
            model.addAttribute("realStart", DateTimeUtil.startOfDay(date));
            model.addAttribute("realEnd", date);
            model.addAttribute("createDate", DateTimeUtil.startOfDay(date));
            model.addAttribute("checkDate", DateTimeUtil.startOfDay(date));
            model.addAttribute("invalidDate", DateTimeUtil.startOfDay(date));
            model.addAttribute("users", users);
        } catch (Exception e) {
            log.error("添加任务跳转", e);
        }

        return "saturn/pages/manager/layout";
    }

    @RequestMapping(value = "/task/add", method = RequestMethod.POST)
    public String add(@ModelAttribute TaskVO taskVO) {
        try {
            taskVO.setId(GalaxyIdUtil.get64UUID());
            taskVO.setSiteId(Sessions.createByAction().currentSiteId());
            taskVO.setCreateDate(new Date());
            taskVO.setCreateUser(Sessions.createByAction().currentUser().getId());
            taskVO.setUpdateDate(new Date());
            taskService.insert(taskVO);
        } catch (Exception e) {
            log.error("添加任务", e);
        }
        return "redirect:/saturn/task/index";
    }

    @RequestMapping(value = "/task/delete", method = RequestMethod.GET)
    public String delete(@RequestParam String id) {
        try {
            taskService.deleteById(id);
        } catch (Exception e) {
            log.error("删除任务", e);
        }
        return "redirect:/saturn/task/index";
    }

    @RequestMapping(value = "/task/delete_records/{taskId}")
    @ResponseBody
    public boolean deleteRecordTaskByTaskId(@PathVariable String taskId) {
        try {
            taskRecordService.deleteByTaskId(taskId);
            return true;
        } catch (Exception e) {
            log.error("删除任务记录", e);
            return false;
        }
    }

    @RequestMapping(value = "/task/{path}", method = RequestMethod.GET)
    public String toEdit(@RequestParam String id, Model model,@PathVariable String path) {
        try {
            List<User> users = userService.findUserLists(Sessions.createByAction().currentLogicGroup().getId());
            model.addAttribute("task", taskService.findById(id));
            model.addAttribute("users", users);
            if("to_edit".equals(path)){

                model.addAttribute("_pagePath", "task/task-edit.ftl");
            }else if("to_view".equals(path)){
                model.addAttribute("_pagePath", "task/task-view.ftl");
            }
        } catch (Exception e) {
            log.error("跟新任务页面跳转", e);
        }
        return "saturn/pages/manager/layout";
    }

    @RequestMapping(value = "/task/edit", method = RequestMethod.POST)
    public String edit(@ModelAttribute TaskVO taskVO) {
        try {
            taskVO.setUpdateDate(new Date());
            taskService.updateById(taskVO);
        } catch (Exception e) {
            log.error("更新任务", e);
        }
        return "redirect:/saturn/task/index";
    }


    @RequestMapping(value = "/task/updateState", method = RequestMethod.GET)
    @ResponseBody
    public String updateState(@RequestParam String id, @RequestParam Integer state) {
        try {
            int userId = Sessions.createByAction().currentUser().getId();
            taskService.updateStateById(id, state, userId);
            Gson gson = new Gson();
            return gson.toJson(true);
        } catch (Exception e) {
            log.error("更新状态", e);
            return null;
        }
    }

    @RequestMapping(value = "/task/findTaskRecords")
    @ResponseBody
    public int findTaskRecords(@RequestParam String id) {
        try {
            List<TaskRecordVO> taskRecordVOs = taskRecordService.findTaskRecords(id);
            return taskRecordVOs.size();
        } catch (Exception e) {
            log.error("查询任务记录", e);
            return 0;
        }
    }

}
