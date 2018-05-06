package com.microwise.blackhole.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 部门实体
 *
 * @author xu.baoji
 * @date 2013-8-17
 */
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Department {

    /**
     * 部门id
     */
    private int id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 逻辑站点编号
     */
    private LogicGroup logicGroup;

    public Department() {
        super();
    }

    public Department(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LogicGroup getLogicGroup() {
        return logicGroup;
    }

    public void setLogicGroup(LogicGroup logicGroup) {
        this.logicGroup = logicGroup;
    }

}
