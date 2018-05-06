package com.microwise.blueplanet.action.app;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Strings;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.ZoneVO;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.service.ZoneService;
import com.microwise.blueplanet.sys.AppCacheHolder;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.BeanMaps;
import com.microwise.common.util.ResourceBundleUtil;
import com.opensymphony.xwork2.ActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * 区域设备路径(用于概览导航)
 * <p/>
 *
 * @author gaohui
 * @date 13-2-26 15:49
 * @check @wang yunlong 2013-4-19 #2738
 */
@Beans.Action
@Blueplanet
public class ZoneLocationPathAction {
    public static final Logger log = LoggerFactory.getLogger(ZoneLocationPathAction.class);

    @Autowired
    private AppCacheHolder appCacheHolder;
    @Autowired
    private ZoneService zoneService;
    @Autowired
    private LocationService locationService;

    //input
    /**
     * 位置点ID
     */
    private String locationId;
    /**
     * 区域Id
     */
    private String zoneId;

    @Route(value = "/blueplanet/zone-location-path.json", params = "locationId")
    public String locationPath() throws ExecutionException {
        LocationVO location = appCacheHolder.loadLocation(locationId);
        List<ZoneVO> parentZonePath = new LinkedList<ZoneVO>();
        if (!Strings.isNullOrEmpty(location.getZoneId())) {
            parentPath(parentZonePath, location.getZoneId());
        }

        Map<String, Object> zoneDevicePath = zoneLocationPath(location.getSiteId(), parentZonePath);

        ActionContext.getContext().put("data", zoneDevicePath);
        return Results.json().root("data").done();
    }

    /**
     * 区域路径(区域在末端)
     *
     * @throws ExecutionException
     */
    @Route(value = "/blueplanet/zone-location-path.json", params = "zoneId")
    public String zonePath() throws ExecutionException {
        ZoneVO zone = appCacheHolder.loadZone(zoneId);
        List<ZoneVO> parentZonePath = new LinkedList<ZoneVO>();
        parentPath(parentZonePath, zoneId);

        Map<String, Object> zoneLocationPath = zoneLocationPath(zone.getSiteId(), parentZonePath);

        ActionContext.getContext().put("data", zoneLocationPath);
        return Results.json().root("data").done();
    }

    /**
     * 返回区域位置点路径
     *
     * @param siteId
     * @param parentZonePath
     * @return
     */
    private Map<String, Object> zoneLocationPath(String siteId, List<ZoneVO> parentZonePath) {
        // 站点节点
        Map<String, Object> siteNode = new HashMap<String, Object>();
        siteNode.put("name", ResourceBundleUtil.getBundle().getString("common.site"));
        siteNode.put("open", true);
        siteNode.put("url", "");
        siteNode.put("type", "site");

        List<Map<String, Object>> childrenNodes = new LinkedList<Map<String, Object>>();
        siteNode.put("children", childrenNodes);

        Iterator<ZoneVO> parentZonePathIt = parentZonePath.iterator();
        addZoneChildren(parentZonePathIt, childrenNodes, zoneService.findZoneList(siteId, null));

        return siteNode;
    }

    /**
     * 区域路径
     *
     * @param zonePath
     * @param zoneId
     * @return
     * @throws ExecutionException
     */
    private void parentPath(List<ZoneVO> zonePath, String zoneId) throws ExecutionException {
        // 从叶子逐层向上级遍历

        ZoneVO zone = appCacheHolder.loadZone(zoneId);
        zonePath.add(0, zone);

        List<ZoneVO> childrenOfZone = zoneService.findZones(zone.getZoneId());
        zone.setZones(childrenOfZone);

        if (!Strings.isNullOrEmpty(zone.getParentId())) {
            parentPath(zonePath, zone.getParentId());
        }
    }

    /**
     * @param zonePath
     * @param zone
     * @param selected
     * @return
     */
    private Map<String, Object> zoneNode(Iterator<ZoneVO> zonePath, ZoneVO zone, boolean selected) {
        Map<String, Object> zoneNode = BeanMaps.toMap(zone, new String[]{"zoneId", "zoneName"}, new String[]{"id", "name"});
        zoneNode.put("url", "zone/" + zone.getZoneId() + "/realtime-data");
        zoneNode.put("type", "zone");

        // 没选中的区域，不需要加载孩子(子区域和设备)
        if (selected) {
            zoneNode.put("open", true);
            List<Map<String, Object>> childrenNodes = new LinkedList<Map<String, Object>>();
            zoneNode.put("children", childrenNodes);

            String selectedLocationId = null;
            if (!zonePath.hasNext()) { // 区域路径结束
                if (!Strings.isNullOrEmpty(locationId)) {
                    selectedLocationId = locationId;
                }
            }

            addZoneChildren(zonePath, childrenNodes, zone.getZones());
            List<LocationVO> locations = locationService.findLocationsByZoneId(zone.getZoneId(), false);
            addLocations(childrenNodes, locations, selectedLocationId);
        }

        return zoneNode;
    }

    /**
     * 加载区域孩子
     *
     * @param zonePathIt
     * @param childrenNode
     * @param childrenZone
     */
    private void addZoneChildren(Iterator<ZoneVO> zonePathIt, List<Map<String, Object>> childrenNode, List<ZoneVO> childrenZone) {
        if (childrenZone == null) {
            return;
        }

        // 下一个节点，要在当前孩子中选中
        ZoneVO selectedZone = null;
        if (zonePathIt.hasNext()) {
            selectedZone = zonePathIt.next();
        }

        for (ZoneVO childZone : childrenZone) {
            if (selectedZone != null) {
                if (childZone.getZoneId().equals(selectedZone.getZoneId())) {
                    childrenNode.add(zoneNode(zonePathIt, selectedZone, true));
                    continue;
                }
            }
            childrenNode.add(zoneNode(zonePathIt, childZone, false));
        }
    }

    /**
     * 添加位置点
     *
     * @param childrenNode
     * @param locations
     * @param selectedLocationId
     */
    private void addLocations(List<Map<String, Object>> childrenNode, List<LocationVO> locations, String selectedLocationId) {
        if (locations == null) {
            return;
        }

        for (LocationVO location : locations) {
            if (!Strings.isNullOrEmpty(selectedLocationId)) {
                if (location.getId().equals(selectedLocationId)) {
                    childrenNode.add(locationNode(location, true));
                    continue;
                }
            }

            childrenNode.add(locationNode(location, false));
        }
    }

    /**
     * 返回设备节点
     *
     * @param location
     * @return
     */
    private Map<String, Object> locationNode(LocationVO location, boolean open) {
        Map<String, Object> locationNode = BeanMaps.toMap(location, new String[]{"locationName"}, new String[]{"name"});
        locationNode.put("sensorPhysicalIds", location.getSensorIdList());
        locationNode.put("open", open);
        locationNode.put("url", "location/" + location.getId());
        locationNode.put("type", "location");
        locationNode.put("id", location.getId());
        return locationNode;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }
}
