package com.microwise.orion.action.archivesComplete;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.DetectionAnalysis;
import com.microwise.orion.service.DetectionAnalysisService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 文物检测分析表
 *
 * @author 王耕
 * @date 15-9-29
 */
@Beans.Action
@Orion
@Route("/orion/archives")
public class DetectionAnalysisAction  extends OrionLoggerAction {
    public static final Logger log = LoggerFactory.getLogger(DetectionAnalysisAction.class);

    @Autowired
    private DetectionAnalysisService detectionAnalysisService;

    private DetectionAnalysis detectionAnalysis;
    private String actionName = "detection-analysis";
    private int repairRecordId;
    private int relicId;
    private List<DetectionAnalysis> detectionAnalysises;
    private int analysisNum;

    @Route(value = "saveDetectionAnalysis")
    public String saveDetectionAnalysis(){
        try{
            detectionAnalysisService.saveOrUpdate(detectionAnalysis);
            ActionMessage.createByAction().success("文物检测分析保存成功");
            log("文物检测分析保存","文物修复");
        }catch (Exception e){
            ActionMessage.createByAction().success("文物检测分析保存失败");
            log.error("文物检测分析保存",e);
        }
        return Results.redirect("index?repairRecordId=" + detectionAnalysis.getRepairRecord().getId() + "&relicId=" + relicId + "&actionName=" + actionName);
    }

    @Route(value = "detection-analysis.json")
    public String getJSON() {
        try{
            detectionAnalysises = detectionAnalysisService.findByRepairRecordId(repairRecordId);
            for(DetectionAnalysis detectionAnalysis : detectionAnalysises){
                detectionAnalysis.setRepairRecord(null);
            }
            log("文物检测列表","文物修复");
        }catch (Exception e){
            log.error("文物检测列表",e);
        }
        return Results.json().excludeProperties("").done();
    }

    @Route(value = "deleteDetectionAnalysis")
    public String deleteDetectionAnalysis(){
        try{
            detectionAnalysisService.deleteDetectionAnalysis(analysisNum);
            log("删除文物检测", "文物修复");
        }catch (Exception e){
            log.error("删除文物检测",e);
        }
        return Results.redirect("index?repairRecordId=" + repairRecordId + "&relicId=" + relicId + "&actionName=" + actionName);
    }

    public DetectionAnalysis getDetectionAnalysis() {
        return detectionAnalysis;
    }

    public void setDetectionAnalysis(DetectionAnalysis detectionAnalysis) {
        this.detectionAnalysis = detectionAnalysis;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public int getRepairRecordId() {
        return repairRecordId;
    }

    public void setRepairRecordId(int repairRecordId) {
        this.repairRecordId = repairRecordId;
    }

    public int getRelicId() {
        return relicId;
    }

    public void setRelicId(int relicId) {
        this.relicId = relicId;
    }

    public List<DetectionAnalysis> getDetectionAnalysises() {
        return detectionAnalysises;
    }

    public void setDetectionAnalysises(List<DetectionAnalysis> detectionAnalysises) {
        this.detectionAnalysises = detectionAnalysises;
    }

    public int getAnalysisNum() {
        return analysisNum;
    }

    public void setAnalysisNum(int analysisNum) {
        this.analysisNum = analysisNum;
    }
}
