package com.microwise.blackhole.action.zone;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Strings;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blueplanet.bean.vo.ZoneVO;
import com.microwise.blueplanet.proxy.ZoneManagerProxy;
import com.microwise.blueplanet.service.ControlCenterCommunicationService;
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
 * 移动区域
 * </pre>
 *
 * @author Wang yunlong
 * @time 13-1-25 下午3:07
 * @check @gaohui 2013-02-25 #1471
 */
@Beans.Action
@Blackhole
public class MoveZoneAction extends BlueplanetLoggerAction {
    private static final Logger log = LoggerFactory.getLogger(MoveZoneAction.class);

    @Autowired
    private AppCacheHolder appCacheHolder;
    @Autowired
    private ZoneManagerProxy zoneManagerProxy;
    @Autowired
    private ControlCenterCommunicationService controlCenterCommunicationService;
    //input
    /**
     * 区域id
     */
    private String zoneId;
    /**
     * 移动至区域id
     */
    private String newParentId;

    @Route("/blackhole/zone/move.json")
    public String execute() {
        Map<String, Object> data = new HashMap<String, Object>();
        String siteId = ManagerIndexAction.getSiteId();
        try {
            ZoneVO zone = zoneManagerProxy.findZoneById(zoneId);
            if (zoneManagerProxy.containsName(siteId, Strings.emptyToNull(newParentId), zone.getZoneName())) {
                data.put("success", false);
                data.put("msg", "该区域下已经存在名称为“" + zone.getZoneName() + "”的区域");
            } else {
                zoneManagerProxy.changeParent(zoneId, Strings.emptyToNull(newParentId));
                data.put("success", true);
                appCacheHolder.evictZoneDeviceTree(siteId);
                log("区域管理", "移动区域" + zone.getZoneName());
            }
            //上传区域信息
            /*controlCenterCommunicationService.uploadZone(siteId, zoneId);*/
        } catch (Exception e) {
            data.put("success", false);
            data.put("msg", "未知错误");
            log.error("移动区域", e);
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

    public String getNewParentId() {
        return newParentId;
    }

    public void setNewParentId(String newParentId) {
        this.newParentId = newParentId;
    }
}
