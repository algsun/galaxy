package com.microwise.saturn.action;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.util.DateTimeUtil;
import com.microwise.saturn.bean.Affair;
import com.microwise.saturn.service.AffairService;
import com.microwise.saturn.sys.Saturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Controller
@Saturn
public class StatisticsController {

    @Autowired
    private AffairService affairService;

    @RequestMapping(value = "statistics/result")
    public String statistics(@RequestParam(required = false) Integer type, @RequestParam(required = false) Integer year, @RequestParam(required = false) Integer quarterNum, Model model) {
        String siteId = Sessions.createByAction().currentSiteId();
        Calendar calendar = Calendar.getInstance();
        if (year == null) year = calendar.get(Calendar.YEAR);
        List<Affair> affairList = affairService.findAffairChart(siteId, year, quarterNum);
        List<Affair> affairs = affairService.findAllByTypeAndYear(siteId, type, year, quarterNum);
        model.addAttribute("_pagePath", "statistics-result.ftl");
        model.addAttribute("affairList", affairList);
        model.addAttribute("type", type);
        model.addAttribute("year", year);
        model.addAttribute("quarterNum", quarterNum);
        model.addAttribute("affairs", affairs);
        return "saturn/pages/layout";
    }
}
