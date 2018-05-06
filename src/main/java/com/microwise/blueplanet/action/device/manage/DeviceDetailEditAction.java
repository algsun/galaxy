package com.microwise.blueplanet.action.device.manage;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.MethodType;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.po.AirConditionerController;
import com.microwise.blueplanet.bean.po.FloatValue;
import com.microwise.blueplanet.bean.po.Formula;
import com.microwise.blueplanet.bean.po.HumidityControllerState;
import com.microwise.blueplanet.bean.vo.DevicePropertyVO;
import com.microwise.blueplanet.bean.vo.DeviceVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.service.DeviceService;
import com.microwise.blueplanet.service.FloatValueService;
import com.microwise.blueplanet.service.FormulaService;
import com.microwise.blueplanet.sys.AppCacheHolder;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.blueplanet.util.BPHttpApiClient;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.sys.freemarker.tools.LocaleBundleTools;
import com.microwise.halley.service.NodeInfoService;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备编辑
 *
 * @author gaohui
 * @date 13-1-24 14:35
 * @check @wang.yunlong #1397 2013-02-04
 * @check @wang.yunlong #1624 2013-02-26
 */
@Beans.Action
@Blueplanet
public class DeviceDetailEditAction extends BlueplanetLoggerAction {
    public static final Logger log = LoggerFactory.getLogger(DeviceDetailEditAction.class);

    // 内容页面
    public static final String _pagePath = "device-detail-edit.ftl";
    /**
     * 设备类型 0-其他 1-恒湿机 2-空调
     */
    private static final int DEVICE_TYPE_HUMIDITY = 1;
    private static final int DEVICE_TYPE_AIRCONDITIONER = 2;

    public static String URL = "";

    static {
        URL = ConfigFactory.getInstance().getConfig("config.properties").get("blueplanet.daemon.api.url");
    }

    @Autowired
    private AppCacheHolder appCacheHolder;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private FormulaService formulaService;
    @Autowired
    private FloatValueService floatValueService;
    @Autowired
    private NodeInfoService nodeInfoService;


    //input, output
    /**
     * 设备ID, nodeId 的别名
     */
    private String deviceId;

    /**
     * 设备类型, 不修改只是传递
     */
    private int deviceType;

    /**
     * 设备监测指标
     */
    private int sensorId;

    //output
    /**
     * 设备
     */
    private DeviceVO device;
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
     * 工作周期
     */
    private int interval;

    /**
     * 工作周期是否改变
     */
    private boolean isIntervalChange;
    /**
     * 电压阈值
     */
    private Float voltageThreshold;

    /**
     * 设备版本号
     */
    private int nodeVersion;

    /**
     * 是否可控
     */
    private boolean control;

    /**
     * 上限
     */
    private int maxValue;

    /**
     * 下限
     */
    private int minValue;

    //恒湿机类型
    private int humidityType;

    //目标湿度
    private float targetHumidity;
    //目标温度
    private float targetTemperature;
    //湿度高阈值
    private float highHumidity;
    //湿度低阈值
    private float lowHumidity;

    private float humidity;

    private float temperature;

    private Date createTime;

    private boolean isHumidityController;

    private boolean isAirConditionerController;

    private HumidityControllerState humidityControllerState;

    private boolean isHumCompensate;

    private int humCompensate;

    /**
     * 标定状态
     * <p/>
     * 0-非标定模式
     * 1-标定模式
     */
    private int demarcate;

    /**
     * 设备显示屏开关状态
     * <p/>
     * 0-关
     * 1-开
     */
    private int screenState;
    /**
     * 空调开关状态
     * 0-关
     * 1-开
     */
    private int switchState;

    /**
     * 上下限浮动默认值
     */
    private Map<Integer, FloatValue> floatValueMap;

    /**
     * 上下限浮动自定义值
     */
    private Map<Integer, FloatValue> customFloatValueMap;
    /**
     * 空调组
     */
    private AirConditionerController airConditionerController;

