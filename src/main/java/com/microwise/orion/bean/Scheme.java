package com.microwise.orion.bean;

import java.util.Date;

/**
 * @author 王耕
 * @date 15-9-9
 */
public class Scheme {

    /**
     * 主键，自增ID
     */
    private int id;
    /**
     * 方案编号
     */
    private String schemeId;
    /**
     * 方案名称
     */
    private String name;
    /**
     * 设计单位
     */
    private Institution institution;
    /**
     * 批准时间
     */
    private Date confirmTime;
    /**
     * 批准文号
     */
    private String confirmNum;

    private String siteId;

    public Scheme() {

    }

    public Scheme(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getConfirmNum() {
        return confirmNum;
    }

    public void setConfirmNum(String confirmNum) {
        this.confirmNum = confirmNum;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }
}
