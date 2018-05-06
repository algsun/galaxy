package com.microwise.blueplanet.action.offline;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
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
import org.elasticsearch.common.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * 离线数据
 *
 * @author chen.yaofei
 * @date 16-4-20
 */
@Beans.Action
@Blueplanet
public class AddOfflineLocationAction extends BlueplanetLoggerAction {
    private static final Logger logger = LoggerFactory.getLogger(AddOfflineLocationAction.class);

    private static final String _pagePath = "offlineAddLocation.ftl";

    @Autowired
    private OfflineService offlineService;

    @Autowired
    private LocationService locationService;

    @Autowired
    AppCacheHolder appCacheHolder;

    @Autowired
    SensorinfoService sensorinfoService;

    //input
    /**
     * 位置点名称名称
     */
    private String locationName;

    /**
     * 选中的监测指标
     */
    private List<Integer> checkedSensorInfoList;

    //output
    /**
     * 默认指标列表
     */
    private List<SensorinfoVO> offlineSensorinfoList = new ArrayList<SensorinfoVO>();
    /**
     * 区域编号
     */
    private String zoneId;
    /**
     * 全部监测指标
     */
    private List<SensorinfoVO> sensorInfos;

    @Route("/blueplanet/offline/addLocation")
    public String view() {
        try {
            //新增批次页面的监测数据列表
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
            log("离线数据", "新增离线位置点");
        } catch (Exception e) {
            logger.error("离线数据--离线位置点新增显示出错", e);
        }
        return Results.ftl("/blueplanet/pages/offline/layout.ftl");
    }

    @Route("/blueplanet/offline/addLocation/add")
    public String addLocation() {
        String siteId = Sessions.createByAction().currentSiteId();
        if(!locationService.isExistLocationName(locationName, siteId)){
            try {
                LocationVO location = new LocationVO();
                location.setId(locationService.getNewLocationId(siteId));
                location.setSiteId(siteId);
                location.setLocationName(locationName);
                if (Strings.isNullOrEmpty(zoneId)) {
                    location.setZoneId(null);
                } else {
                    location.setZoneId(zoneId);
                }

                location.setType(Common.LOCATION_TYPE);
                locationService.addLocation(location);
                appCacheHolder.evictZoneDeviceTree(siteId);
                //添加监测指标
                offlineService.addLocationSensor(location.getId(), checkedSensorInfoList);
                ActionMessage.createByAction().success(ResourceBundleUtil.getBundle().getString("blueplanet.offline.insertSuccess"));
                log("离线数据", "新增离线位置点");
            } catch (Exception e) {
                ActionMessage.createByAction().fail(ResourceBundleUtil.getBundle().getString("blueplanet.offline.insertFail"));
                logger.error("离线数据--离线位置点新增出错", e);
            }
        }
        return Results.redirect("/blueplanet/offline/offline.action");
    }


    @Route(value = "/blueplanet/offline/addLocation/validateLocationName", params = {"locationName"})
    public String validateLocationName() {
        boolean flag = true;
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            flag = locationService.isExistLocationName(locationName, siteId);
        } catch (Exception e) {
            logger.error("离线数据--离线位置点名称验证出错", e);
        }

        return Results.json().asRoot(flag).done();
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public List<SensorinfoVO> getOfflineSensorinfoList() {
        return offlineSensorinfoList;
    }

    public void setOfflineSensorinfoList(List<SensorinfoVO> offlineSensorinfoList) {
        this.offlineSensorinfoList = offlineSensorinfoList;
    }

    public List<Integer> getCheckedSensorInfoList() {
        return checkedSensorInfoList;
    }

    public void setCheckedSensorInfoList(List<Integer> checkedSensorInfoList) {
        this.checkedSensorInfoList = checkedSensorInfoList;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public List<SensorinfoVO> getSensorInfos() {
        return sensorInfos;
    }

    public void setSensorInfos(List<SensorinfoVO> sensorInfos) {
        this.sensorInfos = sensorInfos;
    }
}
