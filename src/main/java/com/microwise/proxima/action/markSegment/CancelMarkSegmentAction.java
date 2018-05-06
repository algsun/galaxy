package com.microwise.proxima.action.markSegment;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.proxima.service.MarkSegmentService;
import com.microwise.proxima.sys.Proxima;
import com.opensymphony.xwork2.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 取消/注销 标记段
 *
 * @author gaohui
 * @date 2012-7-19
 * @check guo.tian li.jianfei 2012-09-19
 */
@Beans.Action
@Proxima
public class CancelMarkSegmentAction {
    private static final Logger log = LoggerFactory.getLogger(CancelMarkSegmentAction.class);

    /**
     * 标记段 service
     */
    @Autowired
    private MarkSegmentService markSegmentService;

    // input
    /**
     * 标记段 id
     */
    private int markSegmentId;

    // output
    /**
     * 操作成功与否
     */
    private boolean success;

    public String execute() {
        try {
            markSegmentService.cancelMarkSegment(markSegmentId);
            success = true;
        } catch (Exception ex) {
            log.error("", ex);
            success = false;
        }
        return Action.SUCCESS;
    }

    public int getMarkSegmentId() {
        return markSegmentId;
    }

    public void setMarkSegmentId(int markSegmentId) {
        this.markSegmentId = markSegmentId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
