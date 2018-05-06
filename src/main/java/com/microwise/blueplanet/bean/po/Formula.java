package com.microwise.blueplanet.bean.po;

import java.util.Map;

/**
 * 公式
 * @author gaohui
 * @date 13-12-31 14:16
 */
public class Formula {

    // 无范围限制
    public static final int RANG_TYPE_ANY = 0;
    // 只有最小值限制
    public static final int RANG_TYPE_MIN  = 1;
    // 只有最大值限制性
    public static final int RANG_TYPE_MAX  = 2;
    // 有最小值，最大值限制
    public static final int RANG_TYPE_BOTH  = 3;

    // 公式id
    private int id;

    // 公式名称
    private String formulaName;

    // 监测指标标识
    private int sensorId;

    // 设备ID
    private String deviceId;

    // x 最小取值范围
    private int minX;

    // x 最大取值范围
    private int maxX;

    // x 范围类型
    private int xRangeType;

    // y 最小取值范围
    private int minY;

    // y 最小取值范围
    private int maxY;

    // y 范围类型
    private int yRangeType;

    // 原始值符号类型
    private int signType;

    // 公式系数个数
    private int paramCount;

    // 公式系数
    private Map<String, String> formulaParams;

    /**
     * 是否有最小值限制
     *
     * @return
     */
    public boolean isXMinEnable(){
        return xRangeType == RANG_TYPE_MIN || xRangeType == RANG_TYPE_BOTH;
    }

    /**
     * 是否有最大值限制
     *
     * @return
     */
    public boolean isXMaxEnable(){
        return xRangeType == RANG_TYPE_MAX || xRangeType == RANG_TYPE_BOTH;
    }

    /**
     * 是否有最小值限制
     *
     * @return
     */
    public boolean isYMinEnable(){
        return yRangeType == RANG_TYPE_MIN || yRangeType == RANG_TYPE_BOTH;
    }

    /**
     * 是否有最大值限制
     *
     * @return
     */
    public boolean isYMaxEnable(){
        return yRangeType == RANG_TYPE_MAX || yRangeType == RANG_TYPE_BOTH;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFormulaName() {
        return formulaName;
    }

    public void setFormulaName(String formulaName) {
        this.formulaName = formulaName;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getMinX() {
        return minX;
    }

    public void setMinX(int minX) {
        this.minX = minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getxRangeType() {
        return xRangeType;
    }

    public void setxRangeType(int xRangeType) {
        this.xRangeType = xRangeType;
    }

    public int getMinY() {
        return minY;
    }

    public void setMinY(int minY) {
        this.minY = minY;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public int getyRangeType() {
        return yRangeType;
    }

    public void setyRangeType(int yRangeType) {
        this.yRangeType = yRangeType;
    }

    public int getSignType() {
        return signType;
    }

    public void setSignType(int signType) {
        this.signType = signType;
    }

    public int getParamCount() {
        return paramCount;
    }

    public void setParamCount(int paramCount) {
        this.paramCount = paramCount;
    }

    public Map<String, String> getFormulaParams() {
        return formulaParams;
    }

    public void setFormulaParams(Map<String, String> formulaParams) {
        this.formulaParams = formulaParams;
    }

    @Override
    public String toString() {
        return "Formula{" +
                "id=" + id +
                ", formulaName='" + formulaName + '\'' +
                ", sensorId=" + sensorId +
                ", deviceId='" + deviceId + '\'' +
                ", minX=" + minX +
                ", maxX=" + maxX +
                ", xRangeType=" + xRangeType +
                ", minY=" + minY +
                ", maxY=" + maxY +
                ", yRangeType=" + yRangeType +
                ", signType=" + signType +
                ", paramCount=" + paramCount +
                ", formulaParams=" + formulaParams +
                '}';
    }
}
