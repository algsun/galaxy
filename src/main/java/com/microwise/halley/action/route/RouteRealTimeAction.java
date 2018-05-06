package com.microwise.halley.action.route;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.vo.DeviceVO;
import com.microwise.blueplanet.bean.vo.LocationDataVO;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.RealtimeDataVO;
import com.microwise.blueplanet.proxy.LocationProxy;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.halley.bean.po.PathPO;
import com.microwise.halley.bean.po.RouteHistoryPO;
import com.microwise.halley.bean.vo.CarVO;
import com.microwise.halley.bean.vo.ExhibitionVO;
import com.microwise.halley.service.*;
import com.microwise.halley.sys.Halley;
import com.microwise.halley.sys.HalleyLoggerAction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 实时地图路线运行状态与实时数据
 *
 * @author wang.geng
 * @date 13-10-21 下午2:55
 * @check @li.jianfei @xu.yuexi 2013-10-25 #6163
 * @check @li.jianfei @xu.yuexi 2013-11-11 #6510
 */
@Beans.Action
@Halley
public class RouteRealTimeAction {

    /**
     * 页面
     */
    public static final String _pagePath = "../route/route-realtime.ftl";

    @Autowired
    private HalleyLoggerAction halleyLogger;

    /**
     * 线路预设服务
     */
    @Autowired
    private PathService pathService;

    /**
     * GPS设备经纬度上传代理
     */
    @Autowired
    private LocationProxy locationProxy;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ExhibitionService exhibitionService;


    /**
     * 外展状态Service
     */
    @Autowired
    private ExhibitionStateService exhibitionStateService;

    /**
     * 车辆Service
     */
    @Autowired
    private CarService carService;

    // Input
    /**
     * 外展ID
     */
    private int exhibitionId;

    //Output
    /**
     * 线路预设点
     */
    private List<PathPO> pathPOList;

    /**
     * 实时数据
     */
    private List<RealtimeDataVO> realTimeDateList = new ArrayList<RealtimeDataVO>();


    private List<CarVO> carsWithDevices = new ArrayList<CarVO>();

    /**
     * 报警范围
     */
    private Integer alarmRange;

    /**
     * 线路类型
     */
    private int pathType;

    /**
     * 跳转至运行状态页面
     *
     * @return
     */
    @Route("/halley/routeRealtime/exhibition/{exhibitionId}")
    public String routePlaning() {
        try {
            this.getCarInfoData();
            List<RealtimeDataVO> list = new ArrayList<RealtimeDataVO>();

            List<LocationVO> locations = locationService.findLocationListByExhibitionId(exhibitionId);
            for (LocationVO locationVO : locations) {//realTimeDateList
                list.add(locationProxy.findLocationData(locationVO.getId()));
            }
            removeCarInfo(list);

            alarmRange = carService.findConfigExhibitionId(exhibitionId);
            realTimeDateList = filterRealtimeDatas(list, exhibitionId);
            List<PathPO> pathList = pathService.findPathByExhibitionId(exhibitionId);
            pathType = pathList.get(0).getPathType();
            halleyLogger.log("外展管理", "运行状态页面跳转");
        } catch (Exception e) {
            halleyLogger.logFailed("外展管理", "运行状态页面跳转");
            e.printStackTrace();
        }
        return Results.ftl("/halley/pages/index/layout.ftl");
    }

    /**
     * 获取预设路线
     *
     * @return
     */
    @Route("/halley/exhibition/{exhibitionId}/getPathPoint.json")
    public String getPathPoint() {
        try {
            pathPOList = pathService.findPathByExhibitionId(exhibitionId);
            halleyLogger.log("外展管理", "查询线路预设");
        } catch (Exception e) {
            halleyLogger.logFailed("外展管理", "查询线路预设");
            e.printStackTrace();
        }
        return Results.json().root("pathPOList").done();
    }

