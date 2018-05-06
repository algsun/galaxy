package com.microwise.blueplanet.action.location.manage;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.MethodType;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.microwise.blackhole.bean.Subsystem;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.DeviceVO;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.service.ControlCenterCommunicationService;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.sys.AppCacheHolder;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.proxy.RelicProxy;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiedeng
 * @date 14-6-24
 */

@Beans.Action
@Blueplanet
public class AddLocationAction extends BlueplanetLoggerAction {

    public static final Logger logger = LoggerFactory.getLogger(AddLocationAction.class);

    public static String _pagePath = "/blueplanet/pages/location/manage/add-location.ftl";

    @Autowired
    private LocationService locationService;

    @Autowired
    private AppCacheHolder appCacheHolder;

    @Autowired
    private ControlCenterCommunicationService controlCenterCommunicationService;

    @Autowired
    private RelicProxy relicProxy;

    /**
     * 未绑定设备列表
     */
    private List<DeviceVO> unbindDeviceList;

    /**
     * 设备的类型集合
     */
    private List<Integer> deviceTypes;

    /**
     * 设备编号
     */
    private String nodeId;

    /**
     * 区域编号
     */
    private String zoneId;

    /**
     * 位置点名称
     */
    private String locationName;

    /**
     * 备注
     */
    private String remark;
    /**
     * 经度
     */
    private Double lng;
    /**
     * 维度
     */
    private Double lat;

    /**
     * 位置点实景图
     */
    private String photo;

    /**
     * 检索文物名称
     */
    private String relicNameTotalCode;

    /**
     * select2 检索分页
     */
    private int pageIndex;

    /**
     * select2 检索分页
     */
    private int pageSize;

    /**
     * select2选择的relicIds
     */
    private String relic;

    /**
     * 是否开通了资产管理
     */
    private boolean orion;

    @Route(value = "/blueplanet/addLocation")
    public String addLocation() {
        if (StringUtils.isBlank(locationName)) {
            return Results.redirect("queryLocations");
        }
        String siteId = Sessions.createByAction().currentSiteId();
        LocationVO location = new LocationVO();
        location.setId(locationService.getNewLocationId(siteId));
        location.setSiteId(siteId);
        location.setLocationName(locationName);
        location.setPhoto(photo);
        if (StringUtils.isNotBlank(nodeId)) {
            location.setNodeId(nodeId);
        }
        location.setRemark(remark);
        location.setLng(lng);
        location.setLat(lat);
        if (!locationService.isExistLocationName(locationName, siteId)) {
            try {
                if (!Strings.isNullOrEmpty(relic)) {
                    relic = relic.replace(" ","");
                    String[] relicIds = relic.split(",");
                    for (String relicId : relicIds) {
                        locationService.addLocationRelic(Integer.parseInt(relicId.trim()), location.getId());
                    }
                    //执行检测指标融合
                    locationService.addTextureThreshold(location.getId(),relicIds);
                }
                locationService.addLocation(location);
                //上传位置信息
               /* if (StringUtils.isNotBlank(location.getNodeId())) {
                    controlCenterCommunicationService.uploadZone(siteId, null, location, true);
                }*/
                appCacheHolder.evictZoneDeviceTree(siteId);
                appCacheHolder.putLocation(location);
                locationService.notifyLocationChanged(location.getNodeId());
            } catch (Exception e) {
                logger.error("添加位置点信息失败", e);
            }
        }
        return Results.redirect("queryLocations");
    }

    @Route(value = "/blueplanet/isExistLocationName")
    public String isExistLocationName() {
        String siteId = Sessions.createByAction().currentSiteId();
        boolean success = locationService.isExistLocationName(locationName, siteId);
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("success", success);
        return Results.json().asRoot(msg).done();
    }

    @Route(value = "/blueplanet/toAddLocation")
    public String toAddLocation() {
        photo = "../blueplanet" + File.separator + "images" + File.separator + "no-relic.jpg";
        Map<String, Object> session = ActionContext.getContext().getSession();
        List<Subsystem> subsystems = (List<Subsystem>) session.get("subsystemList");
        for (Subsystem subsystem : subsystems) {
            if (subsystem.getSubsystemCode().equals("orion")) {
                orion = subsystem.isEnable();
                break;
            }
        }
        unbindDeviceList = locationService.findUnbindDevices(Sessions.createByAction().currentSiteId());
        deviceTypes = locationService.getDeviceTypes(unbindDeviceList);
        return Results.ftl("/blueplanet/pages/device/manage/device-manage-layout");
    }

    @Route("/blueplanet/getRelics.json")
    public String getRelicList() {
        Map<String, Object> json = new HashMap<String, Object>();
        try {
            relicNameTotalCode = new String(relicNameTotalCode.getBytes("ISO-8859-1"), "UTF-8");
            json.put("total_count", relicProxy.findRelicsCount(relicNameTotalCode, null));
            json.put("relics", relicProxy.findRelics(relicNameTotalCode, pageIndex, pageSize, null));
        } catch (UnsupportedEncodingException e) {
            logger.error("获取文物失败", e);
        }
        return Results.json().includeProperties("total_count,relics\\[\\d+\\]\\.id,relics\\[\\d+\\]\\.name,relics\\[\\d+\\]\\.totalCode,relics\\[\\d+\\]\\.photos.*,relics\\[\\d+\\]\\.siteId,relics\\[\\d+\\]\\.era.*").asRoot(json).done();
    }

    @Route(value = "/blueplanet/locations", params = {"zoneId", "nodeId", "locationName"}, method = MethodType.POST)
    public String ajaxAddLocation() {
        Map<String, Object> data = Maps.newHashMap();
        try {
            // TODO false 时页面提示
            String siteId = Sessions.createByAction().currentSiteId();
            if (!locationService.isExistLocationName(locationName, siteId)) {
                LocationVO location = new LocationVO();
                location.setId(locationService.getNewLocationId(siteId));
                location.setSiteId(siteId);
                location.setZoneId(zoneId);
                location.setNodeId(nodeId);
                location.setLocationName(locationName);
                locationService.addLocation(location);

                location = locationService.findLocationById(location.getId());
                appCacheHolder.putLocation(location);
                appCacheHolder.evictZoneDeviceTree(siteId);
                data.put("location", location);
                locationService.notifyLocationChanged(nodeId);
                data.put("success", true);
            } else {
                data.put("success", false);
            }
        } catch (Exception e) {
            data.put("success", false);
            logger.error("添加位置点", e);
        }
        return Results.json().asRoot(data).done();
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public List<DeviceVO> getUnbindDeviceList() {
        return unbindDeviceList;
    }

    public void setUnbindDeviceList(List<DeviceVO> unbindDeviceList) {
        this.unbindDeviceList = unbindDeviceList;
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public List<Integer> getDeviceTypes() {
        return deviceTypes;
    }

    public void setDeviceTypes(List<Integer> deviceTypes) {
        this.deviceTypes = deviceTypes;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRelicNameTotalCode() {
        return relicNameTotalCode;
    }

    public void setRelicNameTotalCode(String relicNameTotalCode) {
        this.relicNameTotalCode = relicNameTotalCode;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getRelic() {
        return relic;
    }

    public void setRelic(String relic) {
        this.relic = relic;
    }

    public boolean isOrion() {
        return orion;
    }

    public void setOrion(boolean orion) {
        this.orion = orion;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}
