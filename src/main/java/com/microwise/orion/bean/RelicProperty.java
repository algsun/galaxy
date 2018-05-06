package com.microwise.orion.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;

/**
 * 文物属性 信息实体对象
 *
 * @author xubaoji
 * @date 2013-5-13
 * @check 2013-06-04 zhangpeng svn:3493
 */
public class RelicProperty {

    /**
     * 属性信息id
     */
    private Integer id;

    /**
     * 属性实体
     */
    private Property property;

    /**
     * 文物信息实体
     */
    @JsonIgnore
    private Relic relic;

    /**
     * 属性值
     */
    @Expose
    private String propertyValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Relic getRelic() {
        return relic;
    }

    public void setRelic(Relic relic) {
        this.relic = relic;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

}
