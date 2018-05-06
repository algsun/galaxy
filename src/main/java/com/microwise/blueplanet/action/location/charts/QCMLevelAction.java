package com.microwise.blueplanet.action.location.charts;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.ControllerPackage;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.po.EvaluateCriterionPO;
import com.microwise.blueplanet.bean.po.ReplaceSensorPO;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.service.QCMService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * qmc等级评估
 *
 * @author liuzhu
 * @date 2016-1-7
 */


@Beans.Action
@Blueplanet
@ControllerPackage(parent = "blueplanet-location")
public class QCMLevelAction {

    public static final Logger log = LoggerFactory.getLogger(QCMAction.class);

    @Autowired
    private QCMService qcmService;

    @Autowired
    private LocationService locationService;

    // 内容页面
    private final String _pagePath = "charts/qcm-level.ftl";

    /**
     * 位置点id
     */
    private String locationId;

    private LocationVO locationVO;

    private List<ReplaceSensorPO> replaceSensors;

    private Map<String, List<ReplaceSensorPO>> stringListMap = new TreeMap<String, List<ReplaceSensorPO>>();

    private List<EvaluateCriterionPO> evaluateCriterions;

    private boolean allDataFlag = false;

    private String more = "";

    /**
     * 位置点概览
     */
    @Route(value = "/blueplanet/location/{locationId}/qcm_level", interceptors = "locationStack")
    public String show() {
        locationVO = locationService.findLocationById(locationId);
        //查出最小时间 最大时间
        List<Date> dates = qcmService.findQCMMinMaxDate(locationId);
        List<Date> dataList = new ArrayList<Date>();
        Date minDate = DateTime.now().minusDays(180).toDate();//半年
        if (!allDataFlag) {
            if (minDate.before(dates.get(0))) {
                minDate = dates.get(0);
                more = "none";
            }
            dataList.add(minDate);
            dataList.add(DateTime.now().toDate());
        } else {
            more = "none";
            dataList = dates;
        }
        stringListMap = qcmService.assembleQCMLevel(locationId, dataList.get(0), dataList.get(1));
        evaluateCriterions = qcmService.findEvaluateCriterions();
        return Results.ftl("/blueplanet/pages/location/layout");
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

    public List<ReplaceSensorPO> getReplaceSensors() {
        return replaceSensors;
    }

    public void setReplaceSensors(List<ReplaceSensorPO> replaceSensors) {
        this.replaceSensors = replaceSensors;
    }

    public Map<String, List<ReplaceSensorPO>> getStringListMap() {
        return stringListMap;
    }

    public void setStringListMap(Map<String, List<ReplaceSensorPO>> stringListMap) {
        this.stringListMap = stringListMap;
    }

    public LocationVO getLocationVO() {
        return locationVO;
    }

    public void setLocationVO(LocationVO locationVO) {
        this.locationVO = locationVO;
    }

    public List<EvaluateCriterionPO> getEvaluateCriterions() {
        return evaluateCriterions;
    }

    public void setEvaluateCriterions(List<EvaluateCriterionPO> evaluateCriterions) {
        this.evaluateCriterions = evaluateCriterions;
    }

    public boolean isAllDataFlag() {
        return allDataFlag;
    }

    public void setAllDataFlag(boolean allDataFlag) {
        this.allDataFlag = allDataFlag;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }
}
