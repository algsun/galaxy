package com.microwise.blueplanet.action.location.charts;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.ControllerPackage;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.QCMVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.service.QCMService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.DateTimeUtil;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * qcm action
 */

@Beans.Action
@Blueplanet
@ControllerPackage(parent = "blueplanet-location")
public class QCMAction {

    public static final Logger log = LoggerFactory.getLogger(QCMAction.class);

    @Autowired
    private QCMService qcmService;

    @Autowired
    private LocationService locationService;

    // 内容页面
    private final String _pagePath = "charts/qcm.ftl";

    /**
     * 位置点id
     */
    private String locationId;

    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 监测指标
     */
    private List<SensorinfoVO> sensorinfoVOs;

    /**
     * 监测指标id
     */
    private int sensorId;

    /**
     * 位置点概览
     */
    @Route(value = "/blueplanet/location/{locationId}/qcm", interceptors = "locationStack")
    public String show() {
        startDate = DateTime.now().minusDays(30).toDate();
        endDate = new Date();
        LocationVO location = locationService.findLocationById(locationId);
        sensorinfoVOs = location.getSensorInfoList();
        return Results.ftl("/blueplanet/pages/location/layout");
    }

    @Route(value = "/blueplanet/location/{locationId}/qcm_data")
    public String findOP() {
        startDate = DateTimeUtil.startOfDay(startDate);
        endDate = DateTimeUtil.endOfDay(endDate);
        List<QCMVO> qcmvos = qcmService.findQCM(locationId, startDate, endDate, sensorId);
        return Results.json().asRoot(qcmvos).done();
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String get_pagePath() {
        return _pagePath;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<SensorinfoVO> getSensorinfoVOs() {
        return sensorinfoVOs;
    }

    public void setSensorinfoVOs(List<SensorinfoVO> sensorinfoVOs) {
        this.sensorinfoVOs = sensorinfoVOs;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }
}
