package com.microwise.blueplanet.action.location.manage;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.collect.Maps;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.service.ControlCenterCommunicationService;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.service.ZoneService;
import com.microwise.blueplanet.sys.AppCacheHolder;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author xiedeng
 * @date 14-7-4
 */
@Beans.Action
@Blueplanet
public class DeployLocationAction extends BlueplanetLoggerAction {

    public static final Logger logger = LoggerFactory.getLogger(DeployLocationAction.class);

    @Autowired
    private AppCacheHolder appCacheHolder;

    @Autowired
    private ControlCenterCommunicationService controlCenterCommunicationService;

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private LocationService locationService;

    /**
     * 位置点id
     */
    private String locationId;

    /**
     * 位置点名称
     */
    private String locationName;

    /**
     * 位置点集合
     */
    private String locationIds;

    /**
     * 绑定的区域编号
     */
    private String bindZoneId;

    /**
     * 区域编号
     */
    private String zoneId;

    /**
     * 区域名称
     */
    private String zoneName;

    /**
     * 分页
     */
    private String page;


    /**
     * 根据zoneId或者locationName查询
     *
     * @return
     */
    @Route(value = "/blueplanet/deployLocation")
    public String deployLocation() {
        try {
            if (StringUtils.isNotBlank(bindZoneId) && StringUtils.isNotBlank(locationIds)) {
                String[] ids = locationIds.split(",");
                locationService.deployLocation(ids, bindZoneId);
                appCacheHolder.evictZoneDeviceTree(Sessions.createByAction().currentSiteId());
                for (String locationId : ids) {
                    LocationVO location = locationService.findLocationById(locationId);
                    /*if (StringUtils.isNotBlank(location.getNodeId())) {
                        String siteId = Sessions.createByAction().currentSiteId();
                        controlCenterCommunicationService.uploadZone(siteId, bindZoneId, location, true);
                    }*/
                    appCacheHolder.putLocation(location);
                }
            }
            log("位置点管理", "部署位置点");
        } catch (Exception e) {
            logger.error("部署位置点错误", e);
        }
        return Results.redirect(getRedirectUrl());
    }

    @Route(value = "/blueplanet/unDeployLocation")
    public String unDeployLocation() {
        try {
            if (StringUtils.isNotBlank(locationId)) {
                locationService.unDeployLocation(locationId);
                appCacheHolder.evictZoneDeviceTree(Sessions.createByAction().currentSiteId());
                LocationVO location = locationService.findLocationById(locationId);
                /*if (StringUtils.isNotBlank(location.getNodeId())) {
                    String siteId = Sessions.createByAction().currentSiteId();
                    //todo bindZoneId 无用
                    controlCenterCommunicationService.uploadZone(siteId, bindZoneId, location, true);
                }*/
                appCacheHolder.putLocation(location);
            }
            log("位置点管理", "解除部署位置点");
        } catch (Exception e) {
            logger.error("解除部署位置点错误", e);
        }
        return Results.redirect(getRedirectUrl());
    }

    @Route(value = "/blueplanet/location/{locationId}/unDeploy")
    public String ajaxUnDeployLocation() {
        Map<String, Object> data = Maps.newHashMap();
        try {
            locationService.unDeployLocation(locationId);
            LocationVO location = locationService.findLocationById(locationId);
            appCacheHolder.putLocation(location);
            /*if (StringUtils.isNotBlank(location.getNodeId())) {
                String siteId = Sessions.createByAction().currentSiteId();
                controlCenterCommunicationService.uploadZone(siteId, null, location, false);
            }*/
            appCacheHolder.evictZoneDeviceTree(Sessions.createByAction().currentSiteId());
            data.put("success", true);
        } catch (Exception e) {
            data.put("success", false);
            logger.error("取消部署位置点", e);
        }
        ActionContext.getContext().put("data", data);
        return Results.json().root("data").done();
    }

    @Route(value = "/blueplanet/location/{locationId}/unShow")
    public String ajaxUnShowLocation() {
        Map<String, Object> data = Maps.newHashMap();
        try {
            zoneService.deletePlanImageCoordinate(zoneId, locationId);
            appCacheHolder.evictZoneDeviceTree(Sessions.createByAction().currentSiteId());
            data.put("success", true);
        } catch (Exception e) {
            data.put("success", false);
            logger.error("取消显示位置点", e);
        }
        ActionContext.getContext().put("data", data);
        return Results.json().root("data").done();
    }

    private String getRedirectUrl() {
        String path = "";
        try {
            zoneName = URLEncoder.encode(zoneName, "UTF-8");
            locationName = URLEncoder.encode(locationName, "UTF-8");
            path = "/blueplanet/queryLocationsBy?locationName=" + locationName + "&page=" + page
                    + "&zoneName=" + zoneName + "&zoneId=" + zoneId;
        } catch (UnsupportedEncodingException e) {
            logger.error("字符串转义异常", e);
        }
        return path;
    }

    public LocationService getLocationService() {
        return locationService;
    }

    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
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

    public String getLocationIds() {
        return locationIds;
    }

    public void setLocationIds(String locationIds) {
        this.locationIds = locationIds;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getBindZoneId() {
        return bindZoneId;
    }

    public void setBindZoneId(String bindZoneId) {
        this.bindZoneId = bindZoneId;
    }
}
