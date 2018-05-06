package com.microwise.orion.action.stock;

import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.service.OutEventService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import com.opensymphony.xwork2.Action;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 取消申请
 *
 * @author gaohui
 * @date 13-5-31 10:31
 * @check xiedeng 2013-6-6 15:24 svn:3985
 */
@Beans.Action
@Orion
public class CancelStockOutAction extends OrionLoggerAction {

    /**
     * 出库 记录 （出库单） service
     */
    @Autowired
    private OutEventService outEventService;

    @Autowired
    private TaskService taskService;

    //input
    private String outEventId;
    private String taskId;

    /**
     * 取消任务申请
     * @return
     */
    public String execute() {
        try {
            // 取消任务
            Map<String,Object> variables = new HashMap<String, Object>();
            variables.put("resendRequest", false);
            taskService.complete(taskId, variables);
            // 删除 outEvent
            outEventService.deleteOutEvent(outEventId);

            log("出入库管理", "取消出库申请，申请单号：" + outEventId);
            ActionMessage.createByAction().success("取消申请成功");
        } catch (Exception e) {
            logFailed("取消申请", "取消申请成功");
            e.printStackTrace();
            ActionMessage.createByAction().fail("取消申请失败");
        }

        return Action.SUCCESS;
    }

    public String getOutEventId() {
        return outEventId;
    }

    public void setOutEventId(String outEventId) {
        this.outEventId = outEventId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
