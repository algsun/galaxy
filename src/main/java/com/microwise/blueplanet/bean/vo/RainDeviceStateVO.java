package com.microwise.blueplanet.bean.vo;

/**
 * @author xiedeng
 * @date 14-12-4
 */
public class RainDeviceStateVO {

    /**
     * 降雨状态:0代表无降雨，1代表有降雨
     */
    private Integer rainState;

    /**
     * 雨量筒状态:0代表已关盖，1代表已开盖，2代表错误，3代表正在开盖或关盖
     */
    private Integer rainGaugeState;

    public Integer getRainState() {
        return rainState;
    }

    public void setRainState(Integer rainState) {
        this.rainState = rainState;
    }

    public Integer getRainGaugeState() {
        return rainGaugeState;
    }

    public void setRainGaugeState(Integer rainGaugeState) {
        this.rainGaugeState = rainGaugeState;
    }
}
