package com.microwise.cybertron.bean;

/**
 * 卷 实体类
 *
 * @author li.jianfei
 * @date 2014-07-16
 */
public class Volume {

    /**
     * 卷 代码
     */
    private int volumeCode;

    /**
     * 卷 名称
     */
    private String name;

    public Integer getParentCode() {
        return parentCode;
    }

    public void setParentCode(Integer parentCode) {
        this.parentCode = parentCode;
    }

    /**
     * 上级卷 代码
     */
    private Integer parentCode;

    public int getVolumeCode() {
        return volumeCode;
    }

    public void setVolumeCode(int volumeCode) {
        this.volumeCode = volumeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
