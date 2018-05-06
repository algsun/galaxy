package com.microwise.phoenix.action.blueplanet;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.biela.bean.po.SensorInfoPO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.service.ZoneService;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.phoenix.bean.vo.ZoneStability;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.phoenix.sys.PhoenixLoggerAction;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 区域数据分析
 *
 * @author li.jianfei
 * @date 2016-08-17
 */
@Beans.Action
@Phoenix
public class ZoneAnalysisAction extends PhoenixLoggerAction {
    /**
     * 日志
     */
    public static final Logger log = LoggerFactory.getLogger(ZoneAnalysisAction.class);

    /**
     * 页面layout路径
     */
    public static final String _pagePath = "../blueplanet/zone-analysis.ftl";


    @Autowired
    private ZoneService zoneService;


    // Input
    /**
     * 时间类型
     * 1-年  2-月
     */
    private int dateType;

    /**
     * 日期
     */
    private Date date;

    /**
     * 区域ID
     */
    private String zoneId;

    /**
     * 监测指标ID
     */
    private int sensorId = 33;

    // Output
    private List<SensorinfoVO> sensors;


    /**
     * 组装区域稳定性页面图表需要的数据
     *
     * @return 页面
     */
    @Route("/phoenix/blueplanet/zoneAnalysis")
    public String execute() {

        dateType = Constants.FIND_TYPE_MONTH;
        if (date == null) {
            date = new Date();
        }
        sensors = zoneService.findSensorInfos(zoneId, SensorinfoVO.SHOW_TYPE_DEFAULT);

        return Results.ftl("/phoenix/pages/index/layout-b4.ftl");
    }


    public static String get_pagePath() {
        return _pagePath;
    }

    public int getDateType() {
        return dateType;
    }

    public void setDateType(int dateType) {
        this.dateType = dateType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public List<SensorinfoVO> getSensors() {
        return sensors;
    }

    public void setSensors(List<SensorinfoVO> sensors) {
        this.sensors = sensors;
    }
}
