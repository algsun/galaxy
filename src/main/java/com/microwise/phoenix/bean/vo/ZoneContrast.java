package com.microwise.phoenix.bean.vo;

/***
 * 环境监控：区域对比统计分析实体
 *
 * @author xu.baoji
 * @date 2013-7-3
 */
public class ZoneContrast {

    /**
     * 监测指标中文名称
     */
    private String cnName;

    /**
     * 监测指标单位
     */
    private String units;

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
}
