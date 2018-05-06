package com.microwise.orion.bean;

import java.util.Date;
import java.util.List;

/**
 * 修复记录日志
 *
 * @author 王耕
 * @date 15-9-23
 */
public class RepairLog {
    /**
     * 主键ID
     */
    private int id;
    /**
     * 修复记录
     */
    private RepairRecord repairRecord;
    /**
     * 保护修复人
     */
    private String repairPerson;
    /**
     * 时间日期
     */
    private Date stamp;
    /**
     * 日志
     */
    private String log;

    /**
     * 保护修复人name
     */
    private List<String> repairPersonName;

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

    public String getRepairPerson() {
        return repairPerson;
    }

    public void setRepairPerson(String repairPerson) {
        this.repairPerson = repairPerson;
    }

    public Date getStamp() {
        return stamp;
    }

    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public List<String> getRepairPersonName() {
        return repairPersonName;
    }

    public void setRepairPersonName(List<String> repairPersonName) {
        this.repairPersonName = repairPersonName;
    }
}
