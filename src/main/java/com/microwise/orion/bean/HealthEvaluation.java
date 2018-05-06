package com.microwise.orion.bean;

import java.util.Date;

/**
 * 文物健康评测表实体
 *
 * @author bai.weixing
 * @since 2017/7/4.
 */
public class HealthEvaluation {

    //自增id
    private Integer id;


    //文物
    private Relic relic;

    //评测人
    private String evaluator;

    //样品编号
    private String sampleNumber;

    //样品描述
    private String sampleDesc;

    //综合评测结论
    private Short conclusion;

    //保护建议
    private Short suggestion;

    //评测日期
    private Date evaluationDate;

    //文物现状记录信息id
    private Integer statusQuoId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Relic getRelic() {
        return relic;
    }

    public void setRelic(Relic relic) {
        this.relic = relic;
    }

    public String getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(String evaluator) {
        this.evaluator = evaluator;
    }

    public String getSampleNumber() {
        return sampleNumber;
    }

    public void setSampleNumber(String sampleNumber) {
        this.sampleNumber = sampleNumber;
    }

    public String getSampleDesc() {
        return sampleDesc;
    }

    public void setSampleDesc(String sampleDesc) {
        this.sampleDesc = sampleDesc;
    }

    public Short getConclusion() {
        return conclusion;
    }

    public void setConclusion(Short conclusion) {
        this.conclusion = conclusion;
    }

    public Short getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(Short suggestion) {
        this.suggestion = suggestion;
    }

    public Date getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(Date evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    public Integer getStatusQuoId() {
        return statusQuoId;
    }

    public void setStatusQuoId(Integer statusQuoId) {
        this.statusQuoId = statusQuoId;
    }
}
