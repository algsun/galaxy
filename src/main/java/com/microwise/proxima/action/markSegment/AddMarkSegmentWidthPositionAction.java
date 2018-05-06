package com.microwise.proxima.action.markSegment;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.proxima.bean.MarkSegmentPositionBean;
import com.microwise.proxima.service.MarkSegmentService;
import com.microwise.proxima.sys.Proxima;
import com.opensymphony.xwork2.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 添加 标记段和标记段坐标
 *
 * @author gaohui
 * @date 2012-7-17
 * @check guo.tian li.jianfei 2012-09-19
 */
@Beans.Action
@Proxima
public class AddMarkSegmentWidthPositionAction {
    private static final Logger log = LoggerFactory.getLogger(AddMarkSegmentWidthPositionAction.class);

    @Autowired
    private MarkSegmentService markSegmentService;

    // input
    /**
     * 摄像机点位ID
     */
    private int dvPlaceId;

    /**
     * 图片ID
     */
    private int picId;

    /**
     * 标记段名称
     */
    private String markName;

    /**
     * 起点坐标
     */
    private int positionX;
    private int positionY;

    /**
     * 终点坐标
     */
    private int positionX2;
    private int positionY2;

    // output
    /**
     * 成功与否
     */
    private boolean success;

    /**
     * 标记段坐标ID
     */
    private String markPositionId;

    /**
     * 标记段ID
     */
    private String markSegmentId;

    /**
     * 反馈消息
     */
    private String message;

    public String execute() {
        try {

            // 查看是否重名
            if (markSegmentService.findMarkSegmentOfDVPlaceByName(dvPlaceId, markName) != null) {
                success = false;
                message = "标记段重名，请更换名称";
                return Action.SUCCESS;
            }

            // 保存标记段和位置
            MarkSegmentPositionBean markSegmentPosition = markSegmentService
                    .addMarkSegmentWidthPoistion(dvPlaceId, markName, picId,
                            positionX, positionY, positionX2, positionY2);
            markPositionId = markSegmentPosition.getId();
            markSegmentId = markSegmentPosition.getMarkSegment().getId();
            success = true;
        } catch (Exception ex) {
            log.error("", ex);
            message = "添加失败";
            success = false;
        }
        return Action.SUCCESS;
    }

    public int getDvPlaceId() {
        return dvPlaceId;
    }

    public void setDvPlaceId(int dvPlaceId) {
        this.dvPlaceId = dvPlaceId;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public String getMarkName() {
        return markName;
    }

    public void setMarkName(String markName) {
        this.markName = markName;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getPositionX2() {
        return positionX2;
    }

    public void setPositionX2(int positionX2) {
        this.positionX2 = positionX2;
    }

    public int getPositionY2() {
        return positionY2;
    }

    public void setPositionY2(int positionY2) {
        this.positionY2 = positionY2;
    }

    public String getMarkPositionId() {
        return markPositionId;
    }

    public void setMarkPositionId(String markPositionId) {
        this.markPositionId = markPositionId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMarkSegmentId() {
        return markSegmentId;
    }

    public void setMarkSegmentId(String markSegmentId) {
        this.markSegmentId = markSegmentId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
