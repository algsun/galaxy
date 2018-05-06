package com.microwise.blueplanet.action.alarm;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.AlarmRecordVO;
import com.microwise.blueplanet.bean.vo.MeasureVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.service.AlarmService;
import com.microwise.blueplanet.service.SiteService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.PagingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;


/**
 * @author liuzhu
 * @date 14-3-11
 */
 @Beans.Action
 @Blueplanet
 @Route("/blueplanet")
public class AlarmHistoryAction extends BlueplanetLoggerAction{

    public static final Logger log = LoggerFactory.getLogger(AlarmHistoryAction.class);

    public static final String _pagePath = "alarm-history.ftl";

    @Autowired
    public AlarmService alarmService;

    @Autowired
    private SiteService siteService;

    //input
    /**
     * 监测指标ID
     */
    private Integer sensorId;

    /**
     * 起始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 当前页
     */
    private Integer index = 1;

    /**
     * 分页大小
     */
    private Integer size;


    //output
    /**
     * 报警记录对象列表
     */
    private List<AlarmRecordVO> alarmRecords;

    /**
     * 分页总数
     */
    private Integer pageCount;

    /**
     * 记录总数
     */
    private Integer count;

    /**
     * 监测指标
     */
    private List<SensorinfoVO> sensorinfoVOs;

    /**
     * 措施
     */
    private List<MeasureVO> measureVOs;

    @Route("alarm/history")
    public String execute(){
        String siteId = Sessions.createByAction().currentSiteId();
        sensorinfoVOs = siteService.findSensorinfo(siteId);
        measureVOs = alarmService.findMeasureList(siteId);
        alarmRecords = alarmService.findRecordVOByPages(sensorId,startTime,endTime,1,Constants.SIZE_PER_PAGE ,siteId);
        count = alarmService.findRecordVOCount(sensorId,startTime,endTime,siteId);
        //总分页数
        pageCount = PagingUtil.pagesCount(count, Constants.SIZE_PER_PAGE);
        return Results.ftl("/blueplanet/pages/alarm/layout");
    }


    @Route(value = "alarm/history",params = {"sensorId","measureId","startTime","endTime","index","size"})
    public String findHistoryByPages(){
        String siteId = Sessions.createByAction().currentSiteId();
        sensorinfoVOs = siteService.findSensorinfo(siteId);
        measureVOs = alarmService.findMeasureList(siteId);
        alarmRecords = alarmService.findRecordVOByPages(sensorId,startTime,endTime,index,Constants.SIZE_PER_PAGE,siteId);
        count = alarmService.findRecordVOCount(sensorId,startTime,endTime,siteId);
        //总分页数
        pageCount = PagingUtil.pagesCount(count, Constants.SIZE_PER_PAGE);
        return Results.ftl("/blueplanet/pages/alarm/layout");
    }
    public static String get_pagePath() {
        return _pagePath;
    }

    public List<AlarmRecordVO> getAlarmRecords() {
        return alarmRecords;
    }

    public void setAlarmRecords(List<AlarmRecordVO> alarmRecords) {
        this.alarmRecords = alarmRecords;
    }

    public Integer getSensorId() {
        return sensorId;
    }

    public void setSensorId(Integer sensorId) {
        this.sensorId = sensorId;
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

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<SensorinfoVO> getSensorinfoVOs() {
        return sensorinfoVOs;
    }

    public void setSensorinfoVOs(List<SensorinfoVO> sensorinfoVOs) {
        this.sensorinfoVOs = sensorinfoVOs;
    }

    public List<MeasureVO> getMeasureVOs() {
        return measureVOs;
    }

    public void setMeasureVOs(List<MeasureVO> measureVOs) {
        this.measureVOs = measureVOs;
    }
}
