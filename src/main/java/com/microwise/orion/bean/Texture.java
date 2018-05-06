package com.microwise.orion.bean;

/**
 * 文物质地实体对象
 *
 * @author xubaoji
 * @date 2013-5-14
 * @check 2013-06-04 zhangpeng svn:3424
 */
public class Texture {

    /**
     * 时代 id 编号
     */
    private Integer id;

    /**
     * 时代名称
     */
    private String name;

    private int deleteAble;

    private String enName;

    /**
     * 是否设置阈值
     */
    private boolean setting;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDeleteAble() {
        return deleteAble;
    }

    public void setDeleteAble(int deleteAble) {
        this.deleteAble = deleteAble;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public boolean getSetting() {
        return setting;
    }

    public void setSetting(boolean setting) {
        this.setting = setting;
    }
}

