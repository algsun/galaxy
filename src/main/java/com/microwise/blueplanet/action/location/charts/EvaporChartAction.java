package com.microwise.blueplanet.action.location.charts;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.ControllerPackage;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.vo.ChartVO;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.service.ChartService;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.ResourceBundleUtil;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 蒸发量action
 *
 * @author liuzhu
 * @date 2014-6-18
 */
@Beans.Action("locationEvaporChartAction")
@Blueplanet
@ControllerPackage(parent = "blueplanet-location")
public class EvaporChartAction extends BlueplanetLoggerAction {
    private static Logger log = LoggerFactory.getLogger(EvaporChartAction.class);

    // 内容页面
    private final String _pagePath = "charts/evaporation-chart.ftl";

    @Autowired
    private ChartService chartService;

    @Autowired
    private LocationService locationService;

    //input
    //位置点id
    private String locationId;

    /**
     * 时间类型
     */
    private int dateType = Constants.FIND_TYPE_MONTH;

    /**
     * 开始日期
     */
    private Date date = new Date();

    /**
     * 开始日期
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

    //input
    /**
     * chartVO数据
     */
    private ChartVO chartVO;

    /**
     * 近几天数据
     */
    private List<ChartVO> chartVOs;

    /**
     * 位置点名称
     */
    private String locationName;


    @Route(value = "/blueplanet/location/{locationId}/evaporation-chart", interceptors = "locationStack")
    public String execute() {
        try {
            //初始化时间
            initDate();

            if (dateType == Constants.FIND_TYPE_DAY) {
                endDate = new DateTime(endDate).plusDays(1).minusSeconds(1).toDate();
                chartVO = chartService.findEvaporation(locationId, dateType, startDate, endDate);
            } else {
                chartVO = chartService.findEvaporation(locationId, dateType, null, date);
            }
            LocationVO locationVO = locationService.findLocationById(locationId);
            locationName = locationVO.getLocationName();
            //近几天数据
            chartVOs = new ArrayList<ChartVO>();
            ChartVO chartOneDay = chartService.findEvaporationRecentDay(locationId, 1);
            chartOneDay.setDateString(ResourceBundleUtil.getBundle().getString("blueplanet.action.location.recentDay"));
            chartVOs.add(chartOneDay);
            ChartVO chartSeven = chartService.findEvaporationRecentDay(locationId, 7);
            chartSeven.setDateString(ResourceBundleUtil.getBundle().getString("blueplanet.action.location.recentWeek"));
            chartVOs.add(chartSeven);
            ChartVO chartOneMonth = chartService.findEvaporationRecentMonth(locationId, 1);
            chartOneMonth.setDateString(ResourceBundleUtil.getBundle().getString("blueplanet.action.location.recentMonth"));
            chartVOs.add(chartOneMonth);
            ChartVO chartThreeMonth = chartService.findEvaporationRecentMonth(locationId, 3);
            chartThreeMonth.setDateString(ResourceBundleUtil.getBundle().getString("blueplanet.action.location.recentThreeMonth"));
            chartVOs.add(chartThreeMonth);
            ChartVO chartSixMonth = chartService.findEvaporationRecentMonth(locationId, 6);
            chartSixMonth.setDateString(ResourceBundleUtil.getBundle().getString("blueplanet.action.location.recentSixMonth"));
            chartVOs.add(chartSixMonth);
            ChartVO chartOneYear = chartService.findEvaporationRecentMonth(locationId, 12);
            chartOneYear.setDateString(ResourceBundleUtil.getBundle().getString("blueplanet.action.location.recentYear"));
            chartVOs.add(chartOneYear);
        } catch (Exception e) {
            log.error("蒸发量错误", e);
        }
        log("位置点", "蒸发量");

        return Results.ftl("/blueplanet/pages/location/layout");
    }

    private void initDate() {
        if (startDate == null) {
            startDate = DateTime.now().withTimeAtStartOfDay().toDate();
        }
        if (endDate == null) {
            endDate = DateTime.now().plusDays(1).withTimeAtStartOfDay().minusMillis(1).toDate();
        }
    }

    public String get_pagePath() {
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

    public Date getStartDate() {
        return startDate;
    }

    public List<ChartVO> getChartVOs() {
        return chartVOs;
    }

    public void setChartVOs(List<ChartVO> chartVOs) {
        this.chartVOs = chartVOs;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public ChartVO getChartVO() {
        return chartVO;
    }

    public void setChartVO(ChartVO chartVO) {
        this.chartVO = chartVO;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}

