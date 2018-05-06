package com.microwise.blueplanet.action.device.manage;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.MethodType;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.po.DeviceFormulaPO;
import com.microwise.blueplanet.bean.po.Formula;
import com.microwise.blueplanet.bean.vo.DeviceVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.service.DeviceService;
import com.microwise.blueplanet.service.FormulaService;
import com.microwise.blueplanet.service.SensorinfoService;
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
 * 保存或者修改设备的公式系数
 *
 * @author gaohui
 * @date 13-2-1 14:07
 * @check @wang.yunlong #1421 2013-02-04
 * @check @wang.yunlong #1477 2013-02-26
 */
@Beans.Action
@Blueplanet
public class SaveOrUpdateCoefficientAction extends BlueplanetLoggerAction {
    public static final Logger log = LoggerFactory.getLogger(SaveOrUpdateCoefficientAction.class);

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private FormulaService formulaService;

    @Autowired
    private SensorinfoService sensorinfoService;

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
     * 自定义公式系数
     */
    private Map<String, String> params;


    @Route(value = "/blueplanet/device/{deviceId}/sensorinfo/{sensorPhysicalId}.json", method = MethodType.GET)
    public String saveOrUpdate() {
        Map<String, Object> jsonData = new HashMap<String, Object>();
        try {
            jsonData.put("success", true);
            formulaService.saveOrUpdateParamsByDeviceId(deviceId, sensorPhysicalId, params);
            BPHttpApiClient apiClient = new BPHttpApiClient();
            apiClient.evictCustomFormulaCache(deviceId);

//            //上传设备公式
//            uploadDeviceFormulaToOnline(apiClient);

            log("设备管理", "自定义公式系数 设备ID: " + deviceId + ", 监测指标ID: " + sensorPhysicalId);
        } catch (Exception e) {
            jsonData.put("success", false);
            log.error("自定义监测指标公式系数", e);
        }
        ActionContext.getContext().put("data", jsonData);
        return Results.json().root("data").done();
    }

    /**
     * 上传设备公式方式
     *
     * @param apiClient httpClient对象
     */
    private void uploadDeviceFormulaToOnline(BPHttpApiClient apiClient) {
        DeviceVO deviceVO = deviceService.findDeviceById(deviceId);
        if(!"0".equals(deviceVO.getSn())){
            SensorinfoVO sensorinfoVO = sensorinfoService.findByPhysicalid(sensorPhysicalId);
            Map<Integer, Map<String, String>> mapMap = formulaService.findParamsByDeviceId(deviceId);
            Map<String, String> map = new HashMap<String, String>();
            Formula formula = formulaService.findBySensorId(sensorPhysicalId);
            DeviceFormulaPO deviceFormula = new DeviceFormulaPO();
            deviceFormula.setSerialNumber(deviceVO.getSn());
            deviceFormula.setSensorName(sensorinfoVO.getCnName());
            deviceFormula.setSensorId(sensorinfoVO.getSensorPhysicalid());
            deviceFormula.setFormulaName(formula.getFormulaName());
            map.putAll(mapMap.get(sensorPhysicalId));
            deviceFormula.setFormulaParams(map);

            apiClient.uploadDeviceFormula(deviceVO.getSn(), deviceFormula);
        }
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

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
