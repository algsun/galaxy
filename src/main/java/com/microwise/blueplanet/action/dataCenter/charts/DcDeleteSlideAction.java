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
 * 删除幻灯片.
 *
 * @author wang.geng
 * @date 14-2-13 下午2:11
 * @check xie.deng li.jianfei  2014-3-5
 */
@Beans.Action
@Blueplanet
public class DcDeleteSlideAction extends BlueplanetLoggerAction {
    /**
     * 日志对象
     */
    public static final Logger log = LoggerFactory.getLogger(DcAddLayoutAction.class);

    /**
     * 数据中心服务
     */
    @Autowired
    private DataCenterService dataCenterService;

    //output
    /**
     * 页面layout路径
     */
    public static final String _pagePath = "dc-list-slide.ftl";

    //input
    /**
     * 布局ID
     */
    private String uuid;

    /**
     * 控件ID
     */
    private String itemId;

    /**
     * 幻灯片ID
     */
    private int slideId;

    /**
     * 判断幻灯片是否有子幻灯片
     * true： 有子幻灯片
     * false：无子幻灯片
     */
    private boolean ok = false;

    /**
     * siteId站点id
     */
    private String siteId = Sessions.createByAction().currentSiteId();

    @Route("/blueplanet/dataCenter/charts/index/slide/deleteSlide/{uuid}/{itemId}/{slideId}")
    public String deleteSlide() {
        try {
            dataCenterService.deleteSlideById(slideId);
        } catch (Exception e) {
            log.error("信息发布，删除幻灯片", e);
        }
        log("删除幻灯片", "信息发布");
        return Results.redirect(String.format("/blueplanet/dataCenter/charts/index/slide/toSlideList/%s/%s", uuid, itemId));
    }

    @Route("/blueplanet/dataCenter/charts/index/slide/deleteAllSlide/{uuid}/{itemId}")
    public String deleteAllSlide() {
        try {
            dataCenterService.deleteItemAllSlidesByIds(uuid, itemId);
        } catch (Exception e) {
            log.error("信息发布，删除控件的所有幻灯片", e);
        }
        log("删除控件的所有幻灯片", "信息发布");
        return Results.redirect(String.format("/blueplanet/dataCenter/charts/toUpdateItem/%s/2", uuid));
    }

    @Route("/blueplanet/dataCenter/charts/index/slide/hasChildSlide.json")
    public String hasChildSlide() {
        try {
            List<DCSlidePO> slideLists = dataCenterService.findSlideList(uuid, itemId);
            if (slideLists.size() > 0) {
                ok = true;
            }
        } catch (Exception e) {
            log.error("信息发布，判断是否有子幻灯片", e);
        }
        log("判断是否有子幻灯片", "信息发布");
        return Results.json().root("ok").done();
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

    public int getSlideId() {
        return slideId;
    }

    public void setSlideId(int slideId) {
        this.slideId = slideId;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }
}
