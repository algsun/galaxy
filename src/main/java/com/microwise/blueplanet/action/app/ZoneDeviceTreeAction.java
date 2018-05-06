package com.microwise.blueplanet.action.app;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.service.SiteService;
import com.microwise.blueplanet.sys.AppCacheHolder;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author gaohui
 * @date 13-1-22 13:55
 */
@Beans.Action
@Blueplanet
public class ZoneDeviceTreeAction {
    public static final Logger log = LoggerFactory.getLogger(ZoneDeviceTreeAction.class);

    @Autowired
    private AppCacheHolder appCacheHolder;
    @Autowired
    protected SiteService siteService;

    // 站点-区域-设备树
    protected List<Map<String, Object>> zoneDeviceTree;

    /**
     * 一个站点下的 区域设备树(ztree)
     */
    @Route("/blueplanet/zone-device-tree.json")
    public String execute() {
        String siteId = Sessions.createByAction().currentSiteId();

        try {
            zoneDeviceTree = appCacheHolder.loadZoneDeviceTree(siteId);
        } catch (ExecutionException e) {
            log.error("设备树", e);
        }
        return Results.json().root("zoneDeviceTree").done();
    }

    public List<Map<String, Object>> getZoneDeviceTree() {
        return zoneDeviceTree;
    }
}
