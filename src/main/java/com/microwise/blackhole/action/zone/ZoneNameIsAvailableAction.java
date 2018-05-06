package com.microwise.blackhole.action.zone;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blueplanet.proxy.ZoneManagerProxy;
import com.microwise.common.sys.annotation.Beans;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <pre>
 *  判断区域名称是否可用
 * </pre>
 *
 * @author Wang yunlong
 * @date 13-1-24 下午2:16
 * @check @gaohui 2013-02-25 #1438
 */
@Beans.Action
@Blackhole
public class ZoneNameIsAvailableAction {
    @Autowired
    private ZoneManagerProxy zoneManagerProxy;
    /**
     * 父区域 id
     */
    private String parentZoneId;
    /**
     * 区域 id
     */
    private String zoneId;
    /**
     * 新区域名称
     */
    private String zoneName;

    /**
     * 返回值
     */
    private boolean isAvailable;

    @Route("/blackhole/zone/{zoneName}/available")
    public String execute() {
        String siteId = ManagerIndexAction.getSiteId();
        isAvailable = zoneManagerProxy.isNameAvailable(siteId, parentZoneId, zoneId, zoneName);
        System.out.println("执行啦");
        return Results.json().root("isAvailable").done();
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getParentZoneId() {
        return parentZoneId;
    }

    public void setParentZoneId(String parentZoneId) {
        this.parentZoneId = parentZoneId;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }
}
