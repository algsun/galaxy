package com.microwise.biela.bean.po;

/**
 * @author liuzhu
 * @date 14-1-2
 */
public class AreaCodePO {

    /**
     * 地区编码
     */
    private int areaCode;

    /**
     * 地区名称
     */
    private String areaName;

    /**
     * 父级编码
     */
    private int parentCode;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }

    public int getParentCode() {
        return parentCode;
    }

    public void setParentCode(int parentCode) {
        this.parentCode = parentCode;
    }
}
