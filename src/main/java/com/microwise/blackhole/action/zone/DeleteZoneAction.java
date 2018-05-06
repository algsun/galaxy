package com.microwise.blackhole.action.zone;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blueplanet.proxy.ZoneManagerProxy;
import com.microwise.blueplanet.sys.AppCacheHolder;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.proxima.proxy.ZoneProxy;
import com.opensymphony.xwork2.ActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 删除区域
 * </pre>
 *
 * @author Wang yunlong
 * @time 13-1-18 上午9:56
 * @check @gaohui 2013-02-25 #1562
 */
@Beans.Action
@Blackhole
public class DeleteZoneAction extends BlueplanetLoggerAction {
    private static final Logger log = LoggerFactory.getLogger(DeleteZoneAction.class);
    @Autowired
    private AppCacheHolder appCacheHolder;
    @Autowired
    private ZoneManagerProxy zoneManagerProxy;
    @Autowired
    private ZoneProxy zoneProxy;

    //input
    /**
     * 区域id
     */
    private String zoneId;

    @Route("/blackhole/zone/{zoneId}/delete")
    public String execute() {
        String siteId = ManagerIndexAction.getSiteId();
        Map<String, Object> data = new HashMap<String, Object>();
        try {
            if (zoneManagerProxy.isEmpty(zoneId) && (!zoneProxy.hasDV(zoneId))) {
                zoneManagerProxy.deleteZone(zoneId);
                data.put("success", true);
                appCacheHolder.evictZoneDeviceTree(siteId);
                log("区域管理", "删除区域,id为:" + zoneId);
            } else {
                data.put("success", false);
                data.put("msg", "该区域还有子区域、设备或摄像机,不能被删除");
            }
        } catch (Exception e) {
            data.put("success", false);
            data.put("msg", "未知异常");
            log.error("删除区域", e);
        }
        ActionContext.getContext().put("data", data);
        return Results.json().root("data").done();
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }
}
