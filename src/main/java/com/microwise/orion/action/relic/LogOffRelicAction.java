package com.microwise.orion.action.relic;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.service.RelicService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 文物注销 EventAction
 *
 * @author li.jianfei
 * @date 2013-09-03
 * <p>
 * TODO 点击注销或取消注销后的分页状态保留
 */
@Beans.Action
@Orion
public class LogOffRelicAction extends OrionLoggerAction {

    @Autowired
    private RelicService relicService;

    /**
     * 文物ID
     */
    private int relicId;

    /**
     * 页面筛选条件
     */
    private int logOff;

    /**
     * 注销状态
     * 0-未注销；1-注销
     */
    private int cancelState;

    /**
     * 注销/取消注销文物信息
     *
     * @return
     */
    public String execute() {
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            relicService.updateRelicCanceledState(siteId, relicId, cancelState == 1);
            ActionMessage.createByAction().success("文物" + (cancelState == 1 ? "" : "取消") + "注销成功");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("文物" + (cancelState == 1 ? "" : "取消") + "注销失败");
            logFailed("注销/取消注销文物", "注销/取消注销文物失败");
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    public int getRelicId() {
        return relicId;
    }

    public void setRelicId(int relicId) {
        this.relicId = relicId;
    }

    public int getLogOff() {
        return logOff;
    }

    public void setLogOff(int logOff) {
        this.logOff = logOff;
    }

    public int getCancelState() {
        return cancelState;
    }

    public void setCancelState(int cancelState) {
        this.cancelState = cancelState;
    }
}
