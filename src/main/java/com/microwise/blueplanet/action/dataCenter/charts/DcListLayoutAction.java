package com.microwise.blueplanet.action.dataCenter.charts;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.po.DCLayoutPO;
import com.microwise.blueplanet.service.DataCenterService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 布局列表.
 *
 * @author wang.geng
 * @date 13-12-6 下午4:09
 * @check @liu.zhu wang.geng 2013-12-18 #7067
 * @check @xie.deng li.jianfei 2014-3-5  #8049
 */
@Beans.Action
@Blueplanet
public class DcListLayoutAction extends BlueplanetLoggerAction {

    private static final Logger log = LoggerFactory.getLogger(DcListLayoutAction.class);

    /**
     * 数据中心service
     */
    @Autowired
    private DataCenterService dataCenterService;

    /**
     * 页面layout路径
     */
    public static final String _pagePath = "dc-list-layout.ftl";

    /**
     * 布局列表
     */
    private List<DCLayoutPO> layoutList;

    private int logicGroupId = Sessions.createByAction().currentLogicGroup().getId();

    /**
     * siteId站点id
     */
    private String siteId = Sessions.createByAction().currentSiteId();

    @Route("/blueplanet/dataCenter/charts/listLayout")
    public String toListLayout() {
        return layoutList();
    }

    /**
     * 多站点
     */
    @Route("/blueplanet/dataCenter/charts/toAllBasicSite/{logicGroupId}")
    public String toAllBasicSite() {
        return layoutList();
    }

    private String layoutList() {
        try {
            // 获取当前站点id
            layoutList = dataCenterService.findAllLayouts(logicGroupId);
        } catch (Exception e) {
            log.error("布局列表", e);
        }
        log("数据中心", "布局列表");
        return Results.ftl("/blueplanet/pages/dataCenter/charts/pages/index/layout");
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public List<DCLayoutPO> getLayoutList() {
        return layoutList;
    }

    public void setLayoutList(List<DCLayoutPO> layoutList) {
        this.layoutList = layoutList;
    }

    public int getLogicGroupId() {
        return logicGroupId;
    }

    public void setLogicGroupId(int logicGroupId) {
        this.logicGroupId = logicGroupId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }
}
