package com.microwise.proxima.action.opticsDVPlace;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.proxima.bean.DVPlaceBean;
import com.microwise.proxima.service.DVPlaceService;
import com.microwise.proxima.sys.Proxima;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 更新图片实景宽度
 *
 * @author gaohui
 * @date 13-6-24 11:10
 */
@Beans.Action
@Proxima
public class UpdateImageRealWidthAction {
    public static final Logger log = LoggerFactory.getLogger(UpdateImageRealWidthAction.class);

    @Autowired
    private DVPlaceService dvPlaceService;

    //input
    // 图片实景宽度
    private int width;
    // 摄像机点位id
    private String dvPlaceId;

    //output
    // 成功与否
    private boolean success = false;


    public String execute() {
        try {
            DVPlaceBean dvPlace = dvPlaceService.findById(dvPlaceId);
            dvPlace.setImageRealWidth(width);

            dvPlaceService.update(dvPlace);
            success = true;
        } catch (Exception e) {
            log.error("更新实景宽度", e);
        }

        return Action.SUCCESS;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getDvPlaceId() {
        return dvPlaceId;
    }

    public void setDvPlaceId(String dvPlaceId) {
        this.dvPlaceId = dvPlaceId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
