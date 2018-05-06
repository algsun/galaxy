package com.microwise.orion.action.stock;

import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.service.OutEventService;
import com.microwise.orion.sys.Orion;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 不同意, 回到“调整申请”节点
 *
 * @author gaohui
 * @date 13-5-31 17:13
 *
 * @check xiedeng 2013-6-6 15:24 svn:3953
 */
@Beans.Action
@Orion
public class RejectStockOutAction {

    /**
     * 出库 记录 （出库单） service
     */
    @Autowired
    private OutEventService outEventService;

    //input
    private String outEventId;
    private boolean _success;

    /**
     * 不同意, 回到“调整申请”节点
     * @return
     */
    public String execute(){
        // 将文物状态从“待出库”改为“在库”
        if(_success){
            outEventService.updateRelicState(outEventId, Relic.STATE_IN);
            ActionMessage.createByAction().success("审批成功");
        }else{
            ActionMessage.createByAction().fail("审批失败");
        }
        return Action.SUCCESS;
    }

    public String getOutEventId() {
        return outEventId;
    }

    public void setOutEventId(String outEventId) {
        this.outEventId = outEventId;
    }

    public boolean is_success() {
        return _success;
    }

    public void set_success(boolean _success) {
        this._success = _success;
    }
}
