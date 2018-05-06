package com.microwise.saturn.action;

import com.microwise.common.sys.ConfigFactory;
import com.microwise.saturn.sys.Saturn;
import com.microwise.saturn.webservice.*;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Controller
@Saturn
@RequestMapping("statistics")
public class RepairController {

    @RequestMapping(value = "repair")
    public String statistics(Model model) {
        String pictureUrl = ConfigFactory.getInstance().getConfig("webservice.properties").get("service.pictures");
        model.addAttribute("_pagePath", "statistics-repair.ftl");
        model.addAttribute("pictureUrl", pictureUrl);
        return "saturn/pages/layout";
    }

    @RequestMapping(value = "repair/relic_chart")
    @ResponseBody
    public List<MaterialSummary> getRelicChart(@RequestParam(required = false) Integer year, @RequestParam(required = false) Integer quarterNum) {

        List<MaterialSummary> materialSummaryList = new ArrayList<MaterialSummary>();
        try {
            if (year == null) {
                year = DateTime.now().getDayOfYear();
            }

            XMLGregorianCalendar xmlCurrentQuarterStartTime = getQuarterStartDate(year, quarterNum);
            XMLGregorianCalendar xmlCurrentQuarterEndTime = getQuarterEndDate(year, quarterNum);

            IRpService iRpService = new RpService().getBasicHttpBindingIRpService();

            ArrayOfMaterialSummary arrayOfMaterialSummary = iRpService.getSummaryWithRelicMaterial(xmlCurrentQuarterStartTime, xmlCurrentQuarterEndTime);
            materialSummaryList = arrayOfMaterialSummary.getMaterialSummary();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return materialSummaryList;
    }

    @RequestMapping(value = "repair/relic_table")
    @ResponseBody
    public List<RelicData> getRelicTable(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer quarterNum,
            @RequestParam(required = false) String type) {
        List<RelicData> relics = new ArrayList<RelicData>();
        try {
            if (year == null) {
                year = DateTime.now().getDayOfYear();
            }
            XMLGregorianCalendar xmlCurrentQuarterStartTime = getQuarterStartDate(year, quarterNum);
            XMLGregorianCalendar xmlCurrentQuarterEndTime = getQuarterEndDate(year, quarterNum);

            IRpService iRpService = new RpService().getBasicHttpBindingIRpService();
            ArrayOfRelicData arrayOfRelicData = iRpService.getListByRelicMaterial(xmlCurrentQuarterStartTime, xmlCurrentQuarterEndTime, type);
            relics = arrayOfRelicData.getRelicData();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return relics;
    }

    @RequestMapping(value = "repair/user_chart")
    @ResponseBody
    public List<OwnerSummary> getUserChart(@RequestParam(required = false) Integer year, @RequestParam(required = false) Integer quarterNum) {

        List<OwnerSummary> ownerSummaries = new ArrayList<OwnerSummary>();
        try {
            if (year == null) {
                year = DateTime.now().getDayOfYear();
            }

            XMLGregorianCalendar xmlCurrentQuarterStartTime = getQuarterStartDate(year, quarterNum);
            XMLGregorianCalendar xmlCurrentQuarterEndTime = getQuarterEndDate(year, quarterNum);

            IRpService iRpService = new RpService().getBasicHttpBindingIRpService();

            ArrayOfOwnerSummary arrayOfOwnerSummary = iRpService.getSummaryWithOwner(xmlCurrentQuarterStartTime, xmlCurrentQuarterEndTime);
            ownerSummaries = arrayOfOwnerSummary.getOwnerSummary();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ownerSummaries;
    }

    @RequestMapping(value = "repair/user_table")
    @ResponseBody
    public List<RelicData> getUserTable(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer quarterNum,
            @RequestParam(required = false) String userId) {
        List<RelicData> relics = new ArrayList<RelicData>();
        try {
            if (year == null) {
                year = DateTime.now().getDayOfYear();
            }
            XMLGregorianCalendar xmlCurrentQuarterStartTime = getQuarterStartDate(year, quarterNum);
            XMLGregorianCalendar xmlCurrentQuarterEndTime = getQuarterEndDate(year, quarterNum);

            IRpService iRpService = new RpService().getBasicHttpBindingIRpService();
            ArrayOfRelicData arrayOfRelicData = iRpService.getListByOwner(xmlCurrentQuarterStartTime, xmlCurrentQuarterEndTime, userId);
            relics = arrayOfRelicData.getRelicData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return relics;
    }

    /**
     * 根据年份，季度 获取季度的开始时间
     *
     * @param year
     * @param quarterNum
     * @return
     */
    private XMLGregorianCalendar getQuarterStartDate(Integer year, Integer quarterNum) {
        LocalDate quarterStartDate = new LocalDate();
        if (quarterNum == null) {
            quarterStartDate = new LocalDate(year, 1, 1);
        } else if (quarterNum == 1) {
            quarterStartDate = new LocalDate(year, 1, 1);
        } else if (quarterNum == 2) {
            quarterStartDate = new LocalDate(year, 4, 1);
        } else if (quarterNum == 3) {
            quarterStartDate = new LocalDate(year, 7, 1);
        } else if (quarterNum == 4) {
            quarterStartDate = new LocalDate(year, 10, 1);
        }
        RpServiceUtil rpServiceUtil = new RpServiceUtil();
        return rpServiceUtil.convertToXMLGregorianCalendar(quarterStartDate.toDate());
    }

    /**
     * 根据年份，季度 获取季度的结束时间
     *
     * @param year
     * @param quarterNum
     * @return
     */
    private XMLGregorianCalendar getQuarterEndDate(Integer year, Integer quarterNum) {
        LocalDate quarterEndDate = new LocalDate();
        if (quarterNum == null) {
            quarterEndDate = new LocalDate(year, 12, 31);
        } else if (quarterNum == 1) {
            quarterEndDate = new LocalDate(year, 3, 31);
        } else if (quarterNum == 2) {
            quarterEndDate = new LocalDate(year, 6, 30);
        } else if (quarterNum == 3) {
            quarterEndDate = new LocalDate(year, 9, 30);
        } else if (quarterNum == 4) {
            quarterEndDate = new LocalDate(year, 12, 31);
        }
        RpServiceUtil rpServiceUtil = new RpServiceUtil();
        return rpServiceUtil.convertToXMLGregorianCalendar(quarterEndDate.toDate());
    }


}
