package com.microwise.phoenix.action;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.phoenix.action.index.EmailSummaryReport;
import com.microwise.phoenix.sys.Phoenix;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @author gaohui
 * @date 13-7-3 14:54
 */
@Beans.Action
@Phoenix
public class ExampleAction {
    public static final String _pagePath = "../blueplanet/example.ftl";

    @Autowired
    private EmailSummaryReport emailSummaryReport;

    public String execute() throws IOException {
        String siteId = Sessions.createByAction().currentSiteId();
        int logicGroupId = Sessions.createByAction().currentLogicGroup().getId();
        Date date = new Date();
        String content = emailSummaryReport.emailContent(siteId, logicGroupId, 1, date, "http://localhost:7080/galaxy/");
        Files.write(content, new File("E:\\gaohui\\webStorms\\misc\\src\\pages\\email-temp.html"), Charsets.UTF_8);
        System.out.println(content);
        return Action.SUCCESS;
    }

    public static String get_pagePath() {
        return _pagePath;
    }
}
