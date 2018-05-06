package com.microwise.phoenix.action.uma;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.LoggingAction;
import com.microwise.blackhole.sys.MessageActionUtil;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.proxima.bean.Zone;
import com.microwise.uma.service.RuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 配置可显示人员停留区域
 *
 * @author xu.yuexi
 * @date 2014-09-3
 */
@Beans.Action
@Phoenix
public class ConfigUserStopoverAction extends LoggingAction {

    public static final Logger log = LoggerFactory.getLogger(ConfigUserStopoverAction.class);
    public static final String _pagePath = "../uma/config-user-stopover.ftl";

    /**
     * 查询相关区域
     */
    @Autowired
    private RuleService ruleService;

    /**
     * 可选择区域
     */
    private List<Zone> zoneList;
    /**
     * 配置区域
     */
    private String[] zoneIds;

    @Route("/phoenix/uma/configPhoenix")
    public String view() {
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            zoneList = ruleService.findZoneList(siteId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Results.ftl("/phoenix/pages/index/layout");
    }

    @Route("/phoenix/uma/configPhoenix/zones")
    public String excute() {
        try {
            ruleService.updateRulesIsCount(zoneIds);
            MessageActionUtil.success("更改区域成功");
        } catch (Exception e) {
            e.printStackTrace();
            MessageActionUtil.fail("更改区域失败");
        }
        return Results.redirect("/phoenix/uma/userStopover");
    }


    public static String get_pagePath() {
        return _pagePath;
    }


    public List<Zone> getZoneList() {
        return zoneList;
    }

    public void setZoneList(List<Zone> zoneList) {
        this.zoneList = zoneList;
    }

    public void setZoneIds(String[] zoneIds) {
        this.zoneIds = zoneIds;
    }

}
