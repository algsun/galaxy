package com.microwise.proxima.action.markSegment;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.proxima.bean.DVPlaceBean;
import com.microwise.proxima.service.DVPlaceService;
import com.microwise.proxima.sys.Proxima;
import com.opensymphony.xwork2.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 获取图片实景长度和图片原始大小
 *
 * @author gaohui
 * @date 2012-7-13
 * @check guo.tian li.jianfei 2012-09-19
 */
@Beans.Action
@Proxima
public class InputImageRealWidthAction {
    private static final Logger log = LoggerFactory.getLogger(InputImageRealWidthAction.class);

    /**
     * 红外热像仪点位信息服务层
     */
    @Autowired
    private DVPlaceService dvPlaceService;

    // input
    private int picId; // 图片ID
    private int dvPlaceId; // 摄像机点位ID
    private float imageRealWidth; // 图片实景宽度
    private int imageWidth; // 图片原始宽度
    private int imageHeight; // 图片原始高度

    public String execute() {
        try {
            DVPlaceBean dvPlace = dvPlaceService
                    .findById(String.valueOf(dvPlaceId));
            dvPlace.setImageRealWidth(imageRealWidth);
            dvPlace.setImageWidth(imageWidth);
            dvPlace.setImageHeight(imageHeight);
            dvPlaceService.update(dvPlace);
        } catch (Exception e) {
            log.error("", e);
        }
        return Action.SUCCESS;
    }

    public int getDvPlaceId() {
        return dvPlaceId;
    }

    public void setDvPlaceId(int dvPlaceId) {
        this.dvPlaceId = dvPlaceId;
    }

    public float getImageRealWidth() {
        return imageRealWidth;
    }

    public void setImageRealWidth(float imageRealWidth) {
        this.imageRealWidth = imageRealWidth;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

}
