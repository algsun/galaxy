package com.microwise.orion.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;

import java.util.Date;

/**
 * 文物鉴定记录信息实体 对象
 *
 * @author xubaoji
 * @date 2013-5-13
 * @check 2013-06-04 zhangpeng svn:3448
 */
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Appraisal {

    /**
     * 文物鉴定记录id 编号
     */
    private Integer id;

    /**
     * 文物 基本信息实体
     */
    @JsonIgnore
    private Relic relic;

    /**
     * 文物鉴定意见
     */
    @Expose
    private String expertOpinion;

    /**
     * 文物鉴定人
     */
    @Expose
    private String examiner;

    /**
     * 文物鉴定日期
     */
    @Expose
    private Date appraisalDate;

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

    public String getExpertOpinion() {
        return expertOpinion;
    }

    public void setExpertOpinion(String expertOpinion) {
        this.expertOpinion = expertOpinion;
    }

    public String getExaminer() {
        return examiner;
    }

    public void setExaminer(String examiner) {
        this.examiner = examiner;
    }

    public Date getAppraisalDate() {
        return appraisalDate;
    }

    public void setAppraisalDate(Date appraisalDate) {
        this.appraisalDate = appraisalDate;
    }

}
