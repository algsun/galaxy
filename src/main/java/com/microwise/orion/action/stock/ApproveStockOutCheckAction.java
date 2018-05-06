package com.microwise.orion.action.stock;


import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.service.OutEventService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 出库核对同意
 *
 * @author gaohui
 * @date 13-5-31 17:13
 *
 * @check xiedeng 2013-6-6 15:24 svn:3985
 */
@Beans.Action
@Orion
public class ApproveStockOutCheckAction extends OrionLoggerAction {
    @Autowired
    private OutEventService outEventService;

    //input
    private boolean _success;
    private String outEventId;

    /**
     * 核对通过
     *
     * @return
     */
    public String execute() {
        if (_success) {
            // 更改出库单中的文物状态为 “待出库” @gaohui 2013-05-31
            outEventService.updateRelicState(outEventId, Relic.STATE_TO_BE_OUT);

            log("出入库管理", "出库单核对通过, 申请单号：" + outEventId);
            ActionMessage.createByAction().success("审批成功");
        } else {
            // 反馈信息 @gaohui 2013-05-31
            ActionMessage.createByAction().fail("审批失败");
        }
        return Action.SUCCESS;
    }

    public boolean is_success() {
        return _success;
    }

    public void set_success(boolean _success) {
        this._success = _success;
    }

    public String getOutEventId() {
        return outEventId;
    }

    public void setOutEventId(String outEventId) {
        this.outEventId = outEventId;
    }
}
