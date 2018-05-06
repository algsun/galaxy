package com.microwise.blueplanet.action.alarm;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.vo.MeasureVO;
import com.microwise.blueplanet.service.AlarmService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.ResourceBundleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 措施管理action
 *
 * @author liuzhu
 * @date 14-3-11
 */

@Beans.Action
@Blueplanet
@Route("/blueplanet")
public class MeasureAction extends BlueplanetLoggerAction {

    public static final Logger log = LoggerFactory.getLogger(MeasureAction.class);

    public static final String _pagePath = "measure.ftl";

    /**
     * 监测预警service
     */
    @Autowired
    private AlarmService alarmService;

    //input
    /**
     * 措施id
     */
    private String measureId;

    //output
    /**
     * 措施list
     */
    private List<MeasureVO> measureVOList;

    /**
     * 是否有绑定
     */
    private boolean flag = false;

    @Route("alarm/measure")
    public String execute() {
        try {
            measureVOList = alarmService.findMeasureList(Sessions.createByAction().currentSiteId());
            ActionMessage.createByAction().consume();
        } catch (Exception e) {
            log.error("措施管理", e);
        }
        log("措施管理", "监测预警");
        return Results.ftl("/blueplanet/pages/alarm/layout");
    }

//    @Route("alarm/hasZoneMeasureId")
//    public String hasZoneMeasureId(){
//        try{
//            flag = alarmService.hasZoneMeasureId(measureId);
//        }catch (Exception e){
//            log.error("删除措施", e);
//        }
//        return Results.json().root("flag").done();
//    }

    @Route("alarm/deleteMeasure/{measureId}")
    public String deleteMeasure() {
        try {
            alarmService.deleteMeasure(measureId);
            measureVOList = alarmService.findMeasureList(Sessions.createByAction().currentSiteId());

            ActionMessage.createByAction().success(ResourceBundleUtil.getBundle().getString("blueplanet.action.alarm.deleteSuccess"));
        } catch (Exception e) {
            log.error("删除措施", e);
            ActionMessage.createByAction().fail(ResourceBundleUtil.getBundle().getString("blueplanet.action.alarm.deleteFailed"));
        }
        log("删除措施", "监测预警");
        return Results.redirect("/blueplanet/alarm/measure");
    }

    public List<MeasureVO> getMeasureVOList() {
        return measureVOList;
    }

    public void setMeasureVOList(List<MeasureVO> measureVOList) {
        this.measureVOList = measureVOList;
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public String getMeasureId() {
        return measureId;
    }

    public void setMeasureId(String measureId) {
        this.measureId = measureId;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
