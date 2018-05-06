package com.microwise.blackhole.action.zone;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.MethodType;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blueplanet.bean.vo.ZoneVO;
import com.microwise.blueplanet.proxy.ZoneManagerProxy;
import com.microwise.blueplanet.sys.AppCacheHolder;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.ActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 添加区域
 * </pre>
 *
 * @author Wang yunlong
 * @date 13-1-18 上午9:57
 * @check @gaohui 2013-02-25 #1471
 */
@Beans.Action
@Blackhole
public class AddZoneAction extends BlueplanetLoggerAction {
    public static final Logger log = LoggerFactory.getLogger(AddZoneAction.class);

    @Autowired
    private AppCacheHolder appCacheHolder;
    @Autowired
    private ZoneManagerProxy zoneManagerProxy;

    //input
    /**
     * 区域名称
     */
    private String zoneName;
    /**
     * 上级区域id
     */
    private String parentZoneId;

    /**
     * 区域位置
     */
    private int position;

    /**
     * 添加顶级区域
     */
    @Route(value = "/blackhole/zone/add.json", method = MethodType.POST)
    public String addZone() {
        String siteId = ManagerIndexAction.getSiteId();
        ZoneVO zone = new ZoneVO();
        zone.setZoneName(zoneName);
        zone.setSiteId(siteId);
        zone.setParentId(Strings.emptyToNull(parentZoneId));
        zone.setPosition(position);
        Map<String, Object> data = new HashMap<String, Object>();
        try {
            if (zoneManagerProxy.containsName(siteId, Strings.emptyToNull(parentZoneId), zoneName)) {
                data.put("success", false);
                data.put("msg", "该名称已存在");
            } else {
                String zoneId = zoneManagerProxy.saveZone(zone);
                data.put("success", true);
                ZoneVO newZone = zoneManagerProxy.findZoneById(zoneId);
                data.put("zone", newZone);
                //清除缓存
                appCacheHolder.evictZoneDeviceTree(siteId);
                log("区域管理", "添加区域:");

            }
        } catch (Exception e) {
            log.error("添加区域", e);
            data.put("success", false);
            data.put("msg", "未知异常");
        }
        return Results.json().asRoot(data).done();
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getParentZoneId() {
        return parentZoneId;
    }

    public void setParentZoneId(String parentZoneId) {
        this.parentZoneId = parentZoneId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
