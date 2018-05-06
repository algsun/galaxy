package com.microwise.blueplanet.action.location.charts;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.vo.ChartVO;
import com.microwise.blueplanet.service.ChartService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.util.HighChartUtil;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.DateTimeUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 获取基础曲线图数据
 *
 * @author Wang yunlong
 * @date 13-1-31 上午10:56
 * @check @li.jianfei 2013-03-13 #2011
 */
@Beans.Action
@Blueplanet
public class GetBasicChartDataAction {
    @Autowired
    private ChartService chartService;
    //input
    /**
     * 位置点ID
     */
    private String locationId;
    /**
     * 位置点名称
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

    @Route("/blueplanet/location/{locationId}/basic-chart.json")
    public String execute() {
        //当前选择日期的开始，即：yyyy-MM-dd 00:00:00
        Date start = DateTimeUtil.startOfDay(startTime);
        //当前选择日期的最后一秒，即：yyyy-MM-dd 23:5959:
        Date end = DateTimeUtil.endOfDay(endTime);

        List<ChartVO> basicChartData = chartService.findBasicChart(locationId, Arrays.asList(sensorinfoes), start, end);
        data = HighChartUtil.packageBasic(locationId, locationName, basicChartData);
        return Results.json().root("data").done();
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

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
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

    public int getQueryType() {
        return queryType;
    }

    public void setQueryType(int queryType) {
        this.queryType = queryType;
    }

    public Integer[] getSensorinfoes() {
        return sensorinfoes;
    }

    public void setSensorinfoes(Integer[] sensorinfoes) {
        this.sensorinfoes = sensorinfoes;
    }
}
