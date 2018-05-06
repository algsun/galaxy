package com.microwise.blueplanet.action.controlpanel;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blueplanet.bean.po.SwitchAction;
import com.microwise.blueplanet.bean.po.SwitchDailyAction;
import com.microwise.blueplanet.bean.po.SwitchIntervalAction;
import com.microwise.blueplanet.service.ActionService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.blueplanet.util.BPHttpApiClient;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.ResourceBundleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuyuexi
 * @date 14-3-7
 */
@Beans.Action
@Blueplanet
@Route("/blueplanet")
public class ControlModuleAddActionsAction extends BlueplanetLoggerAction {
    private static final Logger logger = LoggerFactory.getLogger(ControlModuleAddActionsAction.class);

    @Autowired
    private ActionService actionService;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 添加动作的路数
     */
    private int actionRoute;

    /**
     * 添加动作类型，1为每日定时开关，2间隔时间开关
     */
    private int type;

    /**
     * 每日定时的时间
     */
    private Date daily;

    /**
     * 间隔时间
     */
    private int intervalTime;

    /**
     * 执行时间
     */
    private int executionTime;

    /**
     * 动作1开0关
     */
    private int action;

    @Route("control-panel/{deviceId}/addActions")
    public String actions() {
        try {
            SwitchAction switchAction = null;
            //type为1，则是定时动作
            if (type == SwitchAction.TYPE_DAILY) {
                SwitchDailyAction switchDailyAction = new SwitchDailyAction();
                switchAction = switchDailyAction;
                injectSwitchAction(switchDailyAction);
                switchDailyAction.setTime(new Time(daily.getTime()));
                actionService.insertDailyAction(switchDailyAction);
                //type为2，则是间隔动作
            } else if (type == SwitchAction.TYPE_INTERVAL) {
                SwitchIntervalAction switchIntervalAction = new SwitchIntervalAction();
                switchAction = switchIntervalAction;
                injectSwitchAction(switchIntervalAction);
                switchIntervalAction.setIntervalTime(intervalTime);
                switchIntervalAction.setExecutionTime(executionTime);
                actionService.insertIntervalAction(switchIntervalAction);
            }
            ActionMessage.createByAction().success("#" + actionRoute + ResourceBundleUtil.getBundle().getString("blueplanet.action.controlpanel.addSuccess"));
            //发命令,动作改变
            BPHttpApiClient bpHttpApiClient = new BPHttpApiClient();
            bpHttpApiClient.actionChanged(deviceId, switchAction.getType(), switchAction.getId());
            log("控制面板", "添加动作");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("#" + actionRoute + ResourceBundleUtil.getBundle().getString("blueplanet.action.controlpanel.addFails"));
            logger.error("控制面板添加动作失败", e);
        }
        return Results.redirect("/blueplanet/control-panel/" + deviceId + "/actions");
    }

    @Route("control-panel/{deviceId}/select")
    public String excute() {
        Map<String, Object> success = new HashMap<String, Object>();
        try {
            //为页面做定时与间隔时间互斥
            SwitchIntervalAction switchIntervalAction = actionService.findIntervalActions(deviceId, actionRoute);
            List<SwitchDailyAction> switchDailyActions = actionService.findDailyActions(deviceId, actionRoute);
            if (switchIntervalAction == null) {
                if (switchDailyActions.size() == 0) {
                    success.put("empty", true);
                    success.put("hasDaily", false);
                } else {
                    success.put("empty", false);
                    success.put("hasDaily", true);
                }
                success.put("hasInterval", false);
            } else {
                success.put("hasInterval", true);
            }
            success.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            success.put("success", false);
        }
        return Results.json().asRoot(success).done();
    }

    private void injectSwitchAction(SwitchAction switchAction){
        switchAction.setDeviceId(deviceId);
        switchAction.setRoute(actionRoute);
        switchAction.setAction(action);
        switchAction.setUpdateTime(new Date());
    }


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getActionRoute() {
        return actionRoute;
    }

    public void setActionRoute(int actionRoute) {
        this.actionRoute = actionRoute;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getDaily() {
        return daily;
    }

    public void setDaily(Date daily) {
        this.daily = daily;
    }

    public int getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

}
