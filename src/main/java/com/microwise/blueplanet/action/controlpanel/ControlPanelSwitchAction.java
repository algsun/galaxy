package com.microwise.blueplanet.action.controlpanel;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.blueplanet.util.BPHttpApiClient;
import com.microwise.common.sys.annotation.Beans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xuyuexi
 * @date 14-2-25
 */
@Beans.Action
@Blueplanet
@Route("/blueplanet")
public class ControlPanelSwitchAction extends BlueplanetLoggerAction {
    private static final Logger logger = LoggerFactory.getLogger(ControlPanelSwitchAction.class);

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
     * 开关
     */
    private boolean onOrOff;

    @Route(value = "control-panel/switch", params = {"deviceId", "route", "onOrOff"})
    public String index() {
        Map<String, Object> isSuccess = new HashMap<String, Object>();
        try {
            BPHttpApiClient bpHttpApiClient = new BPHttpApiClient();
            Map<String, Boolean> map = bpHttpApiClient.turnSwitch(deviceId, route, onOrOff);
            isSuccess.put("success", map);
            log("控制面板","操作开关");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("控制面板开关操作失败",e);
        }
        return Results.json().asRoot(isSuccess).done();
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

    public boolean isOnOrOff() {
        return onOrOff;
    }

    public void setOnOrOff(boolean onOrOff) {
        this.onOrOff = onOrOff;
    }
}
