package com.microwise.orion.action.stock;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.OutEvent;
import com.microwise.orion.bean.OutEventAttachment;
import com.microwise.orion.service.OutEventService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 出库详情
 *
 * @author gaohui
 * @date 13-5-30 09:40
 *
 * @check xiedeng 2013-6-6 15:24 svn:3892
 */
@Beans.Action
@Orion
public class ViewStockOutAction extends OrionLoggerAction {

    /**
     * 出库文物service
     */
    @Autowired
    private OutEventService outEventService;

    //input
    // 出库申请单号
    private String outEventId;

    //output
    private OutEvent outEvent;

    /**
     * 出库事件的关联文档
     */
    private List<OutEventAttachment> outEventAttachmentList;

    /**
     * 出库详情
     * @return
     */
    public String execute(){
        try{
            outEvent = outEventService.findById(outEventId);
            outEventAttachmentList = outEventService.findOutEventAttachmentByEventId(outEventId);
            log("出入库管理","出库单详情");
        }catch (Exception e){
            e.printStackTrace();
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

    public List<OutEventAttachment> getOutEventAttachmentList() {
        return outEventAttachmentList;
    }

    public void setOutEventAttachmentList(List<OutEventAttachment> outEventAttachmentList) {
        this.outEventAttachmentList = outEventAttachmentList;
    }
}
