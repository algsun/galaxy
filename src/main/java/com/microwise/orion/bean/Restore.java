package com.microwise.orion.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;

import java.util.Date;

/**
 * 文物修复记录实体对象
 *
 * @author xubaoji
 * @date 2013-5-13
 * @check 2013-06-04 zhangpeng svn:3448
 */
public class Restore {

    /**
     * 文物修复记录 id 编号
     */
    private Integer id;

    /**
     * 文物信息实体
     */
    @JsonIgnore
    private Relic relic;

    /**
     * 文物修复人/修复单位
     */
    @Expose
    private String restorers;

    /**
     * 文物修复日期
     */
    @Expose
    private Date restoreDate;

    /**
     * 文物修复信息
     */
    @Expose
    private String restoreInfo;

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

    public String getRestorers() {
        return restorers;
    }

    public void setRestorers(String restorers) {
        this.restorers = restorers;
    }

    public Date getRestoreDate() {
        return restoreDate;
    }

    public void setRestoreDate(Date restoreDate) {
        this.restoreDate = restoreDate;
    }

    public String getRestoreInfo() {
        return restoreInfo;
    }

    public void setRestoreInfo(String restoreInfo) {
        this.restoreInfo = restoreInfo;
    }

}
