/**
 *
 */
package com.microwise.blackhole.bean;

/**
 * 子系统对象Bean
 *
 * @author zhangpeng
 * @date 2012-11-21
 */
public class Subsystem {

    /**
     * 业务系统id
     */
    private int subsystemId;

    /**
     * 业务系统名称
     */
    private String subsystemName;

    /**
     * 业务系统英文名称
     */
    private String subsystemNameEn;

    /**
     * 业务系统代号
     */
    private String subsystemCode;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否启用
     */
    private boolean enable;

    /**
     * 业务系统是否启用
     */
    private int state;

    public int getSubsystemId() {
        return subsystemId;
    }

    public void setSubsystemId(int subsystemId) {
        this.subsystemId = subsystemId;
    }

    public String getSubsystemName() {
        return subsystemName;
    }

    public void setSubsystemName(String subsystemName) {
        this.subsystemName = subsystemName;
    }

    public String getSubsystemCode() {
        return subsystemCode;
    }

    public void setSubsystemCode(String subsystemCode) {
        this.subsystemCode = subsystemCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getSubsystemNameEn() {
        return subsystemNameEn;
    }

    public void setSubsystemNameEn(String subsystemNameEn) {
        this.subsystemNameEn = subsystemNameEn;
    }
}
