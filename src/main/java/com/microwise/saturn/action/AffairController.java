package com.microwise.saturn.action;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.saturn.bean.Affair;
import com.microwise.saturn.service.AffairService;
import com.microwise.saturn.sys.Saturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by lijianfei on 15-3-19.
 */
@Controller
@Saturn
@RequestMapping(value = "/manager")
public class AffairController {

    @Autowired
    private AffairService affairService;

    @RequestMapping(value = "affair")
    public String view(Model model) {
        model.addAttribute("_pagePath", "affair/affair-list.ftl");
        return "saturn/pages/manager/layout";
    }

    @RequestMapping(value = "affairs", method = RequestMethod.GET)
    @ResponseBody
    public List<Affair> list() {
        String siteId = Sessions.createByAction().currentSiteId();
        return affairService.findAll(siteId);
    }


    @RequestMapping(value = "affairs/add")
    public String add(Model model) {
        model.addAttribute("_pagePath", "affair/affair-add.ftl");
        return "saturn/pages/manager/layout";
    }

    @RequestMapping(value = "affairs/save", method = RequestMethod.POST)
    public String save(@ModelAttribute Affair affair) {
        try {
            affair.setCreateTime(new Date());
            affair.setCreateBy(Sessions.createByAction().currentUser().getUserName());
            affair.setSiteId(Sessions.createByAction().currentSiteId());
            affairService.save(affair);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:../affair";
    }

    @RequestMapping(value = "affairs/{affairId}/edit")
    public String edit(@PathVariable int affairId, Model model) {
        try {
            Affair affair = affairService.findById(affairId);
            model.addAttribute(affair);
            model.addAttribute("_pagePath", "affair/affair-edit.ftl");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "saturn/pages/manager/layout";
    }

    @RequestMapping(value = "affairs/{affairId}", method = RequestMethod.POST)
    public String update(@PathVariable int affairId, @ModelAttribute Affair affair) {
        try {
            affairService.update(affair);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:../affair";
    }


    @RequestMapping(value = "affairs/{affairId}/delete")
    public String delete(@PathVariable int affairId) {
        affairService.delete(affairId);
        return "redirect:../../affair";
    }
}
