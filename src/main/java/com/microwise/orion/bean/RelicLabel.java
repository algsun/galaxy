package com.microwise.orion.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Set;

/**
 * 文物标签
 *
 * @author li.jianfei
 * @date 2014-04-25
 */
public class RelicLabel implements Serializable {

    /**
     * 唯一标识
     */
    private int id;

    /**
     * 标签名称
     */
    @Expose
    private String name;

    /**
     * 文物
     */
    @JsonIgnore
    private Set<Relic> relics;


    public Set<Relic> getRelics() {
        return relics;
    }

    public void setRelics(Set<Relic> relics) {
        this.relics = relics;
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
}
