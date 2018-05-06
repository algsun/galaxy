package com.microwise.blueplanet.action.device.manage;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.microwise.blueplanet.bean.po.Formula;
import com.microwise.blueplanet.bean.vo.DeviceVO;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.service.DeviceService;
import com.microwise.blueplanet.service.FormulaService;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.util.BPHttpApiClient;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.sys.freemarker.tools.LocaleBundleTools;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 设备详细信息
 *
 * @author gaohui
 * @date 13-1-24 14:3
 */
@Beans.Action
@Blueplanet
public class DeviceDetailAction {
    public static final String _pagePath = "device-detail.ftl";

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private FormulaService formulaService;

    //input, output
    /**
     * 设备Id
     */
    private String deviceId;
    //output
    /**
     * 设备
     */
    private DeviceVO device;

    /**
     * 与设备绑定位置点
     */
    private LocationVO location;

    /**
     * 监测指标
     */
    private List<SensorinfoVO> sensorinfoes;

    /**
     * 监测指标对应的默认公式
     * <p/>
     * sensorId => formula
     */
    private Map<Integer, Formula> formulaMap;
    /**
     * 设备自定义参数
     */
    private Map<Integer, Map<String, String>> customFormulaParamMap;
    /**
     * 木卫1显示蜂鸣器
     */
    boolean buzzerSwitch;

    /**
     * 设备详细信息
     */
    @Route("/blueplanet/device/{deviceId}/detail")
    public String show() {
        device = deviceService.findDeviceById(deviceId);
        location = locationService.findLocationByNodeId(deviceId);
        // 网关也可能有数据(GPS) @gaohui 2014-01-07
        // 只有传感器节点与从模块有监测指标
        if (device.getNodeType() == DeviceVO.DEVICE_TYPE_SENSOR
                || device.getNodeType() == DeviceVO.DEVICE_TYPE_SLAVE_MODULE
                || device.getNodeType() == DeviceVO.DEVICE_TYPE_GATEWAY
                ) {
            // 查询设备拥有的所有监测指标(包括激活与未激活)
            sensorinfoes = deviceService.findAllSensorinfo(deviceId,LocaleBundleTools.appLocale());
            if(device.getNodeType() == 1){
                buzzerSwitch = deviceService.isBuzzerSwitch(sensorinfoes);
            }
            List<Integer> sensorIds = Lists.transform(sensorinfoes, new Function<SensorinfoVO, Integer>() {
                @Override
                public Integer apply(SensorinfoVO sensorinfoVO) {
                    return sensorinfoVO.getSensorPhysicalid();
                }
            });

            formulaMap = formulaService.findBySensorIds(Ints.toArray(sensorIds));
            customFormulaParamMap = formulaService.findParamsByDeviceId(deviceId);
        }
        return Results.ftl("/blueplanet/pages/device/manage/device-manage-layout.ftl");
    }

    @Route("/blueplanet/device/buzzerSwitch")
    public String buzzerSwitch() {
        BPHttpApiClient client = new BPHttpApiClient();
        boolean result = client.buzzerSwitch(deviceId);
        return Results.json().asRoot(result).done();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public void setDevice(DeviceVO device) {
        this.device = device;
    }

    public DeviceVO getDevice() {
        return device;
    }

    public List<SensorinfoVO> getSensorinfoes() {
        return sensorinfoes;
    }

    public void setSensorinfoes(List<SensorinfoVO> sensorinfoes) {
        this.sensorinfoes = sensorinfoes;
    }

    public Map<Integer, Formula> getFormulaMap() {
        return formulaMap;
    }

    public void setFormulaMap(Map<Integer, Formula> formulaMap) {

        this.formulaMap = formulaMap;
    }

    public Map<Integer, Map<String, String>> getCustomFormulaParamMap() {
        return customFormulaParamMap;
    }

    public void setCustomFormulaParamMap(Map<Integer, Map<String, String>> customFormulaParamMap) {
        this.customFormulaParamMap = customFormulaParamMap;
    }

    public LocationVO getLocation() {
        return location;
    }

    public void setLocation(LocationVO location) {
        this.location = location;
    }

    public boolean isBuzzerSwitch() {
        return buzzerSwitch;
    }

    public void setBuzzerSwitch(boolean buzzerSwitch) {
        this.buzzerSwitch = buzzerSwitch;
    }
}
