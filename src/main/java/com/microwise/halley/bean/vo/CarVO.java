package com.microwise.halley.bean.vo;

import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.halley.bean.po.CarPO;
import com.microwise.halley.bean.po.DevicePO;

import java.util.List;
import java.util.Map;

/**
 * 查询车辆信息业务实体类，包括车辆基本信息与设备信息
 *
 * @author wanggeng
 * @date 13-9-26 下午1:23
 */
public class CarVO extends CarPO {

    /**
     * 摄像机信息
     */
    private List<DevicePO> devicePOList;

    /**
     * 位置点信息
     */
    private List<LocationVO> locationVOs;

    /**
     * 车的监测指标
     */
    private Map<String, String> sensorMap;

    /**
     * 报警范围
     */
    private int alarmRange;

    public List<DevicePO> getDevicePOList() {
        return devicePOList;
    }

    public void setDevicePOList(List<DevicePO> devicePOList) {
        this.devicePOList = devicePOList;
    }

    public List<LocationVO> getLocationVOs() {
        return locationVOs;
    }

    public void setLocationVOs(List<LocationVO> locationVOs) {
        this.locationVOs = locationVOs;
    }

    public Map<String, String> getSensorMap() {
        return sensorMap;
    }

    public void setSensorMap(Map<String, String> sensorMap) {
        this.sensorMap = sensorMap;
    }

    public int getAlarmRange() {
        return alarmRange;
    }

    public void setAlarmRange(int alarmRange) {
        this.alarmRange = alarmRange;
    }
}
