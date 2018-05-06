package com.microwise.orion.bean;

/**
 * 文物级别实体
 *
 * @author xubaoji
 * @date 2013-5-14
 * @check 2013-06-04 zhangpeng svn:3424
 */
public class Level {

    /**
     * 文物级别 id
     */
    private Integer id;

    /**
     * 文物级别名称
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
