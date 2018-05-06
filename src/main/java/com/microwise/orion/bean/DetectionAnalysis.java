package com.microwise.orion.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * 文物检测分析表
 *
 * @author 王耕
 * @date 15-9-17
 */
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class DetectionAnalysis {
    /**
     * 编号
     */
    private int analysisNum;
    /**
     * 修复记录
     */
    private RepairRecord repairRecord;
    /**
     * 样品名称
     */
    private String sampleName;
    /**
     * 样品描述
     */
    private String sampleDescription;
    /**
     * 分析目的
     */
    private String analysisPurpose;
    /**
     * 分析方法
     */
    private String analysisMethod;
    /**
     * 分析结果
     */
    private String analysisResult;
    /**
     * 报告代码
     */
    private String reportCode;
    /**
     * 分析时间
     */
    private Date analysisTime;
    /**
     * 备注
     */
    private String remark;

    public int getAnalysisNum() {
        return analysisNum;
    }

    public void setAnalysisNum(int analysisNum) {
        this.analysisNum = analysisNum;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getSampleDescription() {
        return sampleDescription;
    }

    public void setSampleDescription(String sampleDescription) {
        this.sampleDescription = sampleDescription;
    }

    public String getAnalysisPurpose() {
        return analysisPurpose;
    }

    public void setAnalysisPurpose(String analysisPurpose) {
        this.analysisPurpose = analysisPurpose;
    }

    public String getAnalysisMethod() {
        return analysisMethod;
    }

    public void setAnalysisMethod(String analysisMethod) {
        this.analysisMethod = analysisMethod;
    }

    public String getAnalysisResult() {
        return analysisResult;
    }

    public void setAnalysisResult(String analysisResult) {
        this.analysisResult = analysisResult;
    }

    public String getReportCode() {
        return reportCode;
    }

    public void setReportCode(String reportCode) {
        this.reportCode = reportCode;
    }

    public Date getAnalysisTime() {
        return analysisTime;
    }

    public void setAnalysisTime(Date analysisTime) {
        this.analysisTime = analysisTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public RepairRecord getRepairRecord() {
        return repairRecord;
    }

    public void setRepairRecord(RepairRecord repairRecord) {
        this.repairRecord = repairRecord;
    }
}
