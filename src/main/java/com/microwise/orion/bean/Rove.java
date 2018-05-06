package com.microwise.orion.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;

import java.util.Date;

/**
 * 流传经历
 *
 * @author xubaoji
 * @date 2013-5-25
 * @check 2013-06-04 zhangpeng svn:3736
 */
public class Rove {

    /**
     * 流传经历id
     */
    private Integer id;

    private Integer relicId;

    /**
     * 流传经历描述
     */
    @Expose
    private String roveInfo;

    /**
     * 流传日期
     */
    @Expose
    private Date roveDate;

    /**
     * 文物
     */
    @JsonIgnore
    private Relic relic;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoveInfo() {
        return roveInfo;
    }

    public void setRoveInfo(String roveInfo) {
        this.roveInfo = roveInfo;
    }

    public Date getRoveDate() {
        return roveDate;
    }

    public void setRoveDate(Date roveDate) {
        this.roveDate = roveDate;
    }

    public Relic getRelic() {
        return relic;
    }

    public void setRelic(Relic relic) {
        this.relic = relic;
    }

    public Integer getRelicId() {
        return relicId;
    }

    public void setRelicId(Integer relicId) {
        this.relicId = relicId;
    }
}
