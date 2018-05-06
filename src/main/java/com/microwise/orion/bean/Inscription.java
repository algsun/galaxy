package com.microwise.orion.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;

/**
 * 文物铭文题跋信息实体
 *
 * @author xubaoji
 * @date 2013-5-13
 * @check 2013-06-04 zhangpeng svn:3448
 */
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Inscription {

    /**
     * 类型标识
     */
    public static final String INSCRIPTION = "inscription";

    /**
     * 铭文题跋 id 编号
     */
    private Integer id;

    /**
     * 文物信息实体
     */
    @JsonIgnore
    private Relic relic;

    /**
     * 铭文题跋内容
     */
    @Expose
    private String info;

    /**
     * 铭文题跋 图片路径
     */
    @Expose
    private String path;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Relic getRelic() {
        return relic;
    }

    public void setRelic(Relic relic) {
        this.relic = relic;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
