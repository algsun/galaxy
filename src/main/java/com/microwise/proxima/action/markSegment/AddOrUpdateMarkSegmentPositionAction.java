package com.microwise.proxima.action.markSegment;

import com.microwise.proxima.service.MarkSegmentPositionService;
import com.opensymphony.xwork2.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 添加或者修改 标记段坐标
 *
 * @author gaohui
 * @date 2012-7-18
 * @check guo.tian li.jianfei 2012-09-19
 */
@Component
@Scope("prototype")
public class AddOrUpdateMarkSegmentPositionAction {
    private static final Logger log = LoggerFactory.getLogger(AddOrUpdateMarkSegmentPositionAction.class);
    /**
     * 标记段坐标位置service
     */
    @Autowired
    private MarkSegmentPositionService markSegmentPositionService;

    // input
    /**
     * 图片id
     */
    private String picId;

    /**
     * 标记段集合 json, 使用特定的格式，参考 imageViewer-*.js
     */
    private String markSegments;

    // output
    /**
     * 是否成功
     */
    private boolean success = false;

    public String execute() {
        try {
            markSegmentPositionService.addOrUpdateMarkSegmentPosition(picId,
                    markSegments);
            success = true;
        } catch (Exception ex) {
            log.error("", ex);
            success = false;
        }
        return Action.SUCCESS;
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public String getMarkSegments() {
        return markSegments;
    }

    public void setMarkSegments(String markSegments) {
        this.markSegments = markSegments;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
