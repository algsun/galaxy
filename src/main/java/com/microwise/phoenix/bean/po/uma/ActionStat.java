package com.microwise.phoenix.bean.po.uma;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 人员管理：规则统计实体
 *
 * @author xu.baoji
 * @date 2013-7-16
 */
@ApiModel(value = "规则统计实体")
public class ActionStat {

    /**
     * 规则名称
     */
    @ApiModelProperty(value = "规则名称", required = true)
    private String actionName;

    /**
     * 规则触发次数
     */
    @ApiModelProperty(value = "规则触发次数", required = true)
    private Integer actionCount;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public Integer getActionCount() {
        return actionCount;
    }

    public void setActionCount(Integer actionCount) {
        this.actionCount = actionCount;
    }

}
