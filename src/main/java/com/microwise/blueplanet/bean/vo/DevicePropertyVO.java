package com.microwise.blueplanet.bean.vo;

import com.microwise.blueplanet.bean.po.AirConditionerController;
import com.microwise.blueplanet.bean.po.HumidityController;
import com.microwise.blueplanet.bean.po.LocationPO;

import java.util.Date;
import java.util.List;

/**
 * @author xiedeng
 * @date 14-12-4
 */
public class DevicePropertyVO {

    /**
     * 降雨状态:0代表无降雨，1代表有降雨
     */
    private Integer rainState;

    /**
     * 雨量筒状态:0代表已关盖，1代表已开盖，2代表错误，3代表正在开盖或关盖
     */
    private Integer rainGaugeState;

    private HumidityController humidityController;

    private AirConditionerController airConditionerController;

    private Date createTime;

    private List<DevicePropertyVO> devicePropertyVO;

    private String deviceId;

    private LocationPO location;

    private String faultCode;

    /**
     * 设备屏幕开关状态
     * <p>
     * 0-关
     * 1-开
     */
    private Integer screenState;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public HumidityController getHumidityController() {
        return humidityController;
    }

    public void setHumidityController(HumidityController humidityController) {
        this.humidityController = humidityController;
    }

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

    public List<DevicePropertyVO> getDevicePropertyVO() {
        return devicePropertyVO;
    }

    public void setDevicePropertyVO(List<DevicePropertyVO> devicePropertyVO) {
        this.devicePropertyVO = devicePropertyVO;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getScreenState() {
        return screenState;
    }

    public void setScreenState(Integer screenState) {
        this.screenState = screenState;
    }

    public AirConditionerController getAirConditionerController() {
        return airConditionerController;
    }

    public void setAirConditionerController(AirConditionerController airConditionerController) {
        this.airConditionerController = airConditionerController;
    }

    public LocationPO getLocation() {
        return location;
    }

    public void setLocation(LocationPO location) {
        this.location = location;
    }

    public String getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }
}
