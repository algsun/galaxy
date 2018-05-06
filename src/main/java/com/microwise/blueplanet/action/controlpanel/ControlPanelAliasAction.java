package com.microwise.blueplanet.action.controlpanel;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.service.SwitchService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xuyuexi
 * @date 14-2-26
 */
@Beans.Action
@Blueplanet
@Route("/blueplanet")
public class ControlPanelAliasAction extends BlueplanetLoggerAction {
    private static final Logger logger = LoggerFactory.getLogger(ControlPanelAliasAction.class);

    @Autowired
    private SwitchService switchService;

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
     * 备注
     */
    private String alias;

    @Route("control-panel/{deviceId}/alias")
    public String updateAlias() {
        try {
            switchService.updateAlias(deviceId, route, alias);
            log("控制面板","更新备注");
        } catch (Exception e) {
            logger.error("控制面板备注更新失败",e);
        }
        return Results.redirect("/blueplanet/control-panel/" + deviceId);
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
