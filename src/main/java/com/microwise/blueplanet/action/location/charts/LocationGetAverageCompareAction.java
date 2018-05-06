package com.microwise.blueplanet.action.location.charts;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.ControllerPackage;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.vo.ChartVO;
import com.microwise.blueplanet.service.ChartService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.blueplanet.util.HighChartUtil;
import com.microwise.common.sys.annotation.Beans;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 均值比较Action
 *
 * @author xu.yuexi
 * @date 14-7-11
 */
@Beans.Action
@Blueplanet
@ControllerPackage(parent = "blueplanet-location")
public class LocationGetAverageCompareAction extends BlueplanetLoggerAction {

    /**
     * 日志对象
     */
    public static final Logger log = LoggerFactory.getLogger(LocationGetAverageCompareAction.class);

    /**
     * 内容页面
     */
    private static final String _pagePath = "average-compare.ftl";

    @Autowired
    private ChartService chartService;
    //input
    /**
     * 设备id
     */
    private String locationId;
    /**
     * 设备名称
     */
    private String locationName;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 查询类型
     */
    private int queryType;
    /**
     * 需要展现图像的监测指标
     */
    private Integer[] sensorinfoes;
    //output
    /**
     * 基础曲线图数据
     * {
     * deviceName:设备名称
     * param1：param1Value，
     * ...,
     * param2:param2Value,
     * chartData:[
     * {
     * sensorinfoId:sensorinfoValue,
     * sensorinfoName:sensorinfoValue,
     * chartName:`name`, 设备-监测指标(单位)
     * units:unitsValue,
     * tooltip:{
     * ySuffix:`units`
     * },
     * yAxis:`index (number)`,
     * chart:[[time,value],[],...,[]]
     * },
     * ...,
     * {...}
     * ]
     * }
     */
    private Map<String, Object> data;


    @Route(value = "/blueplanet/location/{locationId}/getAverageCompare.json")
    public String getJSONData() {
        try {
            //当前选择日期的开始，即：yyyy-MM-dd 00:00:00
            Date start = DateTime.now().withMillis(startTime.getTime()).withTimeAtStartOfDay().toDate();
            //当前选择日期的最后一秒，即：yyyy-MM-dd 23:5959:
            Date end = DateTime.now().withMillis(endTime.getTime()).plusDays(1).withTimeAtStartOfDay().minusMillis(1).toDate();

            List<ChartVO> averageChartData = chartService.findAverageChart(locationId, Arrays.asList(sensorinfoes), start, end);
            data = HighChartUtil.packageAvg(locationId, locationName, averageChartData);
            log("位置点", "均值比较");
            return Results.json().root("data").done();
        } catch (Exception e) {
            log.error("均值比较", e);
            return null;
        }

    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public int getQueryType() {
        return queryType;
    }

    public void setQueryType(int queryType) {
        this.queryType = queryType;
    }


    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public Integer[] getSensorinfoes() {
        return sensorinfoes;
    }

    public void setSensorinfoes(Integer[] sensorinfoes) {
        this.sensorinfoes = sensorinfoes;
    }
}
