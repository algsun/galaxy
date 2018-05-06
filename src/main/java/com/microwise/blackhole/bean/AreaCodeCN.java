package com.microwise.blackhole.bean;

/**
 * 地区编码Bean
 *
 * @author zhangpeng
 * @date 2012-11-15
 */
public class AreaCodeCN {

    /**
     * 地区编码
     */
    private int areaCode;

    /**
     * 地区名称
     */
    private String areaName;

    /**
     * 父级地区对象
     */
    private AreaCodeCN parentAreaCodeCN;

    /**
     * 是否过滤
     */
    private boolean filte;

    public int getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public AreaCodeCN getParentAreaCodeCN() {
        return parentAreaCodeCN;
    }

    public void setParentAreaCodeCN(AreaCodeCN parentAreaCodeCN) {
        this.parentAreaCodeCN = parentAreaCodeCN;
    }

    public boolean isFilte() {
        return filte;
    }

    public void setFilte(boolean filte) {
        this.filte = filte;
    }

}
