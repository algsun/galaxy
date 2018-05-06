package com.microwise.halley.action.route;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.vo.LocationDataVO;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.RealtimeDataVO;
import com.microwise.blueplanet.proxy.LocationProxy;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.halley.bean.po.PathPO;
import com.microwise.halley.bean.vo.CarVO;
import com.microwise.halley.service.CarService;
import com.microwise.halley.service.LocationService;
import com.microwise.halley.service.PathService;
import com.microwise.halley.sys.Halley;
import com.microwise.halley.sys.HalleyLoggerAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 历史传感器数据和路线数据
 *
 * @author xu.yuexi
 * @date 13-11-4
 * @check @li.jianfei 2014-5-22 #8588
 */
@Beans.Action
@Halley
public class RouteHistoryAction {

    public static final Logger log = LoggerFactory.getLogger(RouteHistoryAction.class);

    /**
     * 页面
     */
    public static final String _pagePath = "../route/route-history.ftl";
    /**
     * 设备检测指标ID:经度
     */
    public static final int SENSORPHYSICALID_LONGITUDE = 12287;
    /**
     * 设备检测指标ID:纬度
     */
    public static final int SENSORPHYSICALID_LATITUDE = 12286;

    /**
     * 设备检测指标ID:开关量
     */
    public static final int SENSORPHYSICALID_SWH = 89;

    /**
     * 设备检测指标ID:震动
     */
    public static final int SENSORPHYSICALID_SHAKE = 88;

    /**
     * 设备检测指标ID:加速度
     */
    public static final int SENSORPHYSICALID_ACCL = 87;

    /**
     * 设备检测指标常量:经度
     */
    public static final String LONGITUDE = "longitude";

    /**
     * 设备检测指标常量:纬度
     */
    public static final String LATITUDE = "latitude";

    /**
     * 设备检测指标常量:开关量
     */
    public static final String SWH = "swh";

    /**
     * 设备检测指标常量:震动
     */
    public static final String SHAKE = "shake";

    /**
     * 设备检测指标常量:加速度
     */
    public static final String ACCL = "accl";

    @Autowired
    private HalleyLoggerAction halleyLogger;

    /**
     * 位置点代理
     */
    @Autowired
    private LocationProxy locationProxy;

    /**
     * 位置点
     */
    @Autowired
    private LocationService locationService;

    /**
     * 线路预设服务
     */
    @Autowired
    private PathService pathService;

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
     * 历史数据点Map
     */
    private Map<String, List<Map<String, String>>> historyPointMap;

    /**
     * 车辆信息集合
     */
    private Map<String, List<Map<String, LocationDataVO>>> carInfoDataMap;

    /**
     * 实时数据
     */
    private List<RealtimeDataVO> realTimeDateList = new ArrayList<RealtimeDataVO>();

    /**
     * 报警范围
     */
    private Integer alarmRange;

    /**
     * 线路类型
     */
    private int pathType;

    @Route("/halley/routeHistory/exhibition/{exhibitionId}/timeIntervalIndex/{timeIntervalIndex}")
    public String defaultHistoryData() {
        try {
            //获取外展传感位置點的ID集合
            List<LocationVO> locations = locationService.findLocationListByExhibitionId(exhibitionId);
            for (LocationVO location : locations) {
                realTimeDateList.add(locationProxy.findLocationData(location.getId()));
            }
            this.getCarInfoData();
            removeCarInfo(realTimeDateList);
            alarmRange = carService.findConfigExhibitionId(exhibitionId);
            List<PathPO> pathList = pathService.findPathByExhibitionId(exhibitionId);
            pathType = pathList.get(0).getPathType();
            halleyLogger.log("查询历史数据", "查询历史数据");
        } catch (Exception e) {
            log.error("查询历史数据", e);
            halleyLogger.logFailed("查询历史数据", "查询历史数据");
        }
        return Results.ftl("/halley/pages/index/layout.ftl");
    }