    /**
     * 获取历史路线点，在关闭浏览器后，再打开仍然可以重现走过的路线
     *
     * @return
     */
    @Route("/halley/exhibition/{exhibitionId}/getHistoryPathPoint.json")
    public String getHistoryPathPoint() {
        Map<String, List<Map<String, Double>>> historyPointMap = new HashMap<String, List<Map<String, Double>>>();
        try {
            Date startTime = exhibitionStateService.findExhibitionBeginTime(exhibitionId);

            //外展还没开始，返回空的结果
            if (startTime == null) {
                return Results.json().asRoot(historyPointMap).done();
            }

            Date nowDate = new Date();

            //如果当前时间在外展开始时间之前，直接返回空的结果
            if (nowDate.getTime() < startTime.getTime()) {
                return Results.json().asRoot(historyPointMap).done();
            }

            List<CarVO> carsWithDevices = carService.findCarsWithDeviceByExhibitionId(exhibitionId);
            filtercarsWithGPSDevices(carsWithDevices);

            for (CarVO car : carsWithDevices) {
                String plateNum = car.getPlateNumber();
                List<Map<String, Double>> latLongList = new ArrayList<Map<String, Double>>();
                List<LocationVO> locations = car.getLocationVOs();

                LocationVO gateWayLocation = null;
                for (LocationVO location : locations) {
                    DeviceVO device = location.getDevice();
                    if (device.getNodeType() == 7) {
                        gateWayLocation = location;
                    }
                }
                if (gateWayLocation != null) {
                    List<RouteHistoryPO> routeHistoryList = pathService.findRouteHistoryByCarId(car.getId(), startTime, nowDate);
                    //获取经纬度数据
                    for (RouteHistoryPO routeHistoryPO : routeHistoryList) {
                        Map<String, Double> latLong = new HashMap<String, Double>(2);
                        latLong.put(Constants.HalleyConstants.LONGITUDE, routeHistoryPO.getLongitude());
                        latLong.put(Constants.HalleyConstants.LATITUDE, routeHistoryPO.getLatitude());
                        latLongList.add(latLong);
                    }
                    if (latLongList.size() > 0) {
                        historyPointMap.put(plateNum, latLongList);
                    }
                }
            }
            halleyLogger.log("外展管理", "查询历史数据点坐标信息");
        } catch (Exception e) {
            halleyLogger.logFailed("外展管理", "查询历史数据点坐标信息");
            e.printStackTrace();
        }
        if (historyPointMap.size() == 0) {
            return Results.json().asRoot(false).done();
        } else {
            return Results.json().asRoot(historyPointMap).done();
        }
    }

    /**
     * 获取实时数据
     *
     * @return
     */
    @Route("/halley/exhibition/{exhibitionId}/getRealTimeDataUrl.json")
    public String getRealTimeData() {
        try {

            List<RealtimeDataVO> list = new ArrayList<RealtimeDataVO>();

            List<LocationVO> locations = locationService.findLocationListByExhibitionId(exhibitionId);
            for (LocationVO locationVO : locations) {//realTimeDateList
                list.add(locationProxy.findLocationData(locationVO.getId()));
            }
            removeCarInfo(list);
            realTimeDateList = filterRealtimeDatas(list, exhibitionId);
            halleyLogger.log("外展管理", "获取实时数据");
        } catch (Exception e) {
            halleyLogger.logFailed("外展管理", "获取实时数据");
            e.printStackTrace();
        }
        return Results.json().root("realTimeDateList").done();
    }

    /**
     * 过滤掉不在外展时间段内的数据
     *
     * @param realTimeDateList 实时数据
     * @return 过滤后的实时数据
     */
    private List<RealtimeDataVO> filterRealtimeDatas(List<RealtimeDataVO> realTimeDateList, int exhibitionId) {
        List<RealtimeDataVO> list = new ArrayList<RealtimeDataVO>();
        ExhibitionVO exhibition = exhibitionService.findExhibition(exhibitionId);
        Date startTime = exhibition.getBeginTime();
        Date endTime = exhibition.getEndTime();

        if (startTime == null) {
            return list;
        }

        for (RealtimeDataVO realtimeData : realTimeDateList) {
            Date stamp = realtimeData.getStamp();
            long stampMills = stamp.getTime();
            if (endTime == null) {
                if (stampMills > startTime.getTime()) {
                    list.add(realtimeData);
                }
            } else {
                if (stampMills > startTime.getTime() && stampMills < endTime.getTime()) {
                    list.add(realtimeData);
                }
            }
        }
        return list;
    }


