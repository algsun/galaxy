package com.microwise.orion.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;

import java.util.Date;

/**
 * 文物现状记录信息 实体对象
 *
 * @author xubaoji
 * @date 2013-5-13
 * @check 2013-06-04 zhangpeng svn:3448
 */
public class StatusQuo {

    /**
     * 文物现状信息 id 编号
     */
    private Integer id;

    /**
     * 文物信息实体
     */
    @JsonIgnore
    private Relic relic;

    /**
     * 文物现状及病害描述
     */
    @Expose
    private String quoInfo;

    /**
     * 文物现状信息记录时间
     */
    @Expose
    private Date quoDate;

    /**
     * 文物保存环境
     */
    private String conserve;
    /**
     * 历次保护修复情况
     */
    private String repairCases;
    /**
     * 现状图片,多个图片以逗号分隔
     */
    private String quoPictures;
    /**
     * 备注
     */
    private String remark;

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

    public String getQuoInfo() {
        return quoInfo;
    }

    public void setQuoInfo(String quoInfo) {
        this.quoInfo = quoInfo;
    }

    public Date getQuoDate() {
        return quoDate;
    }

    public void setQuoDate(Date quoDate) {
        this.quoDate = quoDate;
    }

    public String getConserve() {
        return conserve;
    }

    public void setConserve(String conserve) {
        this.conserve = conserve;
    }

    public String getRepairCases() {
        return repairCases;
    }

    public void setRepairCases(String repairCases) {
        this.repairCases = repairCases;
    }

    public String getQuoPictures() {
        return quoPictures;
    }

    public void setQuoPictures(String quoPictures) {
        this.quoPictures = quoPictures;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