    /**
     * 设备编辑页面
     */
    @Route("/blueplanet/device/{deviceId}/detail/edit")
    public String show() {
        try {
            ActionMessage.createByAction().consume();
            isHumCompensate = false;
            device = deviceService.findDeviceById(deviceId);
            // 只有传感器节点与从模块有监测指标
//            if (device.getNodeType() == DeviceVO.DEVICE_TYPE_SENSOR || device.getNodeType() == DeviceVO.DEVICE_TYPE_MASTER_MODULE || device.getNodeType() == DeviceVO.DEVICE_TYPE_SLAVE_MODULE) {
            // 查询设备拥有的所有监测指标(包括激活与未激活)
            sensorinfoes = deviceService.findAllSensorinfo(deviceId, LocaleBundleTools.appLocale());
            List<Integer> sensorIds = Lists.transform(sensorinfoes, new Function<SensorinfoVO, Integer>() {
                @Override
                public Integer apply(SensorinfoVO sensorinfoVO) {
                    return sensorinfoVO.getSensorPhysicalid();
                }
            });

            isHumCompensate = sensorIds.contains(Constants.ChartConstants.SENSORINFO_HUM);
            formulaMap = formulaService.findBySensorIds(Ints.toArray(sensorIds));
            customFormulaParamMap = formulaService.findParamsByDeviceId(deviceId);
            //查询浮动值列表  按照map将列表组装成一个监测指标对应一个浮动值记录对象
            floatValueMap = floatValueService.findBySensorId(Ints.toArray(sensorIds));
            customFloatValueMap = floatValueService.findCustomByDeviceId(deviceId);

            DevicePropertyVO deviceProperty = deviceService.findLastContent(deviceId);
            if (deviceProperty != null && deviceProperty.getScreenState() != null) {
                device.setScreenState(deviceProperty.getScreenState());
            }
            //恒湿机
            if (deviceProperty != null && deviceProperty.getHumidityController() != null) {
                nodeInfoService.updateNodeInfo(deviceId, DEVICE_TYPE_HUMIDITY);
                isHumidityController = true;
                humidityType = deviceProperty.getHumidityController().getType();
                // 默认为致美恒湿机(兼容老数据)
                if (humidityType == 0) humidityType = 1;
                humidity = deviceProperty.getHumidityController().getCurrentHumidity();
                temperature = deviceProperty.getHumidityController().getCurrentTemperature();
                createTime = deviceProperty.getCreateTime();
                targetHumidity = (float) deviceProperty.getHumidityController().getTargetHumidity() / 10;
                highHumidity = (float) deviceProperty.getHumidityController().getHighHumidity() / 10;
                lowHumidity = (float) deviceProperty.getHumidityController().getLowHumidity() / 10;
                humidityControllerState = deviceProperty.getHumidityController().getHumidityControllerState();
            }
            //空调组
            if (deviceProperty != null && deviceProperty.getAirConditionerController() != null) {
                nodeInfoService.updateNodeInfo(deviceId, DEVICE_TYPE_AIRCONDITIONER);
                isAirConditionerController = true;
                airConditionerController = deviceProperty.getAirConditionerController();
            }
        } catch (Exception e) {
            log.error("查询设备详情失败", e);
        }
        return Results.ftl("/blueplanet/pages/device/manage/device-manage-layout.ftl");
    }

