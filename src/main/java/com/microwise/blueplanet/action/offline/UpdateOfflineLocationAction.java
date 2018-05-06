package com.microwise.blueplanet.action.offline;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Strings;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.service.OfflineService;
import com.microwise.blueplanet.service.SensorinfoService;
import com.microwise.blueplanet.sys.AppCacheHolder;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.ResourceBundleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 离线数据额
 *
 * @author chenyaofei
 * @date 16-4-20
 */
@Beans.Action
@Blueplanet
public class UpdateOfflineLocationAction extends BlueplanetLoggerAction {
    private static final Logger logger = LoggerFactory.getLogger(UpdateOfflineLocationAction.class);

    private static final String _pagePath = "offlineUpdateLocation.ftl";


    @Autowired
    private OfflineService offlineService;

    @Autowired
    private LocationService locationService;


    @Autowired
    private SensorinfoService sensorinfoService;

    @Autowired
    private AppCacheHolder appCacheHolder;


    /**
     * 被选择的监测指标
     */
    private List<Integer> checkedSensorInfoList;

    /**
     * 位置点信息
     */
    LocationVO location;

    /**
     * 位置点编号
     */
    String locationId;
    /**
     * 常用检测指标
     */
    private List<SensorinfoVO> offlineSensorinfoList = new ArrayList<SensorinfoVO>();
    /**
     * 全部监测指标
     */
    private List<SensorinfoVO> sensorInfos;

    /**
     * 是否可以修改监测指标
     */
    private boolean changeSensor;
    /**
     * 位置点名称
     */
    private String locationName;
    /**
     * 区域编号
     */
    private String zoneId;

    @Route("/blueplanet/offline/updateLocation/{locationId}")
    public String view() {
        try {
            //监测指标
            String siteId = Sessions.createByAction().currentSiteId();
            sensorInfos = sensorinfoService.findSensorinfo();
            Iterator<SensorinfoVO> ite = sensorInfos.iterator();
            while(ite.hasNext()){
                SensorinfoVO sensor = ite.next();
                for(int index : Common.OFTEN_SENSORS){
                    if(sensor.getSensorPhysicalid() == index){
                        ite.remove();
                        offlineSensorinfoList.add(sensor);
                        break;
                    }
                }
            }
            //修改内容
            changeSensor = offlineService.findLocationDataById(locationId);
            location = locationService.findLocationById(locationId);
            log("离线数据", "更新离线位置点");
        } catch (Exception e) {
            logger.error("离线数据--离线位置点更新显示出错", e);
        }
        return Results.ftl("/blueplanet/pages/offline/layout.ftl");
    }

    @Route("/blueplanet/offline/updateLocation/update")
    public String update() {
        try {
            LocationVO location = locationService.findLocationById(locationId);
            location.setLocationName(locationName);
            if (!Strings.isNullOrEmpty(zoneId)) {
                location.setZoneId(zoneId);
            }else{
                location.setZoneId(null);
            }
            offlineService.updateLocation(location);
            //更新监测指标
            if (checkedSensorInfoList != null) {
                offlineService.updateSensorById(locationId, checkedSensorInfoList);
            }
            String siteId = Sessions.createByAction().currentSiteId();
            appCacheHolder.evictZoneDeviceTree(siteId);
            ActionMessage.createByAction().success(ResourceBundleUtil.getBundle().getString("blueplanet.offline.updateSuccess"));
            log("离线数据", "更新离线位置点");
        } catch (Exception e) {
            ActionMessage.createByAction().fail(ResourceBundleUtil.getBundle().getString("blueplanet.offline.updateFail"));
            logger.error("离线数据--离线位置点更新出错", e);
        }
        return Results.redirect("/blueplanet/offline/offline.action");
    }


    public static String get_pagePath() {
        return _pagePath;
    }

    public List<Integer> getCheckedSensorInfoList() {
        return checkedSensorInfoList;
    }

    public void setCheckedSensorInfoList(List<Integer> checkedSensorInfoList) {
        this.checkedSensorInfoList = checkedSensorInfoList;
    }

    public LocationVO getLocation() {
        return location;
    }

    public void setLocation(LocationVO location) {
        this.location = location;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public List<SensorinfoVO> getOfflineSensorinfoList() {
        return offlineSensorinfoList;
    }

    public void setOfflineSensorinfoList(List<SensorinfoVO> offlineSensorinfoList) {
        this.offlineSensorinfoList = offlineSensorinfoList;
    }

    public List<SensorinfoVO> getSensorInfos() {
        return sensorInfos;
    }

    public void setSensorInfos(List<SensorinfoVO> sensorInfos) {
        this.sensorInfos = sensorInfos;
    }

    public boolean isChangeSensor() {
        return changeSensor;
    }

    public void setChangeSensor(boolean changeSensor) {
        this.changeSensor = changeSensor;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }
}
