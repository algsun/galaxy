package com.microwise.proxima.action.markRegion;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.proxima.bean.DVPlaceBean;
import com.microwise.proxima.bean.InfraredMarkRegionBean;
import com.microwise.proxima.bean.PictureBean;
import com.microwise.proxima.service.InfraredMarkRegionService;
import com.microwise.proxima.service.PictureService;
import com.microwise.proxima.sys.Proxima;
import com.opensymphony.xwork2.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 编辑红外图片标记区域
 *
 * @author gaohui
 * @date 2012-9-6
 * @check guo.tian li.jianfei 2012-09-19
 * @check li.jianfei liu.zhu 2014-4-15 #8344
 */
@Beans.Action
@Proxima
public class EditMarkRegionAction {
    private static final Logger log = LoggerFactory.getLogger(EditMarkRegionAction.class);

    @Autowired
    private PictureService pictureService;
    @Autowired
    private InfraredMarkRegionService markRegionService;

    // input
    /**
     * 图片ID
     */
    private String picId;

    // output
    /**
     * 图片
     */
    private PictureBean picture;
    /**
     * 摄像机点位
     */
    private DVPlaceBean dvPlace;
    /**
     * 摄像机点位，对应的所有标记区域
     */
    private List<InfraredMarkRegionBean> markRegions;

    /**
     * 图片服务器目录
     */
    private String picturesBasePath;

    // 初始化 图片路径
    {
        picturesBasePath = Sessions.createByAction().getGalaxyResourceURL() + "/proxima/images";
    }

    /**
     * 跳转到标记区域编辑页面
     *
     * @return
     */
    public String view() {
        try {
            picture = pictureService.findById(picId);
            dvPlace = pictureService.findDVPlaceByPictureId(picId);
            markRegions = markRegionService.findAllByDVPlaceId(dvPlace.getId());
        } catch (Exception e) {
            log.error("编辑标记区域", e);
        }
        return Action.SUCCESS;
    }

    public PictureBean getPicture() {
        return picture;
    }

    public void setPicture(PictureBean picture) {
        this.picture = picture;
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public DVPlaceBean getDvPlace() {
        return dvPlace;
    }

    public void setDvPlace(DVPlaceBean dvPlace) {
        this.dvPlace = dvPlace;
    }

    public List<InfraredMarkRegionBean> getMarkRegions() {
        return markRegions;
    }

    public void setMarkRegions(List<InfraredMarkRegionBean> markRegions) {
        this.markRegions = markRegions;
    }

    public String getPicturesBasePath() {
        return picturesBasePath;
    }

    public void setPicturesBasePath(String picturesBasePath) {
        this.picturesBasePath = picturesBasePath;
    }
}
