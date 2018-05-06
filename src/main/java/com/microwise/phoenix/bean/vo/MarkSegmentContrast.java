package com.microwise.phoenix.bean.vo;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 标记段对比vo
 *
 * @author zhangpeng
 * @date 13-8-6
 * @check @wang.geng 2013年8月14日 #4860
 */
@ApiModel(value = "标记段对比实体")
public class MarkSegmentContrast {

    /**
     * 标记段id
     */
    @ApiModelProperty(value = "标记段ID", required = true)
    private String markId;

    /**
     * 标记段名称
     */
    @ApiModelProperty(value = "标记段名称", required = true)
    private String markName;

    @ApiModelProperty(value = "摄像机点位名称", required = true)
    private String placeName;

    /**
     * 标记段变化长度，单位：像素
     */
    @ApiModelProperty(value = "标记段变化长度，单位:像素", required = true)
    private float lengthDelta;

    /**
     * 标记段变化长度，单位：毫米
     */
    @ApiModelProperty(value = "标记段变化长度，单位:毫米", required = true)
    private float lengthRealDelta;

    /**
     * 图片宽度，单位：像素
     */
    @ApiModelProperty(value = "图片宽度，单位:像素", required = true)
    private int imageWidth;

    /**
     * 图片宽度，单位：毫米
     */
    @ApiModelProperty(value = "图片宽度，单位:毫米", required = true)
    private int imageRealWidth;

    /**
     * 比例尺：1像素代表多少毫米
     */
    @ApiModelProperty(value = "比例尺：1像素代表多少毫米", required = true)
    private float scale;

    public String getMarkId() {
        return markId;
    }

    public void setMarkId(String markId) {
        this.markId = markId;
    }

    public String getMarkName() {
        return markName;
    }

    public void setMarkName(String markName) {
        this.markName = markName;
    }

    public float getLengthDelta() {
        return lengthDelta;
    }

    public void setLengthDelta(float lengthDelta) {
        this.lengthDelta = lengthDelta;
    }

    public float getLengthRealDelta() {
        return lengthRealDelta;
    }

    public void setLengthRealDelta(float lengthRealDelta) {
        this.lengthRealDelta = lengthRealDelta;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageRealWidth() {
        return imageRealWidth;
    }

    public void setImageRealWidth(int imageRealWidth) {
        this.imageRealWidth = imageRealWidth;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

}
