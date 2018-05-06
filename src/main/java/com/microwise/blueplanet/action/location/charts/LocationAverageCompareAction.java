package com.microwise.blueplanet.action.location.charts;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.ControllerPackage;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.microwise.blueplanet.bean.vo.ChartVO;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.service.ChartService;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.blueplanet.util.HighChartUtil;
import com.microwise.common.sys.annotation.Beans;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
public class LocationAverageCompareAction extends BlueplanetLoggerAction {

    /**
     * 日志对象
     */
    public static final Logger log = LoggerFactory.getLogger(LocationAverageCompareAction.class);

    /**
     * 内容页面
     */
    private static final String _pagePath = "charts/average-compare.ftl";

    @Autowired
    private ChartService chartService;
    @Autowired
    private LocationService locationService;
    //input
    /**
     * 位置点id
     */
    private String locationId;

    /**
     * 起始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;


    //output
    /**
     * 位置点
     */
    private LocationVO location;
    /**
     * 基础曲线图数据
     * {
     * locationName:位置点名称
     * param1：param1Value，
     * ...,
     * param2:param2Value,
     * chartData:[
     * {
     * sensorinfoId:sensorinfoValue,
     * sensorinfoName:sensorinfoValue,
     * chartName:`name`, 位置点-监测指标(单位)
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

    /**
     * 位置点监测指标
     */
    private List<SensorinfoVO> sensorinfoes;

    @Route(value = "/blueplanet/location/{locationId}/average-compare", interceptors = "locationStack")
    public String defaultView() {
        try {
            startTime = DateTime.now().minusMonths(1).withTimeAtStartOfDay().toDate();
            endTime = DateTime.now().minusMillis(1).toDate();
            //当前选择日期的开始，即：yyyy-MM-dd 00:00:00
            Date start = DateTime.now().withMillis(startTime.getTime()).withTimeAtStartOfDay().toDate();
            //当前选择日期的最后一秒，即：yyyy-MM-dd 23:5959:
            Date end = DateTime.now().withMillis(endTime.getTime()).plusDays(1).withTimeAtStartOfDay().minusMillis(1).toDate();
            location = locationService.findLocationById(locationId);
            sensorinfoes = location.getSensorInfoList();
            List<Integer> sensorIdList = Lists.transform(location.getSensorInfoList(), new Function<SensorinfoVO, Integer>() {
                @Override
                public Integer apply(SensorinfoVO sensorinfoVO) {
                    return sensorinfoVO.getSensorPhysicalid();
                }
            });
            List<ChartVO> averageChartData = chartService.findAverageChart(locationId, sensorIdList, start, end);
            data = HighChartUtil.packageAvg(locationId, location.getLocationName(), averageChartData);
            log("位置点", "均值比较曲线图");
        } catch (Exception e) {
            log.error("均值比较曲线图", e);
        }
        return Results.ftl("/blueplanet/pages/location/layout");
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public LocationVO getLocation() {
        return location;
    }

    public void setLocation(LocationVO location) {
        this.location = location;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public List<SensorinfoVO> getSensorinfoes() {
        return sensorinfoes;
    }

    public void setSensorinfoes(List<SensorinfoVO> sensorinfoes) {
        this.sensorinfoes = sensorinfoes;
    }


    public static String get_pagePath() {
        return _pagePath;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
