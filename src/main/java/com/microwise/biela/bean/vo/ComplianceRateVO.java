package com.microwise.biela.bean.vo;

/**
 * 达标率vo
 *
 * @author liuzhu
 * @date 2014-12-17
 */
public class ComplianceRateVO {

    /**
     * 监测指标id
     */
    private int sensorId;

    /**
     * 达标率
     */
    private String comValue;

    /**
     * 波动值
     */
    private float waveValue;

    public String getComValue() {
        return comValue;
    }

    public void setComValue(String comValue) {
        this.comValue = comValue;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public float getWaveValue() {
        return waveValue;
    }

    public void setWaveValue(float waveValue) {
        this.waveValue = waveValue;
    }
}
