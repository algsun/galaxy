package com.microwise.blueplanet.action.controlpanel;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.blueplanet.util.BPHttpApiClient;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.ResourceBundleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author xuyuexi
 * @date 14-2-25
 */
@Beans.Action
@Blueplanet
@Route("/blueplanet")
public class ControlPanelConfigConditionAction extends BlueplanetLoggerAction {
    private static final Logger logger = LoggerFactory.getLogger(ControlPanelConfigConditionAction.class);

    //input
    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 设备路数
     */
    private int route;

    /**
     * 子节点
     */
    private String subNodeId;

    /**
     * 监测指标ID
     */
    private int sensorId;

    /**
     * 低阈值
     */
    private int low;

    /**
     * 高阈值
     */
    private int high;


    /**
     * 阈值
     */
    private int threshold;

    /**
     * 动作
     */
    private int switchAction;

    @Route(value = "control-panel/{deviceId}/logic-refl")
    public String index() {
        try {
            BPHttpApiClient bpHttpApiClient = new BPHttpApiClient();
            Map<String, Boolean> map = null;
            if (switchAction == 2 || switchAction == 5) {
                map = bpHttpApiClient.configConditionRefl(deviceId, route, subNodeId, sensorId, low, high, switchAction);
            } else if (switchAction == 3 || switchAction == 4) {
                map = bpHttpApiClient.configConditionRefl(deviceId, route, subNodeId, sensorId, threshold, 0, switchAction);
            } else {
                map = bpHttpApiClient.configConditionRefl(deviceId, route, null, 0, 0, 0, switchAction);
            }
            if (map != null && map.get("success")) {
                if (map.get("sendSuccess")) {
                    if (map.get("doSuccess")) {
                        ActionMessage.createByAction().success("#" + route + ResourceBundleUtil.getBundle().getString("blueplanet.action.controlpanel.changedSuccess"));
                    } else {
                        ActionMessage.createByAction().fail("#" + route + ResourceBundleUtil.getBundle().getString("blueplanet.action.controlpanel.executionFailed"));
                    }
                } else {
                    ActionMessage.createByAction().fail("#" + route + ResourceBundleUtil.getBundle().getString("blueplanet.action.controlpanel.commandFails"));
                }
            } else {
                ActionMessage.createByAction().fail("#" + route + ResourceBundleUtil.getBundle().getString("blueplanet.action.controlpanel.changesFail"));
            }
            log("控制面板", "配置条件反射");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("控制面板配置条件反射失败", e);
        }
        return Results.redirect("/blueplanet/control-panel/" + deviceId + "/actions");
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getRoute() {
        return route;
    }

    public void setRoute(int route) {
        this.route = route;
    }

    public String getSubNodeId() {
        return subNodeId;
    }

    public void setSubNodeId(String subNodeId) {
        this.subNodeId = subNodeId;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public int getSwitchAction() {
        return switchAction;
    }

    public void setSwitchAction(int switchAction) {
        this.switchAction = switchAction;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
}