    /**
     * 获取车辆信息
     *
     * @return
     */
    @Route("/halley/exhibition/{exhibitionId}/getCarInfoData.json")
    public String getCarInfoData() {
        try {
            carsWithDevices = carService.findCarsWithDeviceByExhibitionId(exhibitionId);
            for (CarVO car : carsWithDevices) {
                Map<String, String> sensorMap = carService.findCarSensor(car.getId());
                car.setSensorMap(sensorMap);
            }
            halleyLogger.log("外展配置", "获取车辆信息");
        } catch (Exception e) {
            halleyLogger.logFailed("外展配置", "获取车辆信息");
            e.printStackTrace();
        }
        return Results.json().asRoot(carsWithDevices).done();
    }

    /**
     * 去掉实时数据中的车辆信息，车辆信息单独显示
     *
     * @param realTimeDateList 实时数据
     */
    private void removeCarInfo(List<RealtimeDataVO> realTimeDateList) {
        //实时数据中不在显示车辆信息
        Iterator it = realTimeDateList.iterator();
        while (it.hasNext()) {
            RealtimeDataVO data = (RealtimeDataVO) it.next();
            Map<Integer, LocationDataVO> sensorinfoMap = data.getLocationSensorInfoMap();

            if (sensorinfoMap.containsKey(Constants.HalleyConstants.SENSORPHYSICALID_LONGITUDE) ||
                    sensorinfoMap.containsKey(Constants.HalleyConstants.SENSORPHYSICALID_LATITUDE) ||
                    sensorinfoMap.containsKey(Constants.HalleyConstants.SENSORPHYSICALID_SWH) ||
                    sensorinfoMap.containsKey(Constants.HalleyConstants.SENSORPHYSICALID_SHAKE) ||
                    sensorinfoMap.containsKey(Constants.HalleyConstants.SENSORPHYSICALID_ACCL)) {
                it.remove();
            }
        }
    }

    /**
     * 获取车辆运行路线只能获取携带GPS设备的车辆，过滤掉没有GPS设备的车辆
     * 因为GPS数据是从网关发上来的，所以只需要过滤掉没有网关设备的车辆就行了
     *
     * @param carsWithDevices 车辆设备信息集合
     */
    private void filtercarsWithGPSDevices(List<CarVO> carsWithDevices) {
        Iterator iterator = carsWithDevices.iterator();
        while (iterator.hasNext()) {
            CarVO car = (CarVO) iterator.next();
            List<LocationVO> locations = car.getLocationVOs();
            boolean hasGateWayLocation = hasGatewayLocation(locations);
            if (!hasGateWayLocation) {
                iterator.remove();
            }
        }
    }

    /**
     * 判断设备集合中有没有网关
     *
     * @param locations 位置点集合
     * @return true 有网关，false 没有网关
     */
    private boolean hasGatewayLocation(List<LocationVO> locations) {
        boolean flag = false;
        for (LocationVO location : locations) {
            DeviceVO device = location.getDevice();
            int deviceType = device.getNodeType();
            if (deviceType == 7) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public int getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(int exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    public List<PathPO> getPathPOList() {
        return pathPOList;
    }

    public void setPathPOList(List<PathPO> pathPOList) {
        this.pathPOList = pathPOList;
    }

    public List<RealtimeDataVO> getRealTimeDateList() {
        return realTimeDateList;
    }

    public void setRealTimeDateList(List<RealtimeDataVO> realTimeDateList) {
        this.realTimeDateList = realTimeDateList;
    }

    public List<CarVO> getCarsWithDevices() {
        return carsWithDevices;
    }

    public void setCarsWithDevices(List<CarVO> carsWithDevices) {
        this.carsWithDevices = carsWithDevices;
    }

    public Integer getAlarmRange() {
        return alarmRange;
    }

    public void setAlarmRange(Integer alarmRange) {
        this.alarmRange = alarmRange;
    }

    public int getPathType() {
        return pathType;
    }

    public void setPathType(int pathType) {
        this.pathType = pathType;
    }
}
