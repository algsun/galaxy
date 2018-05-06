package com.microwise.orion.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;

import java.util.Date;

/**
 * 文物拓片 实体对象
 *
 * @author xubaoji
 * @date 2013-5-13
 * @check 2013-06-04 zhangpeng svn:3448
 */
public class Rubbing {

    /**
     * 类型标识
     */
    public static final String RUBBING = "rubbing";

    /**
     * 拓片 id 编号
     */
    private Integer id;

    /**
     * 文物信息实体
     */
    @JsonIgnore
    private Relic relic;

    /**
     * 拓片 编号
     */
    @Expose
    private String rubbingCode;

    /**
     * 拓片制作人
     */
    @Expose
    private String producer;

    /**
     * 拓片 制作日期
     */
    @Expose
    private Date rubbingDate;

    /**
     * 拓片比例
     */
    @Expose
    private String ratio;

    /**
     * 拓片存放路径
     */
    @Expose
    private String path;

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

    public String getRubbingCode() {
        return rubbingCode;
    }

    public void setRubbingCode(String rubbingCode) {
        this.rubbingCode = rubbingCode;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public Date getRubbingDate() {
        return rubbingDate;
    }

    public void setRubbingDate(Date rubbingDate) {
        this.rubbingDate = rubbingDate;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
