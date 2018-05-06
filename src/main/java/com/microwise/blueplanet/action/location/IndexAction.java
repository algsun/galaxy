package com.microwise.blueplanet.action.location;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.ControllerPackage;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.po.ReplaceSensorPO;
import com.microwise.blueplanet.bean.vo.*;
import com.microwise.blueplanet.service.*;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.*;

/**
 * 位置点概览
 *
 * @author liuzhu
 * @date 2014-6-26
 */
@Beans.Action("locationIndexAction")
@Blueplanet
@ControllerPackage(parent = "blueplanet-location")
public class IndexAction extends BlueplanetLoggerAction {

    public static final Logger log = LoggerFactory.getLogger(IndexAction.class);

    // 最近数据包数目
    public static final int RECENT_PACKAGE_COUNT = 12;

    // 内容页面
    private static final String _pagePath = "index.ftl";

    @Autowired
    private LocationService locationService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private QCMService qcmService;

    @Autowired
    FollowerService followerService;

    // input
    /**
     * 位置点id
     */
    private String locationId;

    // output
    /**
     * 设备
     */
    private DeviceVO device;

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 酸雨设备状态
     */
    private RainDeviceStateVO rainDeviceState;

    /**
     * 近期数据
     */
    private List<RecentDataVO> recentDataList;

    /**
     * 位置点绑定历史
     */
    private List<LocationHistoryVO> locationHistoryVOList;

    /**
     * 位置点
     */
    private LocationVO location;

    /**
     * qcm设备
     */
    private boolean qcmDevice;

    /**
     * qcm评估等级
     */
    private ReplaceSensorPO replaceSensor;

    /*
     * 位置点实景图目录
     */
    private final String locationPhotoPath;

    /**
     * 文物图片服务器目录
     */
    private final String relicPhotoBasePath;

    /**
     * 区域阈值报警关注
     * 0:未关注 1：已关注
     */
    private int follower;

    {
        locationPhotoPath = Sessions.createByAction().getGalaxyResourceURL() + File.separator +
                "blueplanet" + File.separator + "images" + File.separator + "location" + File.separator;
        //初始化 图片路径
        relicPhotoBasePath = Sessions.createByAction().getGalaxyResourceURL() + File.separator +
                "orion" + File.separator + "images";
    }


    /**
     * 位置点概览
     */
    @Route(value = "/blueplanet/location/{locationId}", interceptors = "locationStack")
    public String show() {
        try {
            location = locationService.findLocationWithRelics(locationId);
            device = location.getDevice();
            if (device != null) {
                rainDeviceState = deviceService.findRainDeviceState(device.getNodeId(), device.getStamp());
            }
            recentDataList = locationService.findRecentDataList(locationId, RECENT_PACKAGE_COUNT, null);
            locationHistoryVOList = locationService.findLocationHistoryList(locationId);

            // QCM 设备 业务处理
            boolean qcmDevice = false;
            for (SensorinfoVO sensorInfo : location.getSensorInfoList()) {
                if (sensorInfo.getSensorPhysicalid() == 3072) {
                    qcmDevice = true;
                    break;
                }
            }
            if (qcmDevice) {
                List<Date> dates = qcmService.findQCMMinMaxDate(locationId);
                Map<String, List<ReplaceSensorPO>> stringListMap = qcmService.assembleQCMLevel(locationId, dates.get(0), dates.get(1));
                String newKey = "";
                for (String key : stringListMap.keySet()) {
                    newKey = key;
                    break;
                }
                List<ReplaceSensorPO> replaceSensors = stringListMap.get(newKey);
                if (replaceSensors.size() != 0) {
                    replaceSensor = replaceSensors.get(0);
                }
            }
            int userId = Sessions.createByAction().currentUser().getId();
            follower = followerService.findFollower(userId,locationId);
        } catch (Exception e) {
            log.error("位置点概览", e);
        }
        log("位置点", "位置点概览");
        return Results.ftl("/blueplanet/pages/location/layout-b4");
    }

    public String get_pagePath() {
        return _pagePath;
    }

    public LocationVO getLocation() {
        return location;
    }

    public void setLocation(LocationVO location) {
        this.location = location;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public DeviceVO getDevice() {
        return device;
    }

    public void setDevice(DeviceVO device) {
        this.device = device;
    }

    public List<RecentDataVO> getRecentDataList() {
        return recentDataList;
    }

    public void setRecentDataList(List<RecentDataVO> recentDataList) {
        this.recentDataList = recentDataList;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public List<LocationHistoryVO> getLocationHistoryVOList() {
        return locationHistoryVOList;
    }

    public void setLocationHistoryVOList(List<LocationHistoryVO> locationHistoryVOList) {
        this.locationHistoryVOList = locationHistoryVOList;
    }

    public RainDeviceStateVO getRainDeviceState() {
        return rainDeviceState;
    }

    public void setRainDeviceState(RainDeviceStateVO rainDeviceState) {
        this.rainDeviceState = rainDeviceState;
    }

    public boolean isQcmDevice() {
        return qcmDevice;
    }

    public void setQcmDevice(boolean qcmDevice) {
        this.qcmDevice = qcmDevice;
    }

    public ReplaceSensorPO getReplaceSensor() {
        return replaceSensor;
    }

    public void setReplaceSensor(ReplaceSensorPO replaceSensor) {
        this.replaceSensor = replaceSensor;
    }

    public String getRelicPhotoBasePath() {
        return relicPhotoBasePath;
    }

    public String getLocationPhotoPath() {
        return locationPhotoPath;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }
}
