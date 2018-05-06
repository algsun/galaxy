package com.microwise.orion.action.stock;

import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.DateTimeUtil;
import com.microwise.orion.bean.EventRelic;
import com.microwise.orion.bean.OutEvent;
import com.microwise.orion.bean.OutEventAttachment;
import com.microwise.orion.bean.Rove;
import com.microwise.orion.service.OutEventService;
import com.microwise.orion.service.RoveService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import com.opensymphony.xwork2.Action;
import org.activiti.engine.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 库房确认通过
 *
 * @author gaohui
 * @date 13-6-1 11:05
 *
 * @check xiedeng 2013-6-6 15:37 svn:4046
 */
@Beans.Action
@Orion
public class ConfirmStockOutAction extends OrionLoggerAction {
    public static final Logger log = LoggerFactory.getLogger(ConfirmStockOutAction.class);

    /** 出库 记录 （出库单） service */
    @Autowired
    private OutEventService outEventService;
    @Autowired
    private TaskService taskService;
    /**流传经历service*/
    @Autowired
    private RoveService roveService;

    //input
    private String outEventId;
    private String taskId;

    //output
    private OutEvent outEvent;
    private boolean couldCheck = true;

    /**
     * 出库事件的关联文档
     */
    private List<OutEventAttachment> outEventAttachmentList;

    public String view() {
        outEvent = outEventService.findById(outEventId);
        outEventAttachmentList = outEventService.findOutEventAttachmentByEventId(outEventId);
        for (EventRelic evenRelic : outEvent.getEventRelics()) {
            if (evenRelic.getState() != EventRelic.STATE_EXIST) {
                couldCheck = false;
            }
        }

        ActionMessage.createByAction().consume();

        return Action.SUCCESS;
    }

    /**
     * 库房确认通过
     *
     * @return
     */
    public String execute() {
        try {
            outEventService.doStockOut(outEventId);

            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("stockOutConfirmApproved", true);
            taskService.complete(taskId, variables);

            //出库信息作为最新流传经历信息
            OutEvent outEvent = outEventService.findById(outEventId);
            for(EventRelic eventRelic : outEvent.getEventRelics()){
                String roveInfo = "出库时间："+ DateTimeUtil.format("yyyy-MM-dd HH:mm",outEvent.getBeginDate())
                        +"，提用目的："+outEvent.getUseFor();
                Rove rove = new Rove();
                rove.setRoveInfo(roveInfo);
                rove.setRoveDate(outEvent.getBeginDate());
                rove.setRelic(eventRelic.getRelic());
                roveService.addRelicRove(rove);
            }

            log("出入库管理", "库房确认通过，申请单号：" + outEventId);
            ActionMessage.createByAction().success("审批通过");
        } catch (Exception e) {
            logFailed("出库确认", "出库确认失败");
            log.error("", e);
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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public OutEvent getOutEvent() {
        return outEvent;
    }

    public void setOutEvent(OutEvent outEvent) {
        this.outEvent = outEvent;
    }

    public boolean isCouldCheck() {
        return couldCheck;
    }

    public void setCouldCheck(boolean couldCheck) {
        this.couldCheck = couldCheck;
    }

    public List<OutEventAttachment> getOutEventAttachmentList() {
        return outEventAttachmentList;
    }

    public void setOutEventAttachmentList(List<OutEventAttachment> outEventAttachmentList) {
        this.outEventAttachmentList = outEventAttachmentList;
    }
}
