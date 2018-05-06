package com.microwise.orion.action.stock;

import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.OutEvent;
import com.microwise.orion.service.OutEventService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 回库, 根据出库申请单将文物入库
 *
 * @author gaohui
 * @date 13-6-3 14:00
 *
 * @check xiedeng 2013-6-6 15:41 svn:3990
 */
@Beans.Action
@Orion
public class ReturnStockOutAction extends OrionLoggerAction {

    /**
     * 出库 记录 （出库单） service
     */
    @Autowired
    private OutEventService outEventService;

    //input
    private String outEventId;

    //output
    private OutEvent outEvent;

    public String view() {
        outEvent = outEventService.findById(outEventId);
        return Action.SUCCESS;
    }

    /**
     * 回库, 根据出库申请单将文物入库
     * @return
     */
    public String execute() {
        try {
            outEventService.backToStoreRoom(outEventId);

            log("出入库管理", "文物回库，申请单号：" + outEventId);
            ActionMessage.createByAction().success("回库成功");
        } catch (Exception e) {
            logFailed("出库单回库", "出库单回库失败");
            e.printStackTrace();
            ActionMessage.createByAction().fail("回库失败");
            return Action.INPUT;
        }
        return Action.SUCCESS;
    }

    public String getOutEventId() {
        return outEventId;
    }

    public void setOutEventId(String outEventId) {
        this.outEventId = outEventId;
    }

    public OutEvent getOutEvent() {
        return outEvent;
    }

    public void setOutEvent(OutEvent outEvent) {
        this.outEvent = outEvent;
    }
}
