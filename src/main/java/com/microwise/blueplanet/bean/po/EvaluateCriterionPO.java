package com.microwise.blueplanet.bean.po;

/**
 * Created by warriorsliuzhu on 16/1/8.
 */
public class EvaluateCriterionPO {

    /**
     * 自增id
     */
    private int id;

    /**
     * 空气质量
     */
    private String airQuality;

    /**
     * 有机污染物等级
     */
    private String opLevel;

    /**
     * 有机污染物范围
     */
    private float opRange;

    /**
     * 无极污染物等级
     */
    private String ipLevel;

    /**
     * 有机污染物范围
     */
    private float ipRange;

    /**
     * 含硫污染物等级
     */
    private String spLevel;

    /**
     * 含硫污染物范围
     */
    private float spRange;

    /**
     * 颜色
     */
    private String color;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAirQuality() {
        return airQuality;
    }

    public void setAirQuality(String airQuality) {
        this.airQuality = airQuality;
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

    public float getOpRange() {
        return opRange;
    }

    public void setOpRange(float opRange) {
        this.opRange = opRange;
    }

    public float getIpRange() {
        return ipRange;
    }

    public void setIpRange(float ipRange) {
        this.ipRange = ipRange;
    }

    public float getSpRange() {
        return spRange;
    }

    public void setSpRange(float spRange) {
        this.spRange = spRange;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
