package com.microwise.blueplanet.action.location.charts;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.ControllerPackage;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.bean.Subscribe;
import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.SubscribeService;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.po.Stock;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.service.StockService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * KDJ随机指标
 *
 * @author 王耕
 * @date 2015-2-26
 */
@Beans.Action
@Blueplanet
@ControllerPackage(parent = "blueplanet-location")
public class StockAction extends BlueplanetLoggerAction {

    private static final Logger logger = LoggerFactory.getLogger(StockAction.class);

    /**
     * 内容页面
     */
    private static final String _pagePath = "charts/stock.ftl";

    /**
     * 位置点服务
     */
    @Autowired
    private LocationService locationService;

    /**
     * 订阅 service
     */
    @Autowired
    private SubscribeService subscribeService;

    @Autowired
    private StockService stockService;

    //input
    /**
     * 位置点ID
     */
    private String locationId;

    /**
     * 订阅类型
     */
    private int subscribeType;

    /**
     * 监测指标
     */
    private int sensorId;

    //output
    /**
     * 是否订阅
     */
    private boolean kdjReport;

    /**
     * 是否订阅成功
     */
    private boolean success = true;

    /**
     * 位置点
     */
    private LocationVO location;

    /**
     * 设备监测指标(除过风向类)
     */
    private List<SensorinfoVO> sensorinfos;

    /**
     * 随机指标集合
     */
    private List<Stock> stocks;

    private static String kdjSensors;

    //每个sheet中的总显示数
    static {
        kdjSensors = ConfigFactory.getInstance().getConfig("config.properties").get("blueplanet.kdj.alarm.sensors");
    }

    @Route(value = "/blueplanet/location/{locationId}/stock", interceptors = "locationStack")
    public String execute() {
        try {
            User user = Sessions.createByAction().currentUser();
            location = locationService.findLocationById(locationId);
            List<SensorinfoVO> sensors = location.getSensorInfoList();
            sensorinfos = getKDJSensorInfos(sensors, kdjSensors);
            kdjReport = isSubscibe(user, locationId);
            // 过滤掉风向类
            for (Iterator<SensorinfoVO> it = sensorinfos.iterator(); it.hasNext(); ) {
                if (it.next().getShowType() == 1) {
                    it.remove();
                }
            }
            log("位置点", "KDJ随机指标");
        } catch (Exception e) {
            logger.error("位置点KDJ随机指标", e);
        }
        return Results.ftl("/blueplanet/pages/location/layout");
    }

    @Route("/blueplanet/location/sensorid/getStocks.json")
    public String getSensorStocks() {
        try {
            Date startDate = DateUtils.addMonths(new Date(),-3);
            Date endDate = new Date();
            stocks = stockService.findByLocationIdAndSensorId(locationId, sensorId,startDate,endDate);
            log("位置点", "位置点的某监测指标的随机指标");
        } catch (Exception e) {
            logger.error("位置点的某监测指标的随机指标", e);
        }
        return Results.json().asRoot(stocks).done();
    }

    @Route("/blueplanet/stock/location/{locationId}/addSubscribe.json")
    public String addSubscribe() {
        try {
            Subscribe subscribe = new Subscribe();
            subscribe.setSiteId(Sessions.createByAction().currentSiteId());
            subscribe.setUser(Sessions.createByAction().currentUser());
            subscribe.setSubscribeType(subscribeType);
            subscribe.setLocationId(locationId);
            subscribeService.addSubscribe(subscribe);
            log("环境监控", "KDJ随机指标");
        } catch (Exception e) {
            success = false;
            logger.error("订阅出错", e);
        }
        return Results.json().root("success").done();
    }

    @Route("/blueplanet/stock/location/{locationId}/deleteSubscribe.json")
    public String deleteSubscribe() {
        try {
            int userId = Sessions.createByAction().currentUser().getId();
            String siteId = Sessions.createByAction().currentSiteId();
            subscribeService.deleteSubscribe(userId, subscribeType, siteId, locationId);
            log("环境监控", "取消KDJ随机指标");
        } catch (Exception e) {
            success = false;
            logger.error("取消订阅出错", e);
        }
        return Results.json().root("success").done();
    }

    /**
     * 根据配置文件过滤KDJ需要的监测指标
     *
     * @param sensors    原始查询出来的位置点监测指标
     * @param kdjSensors 配置文件的kdj监测指标
     * @return 过滤后的监测指标
     */
    private List<SensorinfoVO> getKDJSensorInfos(List<SensorinfoVO> sensors, String kdjSensors) {
        List<SensorinfoVO> returnSensors = new ArrayList<SensorinfoVO>();

        String[] kdjSensorIds = kdjSensors.split(",");

        for (SensorinfoVO sensorinfo : sensors) {
            int sensorId = sensorinfo.getSensorPhysicalid();
            for (String id : kdjSensorIds) {
                int kdjId = Integer.parseInt(id.trim());
                if (sensorId == kdjId) {
                    returnSensors.add(sensorinfo);
                }
            }
        }
        return returnSensors;
    }

    /**
     * 判断当前用户是否订阅了改位置点
     */
    private boolean isSubscibe(User user, String locationId) {
        List<Subscribe> subscribes = subscribeService.findSubscribe(user.getId(), locationId, Subscribe.SUBSCRIBE_TYPE_KDJ_ALARM);
        return subscribes.size() != 0;
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public boolean isKdjReport() {
        return kdjReport;
    }

    public void setKdjReport(boolean kdjReport) {
        this.kdjReport = kdjReport;
    }

    public LocationVO getLocation() {
        return location;
    }

    public void setLocation(LocationVO location) {
        this.location = location;
    }

    public List<SensorinfoVO> getSensorinfos() {
        return sensorinfos;
    }

    public void setSensorinfos(List<SensorinfoVO> sensorinfos) {
        this.sensorinfos = sensorinfos;
    }

    public int getSubscribeType() {
        return subscribeType;
    }

    public void setSubscribeType(int subscribeType) {
        this.subscribeType = subscribeType;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }
}
