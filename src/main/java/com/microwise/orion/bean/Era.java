package com.microwise.orion.bean;

/**
 * 文物时代 实体对象
 *
 * @author xubaoji
 * @date 2013-5-14
 * @check 2013-06-04 zhangpeng svn:3424
 */
public class Era {

    /**
     * 时代id
     */
    private Integer id;

    /**
     * 时代 名称
     */
    private String name;

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


}