    @Route(value = "/blueplanet/device/{deviceId}/showHumidityController.json")
    public String showHumidityController() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            DevicePropertyVO deviceProperty = deviceService.findLastContent(deviceId);
            map.put("humidity", deviceProperty.getHumidityController().getCurrentHumidity());
            map.put("temperature", deviceProperty.getHumidityController().getCurrentTemperature());
            map.put("createTime", deviceProperty.getCreateTime());
            map.put("targetHumidity", deviceProperty.getHumidityController().getTargetHumidity());
            map.put("highHumidity", deviceProperty.getHumidityController().getHighHumidity());
            map.put("lowHumidity", deviceProperty.getHumidityController().getLowHumidity());
        } catch (Exception e) {
            log.error("恒湿机信息查询失败", e);
        }
        return Results.json().asRoot(map).done();
    }

    @Route(value = "/blueplanet/device/{deviceId}/setHumCompensate/{humCompensate}")
    public String setHumCompensate() {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean isSuccess = false;
        try {
            BPHttpApiClient client = new BPHttpApiClient();
            isSuccess = client.setHumCompensate(deviceId, humCompensate);
            map.put("success", isSuccess);
        } catch (Exception e) {
            map.put("success", false);
            log.error("湿度补偿设置失败", e);
        }
        return Results.json().asRoot(map).done();
    }

    @Route(value = "/blueplanet/device/{deviceId}/demarcate", params = "demarcate")
    public String setDemarcate() {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean isSuccess;
        try {
            BPHttpApiClient client = new BPHttpApiClient();
            isSuccess = client.setDemarcate(deviceId, demarcate == 1);
            map.put("success", isSuccess);
        } catch (Exception e) {
            map.put("success", false);
            log.error("标定模式设置失败", e);
        }
        return Results.json().asRoot(map).done();
    }

    @Route(value = "/blueplanet/device/{deviceId}/switchScreen", params = "screenState")
    public String switchScreen() {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean isSuccess;
        try {
            BPHttpApiClient client = new BPHttpApiClient();
            isSuccess = client.switchScreen(deviceId, screenState == 1);
            map.put("success", isSuccess);
        } catch (Exception e) {
            map.put("success", false);
            log.error("显示屏状态切换失败", e);
        }
        return Results.json().asRoot(map).done();
    }

    /**
     * 设置空调开关状态
     *
     * @return
     */
    @Route(value = "/blueplanet/devices/{deviceId}/setAirConditionerSwitchState", params = "switchState")
    public String setAirConditionerSwitchState() {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean isSuccess;
        try {
            BPHttpApiClient client = new BPHttpApiClient();
            isSuccess = client.setAirConditionerSwitchState(deviceId, switchState);
            map.put("success", isSuccess);
        } catch (Exception e) {
            map.put("success", false);
            log.error("空调组开关状态设置失败", e);
        }
        return Results.json().asRoot(map).done();
    }

    @Route(value = "/blueplanet/devices/{deviceId}/setAirConditionerHumidity")
    public String setAirConditionerHumidity() {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean isSuccess;
        try {
            BPHttpApiClient client = new BPHttpApiClient();
            isSuccess = client.setAirConditionerHumidity(deviceId, (int) (targetHumidity * 10));
            map.put("success", isSuccess);
        } catch (Exception e) {
            map.put("success", false);
            log.error("空调组目标湿度设置失败", e);
        }
        return Results.json().asRoot(map).done();
    }

    @Route(value = "/blueplanet/devices/{deviceId}/setAirConditionerTemperature")
    public String setAirConditionerTemperature() {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean isSuccess;
        try {
            BPHttpApiClient client = new BPHttpApiClient();
            isSuccess = client.setAirConditionerTemperature(deviceId, (int) (targetTemperature * 10));
            map.put("success", isSuccess);
        } catch (Exception e) {
            map.put("success", false);
            log.error("空调组目标温度设置失败", e);
        }
        return Results.json().asRoot(map).done();
    }

    @Route(value = "/blueplanet/device/{deviceId}/setHumidityController")
    public String setHumidityController() {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean isSuccess;
        try {
            BPHttpApiClient client = new BPHttpApiClient();
            isSuccess = client.setHumidityController(deviceId, (int) targetHumidity * 10, (int) highHumidity * 10, (int) lowHumidity * 10);
            map.put("success", isSuccess);
        } catch (Exception e) {
            map.put("success", false);
            log.error("恒湿机设置失败", e);
        }
        return Results.json().asRoot(map).done();
    }

    /**
     * 更新设备信息
     */
    @Route(value = "/blueplanet/device/{deviceId}/detail", method = MethodType.POST)
    public String update() {

        DeviceVO device = new DeviceVO();
        device.setNodeId(deviceId);
        device.setNodeType(deviceType);
        device.setInterval(interval);
        device.setVoltageThreshold(voltageThreshold);
        try {
            deviceService.updateDevice(device);
            if (isIntervalChange) {
                if (nodeVersion == Constants.PROCOTOL_VERSION_1) { // 1 表示v1.3的设备
                    modifyInterval(device);
                } else if (nodeVersion == Constants.PROCOTOL_VERSION_3 && (!control)) { // 3 表示v3的设备并且不可控
                    modifyInterval(device);
                }
            } else {
                BPHttpApiClient client = new BPHttpApiClient();
                client.evictDiviceCacheById(deviceId);
                ActionMessage.createByAction().success("修改成功");
            }
            appCacheHolder.evictDevice(deviceId);
            appCacheHolder.evictZoneDeviceTree(Sessions.createByAction().currentSiteId());
            log("设备管理", "修改设备 id: " + deviceId);
        } catch (Exception e) {
            ActionMessage.createByAction().fail("修改失败");
            log.error("修改失败", e);
        }

        // 重定向到编辑查看页面
        return Results.redirect(String.format("/blueplanet/device/%s/detail/edit", deviceId));
    }

    private Map<String, Object> modifyInterval(DeviceVO device) throws IOException, JSONException {
        BPHttpApiClient client = new BPHttpApiClient();
        Map<String, Object> result = client.modifyInterval(device.getNodeId(), device.getInterval());
        if ((Boolean) result.get("success")) {
            if ((Boolean) result.get("sendSuccess")) {
                deviceService.updateDeviceInterval(device);
                ActionMessage.createByAction().success("命令已发送至网关");
            } else {
                ActionMessage.createByAction().fail("命令发送至网关失败");
            }
        } else {
            ActionMessage.createByAction().fail("发送到中间件失败");
        }
        return result;
    }

    private Map<String, Object> availableParents(String deviceId) {
        BPHttpApiClient client = new BPHttpApiClient();
        Map<String, Object> result = client.availableParents(deviceId);
        return result;
    }

    @Route("/blueplanet/device/setSensorThreshold/{deviceId}")
    public String setSensorThreshold() {

        BPHttpApiClient client = new BPHttpApiClient();
        Map<String, Object> result = client.setSensorThreshold(deviceId, sensorId, maxValue, minValue);

        return Results.json().asRoot(result).done();
    }

    @Route("/blueplanet/device/{deviceId}/locate")
    public String locate() {
        BPHttpApiClient client = new BPHttpApiClient();
        Map<String, Object> result = client.locate(deviceId);
        return Results.json().asRoot(result).done();
    }

    /**
     * 设备重启
     * @return
     */
    @Route("/blueplanet/device/{deviceId}/reboot")
    public String reboot() {
        BPHttpApiClient client = new BPHttpApiClient();
        Map<String, Object> result = client.reboot(deviceId);
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

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getNodeVersion() {
        return nodeVersion;
    }

    public void setNodeVersion(int nodeVersion) {
        this.nodeVersion = nodeVersion;
    }

    public boolean isControl() {
        return control;
    }

    public void setControl(boolean control) {
        this.control = control;
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

    public float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }


    public boolean getIsHumidityController() {
        return isHumidityController;
    }

    public void setHumidityController(boolean isHumidityController) {
        this.isHumidityController = isHumidityController;
    }

    public int getHumidityType() {
        return humidityType;
    }

    public void setHumidityType(int humidityType) {
        this.humidityType = humidityType;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public float getLowHumidity() {
        return lowHumidity;
    }

    public void setLowHumidity(float lowHumidity) {
        this.lowHumidity = lowHumidity;
    }

    public float getHighHumidity() {
        return highHumidity;
    }

    public void setHighHumidity(float highHumidity) {
        this.highHumidity = highHumidity;
    }

    public float getTargetHumidity() {
        return targetHumidity;
    }

    public void setTargetHumidity(float targetHumidity) {
        this.targetHumidity = targetHumidity;
    }

    public HumidityControllerState getHumidityControllerState() {
        return humidityControllerState;
    }

    public void setHumidityControllerState(HumidityControllerState humidityControllerState) {
        this.humidityControllerState = humidityControllerState;
    }

    public boolean getIsHumCompensate() {
        return isHumCompensate;
    }

    public void setIsHumCompensate(boolean isHumCompensate) {
        this.isHumCompensate = isHumCompensate;
    }

    public int getHumCompensate() {
        return humCompensate;
    }

    public void setHumCompensate(int humCompensate) {
        this.humCompensate = humCompensate;
    }

    public int getDemarcate() {
        return demarcate;
    }

    public void setDemarcate(int demarcate) {
        this.demarcate = demarcate;
    }

    public int getScreenState() {
        return screenState;
    }

    public void setScreenState(int screenState) {
        this.screenState = screenState;
    }

    public Map<Integer, FloatValue> getFloatValueMap() {
        return floatValueMap;
    }

    public void setFloatValueMap(Map<Integer, FloatValue> floatValueMap) {
        this.floatValueMap = floatValueMap;
    }

    public Map<Integer, FloatValue> getCustomFloatValueMap() {
        return customFloatValueMap;
    }

    public void setCustomFloatValueMap(Map<Integer, FloatValue> customFloatValueMap) {
        this.customFloatValueMap = customFloatValueMap;
    }

    public Float getVoltageThreshold() {
        return voltageThreshold;
    }

    public void setVoltageThreshold(Float voltageThreshold) {
        this.voltageThreshold = voltageThreshold;
    }

    public boolean isIntervalChange() {
        return isIntervalChange;
    }

    public void setIsIntervalChange(boolean isIntervalChange) {
        this.isIntervalChange = isIntervalChange;
    }


    public boolean getIsAirConditionerController() {
        return isAirConditionerController;
    }

    public void setIsAirConditionerController(boolean isAirConditionerController) {
        isAirConditionerController = isAirConditionerController;
    }

    public AirConditionerController getAirConditionerController() {
        return airConditionerController;
    }

    public void setAirConditionerController(AirConditionerController airConditionerController) {
        this.airConditionerController = airConditionerController;
    }

    public float getTargetTemperature() {
        return targetTemperature;
    }

    public void setTargetTemperature(float targetTemperature) {
        this.targetTemperature = targetTemperature;
    }

    public int getSwitchState() {
        return switchState;
    }

    public void setSwitchState(int switchState) {
        this.switchState = switchState;
    }
}
