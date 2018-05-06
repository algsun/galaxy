package com.microwise.proxima.action.markSegment;

import com.google.common.base.Strings;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.proxima.bean.DVPlaceBean;
import com.microwise.proxima.bean.MarkSegmentPositionBean;
import com.microwise.proxima.bean.PictureBean;
import com.microwise.proxima.service.MarkSegmentPositionService;
import com.microwise.proxima.service.PictureService;
import com.microwise.proxima.sys.Proxima;
import com.opensymphony.xwork2.Action;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 编辑标记段
 *
 * @author gaohui
 * @date 2012-7-13
 * @check guo.tian li.jianfei 2012-09-19
 */
@Beans.Action
@Proxima
public class EditMarkSegmentAction {
    private static final Logger log = LoggerFactory.getLogger(EditMarkSegmentAction.class);

    /**
     * 图片 service
     */
    @Autowired
    private PictureService pictureService;

    /**
     * 标记段位置 service
     */
    @Autowired
    private MarkSegmentPositionService markSegmentPositionService;

    // input
    /**
     * 图片id
     */
    private String picId;

    // output
    /**
     * 图片对象
     */
    private PictureBean pic;

    /**
     * 摄像机点位
     */
    private DVPlaceBean dvPlace;

    /**
     * 此图片中的所有标记段坐标
     */
    private List<MarkSegmentPositionBean> markSegmentPositions;

    /**
     * 是否是新编辑
     */
    private boolean isNewOrLatestEditedPicture;

    /**
     * 页面 "返回" 按钮, 后退次数
     */
    private int historyBackTimes = 1;

    public String execute() {
        try {
            // 如果是从裂隙图片(/viewOpticsImage*)跳转过来，那么返回按钮返回一步，否则返回两步
            String referer = ServletActionContext.getRequest().getHeader("referer");
            if (!Strings.isNullOrEmpty(referer)) {
                int index = referer.indexOf("/viewOpticsImage");
                if (index == -1) {
                    historyBackTimes = 2;
                } else {
                    historyBackTimes = 1;
                }
            }

            pic = pictureService.findById(picId);
            dvPlace = pictureService.findDVPlaceByPictureId(picId);

            // 如果 imageRealWidth 之前未进行设置，那么跳转到设置页面
            if (dvPlace.getImageRealWidth() <= 0) {
                return Action.INPUT;
            }

            markSegmentPositions = markSegmentPositionService
                    .findActiveByPicId(picId);
            if (markSegmentPositions.isEmpty()) {
                markSegmentPositions = markSegmentPositionService
                        .findLatestActiveBefore(picId);
            }

            isNewOrLatestEditedPicture = markSegmentPositionService
                    .isNewOrLatestEditedPicture(picId);
        } catch (Exception e) {
            log.error("", e);
        }
        return Action.SUCCESS;
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public PictureBean getPic() {
        return pic;
    }

    public void setPic(PictureBean pic) {
        this.pic = pic;
    }

    public DVPlaceBean getDvPlace() {
        return dvPlace;
    }

    public void setDvPlace(DVPlaceBean dvPlace) {
        this.dvPlace = dvPlace;
    }

    public List<MarkSegmentPositionBean> getMarkSegmentPositions() {
        return markSegmentPositions;
    }

    public void setMarkSegmentPositions(
            List<MarkSegmentPositionBean> markSegmentPositions) {
        this.markSegmentPositions = markSegmentPositions;
    }

    public boolean isNewOrLatestEditedPicture() {
        return isNewOrLatestEditedPicture;
    }

    public void setNewOrLatestEditedPicture(boolean isNewOrLatestEditedPicture) {
        this.isNewOrLatestEditedPicture = isNewOrLatestEditedPicture;
    }

    public int getHistoryBackTimes() {
        return historyBackTimes;
    }

    public void setHistoryBackTimes(int historyBackTimes) {
        this.historyBackTimes = historyBackTimes;
    }

}
