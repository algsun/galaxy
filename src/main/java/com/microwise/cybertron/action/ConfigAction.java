package com.microwise.cybertron.action;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.MethodType;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.collect.Maps;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.cybertron.bean.Config;
import com.microwise.cybertron.service.ConfigService;
import com.microwise.cybertron.sys.Cybertron;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 系统配置 EventAction
 *
 * @author li.jianfei
 * @date 2014-07-18
 */
@Beans.Action
@Cybertron
@Route("/cybertron")
public class ConfigAction {

    public static final Logger log = LoggerFactory.getLogger(ConfigAction.class);

    private static final String _pagePath = "config/config.ftl";

    @Autowired
    private ConfigService configService;

    /**
     * Config uuid
     */
    private String uuid;

    /**
     * 档案全宗号
     */
    private String officialId;

    @Route("setting")
    public String config() {
        return Results.ftl("/cybertron/pages/layout.ftl");
    }

    @Route(value = "config", method = MethodType.GET)
    public String ajaxConfig() {
        Map<String, Object> data = Maps.newHashMap();
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            data.put("config", configService.findConfig(siteId));
            data.put("success", true);
        } catch (Exception e) {
            data.put("success", false);
            log.error("查询档案全宗号", e);
        }
        return Results.json().asRoot(data).done();
    }

    @Route(value = "config", params = {"uuid", "officialId"}, method = MethodType.POST)
    public String ajaxSaveConfig() {
        Map<String, Object> data = Maps.newHashMap();
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            Config config = new Config();
            config.setUuid(uuid);
            config.setSiteId(siteId);
            config.setOfficialId(officialId);
            configService.updateConfig(config);
            data.put("success", true);
        } catch (Exception e) {
            data.put("success", false);
            log.error("更新档案全宗号", e);
        }
        return Results.json().asRoot(data).done();
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

    public String getOfficialId() {
        return officialId;
    }

    public void setOfficialId(String officialId) {
        this.officialId = officialId;
    }
}
