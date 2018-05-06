package com.microwise.phoenix.bean.vo;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 文物次数(出库次数等)
 *
 * @author gaohui
 * @date 13-7-10 15:40
 */
@ApiModel(value = "文物出库次数")
public class RelicFrequency {
    /**
     * 文物ID
     */
    @ApiModelProperty(value = "文物id", required = true)
    private int relicId;

    /**
     * 总登记号
     */
    @ApiModelProperty(value = "总登记号", required = true)
    private String relicTotalCode;

    /**
     * 文物名称
     */
    @ApiModelProperty(value = "文物名称", required = true)
    private String relicName;

    /**
     * 次数
     */
    @ApiModelProperty(value = "次数", required = true)
    private int count;

    public int getRelicId() {
        return relicId;
    }

    public void setRelicId(int relicId) {
        this.relicId = relicId;
    }

    public String getRelicTotalCode() {
        return relicTotalCode;
    }

    public void setRelicTotalCode(String relicTotalCode) {
        this.relicTotalCode = relicTotalCode;
    }

    public String getRelicName() {
        return relicName;
    }

    public void setRelicName(String relicName) {
        this.relicName = relicName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
