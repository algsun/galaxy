package com.microwise.blueplanet.action.zone;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.collect.Maps;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.PlanImageCoordinateVO;
import com.microwise.blueplanet.bean.vo.ZoneVO;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.service.ZoneService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.ActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 获取一个区域下的子区域和设备
 * </pre>
 *
 * @author Wang yunlong
 * @date 13-4-10 下午3:56
 */
@Beans.Action
@Blueplanet
public class GetZoneChildrenAction {
    @Autowired
    private ZoneService zoneService;

    @Autowired
    private LocationService locationService;
    //input
    /**
     * 区域id
     */
    private String zoneId;
    //output
    /**
     * 获取数据对象
     */
    private ZoneVO zone;

    @Route("/blueplanet/zone/{zoneId}/children.json")
    public String execute() {
        Map<String, Object> data = Maps.newHashMap();

        List<ZoneVO> zones = zoneService.findZones(zoneId);
        List<LocationVO> locations = locationService.findLocationsByZoneId(zoneId, true);

        // 处理区域数据
        for (int i = 0; i < zones.size(); i++) {
            ZoneVO zone = zones.get(i);
            // 查询子区域及位置点
            zones.addAll(zoneService.findZones(zone.getZoneId()));
            locations.addAll(locationService.findLocationsByZoneId(zone.getZoneId(), true));

            // 查询平面图显示坐标
            PlanImageCoordinateVO planImageCoordinate = zoneService.findPlanImageCoordinate(zoneId, zone.getZoneId());
            if (planImageCoordinate != null) {
                zone.setCoordinateX(planImageCoordinate.getCoordinateX());
                zone.setCoordinateY(planImageCoordinate.getCoordinateY());
            }
        }

        // 处理位置点在平面图上的显示坐标
        for (LocationVO location : locations) {
            PlanImageCoordinateVO planImageCoordinate = zoneService.findPlanImageCoordinate(zoneId, location.getId());
            if (planImageCoordinate != null) {
                location.setCoordinateX(planImageCoordinate.getCoordinateX());
                location.setCoordinateY(planImageCoordinate.getCoordinateY());
            }
            // TODO 是否去掉从模块
            // 设置是否当前区域设备
            if (location.getZoneId().equals(zoneId)) {
                location.setLocalLocation(true);
            }
        }
        data.put("zones", zones);
        data.put("locations", locations);
        ActionContext.getContext().put("data", data);
        return Results.json().root("data").done();
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public ZoneVO getZone() {
        return zone;
    }

    public void setZone(ZoneVO zone) {
        this.zone = zone;
    }
}
