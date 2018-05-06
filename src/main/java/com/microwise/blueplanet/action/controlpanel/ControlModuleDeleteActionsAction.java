package com.microwise.blueplanet.action.controlpanel;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.po.SwitchAction;
import com.microwise.blueplanet.service.ActionService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.blueplanet.util.BPHttpApiClient;
import com.microwise.common.sys.annotation.Beans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xuyuexi
 * @date 14-3-10
 */
@Beans.Action
@Blueplanet
@Route("/blueplanet")
public class ControlModuleDeleteActionsAction extends BlueplanetLoggerAction {
    private static final Logger logger = LoggerFactory.getLogger(ControlModuleDeleteActionsAction.class);

    @Autowired
    private ActionService actionService;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 设备id
     */
    private String actionId;

    /**
     * 添加动作的路数
     */
    private int type;

    @Route("control-panel/{actionId}/deleteActions")
    public String actions() {
        Map<String, Object> success = new HashMap<String, Object>();
        try {
            SwitchAction switchAction = null;
            if (type == SwitchAction.TYPE_DAILY) {
                switchAction = actionService.findDailyActionById(actionId);
                actionService.deleteDailyAction(actionId);
            } else if (type == SwitchAction.TYPE_INTERVAL) {
                switchAction = actionService.findIntervalActionById(actionId);
                actionService.deleteIntervalActionById(actionId);
            }
            success.put("success", true);
            //发命令,动作改变
            BPHttpApiClient bpHttpApiClient=new BPHttpApiClient();
            bpHttpApiClient.actionChanged(deviceId, switchAction.getType(), switchAction.getId());
            log("控制面板","删除动作");
        } catch (Exception e) {
            e.printStackTrace();
            success.put("success", false);
            logger.error("控制面板删除动作失败",e);
        }
        return Results.json().asRoot(success).done();
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
