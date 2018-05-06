package com.microwise.blueplanet.action.location.manage;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Strings;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.PlanImageCoordinateVO;
import com.microwise.blueplanet.service.ControlCenterCommunicationService;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.service.ZoneService;
import com.microwise.blueplanet.sys.AppCacheHolder;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 更新位置点坐标 EventAction
 *
 * @author li.jianfei
 * @date 2014-07-03
 */
@Beans.Action
@Blueplanet
public class UpdateCoordinateAction {


    public static final Logger logger = LoggerFactory.getLogger(UpdateCoordinateAction.class);

    @Autowired
    private ZoneService zoneService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private AppCacheHolder appCacheHolder;


    @Autowired
    private ControlCenterCommunicationService controlCenterCommunicationService;

    /**
     * 区域ID
     */
    private String zoneId;

    /**
     * 位置点ID
     */
    private String locationId;

    /**
     * 位置点 X 坐标
     */
    private float x;

    /**
     * 位置点 Y 坐标
     */
    private float y;

    @Route(value = "/blueplanet/location/{locationId}/updateCoordinate.json")
    public String updateLocationCoordinate() {
        Map<String, Object> data = new HashMap<String, Object>();
        try {
            String siteId = Sessions.createByAction().currentSiteId();

            LocationVO location = locationService.findLocationById(locationId);
            if (zoneId.equals(location.getZoneId()) || Strings.isNullOrEmpty(location.getZoneId())) {
                location.setZoneId(zoneId);
                locationService.updateLocation(location);
            }

            PlanImageCoordinateVO planImageCoordinate = new PlanImageCoordinateVO();
            planImageCoordinate.setZoneId(zoneId);
            planImageCoordinate.setObjectId(locationId);
            planImageCoordinate.setCoordinateX(x);
            planImageCoordinate.setCoordinateY(y);
            planImageCoordinate.setType(PlanImageCoordinateVO.LOCATION_TYPE);

            zoneService.updatePlanImageCoordinate(planImageCoordinate);
            appCacheHolder.evictZoneDeviceTree(siteId);

            /*if (StringUtils.isNotBlank(location.getNodeId())) {
                controlCenterCommunicationService.uploadZone(siteId, zoneId, location, true);
            }*/

            data.put("success", true);
        } catch (Exception e) {
            data.put("success", false);
            logger.error("更新位置点坐标", e);
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

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
