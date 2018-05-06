package com.microwise.blueplanet.bean.vo;

/**
 * @author  chenyaofei
 * @date 2016-08-26
 */
public class SensorUsedDataVO {
    /**
     * 监测指标名称
     */
    private String sensorName;

    /**
     * 监测指标被应用的次数
     */
    private int usedCount;

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public int getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(int usedCount) {
        this.usedCount = usedCount;
    }
}
