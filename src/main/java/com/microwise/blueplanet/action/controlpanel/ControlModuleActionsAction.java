package com.microwise.blueplanet.action.controlpanel;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.po.*;
import com.microwise.blueplanet.bean.vo.ConditionActionVO;
import com.microwise.blueplanet.bean.vo.DeviceVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.bean.vo.SwitchActionVO;
import com.microwise.blueplanet.service.*;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.blueplanet.util.BPHttpApiClient;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.sys.freemarker.tools.LocaleBundleTools;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gaohui
 * @date 14-2-18 16:18
 */
@Beans.Action
@Blueplanet
@Route("/blueplanet")
public class ControlModuleActionsAction extends BlueplanetLoggerAction {
    private static final Logger logger = LoggerFactory.getLogger(ControlModuleActionsAction.class);

    public static final String _pagePath = "control-module-actions.ftl";

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private SensorinfoService sensorinfoService;
    @Autowired
    private ConditionReflService conditionReflService;
    @Autowired
    private SwitchService switchService;
    @Autowired
    private ActionService actionService;
    @Autowired
    private SiteService siteService;

    private TemplateHashModel statics = BeansWrapper.getDefaultInstance().getStaticModels();

    //input
    private String deviceId;
    private int sensorId;
    private double originValue;
    //output
    private DeviceVO device;
    private Switches switches;
    private List<ConditionRefl> conditionRefls;
    /**
     * 设备下的子节点
     */
    private List<DeviceVO> deviceVOs;

    /**
     * 站点下的所有设备
     */
    private List<DeviceVO> allDevices;

    /**
     * 子设备的检测指标
     */
    private List<SensorinfoVO> sensorinfoVOs;


    /**
     * 所有检测指标
     */
    private List<SensorinfoVO> allSensorinfoVOs;

    /**
     * 动作
     */
    private Map<Integer, SwitchActionVO> switchActionMap;

    /**
     * 没有时间动作的路数
     */
    private List<Switch> switchList;

    /**
     * 条件动作
     */
    private Map<Integer, ConditionActionVO> conditionActionVOMap;

    @Route("control-panel/{deviceId}/actions")
    public String actions() {
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            device = deviceService.findDeviceById(deviceId);
            switches = switchService.findLastSwitchByDeviceId(deviceId);
            // 查询条件反射 @gaohui 2014-02-18
            conditionRefls = conditionReflService.findByDeviceId(deviceId);
            deviceVOs = deviceService.findSubDevicesByDeviceId(deviceId, LocaleBundleTools.appLocale());
            //查询动作 @xuyuexi 2014-03-7
            switchActionMap = findSwitchActionMap(deviceId);
            //查询条件动作
            conditionActionVOMap = findConditionActionMap(deviceId);
            //添加条件动作，查询站点下所有的设备
            //修复bug2457过滤没有检测指标的设备
            allDevices = getDevice(siteService.findAllDevicesBySiteId(siteId));
            //没有时间动作的路数
            switchList = findSwitchListWithoutAction(deviceId);
            //系统所有监测指标
            allSensorinfoVOs = sensorinfoService.findSensorinfo();
            ActionMessage.createByAction().consume();
            log("控制面板", "控制面板显示");
        } catch (Exception e) {
            logger.error("控制面板显示失败", e);
        }