    /**
     * 获取车辆信息
     *
     * @return
     */
    private String getCarInfoData() {
        try {
            carInfoDataMap = new HashMap<String, List<Map<String, LocationDataVO>>>();
            List<CarVO> carsWithDevices = carService.findCarsWithDeviceByExhibitionId(exhibitionId);
            for (CarVO car : carsWithDevices) {
                String plateNum = car.getPlateNumber();
                List<LocationVO> locations = car.getLocationVOs();
                List<RealtimeDataVO> realTimeDates = new ArrayList<RealtimeDataVO>();
                for (LocationVO locationVO : locations) {
                    realTimeDates.add(locationProxy.findLocationData(locationVO.getId()));
                }
                List<Map<String, LocationDataVO>> sensorInfoList = new ArrayList<Map<String, LocationDataVO>>();
                for (RealtimeDataVO realtimeData : realTimeDates) {
                    Map<String, LocationDataVO> sensorMap = new HashMap<String, LocationDataVO>();
                    Map<Integer, LocationDataVO> sensorInfoMap = realtimeData.getLocationSensorInfoMap();
                    if (sensorInfoMap.containsKey(SENSORPHYSICALID_LONGITUDE)) {
                        sensorMap.put(LONGITUDE, sensorInfoMap.get(SENSORPHYSICALID_LONGITUDE));
                    }

                    if (sensorInfoMap.containsKey(SENSORPHYSICALID_LATITUDE)) {
                        sensorMap.put(LATITUDE, sensorInfoMap.get(SENSORPHYSICALID_LATITUDE));
                    }

                    if (sensorInfoMap.containsKey(SENSORPHYSICALID_SWH)) {
                        sensorMap.put(SWH, sensorInfoMap.get(SENSORPHYSICALID_SWH));
                    }

                    if (sensorInfoMap.containsKey(SENSORPHYSICALID_SHAKE)) {
                        sensorMap.put(SHAKE, sensorInfoMap.get(SENSORPHYSICALID_SHAKE));
                    }

                    if (sensorInfoMap.containsKey(SENSORPHYSICALID_ACCL)) {
                        sensorMap.put(ACCL, sensorInfoMap.get(SENSORPHYSICALID_ACCL));
                    }

                    if (!sensorMap.isEmpty()) {
                        sensorInfoList.add(sensorMap);
                    }
                }
                //将检测指标中的经纬度转换为地址信息
                carInfoDataMap.put(plateNum, sensorInfoList);
            }
            halleyLogger.log("外展配置", "获取车辆信息");
        } catch (Exception e) {
            halleyLogger.logFailed("外展配置", "获取车辆信息");
            e.printStackTrace();
        }
        return Results.json().root("carInfoDataMap").done();
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

            if (sensorinfoMap.containsKey(SENSORPHYSICALID_LONGITUDE) ||
                    sensorinfoMap.containsKey(SENSORPHYSICALID_LATITUDE) ||
                    sensorinfoMap.containsKey(SENSORPHYSICALID_SWH) ||
                    sensorinfoMap.containsKey(SENSORPHYSICALID_SHAKE) ||
                    sensorinfoMap.containsKey(SENSORPHYSICALID_ACCL)) {
                it.remove();
            }
        }
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

    public Map<String, List<Map<String, String>>> getHistoryPointMap() {
        return historyPointMap;
    }

    public void setHistoryPointMap(Map<String, List<Map<String, String>>> historyPointMap) {
        this.historyPointMap = historyPointMap;
    }

    public Map<String, List<Map<String, LocationDataVO>>> getCarInfoDataMap() {
        return carInfoDataMap;
    }

    public void setCarInfoDataMap(Map<String, List<Map<String, LocationDataVO>>> carInfoDataMap) {
        this.carInfoDataMap = carInfoDataMap;
    }

    public List<RealtimeDataVO> getRealTimeDateList() {
        return realTimeDateList;
    }

    public void setRealTimeDateList(List<RealtimeDataVO> realTimeDateList) {
        this.realTimeDateList = realTimeDateList;
    }

    public static String get_pagePath() {
        return _pagePath;
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
