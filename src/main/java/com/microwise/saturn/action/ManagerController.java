package com.microwise.saturn.action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lenovo on 15-3-13.
 */
@Controller("managerController")
@RequestMapping("/manager")
public class ManagerController {

    public static final String _pagePath = "manager/manager-index.ftl";

    @RequestMapping("index")
    public String index(Model model) {
        model.addAttribute("_pagePath",_pagePath);
        return "saturn/pages/layout";
    }

}
