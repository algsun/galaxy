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
import com.microwise.common.util.GalaxyIdUtil;
import com.microwise.common.util.ResourceBundleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;


/**
 * 添加，删除措施
 *
 * @author liuzhu
 * @date 14-3-11
 */

@Beans.Action
@Blueplanet
@Route("/blueplanet")
public class AddUpdateMeasureAction extends BlueplanetLoggerAction {

    public static final Logger log = LoggerFactory.getLogger(AddUpdateMeasureAction.class);

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
     * 措施描述
     */
    private String description;

    /**
     * 措施vo
     */
    private MeasureVO measureVO;

    @Route("alarm/doAddMeasure")
    public String doAddMeasure() {
        try {
            MeasureVO measureVO = new MeasureVO();
            measureVO.setId(GalaxyIdUtil.get64UUID());
            measureVO.setDescription(description);
            measureVO.setSiteId(Sessions.createByAction().currentSiteId());
            measureVO.setCreateTime(new Date());
            alarmService.addMeasure(measureVO);
            ActionMessage.createByAction().success(ResourceBundleUtil.getBundle().getString("blueplanet.action.alarm.addsuccess"));
        } catch (Exception e) {
            log.error("添加措施失败", e);
            ActionMessage.createByAction().fail(ResourceBundleUtil.getBundle().getString("blueplanet.action.alarm.addFailed"));
        }
        log("添加措施", "措施管理");
        return Results.redirect("measure");
    }

    @Route("alarm/toUpdateMeasure/{measureId}")
    public String toUpdateMeasure() {
        try {
            measureVO = alarmService.findMeasureVOById(measureId);
        } catch (Exception e) {
            log.error("修改措施", e);
        }
        log("修改措施", "措施管理");
        return Results.json().root("measureVO").done();
    }

    @Route("alarm/doUpdateMeasure")
    public String doUpdateMeasure() {
        try {
            measureVO = alarmService.findMeasureVOById(measureId);
            measureVO.setDescription(description);
            alarmService.updateMeasure(measureVO);
            ActionMessage.createByAction().success(ResourceBundleUtil.getBundle().getString("blueplanet.action.alarm.modifySuccess"));
        } catch (Exception e) {
            log.error("修改措施", e);
            ActionMessage.createByAction().fail(ResourceBundleUtil.getBundle().getString("blueplanet.action.alarm.modifyFailed"));
        }
        log("修改措施", "措施管理");
        return Results.redirect("measure");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MeasureVO getMeasureVO() {
        return measureVO;
    }

    public void setMeasureVO(MeasureVO measureVO) {
        this.measureVO = measureVO;
    }

    public String getMeasureId() {
        return measureId;
    }

    public void setMeasureId(String measureId) {
        this.measureId = measureId;
    }
}
