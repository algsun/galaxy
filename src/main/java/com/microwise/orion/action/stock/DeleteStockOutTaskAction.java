package com.microwise.orion.action.stock;

import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.service.OutEventService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import com.opensymphony.xwork2.Action;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 删除出库申请任务
 *
 * @author bai.weixing
 * @since 2018-03-21
 */
@Beans.Action
@Orion
public class DeleteStockOutTaskAction extends OrionLoggerAction {

    /**
     * 出库 记录 （出库单） service
     */
    @Autowired
    private OutEventService outEventService;

    @Autowired
    private RuntimeService runtimeService;

    //input
    private String outEventId;
    private String processInstanceId;

    /**
     * 删除出库申请任务
     *
     * @return
     */
    public String execute() {
        try {
            // 删除出库任务
            runtimeService.deleteProcessInstance(processInstanceId, "删除出库任务");
            // 删除 outEvent
            outEventService.deleteOutEvent(outEventId);

            log("出入库管理", "删除出库申请，申请单号：" + outEventId);
            ActionMessage.createByAction().success("删除出库申请任务成功");
        } catch (Exception e) {
            logFailed("删除出库申请任务", "删除出库申请任务失败");
            e.printStackTrace();
            ActionMessage.createByAction().fail("删除出库申请任务失败");
        }

        return Action.SUCCESS;
    }

    public String getOutEventId() {
        return outEventId;
    }

    public void setOutEventId(String outEventId) {
        this.outEventId = outEventId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }
}
