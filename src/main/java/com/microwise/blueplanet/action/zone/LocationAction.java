package com.microwise.blueplanet.action.zone;

import com.opensymphony.xwork2.Action;

/**
 * @author gaohui
 * @date 13-1-14 13:15
 */
public class LocationAction {
    // 内容页面
    private final String _pagePath = "location.ftl";

    public String execute() {
        return Action.SUCCESS;
    }


    public String get_pagePath() {
        return _pagePath;
    }
}
