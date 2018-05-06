package com.microwise.blueplanet.bean.po;

import java.util.Map;

/**
 * 设备公式系数bean
 *
 * @author liuzhu
 * @date 15-1-12
 */
public class DeviceFormulaPO {

    /**
     * id
     */
    private String id;
    /**
     * 序列号
     */
    private String serialNumber;

    /**
     * 监测指标id
     */
    private int sensorId;

    /**
     * 公式名称
     */
    private String formulaName;

    /**
     * 监测指标名称
     */
    private String sensorName;

    /**
     * 公式系数
     */
    private Map<String, String> formulaParams;
    private boolean flag;

    public Map<String, String> getFormulaParams() {
        return formulaParams;
    }

    public void setFormulaParams(Map<String, String> formulaParams) {
        this.formulaParams = formulaParams;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public String getFormulaName() {
        return formulaName;
    }

    public void setFormulaName(String formulaName) {
        this.formulaName = formulaName;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
