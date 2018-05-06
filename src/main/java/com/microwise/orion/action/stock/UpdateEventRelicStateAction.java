package com.microwise.orion.action.stock;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.service.EventRelicService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 更新 eventRelicId 状态
 *
 * @author gaohui
 * @date 13-6-1 16:38
 * @check xiedeng 2013-6-6 15:24 svn:3967
 */
@Beans.Action
@Orion
public class UpdateEventRelicStateAction extends OrionLoggerAction {

    /**
     * 出库文物service
     */
    @Autowired
    private EventRelicService eventRelicService;

    //input
    private int eventRelicId;
    private int state;
    // 重置时间
    private boolean resetOutDate = false;

    //output
    private boolean success;

    /**
     * 更新 eventRelicId 状态
     * @return
     */
    public String execute() {
        try {
            eventRelicService.updateStateById(eventRelicId, state);
            if (resetOutDate) {
                eventRelicService.updateOutDateById(eventRelicId, null);
            } else {
                eventRelicService.updateOutDateById(eventRelicId, new Date());
            }
            success = true;
        } catch (Exception e) {
            logFailed("更新事件文物状态", "更新事件文物状态失败");
            e.printStackTrace();
            success = false;
        }
        return Action.SUCCESS;
    }

    public int getEventRelicId() {
        return eventRelicId;
    }

    public void setEventRelicId(int eventRelicId) {
        this.eventRelicId = eventRelicId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isResetOutDate() {
        return resetOutDate;
    }

    public void setResetOutDate(boolean resetOutDate) {
        this.resetOutDate = resetOutDate;
    }
}
