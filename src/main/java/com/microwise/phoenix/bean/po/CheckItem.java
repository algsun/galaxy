package com.microwise.phoenix.bean.po;

/**
 * 系统健康检测统计项实体 po
 *
 * @author xu.baoji
 * @date 2013-7-25
 */
public class CheckItem {

    /**
     * 检测项id
     */
    private Integer id;

    /**
     * 检测项名称
     */
    private String name;

    /**
     * 检测项，检测url地址
     */
    private String checkUrl;

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

    public String getCheckUrl() {
        return checkUrl;
    }

    public void setCheckUrl(String checkUrl) {
        this.checkUrl = checkUrl;
    }

}
