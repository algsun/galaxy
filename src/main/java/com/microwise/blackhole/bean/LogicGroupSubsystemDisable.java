/**
 *
 */
package com.microwise.blackhole.bean;

/**
 * 子系统对象Bean
 *
 * @author liuzhu
 * @date 2014-3-25
 */
public class LogicGroupSubsystemDisable {

    /**
     * uuid
     */
    private String id;

    /**
     * 站点组id
     */
    private int logicGroupId;

    /**
     * 业务系统id
     */
    private int subsystemId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLogicGroupId() {
        return logicGroupId;
    }

    public void setLogicGroupId(int logicGroupId) {
        this.logicGroupId = logicGroupId;
    }

    public int getSubsystemId() {
        return subsystemId;
    }

    public void setSubsystemId(int subsystemId) {
        this.subsystemId = subsystemId;
    }
}
