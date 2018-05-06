package com.microwise.orion.bean;

import java.util.Date;

/**
 * 自评估bean
 *
 * @author liuzhu
 * @date 2015-11-10
 */
public class RepairAssessment {

    /**
     * 自增id
     */
    private int id;

    /**
     * 关联修复记录id
     */
    private RepairRecord repairRecord;

    /**
     * 自评估意见
     */
    private String selfAssessment;

    /**
     * 日期
     */
    private Date stamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RepairRecord getRepairRecord() {
        return repairRecord;
    }

    public void setRepairRecord(RepairRecord repairRecord) {
        this.repairRecord = repairRecord;
    }

    public String getSelfAssessment() {
        return selfAssessment;
    }

    public void setSelfAssessment(String selfAssessment) {
        this.selfAssessment = selfAssessment;
    }

    public Date getStamp() {
        return stamp;
    }

    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }
}
