package com.microwise.blackhole.action.zone;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Strings;
import com.microwise.blueplanet.bean.vo.ZoneVO;
import com.microwise.blueplanet.proxy.ZoneManagerProxy;
import com.microwise.common.util.BeanMaps;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 获取上级区域的下级区域
 * 移动区域选择父区域用到
 * </pre>
 *
 * @author Wang yunlong
 * @date 13-1-25 下午3:46
 * @check @gaohui 2013-02-25 #1297
 */
public class GetZoneChildrenAction {
    @Autowired
    private ZoneManagerProxy zoneManagerProxy;

    //input
    /**
     * 上级区域id
     */

    //output
    private String zoneId;
    /**
     * 下级区域列表
     */
    private List<Map<String, Object>> zones;


    @Route("/blackhole/zone/children.json")
    public String execute() {
        String siteId = ManagerIndexAction.getSiteId();
        List<ZoneVO> zoneList = zoneManagerProxy.findZoneList(siteId, Strings.emptyToNull(zoneId));
        zones = BeanMaps.toMap(zoneList, new String[]{"zoneId", "parentId", "zoneName"}, new String[]{"id", "pId", "name"});
        for (Map<String, Object> map : zones) {
            map.put("isParent", true);
            map.put("iconSkin", "area");
        }
        return Results.json().root("zones").done();
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public List<Map<String, Object>> getZones() {
        return zones;
    }

    public void setZones(List<Map<String, Object>> zones) {
        this.zones = zones;
    }
}
