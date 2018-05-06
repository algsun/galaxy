package com.microwise.biela.bean.vo;

/**
 * 达标率，波动值vo
 */
public class MixtureVO {

    /**
     * 监测指标id
     */
    private int sensorId;

    /**
     * 达标率
     */
    private float comRate;

    /**
     * 波动值
     */
    private float waveValue;

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public float getComRate() {
        return comRate;
    }

    public void setComRate(float comRate) {
        this.comRate = comRate;
    }

    public float getWaveValue() {
        return waveValue;
    }

    public void setWaveValue(float waveValue) {
        this.waveValue = waveValue;
    }
}
