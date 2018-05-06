package com.microwise.orion.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;

import java.util.Date;

/**
 * 文物事故记录信息实体
 *
 * @author xubaoji
 * @date 2013-5-14
 * @check 2013-06-04 zhangpeng svn:3448
 */
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Accident {

    /**
     * 事故记录 id 编号
     */
    private Integer id;

    /**
     * 文物基本信息实体
     */
    @JsonIgnore
    private Relic relic;

    /**
     * 事故记录信息
     */
    @Expose
    private String accidentInfo;

    /**
     * 事故记录时间
     */
    @Expose
    private Date accidentDate;

    public Accident() {
        super();
    }

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

    public String getAccidentInfo() {
        return accidentInfo;
    }

    public void setAccidentInfo(String accidentInfo) {
        this.accidentInfo = accidentInfo;
    }

    public Date getAccidentDate() {
        return accidentDate;
    }

    public void setAccidentDate(Date accidentDate) {
        this.accidentDate = accidentDate;
    }

}
