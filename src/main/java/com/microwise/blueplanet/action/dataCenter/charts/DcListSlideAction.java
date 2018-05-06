package com.microwise.blueplanet.action.dataCenter.charts;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.po.DCSlidePO;
import com.microwise.blueplanet.service.DataCenterService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 跳转至幻灯片列表页面.
 *
 * @author wang.geng
 * @date 14-2-13 上午9:54
 * @check @li.jianfei,xiedeng 2014年3月5日   # 8049
 */
@Beans.Action
@Blueplanet
public class DcListSlideAction extends BlueplanetLoggerAction {

    /**
     * 日志对象
     */
    public static final Logger log = LoggerFactory.getLogger(DcAddLayoutAction.class);

    /**
     * 数据中心服务
     */
    @Autowired
    private DataCenterService dataCenterService;

    //input
    /**
     * 布局ID
     */
    private String uuid;

    /**
     * 控件ID
     */
    private String itemId;

    //output
    /**
     * 页面layout路径
     */
    public static final String _pagePath = "dc-list-slide.ftl";

    /**
     * 幻灯片集合List
     */
    private List<DCSlidePO> slideLists;

    /**
     * siteId站点id
     */
    private String siteId = Sessions.createByAction().currentSiteId();

    @Route("/blueplanet/dataCenter/charts/index/slide/toSlideList/{uuid}/{itemId}")
    public String toSlideList() {
        try {
            slideLists = dataCenterService.findSlideList(uuid, itemId);
        } catch (Exception e) {
            log.error("信息发布，幻灯片列表页面", e);
        }
        log("幻灯片列表页面", "信息发布");
        return Results.ftl("/blueplanet/pages/dataCenter/charts/pages/index/layout");
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public List<DCSlidePO> getSlideLists() {
        return slideLists;
    }

    public void setSlideLists(List<DCSlidePO> slideLists) {
        this.slideLists = slideLists;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }
}
