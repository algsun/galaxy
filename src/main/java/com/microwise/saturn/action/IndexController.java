package com.microwise.saturn.action;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.saturn.bean.TaskVO;
import com.microwise.saturn.service.TaskService;
import com.microwise.saturn.sys.Saturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by lijianfei on 15-3-12.
 */
@Controller
@Saturn
public class IndexController {

    @Autowired
    private TaskService taskService;

    @RequestMapping(value = {"/", "/index"})
    public String index(Model model) {
        List<TaskVO> tasks = taskService.findTasks(Sessions.createByAction().currentSiteId());
        model.addAttribute("_pagePath", "saturn-index.ftl");
        if (tasks.size() > 0 && tasks.size() <= 4) {
            model.addAttribute("tasks", tasks);
        } else if (tasks.size() > 4) {
            model.addAttribute("tasks", tasks.subList(0, 4));
        }
        return "saturn/pages/layout";
    }
}
