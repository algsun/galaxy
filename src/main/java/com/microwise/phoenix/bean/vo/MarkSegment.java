package com.microwise.phoenix.bean.vo;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 标记段信息
 *
 * @author xu.baoji
 * @date 2013-8-8
 */
@ApiModel(value = "标记段信息")
public class MarkSegment {

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

    /**
     * 摄像机名称
     */
    @ApiModelProperty(value = "摄像机点位名称", required = true)
    private String placeName;

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

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

}
