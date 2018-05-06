package com.microwise.blueplanet.action.offline;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.microwise.blueplanet.bean.po.LocationSensorPO;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.RecentDataVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.service.OfflineDataService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 离线位置点数据 action
 *
 * @author liuzhu
 * @date 2016-4-20
 */
@Beans.Action
@Blueplanet
public class OfflineDataAction extends BlueplanetLoggerAction {
    private static final Logger logger = LoggerFactory.getLogger(OfflineDataAction.class);

    private static final String _pagePath = "offline-data-view.ftl";

    @Autowired
    private OfflineDataService offlineDataService;

    @Autowired
    private LocationService locationService;

    /**
     * 位置点id
     */
    private String locationId;

    /**
     * 数据时间戳
     */
    private Date date;

    //成功标识
    private boolean success;

    /**
     * 离线数据当前批次监测指标   表头
     */
    private List<SensorinfoVO> sensorinfos;

    /**
     * 位置点数据json字符串
     */
    private String locationSensorJson;

    /**
     * 位置点
     */
    private LocationVO location;

    /**
     * 离线数据  批次数据展现
     *
     * @return
     */
    @Route("/blueplanet/offline/batchDataView.action")
    public String view() {
        location = locationService.findLocationById(locationId);
        return Results.ftl("/blueplanet/pages/offline/layout.ftl");
    }

    @Route("/blueplanet/offline/getSensors.json")
    public String getSensors() {
        LocationVO location = locationService.findLocationById(locationId);
        sensorinfos = location.getSensorInfoList();
        return Results.json().asRoot(sensorinfos).done();
    }

    @Route("/blueplanet/offline/insert.json")
    public String insert() {
        try {
            Gson gson = new Gson();
            Type recentType = new TypeToken<List<LocationSensorPO>>() {
            }.getType();
            List<LocationSensorPO> locationSensorPOs = gson.fromJson(locationSensorJson, recentType);
            offlineDataService.insert(locationSensorPOs);
            return Results.json().asRoot(true).done();
        } catch (Exception e) {
            logger.error("添加离线数据", e);
            return Results.json().asRoot(true).done();
        }
    }

    /**
     * 离线数据  批次数据展现
     *
     * @return
     */
    @Route("/blueplanet/offline/getOfflineData.json")
    public String getOfflineData() {
        List<RecentDataVO> recentDataVOs = new ArrayList<RecentDataVO>();
        try {
            recentDataVOs = offlineDataService.findOfflineHistory(locationId);
        } catch (Exception e) {
            logger.error("获得离线数据 出错", e);
        }
        return Results.json().asRoot(recentDataVOs).done();
    }


    /**
     * 删除离线位置点数据
     *
     * @return
     */
    @Route("/blueplanet/offline/deleteOfflineData.json")
    public String execute() {
        try {
            offlineDataService.deleteOfflineData(locationId, date);
            success = true;
        } catch (Exception e) {
            success = false;
            logger.error("删除离线数据出错", e);
        }
        return Results.json().asRoot(success).done();
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public String getLocationSensorJson() {
        return locationSensorJson;
    }

    public void setLocationSensorJson(String locationSensorJson) {
        this.locationSensorJson = locationSensorJson;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public LocationVO getLocation() {
        return location;
    }

    public void setLocation(LocationVO location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
