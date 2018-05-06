package com.microwise.blueplanet.action.location;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.ControllerPackage;
import com.bastengao.struts2.freeroute.annotation.MethodType;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.vo.TextureThresholdVO;
import com.microwise.blueplanet.bean.vo.ThresholdVO;
import com.microwise.blueplanet.service.ThresholdService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 位置点报警阈值
 *
 * @author li.jianfei
 * @date 2016-07-20
 */
@Beans.Action
@Blueplanet
@ControllerPackage(parent = "blueplanet-location")
public class LocationThresholdAction extends BlueplanetLoggerAction {
    private static final Logger logger = LoggerFactory.getLogger(LocationThresholdAction.class);

    // 内容页面
    private static final String _pagePath = "threshold.ftl";

    @Autowired
    private ThresholdService thresholdService;

    // input
    /**
     * 位置点ID
     */
    private String locationId;

    /**
     * 监测指标ID
     */
    private int sensorId;


    /**
     * 位置点报警阈值
     */
    private ThresholdVO threshold;

    // output
    /**
     * 报警阈值集合
     */
    private List<ThresholdVO> thresholds;

    @Route(value = "/blueplanet/location/{locationId}/threshold", interceptors = "locationStack")
    public String queryRealtimeData() {
        return Results.ftl("/blueplanet/pages/location/layout-b4");
    }

    //
    @Route("/blueplanet/location/{locationId}/thresholds")
    public String ajaxLocaytionThresholds() {
        try {
            thresholds = thresholdService.findThresholds(locationId);
        } catch (Exception e) {
            logger.error("查询位置点阈值", e);
        }
        return Results.json().asRoot(thresholds).done();
    }

    @Route("/blueplanet/location/thresholds/add")
    public String ajaxAddThreshold() {
        boolean success = false;
        try {
            thresholdService.save(threshold);
            success = true;
        } catch (Exception e) {
            logger.error("添加位置点阈值", e);
        }
        return Results.json().asRoot(success).done();
    }

    @Route("/blueplanet/location/thresholds/delete")
    public String ajaxDeleteThreshold() {
        boolean success = false;
        try {
            thresholdService.delete(locationId, sensorId);
            success = true;
        } catch (Exception e) {
            logger.error("删除位置点报警阈值", e);
        }
        return Results.json().asRoot(success).done();
    }

    @Route("/blueplanet/location/thresholds/exists")
    public String isExist() {
        boolean isExists = false;
        try {
            isExists = thresholdService.exists(threshold);
        } catch (Exception e) {
            logger.error("判断位置点报警阈值是否存在", e);
        }
        return Results.json().asRoot(isExists).done();
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public ThresholdVO getThreshold() {
        return threshold;
    }

    public void setThreshold(ThresholdVO threshold) {
        this.threshold = threshold;
    }

    public List<ThresholdVO> getThresholds() {
        return thresholds;
    }

    public void setThresholds(List<ThresholdVO> thresholds) {
        this.thresholds = thresholds;
    }

    public static String get_pagePath() {
        return _pagePath;
    }
}
