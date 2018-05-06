package com.microwise.phoenix.bean.vo;

import java.util.Map;

/**
 * 用户操作记录实体
 *
 * @author xu.baoji
 * @date 2013-8-16
 */
public class UserOperate {

    /**
     * 功能名称
     */
    private String functionName;

    /**
     * 功能记录次数
     */
    private Integer count;

    /**
     * 该功能下 操作记录 map key：操作名  value：操作次数
     */
    private Map<String, Integer> operates;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public Map<String, Integer> getOperates() {
        return operates;
    }

    public void setOperates(Map<String, Integer> operates) {
        this.operates = operates;
    }

}
