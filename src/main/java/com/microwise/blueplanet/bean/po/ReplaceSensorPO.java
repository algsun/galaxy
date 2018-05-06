package com.microwise.blueplanet.bean.po;

import java.util.Date;

/**
 * 换探头记录表
 */
public class ReplaceSensorPO {

    /**
     * id
     */
    private int id;

    /**
     * 位置点id
     */
    private String locationId;

    /**
     * 更换日期
     */
    private Date replaceDate;

    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 时间范围
     */
    private String stringDate;

    /**
     * 有机污染物差值和
     */
    private float OPSum;

    /**
     * 有机污染物等级
     */
    private String stringOPLevel;
    private String opLevel;

    /**
     * 有机污染物颜色
     */
    private String stringOPLevelColor;

    /**
     * 无机污染物差值和
     */
    private float IPSum;

    /**
     * 无机污染物
     */
    private String stringIPLevel;

    private String ipLevel;

    /**
     * 无机污染物颜色
     */
    private String stringIPLevelColor;

    /**
     * 含硫污染物差值和
     */
    private float SPSum;

    /**
     * 含硫污染物
     */
    private String stringSPLevel;

    private String spLevel;

    /**
     * 含硫污染物颜色
     */
    private String stringSPLevelCoLor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public Date getReplaceDate() {
        return replaceDate;
    }

    public void setReplaceDate(Date replaceDate) {
        this.replaceDate = replaceDate;
    }

    public String getStringDate() {
        return stringDate;
    }

    public void setStringDate(String stringDate) {
        this.stringDate = stringDate;
    }

    public String getStringOPLevel() {
        return stringOPLevel;
    }

    public void setStringOPLevel(String stringOPLevel) {
        this.stringOPLevel = stringOPLevel;
    }

    public String getStringOPLevelColor() {
        return stringOPLevelColor;
    }

    public void setStringOPLevelColor(String stringOPLevelColor) {
        this.stringOPLevelColor = stringOPLevelColor;
    }

    public String getStringIPLevel() {
        return stringIPLevel;
    }

    public void setStringIPLevel(String stringIPLevel) {
        this.stringIPLevel = stringIPLevel;
    }

    public String getStringIPLevelColor() {
        return stringIPLevelColor;
    }

    public void setStringIPLevelColor(String stringIPLevelColor) {
        this.stringIPLevelColor = stringIPLevelColor;
    }

    public String getStringSPLevel() {
        return stringSPLevel;
    }

    public void setStringSPLevel(String stringSPLevel) {
        this.stringSPLevel = stringSPLevel;
    }

    public String getStringSPLevelCoLor() {
        return stringSPLevelCoLor;
    }

    public void setStringSPLevelCoLor(String stringSPLevelCoLor) {
        this.stringSPLevelCoLor = stringSPLevelCoLor;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public float getOPSum() {
        return OPSum;
    }

    public void setOPSum(float OPSum) {
        this.OPSum = OPSum;
    }

    public float getIPSum() {
        return IPSum;
    }

    public void setIPSum(float IPSum) {
        this.IPSum = IPSum;
    }

    public float getSPSum() {
        return SPSum;
    }

    public void setSPSum(float SPSum) {
        this.SPSum = SPSum;
    }

    public String getOpLevel() {
        return opLevel;
    }

    public void setOpLevel(String opLevel) {
        this.opLevel = opLevel;
    }

    public String getIpLevel() {
        return ipLevel;
    }

    public void setIpLevel(String ipLevel) {
        this.ipLevel = ipLevel;
    }

    public String getSpLevel() {
        return spLevel;
    }

    public void setSpLevel(String spLevel) {
        this.spLevel = spLevel;
    }
}
