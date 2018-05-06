package com.microwise.proxima.action.markRegion;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.proxima.bean.InfraredMarkRegionDataBean;
import com.microwise.proxima.bean.InfraredPictureDataBean;
import com.microwise.proxima.bean.PictureBean;
import com.microwise.proxima.daemon.infraredImageResolution.InfraredAnalyzer;
import com.microwise.proxima.service.InfraredMarkRegionDataService;
import com.microwise.proxima.service.InfraredPictureDataService;
import com.microwise.proxima.service.PictureService;
import com.microwise.proxima.sys.Proxima;
import com.opensymphony.xwork2.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 查看图片实时温度
 *
 * @author gaohui
 * @date 2012-9-11
 * @check guo.tian li.jianfei 2012-09-19
 * @check li.jianfei liu.zhu 2014-4-15 #8233
 */
@Beans.Action
@Proxima
public class InfraredImageTempuratureAction {
    private static final Logger log = LoggerFactory.getLogger(InfraredImageTempuratureAction.class);

    /**
     * 红外图片数据 service
     */
    @Autowired
    private InfraredPictureDataService irPictureDataService;
    /**
     * 红外标记区域数据 service
     */
    @Autowired
    private InfraredMarkRegionDataService markRegionDataService;
    /**
     * 图片service
     */
    @Autowired
    private PictureService pictureService;
    /**
     * 红外图片解析类
     */
    @Autowired
    private InfraredAnalyzer infraredAnalyzer;

    // input
    /**
     * 摄像机点位ID
     */
    private String dvPlaceId;
    /**
     * 图片ID
     */
    private String pictureId;
    /**
     * 位置x
     */
    private int x;
    /**
     * 位置y
     */
    private int y;

    // output
    /**
     * 图片是否能够解析
     */
    private boolean isResolved = false;
    /**
     * 是否有图片温度
     */
    private boolean hasPicTemp = false;
    /**
     * 图片最高温度
     */
    private float picMaxTemp;
    /**
     * 图片最低温度
     */
    private float picMinTemp;
    /**
     * 图片平均温度
     */
    private float picAvgTemp;
    /**
     * 图片当前文档
     */
    private float picCurrentTemp;

    /**
     * 是否有图片温度
     */
    private boolean hasRegionTemp = false;
    /**
     * 标记区域最高温度
     */
    private float regionMaxTemp;
    /**
     * 标记区域最低温度
     */
    private float regionMinTemp;
    /**
     * 标记区域平均温度
     */
    private float regionAvgTemp;
    /**
     * 成功与否
     */
    private boolean success;

    public String execute() {
        try {
            // 查询图片温度值
            InfraredPictureDataBean irPictureData = irPictureDataService
                    .findByPicId(pictureId);
            if (irPictureData != null) {
                //如果图片的最高温度和最低温度都为 0 , 则认为图片不能被解析
                if (irPictureData.getHighTemperature() == 0 && irPictureData.getLowTemperature() == 0) {
                    isResolved = false;
                } else {
                    isResolved = true;

                    hasPicTemp = true;
                    picMaxTemp = (float) irPictureData.getHighTemperature();
                    picMinTemp = (float) irPictureData.getLowTemperature();
                    picAvgTemp = (float) irPictureData.getAverageTemperature();
                    PictureBean pictureBean = pictureService.findById(pictureId);
                    String fileSrc = pictureBean.getPath() + "/" + pictureBean.getName();
                    picCurrentTemp = (float) this.infraredAnalyzer.findCurrentPointValue(fileSrc, picMaxTemp, picMinTemp, x, y);
                }
            }

            // 查询对应的标记区域数据
            InfraredMarkRegionDataBean markRegionData = markRegionDataService.findAt(dvPlaceId, pictureId, x, y);
            if (markRegionData != null) {
                hasRegionTemp = true;
                regionMaxTemp = (float) markRegionData.getHighTemperature();
                regionMinTemp = (float) markRegionData.getLowTemperature();
                regionAvgTemp = (float) markRegionData.getAverageTemperature();
            }

            success = true;
        } catch (Exception ex) {
            success = false;
            log.error("查看实时温度", ex);
        }
        return Action.SUCCESS;
    }

    public String getDvPlaceId() {
        return dvPlaceId;
    }

    public void setDvPlaceId(String dvPlaceId) {
        this.dvPlaceId = dvPlaceId;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public float getPicMaxTemp() {
        return picMaxTemp;
    }

    public void setPicMaxTemp(float picMaxTemp) {
        this.picMaxTemp = picMaxTemp;
    }

    public float getPicMinTemp() {
        return picMinTemp;
    }

    public void setPicMinTemp(float picMinTemp) {
        this.picMinTemp = picMinTemp;
    }

    public float getPicAvgTemp() {
        return picAvgTemp;
    }

    public void setPicAvgTemp(float picAvgTemp) {
        this.picAvgTemp = picAvgTemp;
    }

    public float getRegionMaxTemp() {
        return regionMaxTemp;
    }

    public void setRegionMaxTemp(float regionMaxTemp) {
        this.regionMaxTemp = regionMaxTemp;
    }

    public float getRegionMinTemp() {
        return regionMinTemp;
    }

    public void setRegionMinTemp(float regionMinTemp) {
        this.regionMinTemp = regionMinTemp;
    }

    public float getRegionAvgTemp() {
        return regionAvgTemp;
    }

    public void setRegionAvgTemp(float regionAvgTemp) {
        this.regionAvgTemp = regionAvgTemp;
    }

    public boolean isHasPicTemp() {
        return hasPicTemp;
    }

    public void setHasPicTemp(boolean hasPicTemp) {
        this.hasPicTemp = hasPicTemp;
    }

    public boolean isHasRegionTemp() {
        return hasRegionTemp;
    }

    public void setHasRegionTemp(boolean hasRegionTemp) {
        this.hasRegionTemp = hasRegionTemp;
    }

    public float getPicCurrentTemp() {
        return picCurrentTemp;
    }

    public void setPicCurrentTemp(float picCurrentTemp) {
        this.picCurrentTemp = picCurrentTemp;
    }

    public boolean isResolved() {
        return isResolved;
    }

    public void setResolved(boolean isResolved) {
        this.isResolved = isResolved;
    }

}
