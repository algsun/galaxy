package com.microwise.proxima.action.markRegion;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.proxima.service.InfraredMarkRegionService;
import com.microwise.proxima.sys.Proxima;
import com.opensymphony.xwork2.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 删除标记区域
 *
 * @author gaohui
 * @date 2012-9-10
 * @check guo.tian li.jianfei 2012-09-19
 * @check li.jianfei liu.zhu 2014-4-15 #8322
 */
@Beans.Action
@Proxima
public class DeleteMarkRegionAction {
    private static final Logger log = LoggerFactory.getLogger(DeleteMarkRegionAction.class);

    @Autowired
    private InfraredMarkRegionService markRegionService;

    // input
    /**
     * 标记区域ID
     */
    private String markRegionId;

    // output
    /**
     * 成功与否
     */
    private boolean success;
    /**
     * 失败后的消息
     */
    private String message;

    public String execute() {
        try {
            markRegionService.deleteWithMarkRegionDatas(markRegionId);
            success = true;
        } catch (Exception ex) {
            success = false;
            message = "删除失败";
            log.error("", ex);
        }

        return Action.SUCCESS;
    }

    public String getMarkRegionId() {
        return markRegionId;
    }

    public void setMarkRegionId(String markRegionId) {
        this.markRegionId = markRegionId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
