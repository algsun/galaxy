package com.microwise.blueplanet.action.zone;

import com.opensymphony.xwork2.Action;

/**
 * 区域设备
 *
 * @author gaohui
 * @date 13-1-11 13:21
 */
public class DevicesAction {
    // 内容页面
    private final String _pagePath = "devices.ftl";

    public String execute() {
        return Action.SUCCESS;
    }

    public String get_pagePath() {
        return _pagePath;
    }
}
