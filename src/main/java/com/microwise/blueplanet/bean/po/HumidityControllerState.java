package com.microwise.blueplanet.bean.po;

/**
 * @author xiedeng
 * @date 15-2-27
 */
public class HumidityControllerState {
    /**
     * 湿度报警
     * type=2 时有用
     */
    private boolean humidityAlarm;
    /**
     * 展柜湿度过高
     */
    private boolean humidityHigh;
    /**
     * 展柜湿度过低
     */
    private boolean humidityLow;
    /**
     * 缺水
     */
    private boolean waterLow;
    /**
     * 排水容器满
     */
    private boolean waterDrainageFull;
    /**
     * 温度报警
     * type=2 时有用
     */
    private boolean temperatureAlarm;
    /**
     * 环境温度过低
     */
    private boolean temperatureLow;
    /**
     * 湿度超限停机
     */
    private boolean outOfRangeCauseStop;
    /**
     * 外部风扇损坏
     */
    private boolean outFanBreakDown;
    /**
     * 循环风扇损坏
     */
    private boolean cycleFanBreakDown;
    /**
     * 无环境传感器
     */
    private boolean withoutSensor;
    /**
     * PTD温度过高
     */
    private boolean ptdTemperatureHigh;
    /**
     * PTD温度过低
     */
    private boolean ptdTemperatureLow;

    public boolean isTemperatureAlarm() {
        return temperatureAlarm;
    }

    public void setTemperatureAlarm(boolean temperatureAlarm) {
        this.temperatureAlarm = temperatureAlarm;
    }

    public boolean isHumidityAlarm() {
        return humidityAlarm;
    }

    public void setHumidityAlarm(boolean humidityAlarm) {
        this.humidityAlarm = humidityAlarm;
    }

    public boolean isHumidityHigh() {
        return humidityHigh;
    }

    public void setHumidityHigh(boolean humidityHigh) {
        this.humidityHigh = humidityHigh;
    }

    public boolean isHumidityLow() {
        return humidityLow;
    }

    public void setHumidityLow(boolean humidityLow) {
        this.humidityLow = humidityLow;
    }

    public boolean isWaterLow() {
        return waterLow;
    }

    public void setWaterLow(boolean waterLow) {
        this.waterLow = waterLow;
    }

    public boolean isWaterDrainageFull() {
        return waterDrainageFull;
    }

    public void setWaterDrainageFull(boolean waterDrainageFull) {
        this.waterDrainageFull = waterDrainageFull;
    }

    public boolean isTemperatureLow() {
        return temperatureLow;
    }

    public void setTemperatureLow(boolean temperatureLow) {
        this.temperatureLow = temperatureLow;
    }

    public boolean isOutOfRangeCauseStop() {
        return outOfRangeCauseStop;
    }

    public void setOutOfRangeCauseStop(boolean outOfRangeCauseStop) {
        this.outOfRangeCauseStop = outOfRangeCauseStop;
    }

    public boolean isOutFanBreakDown() {
        return outFanBreakDown;
    }

    public void setOutFanBreakDown(boolean outFanBreakDown) {
        this.outFanBreakDown = outFanBreakDown;
    }

    public boolean isCycleFanBreakDown() {
        return cycleFanBreakDown;
    }

    public void setCycleFanBreakDown(boolean cycleFanBreakDown) {
        this.cycleFanBreakDown = cycleFanBreakDown;
    }

    public boolean isWithoutSensor() {
        return withoutSensor;
    }

    public void setWithoutSensor(boolean withoutSensor) {
        this.withoutSensor = withoutSensor;
    }

    public boolean isPtdTemperatureHigh() {
        return ptdTemperatureHigh;
    }

    public void setPtdTemperatureHigh(boolean ptdTemperatureHigh) {
        this.ptdTemperatureHigh = ptdTemperatureHigh;
    }

    public boolean isPtdTemperatureLow() {
        return ptdTemperatureLow;
    }

    public void setPtdTemperatureLow(boolean ptdTemperatureLow) {
        this.ptdTemperatureLow = ptdTemperatureLow;
    }
}
