package com.microwise.saturn.action;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.Constants;
import com.microwise.common.util.PagingUtil;
import com.microwise.saturn.bean.Literature;
import com.microwise.saturn.service.LiteratureService;
import com.microwise.saturn.sys.Saturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by lijianfei on 15-3-16.
 */
@Controller
@Saturn
public class LiteratureController {

    @Autowired
    private LiteratureService literatureService;

    @RequestMapping(value = "/manager/literature")
    public String manageView(Model model) {
        model.addAttribute("_pagePath", "literature/literature-list.ftl");
        return "saturn/pages/manager/layout";
    }

    @RequestMapping(value = "/literatures")
    public String view(
            @RequestParam(defaultValue = "") String title,
            @RequestParam(defaultValue = "") String keywords,
            @RequestParam(defaultValue = "0") int index,
            Model model) {
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            int pageCount = PagingUtil.pagesCount(
                    literatureService.countLiteratures(siteId, title, keywords),
                    Constants.SIZE_PER_PAGE);
            // 分页查询设备列表
            index = (index == 0 ? 1 : index);
            List<Literature> literatures = literatureService.findLiteratures(siteId, title, keywords, index, Constants.SIZE_PER_PAGE);
            model.addAttribute("_pagePath", "literature-index.ftl");
            model.addAttribute("pageCount", pageCount);
            model.addAttribute("literatures", literatures);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "saturn/pages/layout";
    }

    @RequestMapping(value = "/manager/literatures", method = RequestMethod.GET)
    @ResponseBody
    public List<Literature> list() {
        String siteId = Sessions.createByAction().currentSiteId();
        return literatureService.findAll(siteId);
    }

    @RequestMapping(value = "/manager/literatures/add")
    public String add(Model model) {
        model.addAttribute("_pagePath", "literature/literature-add.ftl");
        return "saturn/pages/manager/layout";
    }

    @RequestMapping(value = "/manager/literatures/save", method = RequestMethod.POST)
    public String save(@ModelAttribute Literature literature) {
        try {
            literature.setCreateTime(new Date());
            literature.setCreateBy(Sessions.createByAction().currentUser().getUserName());
            literature.setSiteId(Sessions.createByAction().currentSiteId());
            literatureService.save(literature);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:../literature";
    }

    @RequestMapping(value = "/manager/literatures/{literatureId}/edit")
    public String edit(@PathVariable int literatureId, Model model) {
        try {
            Literature literature = literatureService.findById(literatureId);
            model.addAttribute(literature);
            model.addAttribute("_pagePath", "literature/literature-edit.ftl");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "saturn/pages/manager/layout";
    }

    @RequestMapping(value = "/manager/literatures/{literatureId}", method = RequestMethod.POST)
    public String update(@PathVariable int literatureId, @ModelAttribute Literature literature) {
        try {
            literatureService.update(literature);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:../literature";
    }

    @RequestMapping(value = "/manager/literatures/{literatureId}/delete")
    public String delete(@PathVariable int literatureId) {
        literatureService.delete(literatureId);
        return "redirect:../../literature";
    }

}
