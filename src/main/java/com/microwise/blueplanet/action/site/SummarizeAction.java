package com.microwise.blueplanet.action.site;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.ContentBase;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 许月希 on 2014-12-16.
 */
@Beans.Action
@Blueplanet
@ContentBase("/blueplanet/pages/site")
public class SummarizeAction {


    public static final Logger log = LoggerFactory.getLogger(SiteAction.class);

    /**
     * 内容页面
     */
    private static final String _pagePath = "summarize.ftl";

    @Route("/blueplanet/summarize")
    public String view(){
        return Results.ftl("/blueplanet/pages/site/layout.ftl");
    }

    public static String get_pagePath() {
        return _pagePath;
    }
}
