package com.microwise.blackhole.action.zone;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.ZoneVO;
import com.microwise.blueplanet.proxy.ZoneManagerProxy;
import com.microwise.common.sys.annotation.Beans;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 获取区域信息
 * </pre>
 *
 * @author Wang yunlong
 * @date 13-4-15 下午4:04
 */
@Beans.Action
@Blackhole
public class GetZoneInfoAction {

    @Autowired
    private ZoneManagerProxy zoneManagerProxy;

    /**
     * 区域id
     */
    private String zoneId;

    /**
     * 区域信息
     */
    private Map<String, Object> zone;

    /**
     * 环境监控资源路径
     */
    private String resourcesPath;

    @Route("/blueplanet/zone/{zoneId}/info.json")
    public String execute() {

        resourcesPath = Sessions.createByAction().getGalaxyResourceURL() + "/blueplanet/images/zonePlanImage";
        zone = new HashMap<String, Object>();
        ZoneVO zonevo = zoneManagerProxy.findZoneById(zoneId);
        zone.put("id", zonevo.getZoneId());
        zone.put("name", zonevo.getZoneName());
        zone.put("resourcesPath", resourcesPath);
        zone.put("planImage", zonevo.getPlanImage());
        return Results.json().root("zone").done();
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public Map<String, Object> getZone() {
        return zone;
    }

    public void setZone(Map<String, Object> zone) {
        this.zone = zone;
    }
}
