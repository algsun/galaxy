package com.microwise.blueplanet.action.location.charts;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.ControllerPackage;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Strings;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 水流量监测
 *
 * @author 王耕
 * @date 15-8-27
 * @deprecated 水流量监测功能已被废弃 @by li.jianfei
 */
@Beans.Action
@Blueplanet
@ControllerPackage(parent = "blueplanet-location")
public class WaterFlowAction extends BlueplanetLoggerAction {
    private static final Logger logger = LoggerFactory.getLogger(WaterFlowAction.class);

    /**
     * 内容页面
     */
    private static final String _pagePath = "charts/water-flow.ftl";

    @Autowired
    private LocationService locationService;

    //input
    /**
     * 位置点ID
     */
    private String locationId;

    /**
     * 时间类型
     */
    private int dateType = Constants.FIND_TYPE_DAY;
    /**
     * 日期 默认值：今天
     */
    private Date date;

    /**
     * 监测指标ID
     */
    private int sensorId = Constants.ChartConstants.SENSORINFO_WATER_FLOW;

    /**
     * 被选中的位置点名称
     */
    private String names;

    @Route(value = "/blueplanet/location/{locationId}/water-flow", interceptors = "locationStack")
    public String view() {
        date = new Date();
        return Results.ftl("/blueplanet/pages/location/layout");
    }

    @Route("/blueplanet/location/sensorId/water-flow-data.json")
    public String getJSON() {
        String siteId = Sessions.createByAction().currentSiteId();
        List<Map<String, Object>> orderedchartValues = new ArrayList<Map<String, Object>>();
        try{
            List<Map<String, Object>> chartValues = locationService.findWaterFlowChartData(siteId, date, sensorId, dateType);
            orderedchartValues = orderChartValues(chartValues, locationId);
            log("获取水流量等监测指标的数据成功","环境监控");
        }catch (Exception e){
            logger.error("获取水流量等监测指标的数据出错",e);
        }
        return Results.json().asRoot(orderedchartValues).done();
    }

    @Route("/blueplanet/location/waterflow-location-name.json")
    public String getName() {
        String locationName = "";
        try{
            locationName = locationService.findLocationById(locationId).getLocationName();
            if (Strings.isNullOrEmpty(locationName)) {
                return Results.json().asRoot("").done();
            }
            log("获取位置点名称成功","环境监控水流量监测");
        }catch (Exception e){
            logger.error("获取位置点名称",e);
        }
        return Results.json().asRoot(locationName).done();
    }

    @Route("blueplanet/location/waterflow-backup.json")
    public String getbackup() {
        String siteId = Sessions.createByAction().currentSiteId();
        //names为选中的位置点，只拼接选中的位置点的结论
        List<String> nameList = Arrays.asList(names.split(","));
        String backups = "";
        try{
            List<Map<String, Object>> chartValues = locationService.findWaterFlowChartData(siteId, date, sensorId, dateType);
            backups = backups(chartValues, nameList, dateType);
            log("获取结论信息失败","环境监控水流量监测");
        }catch (Exception e){
            logger.error("获取结论信息失败",e);
        }
        return Results.json().asRoot(backups).done();
    }

    /**
     * 对位置点进行排序，将主查询位置点放到第一位，其它位置不变
     */
    private List<Map<String, Object>> orderChartValues(List<Map<String, Object>> chartValues, String locationId) {
        for (int i = 0; i < chartValues.size(); i++) {
            Map<String, Object> map = chartValues.get(i);
            String lid = (String) map.get("locationId");
            if (lid.equals(locationId)) {
                chartValues.remove(map);
                chartValues.add(0, map);
            }
        }
        return chartValues;
    }

    /**
     * 拼接结论
     */
    private String backups(List<Map<String, Object>> chartValues, List<String> names, int dateType) {
        StringBuffer backup = new StringBuffer("");
        for (Map<String, Object> map : chartValues) {
            String name = (String) map.get("name");
            //所有选中的位置点"names"若没有该位置点则跳出循环，执行下一个循环
            if (!names.contains(name)) {
                continue;
            }

            String title = (String) map.get("title");
            String values[] = (String[]) map.get("data");
            Double avgValue = (Double) map.get("avgValue");//平均值
            Double waveValue = (Double) map.get("waveValue");//波动值(最大值减最小值)

            if (values == null) {
                continue;
            }

            String type = "";
            //todo 国际化
            if (dateType == Constants.FIND_TYPE_YEAR) {
                type = "每月";
            } else if (dateType == Constants.FIND_TYPE_MONTH) {
                type = "每天";
            } else if (dateType == Constants.FIND_TYPE_DAY) {
                type = "各小时";
            }

            //根据values获得最大值与最小值，分别放在list的第一个和第二个位置
            List<Double> list = doubleOrderer(values);

            //当日的统计在当日结束前是没有的，当天只能统计昨天的日数据，所以当天只能统计当天的小时数据而不能统计当日的日数据
            //todo 国际化
            if (avgValue != null && waveValue != null) {
                backup.append("位置点【" + name + "】，" + title + type + "均值的最大值为:" + list.get(0) + ",最小值为:" + list.get(1) + ";日波动值为:" + waveValue + ";日均值为:" + avgValue + "。<br/>");
            } else {
                backup.append("位置点【" + name + "】，" + title + type + "均值的最大值为:" + list.get(0) + ",最小值为:" + list.get(1) + "。<br/>");
            }
        }
        return backup.toString();
    }

    /**
     * 获取最大值与最小值
     */
    private List<Double> doubleOrderer(String values[]) {
        List<Double> list = new ArrayList<Double>();
        double max = 0;
        double min = 0;
        for (String value : values) {
            Double dValue = Double.parseDouble(value);
            //过滤掉为0的数据不做为统计依据
            if (dValue == 0) continue;

            //第一次进来，初始化max与min的值
            if (max == 0 && min == 0) {
                max = dValue;
                min = dValue;
            }

            if (dValue >= max) {
                max = dValue;
            }
            if (dValue <= min) {
                min = dValue;
            }
        }
        list.add(0, max);
        list.add(1, min);
        return list;
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

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }
}