        return Results.ftl("/blueplanet/pages/controlpanel/layout");
    }

    @Route("control-panel/{deviceId}/sensors")
    public String getSensors() {
        Map<String, Object> success = new HashMap<String, Object>();
        try {
            //设备所有监测指标不论激活与否
            sensorinfoVOs = deviceService.findAllSensorinfo(deviceId,LocaleBundleTools.appLocale());
            success.put("success", true);
            success.put("sensorinfo", sensorinfoVOs);
            log("控制面板", "检测指标联动");
        } catch (Exception e) {
            success.put("success", false);
        }
        return Results.json().asRoot(success).done();
    }

    ;

    //所有检测指标
    @Route("control-panel/{deviceId}/allSensors")
    public String getAllSensors() {
        Map<String, Object> success = new HashMap<String, Object>();
        try {
            //系统所有监测指标
            allSensorinfoVOs = sensorinfoService.findSensorinfo();
            success.put("success", true);
            success.put("allSensorinfoVOs", allSensorinfoVOs);
            log("控制面板", "检测指标联动");
        } catch (Exception e) {
            success.put("success", false);
        }
        return Results.json().asRoot(success).done();
    }

    ;

    //计算原始值
    @Route("control-panel/{deviceId}/originValue")
    public String originValue() {
        Map<String, Object> originMap = new HashMap<String, Object>();
        try {
            Map originMaps = null;
            BPHttpApiClient bpHttpApiClient = new BPHttpApiClient();
            if ("-2".equals(deviceId)) {
                originMaps = bpHttpApiClient.getOriginValue(sensorId, originValue);
            } else {
                originMaps = bpHttpApiClient.getOriginValue(deviceId, sensorId, originValue);
            }
            originMap.put("success", true);
            originMap.putAll(originMaps);
            log("控制面板", "计算原始值");
        } catch (Exception e) {
            originMap.put("success", false);
            logger.error("计算原始值", e);
        }
        return Results.json().asRoot(originMap).done();
    }

    ;

    //查询设备的所有条件动作  2014.3.26
    private Map<Integer, ConditionActionVO> findConditionActionMap(String deviceId) {
        Map<Integer, ConditionActionVO> conditionActionVOMap = new HashMap<Integer, ConditionActionVO>();
        List<Switch> switchList = findSwitchListWithoutAction(deviceId);
        for (Switch s : switchList) {
            ConditionActionVO conditionActionVO = actionService.findSensorAction(deviceId, s.getRoute());
            conditionActionVOMap.put(s.getRoute(), conditionActionVO);
        }
        return conditionActionVOMap;
    }

    //查询设备的所有动作
    private Map<Integer, SwitchActionVO> findSwitchActionMap(String deviceId) {
        // 返回最后一次控制模块的开关状态
        Switches switches = switchService.findLastSwitchByDeviceId(deviceId);
        //那到所有可用的开关
        List<Switch> switchesValues = switches.getValues();
        //以路数为key的Map
        Map<Integer, SwitchActionVO> stringSwitchActionVOMap = new HashMap<Integer, SwitchActionVO>();
        //遍历可用开关集合
        for (Switch s : switchesValues) {
            SwitchActionVO switchActionVO = new SwitchActionVO();
            //查询定时动作列表
            List<SwitchDailyAction> switchDailyActions = actionService.findDailyActions(deviceId, s.getRoute());
            //查询间隔动作，间隔动作只会有一个
            SwitchIntervalAction switchIntervalAction = actionService.findIntervalActions(deviceId, s.getRoute());
            //如果没有定时动作和间隔动作，那么跳出本次循环
            if (switchDailyActions.size() == 0 && switchIntervalAction == null) {
                continue;
            }
            switchActionVO.setDeviceId(deviceId);
            switchActionVO.setRoute(s.getRoute());
            switchActionVO.setSwitchIntervalAction(switchIntervalAction);
            switchActionVO.setSwitchDailyActions(switchDailyActions);
            stringSwitchActionVOMap.put(s.getRoute(), switchActionVO);
        }
        return stringSwitchActionVOMap;
    }

    //查询没有时间动作的路数
    private List<Switch> findSwitchListWithoutAction(String deviceId) {
        List<Switch> switchList = new ArrayList<Switch>();
        // 返回最后一次控制模块的开关状态
        Switches switches = switchService.findLastSwitchByDeviceId(deviceId);
        //那到所有可用的开关
        List<Switch> switchesValues = switches.getValues();
        for (Switch s : switchesValues) {
            if (s.isEnable()) {
                //如果没有定时动作和间隔动作，那么跳出本次循环
                if (actionService.findDailyActions(deviceId, s.getRoute()).size() == 0
                        && actionService.findIntervalActions(deviceId, s.getRoute()) == null) {
                    switchList.add(s);
                }
            }
        }
        return switchList;
    }

    private List<DeviceVO> getDevice(List<DeviceVO> allDevices) {
        List<DeviceVO> deviceVOs1 = new ArrayList<DeviceVO>();
        for (DeviceVO deviceVO : allDevices) {
            if (deviceVO.getNodeType() == 1) {
                deviceVOs1.add(deviceVO);
            }
        }
        return deviceVOs1;
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public DeviceVO getDevice() {
        return device;
    }

    public void setDevice(DeviceVO device) {
        this.device = device;
    }

    public List<ConditionRefl> getConditionRefls() {
        return conditionRefls;
    }

    public void setConditionRefls(List<ConditionRefl> conditionRefls) {
        this.conditionRefls = conditionRefls;
    }

    public Switches getSwitches() {
        return switches;
    }

    public void setSwitches(Switches switches) {
        this.switches = switches;
    }

    public TemplateHashModel getStatics() {
        return statics;
    }

    public List<DeviceVO> getDeviceVOs() {
        return deviceVOs;
    }

    public void setDeviceVOs(List<DeviceVO> deviceVOs) {
        this.deviceVOs = deviceVOs;
    }

    public List<SensorinfoVO> getSensorinfoVOs() {
        return sensorinfoVOs;
    }

    public void setSensorinfoVOs(List<SensorinfoVO> sensorinfoVOs) {
        this.sensorinfoVOs = sensorinfoVOs;
    }

    public Map<Integer, SwitchActionVO> getSwitchActionMap() {
        return switchActionMap;
    }

    public void setSwitchActionMap(Map<Integer, SwitchActionVO> switchActionMap) {
        this.switchActionMap = switchActionMap;
    }

    public List<DeviceVO> getAllDevices() {
        return allDevices;
    }

    public void setAllDevices(List<DeviceVO> allDevices) {
        this.allDevices = allDevices;
    }

    public List<Switch> getSwitchList() {
        return switchList;
    }

    public void setSwitchList(List<Switch> switchList) {
        this.switchList = switchList;
    }

    public Map<Integer, ConditionActionVO> getConditionActionVOMap() {
        return conditionActionVOMap;
    }

    public void setConditionActionVOMap(Map<Integer, ConditionActionVO> conditionActionVOMap) {
        this.conditionActionVOMap = conditionActionVOMap;
    }

    public List<SensorinfoVO> getAllSensorinfoVOs() {
        return allSensorinfoVOs;
    }

    public void setAllSensorinfoVOs(List<SensorinfoVO> allSensorinfoVOs) {
        this.allSensorinfoVOs = allSensorinfoVOs;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public double getOriginValue() {
        return originValue;
    }

    public void setOriginValue(double originValue) {
        this.originValue = originValue;
    }


}
