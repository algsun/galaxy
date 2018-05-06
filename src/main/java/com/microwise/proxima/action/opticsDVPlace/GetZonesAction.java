package com.microwise.proxima.action.opticsDVPlace;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.proxima.bean.Zone;
import com.microwise.proxima.service.ZoneService;
import com.microwise.proxima.sys.Proxima;
import com.opensymphony.xwork2.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取所有区域树形关系
 *
 * @author li.jianfei
 * @date 2013-3-25
 * @check @wang yunlong 2013-3-29 #2364
 */
@Beans.Action
@Proxima
public class GetZonesAction {
    private static final Logger log = LoggerFactory.getLogger(GetZonesAction.class);

    @Autowired
    private ZoneService zoneService;

    //Input
    /**
     * 区域集合
     */
    private List<Map<String, Object>> zones;


    public String execute() {

        try {
            // 获取当前站点id
            String siteId = Sessions.createByAction().currentLogicGroup().getSite().getSiteId();

            // 获取当前站点下的所有区域信息
            List<Zone> zoneList = zoneService.find(siteId);

            zones = new ArrayList<Map<String, Object>>();

            for (Zone zone : zoneList) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", zone.getId());
                if (zone.getParent() == null) {
                    map.put("pId", null);
                } else {
                    map.put("pId", zone.getParent().getId());
                }
                map.put("name", zone.getName());
                map.put("isParent", true);
                map.put("iconSkin", "area");
                zones.add(map);
            }
        } catch (Exception e) {
            log.error("", e);
        }

        return Action.SUCCESS;
    }

    public List<Map<String, Object>> getZones() {
        return zones;
    }

    public void setZones(List<Map<String, Object>> zones) {
        this.zones = zones;
    }
}
