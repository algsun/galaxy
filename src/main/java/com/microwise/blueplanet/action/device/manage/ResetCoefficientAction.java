package com.microwise.blueplanet.action.device.manage;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.MethodType;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.service.FormulaService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.blueplanet.util.BPHttpApiClient;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.ActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 重置监测指标公式系数
 *
 * @author gaohui
 * @date 13-2-1 14:33
 * @check @wang.yunlong #1421 2013-02-04
 * @check @wang.yunlong #1477 2013-02-26
 */
@Beans.Action
@Blueplanet
public class ResetCoefficientAction extends BlueplanetLoggerAction {
    public static final Logger log = LoggerFactory.getLogger(ResetCoefficientAction.class);

    @Autowired
    private FormulaService formulaService;

    // input
    /**
     * 设备Id
     */
    private String deviceId;
    /**
     * 监测指标id
     */
    private int sensorPhysicalId;

    /**
     * 全部恢复默认公式系数
     */
    @Route(value = "/blueplanet/device/{deviceId}/sensorinfoes/reset-all.json")
    public String resetAllCoefficient() {
        Map<String, Object> jsonData = new HashMap<String, Object>();
        try {
            formulaService.deleteParamsByDeviceId(deviceId);
            BPHttpApiClient apiClient = new BPHttpApiClient();
            apiClient.evictCustomFormulaCache(deviceId);
            jsonData.put("success", true);
            log("设备管理", "全部恢复公式系数 设备ID: " + deviceId);
        } catch (Exception e) {
            log.error("恢复所有默认公式系数", e);
            jsonData.put("success", false);
        }
        ActionContext.getContext().put("data", jsonData);
        return Results.json().root("data").done();
    }


    /**
     * 恢复默认公式系数
     */
    @Route(value = "/blueplanet/device/{deviceId}/sensorinfo/{sensorPhysicalId}.json", params = {"reset"}, method = MethodType.GET)
    public String resetCoefficient() {
        Map<String, Object> jsonData = new HashMap<String, Object>();
        try {
            formulaService.deleteParamsByDeviceIdAndSensorId(deviceId, sensorPhysicalId);
            BPHttpApiClient apiClient = new BPHttpApiClient();
            apiClient.evictCustomFormulaCache(deviceId);
            jsonData.put("success", true);
            log("设备管理", "恢复义公式系数 设备ID: " + deviceId + ", 监测指标ID: " + sensorPhysicalId);
        } catch (Exception e) {
            jsonData.put("success", false);
            log.error("恢复默认公式系数", e);
        }
        ActionContext.getContext().put("data", jsonData);
        return Results.json().root("data").done();
    }


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getSensorPhysicalId() {
        return sensorPhysicalId;
    }

    public void setSensorPhysicalId(int sensorPhysicalId) {
        this.sensorPhysicalId = sensorPhysicalId;
    }
}
