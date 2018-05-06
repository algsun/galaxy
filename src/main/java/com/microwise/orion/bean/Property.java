package com.microwise.orion.bean;

/**
 * 文物属性实体对象
 *
 * @author xubaoji
 * @date 2013-5-14
 * @check 2013-06-04 zhangpeng svn:3448
 */
public class Property {

    /**
     * 属性id 编号
     */
    private Integer id;

    /**
     * 属性中文名称
     */
    private String cnName;

    /**
     * 属性英文名称
     */
    private String enName;

    /**
     * 属性隶属于
     */
    private Integer belong;

    /**
     * 属性类型
     */
    private Integer propertyType;

    /**
     * 排序号
     */
    private int orderNum;

    /**
     * 是否显示
     */
    private boolean visible;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public Integer getBelong() {
        return belong;
    }

    public void setBelong(Integer belong) {
        this.belong = belong;
    }

    public Integer getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(Integer propertyType) {
        this.propertyType = propertyType;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
