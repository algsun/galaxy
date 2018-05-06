package com.microwise.phoenix.bean.vo.healthCheck;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 文物饼图数据实体
 *
 * @author xu.baoji
 * @date 2013-8-5
 */
@ApiModel(value = "文物饼状图数据")
public class RelicPieData {

    /**
     * 文物饼图数据  list<object> 如果hasData 为true ：该列表有两个元素，第一个 为饼图 块名称，第二个为饼图块 占有 %
     */
    @ApiModelProperty(value = "饼状图数据", required = true)
    private List<List<Object>> pieData;

    /**
     * 饼图中其他数据 list<object> 如果otherData 为true ：该列表有两个元素，第一个 为饼图 块名称，第二个为饼图块 占有 %
     */
    @ApiModelProperty(value = "饼状图其他数据", required = true)
    private List<List<Object>> otherData;

    /**
     * 是否有数据
     */
    @ApiModelProperty(value = "是否有饼状图数据", required = true)
    private boolean hasData;

    /**
     * 是否有其他数据
     */
    @ApiModelProperty(value = "是否有饼状图其他数据", required = true)
    private boolean hasOtherData;

    public List<List<Object>> getPieData() {
        return pieData;
    }

    public void setPieData(List<List<Object>> pieData) {
        this.pieData = pieData;
    }

    public List<List<Object>> getOtherData() {
        return otherData;
    }

    public void setOtherData(List<List<Object>> otherData) {
        this.otherData = otherData;
    }

    public boolean isHasData() {
        return hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }

    public boolean isHasOtherData() {
        return hasOtherData;
    }

    public void setHasOtherData(boolean hasOtherData) {
        this.hasOtherData = hasOtherData;
    }
}
