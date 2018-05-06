package com.microwise.phoenix.action;

import com.microwise.phoenix.sys.Phoenix;
import com.opensymphony.xwork2.Action;
import org.springframework.stereotype.Component;

/**
 * @author gaohui
 * @date 13-7-5 15:14
 */
@Component
@Phoenix
public class HomeAction {
    public static final String _pagePath = "index.ftl";

    public String execute() {
        return Action.SUCCESS;
    }

    public static String get_pagePath() {
        return _pagePath;
    }
}
