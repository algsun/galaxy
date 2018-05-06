package com.microwise.phoenix.bean.vo;

import com.microwise.phoenix.bean.vo.healthCheck.RelicPieData;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 文物统计基本 实体 包括（年代 % 、质地% 、级别% 、区域% ）
 *
 * @author xu.baoji
 * @date 2013-7-9
 */
@ApiModel
public class RelicBasicStats {

    /**
     * 文物统计 年代 %
     */
    @ApiModelProperty(value = "年代信息", required = true)
    private RelicPieData eraStat = new RelicPieData();

    /**
     * 文物统计 级别 %
     */
    @ApiModelProperty(value = "级别信息", required = true)
    private RelicPieData levelStat = new RelicPieData();

    /**
     * 文物统计 质地 %
     */
    @ApiModelProperty(value = "质地信息", required = true)
    private RelicPieData textureStat = new RelicPieData();

    /**
     * 文物统计 区域 %
     */
    @ApiModelProperty(value = "区域信息", required = true)
    private RelicPieData zoneStat = new RelicPieData();

    public RelicPieData getEraStat() {
        return eraStat;
    }

    public void setEraStat(RelicPieData eraStat) {
        this.eraStat = eraStat;
    }

    public RelicPieData getLevelStat() {
        return levelStat;
    }

    public void setLevelStat(RelicPieData levelStat) {
        this.levelStat = levelStat;
    }

    public RelicPieData getTextureStat() {
        return textureStat;
    }

    public void setTextureStat(RelicPieData textureStat) {
        this.textureStat = textureStat;
    }

    public RelicPieData getZoneStat() {
        return zoneStat;
    }

    public void setZoneStat(RelicPieData zoneStat) {
        this.zoneStat = zoneStat;
    }
}
