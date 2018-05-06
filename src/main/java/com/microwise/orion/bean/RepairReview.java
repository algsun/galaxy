package com.microwise.orion.bean;

import java.util.Date;

/**
 * 修复意见
 *
 * @author liuzhu
 * @date 2015-12-8
 */
public class RepairReview {

    /**
     * 自增id
     */
    private int id;

    /**
     *关联到修复记录
     */
    private RepairRecord repairRecord;

    /**
     * 专家验收意见
     */
    private String expertsAcceptance;


    /**
     * 时间
     */
    private Date acceptanceDate;

    /**
     * 组织单位
     */
    private String organizationUnit;

    /**
     * 专家名单
     */
    private String expertsList;

    /**
     * 验收意见
     */
    private String acceptanceIdea;

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

    public String getExpertsAcceptance() {
        return expertsAcceptance;
    }

    public void setExpertsAcceptance(String expertsAcceptance) {
        this.expertsAcceptance = expertsAcceptance;
    }

    public Date getAcceptanceDate() {
        return acceptanceDate;
    }

    public void setAcceptanceDate(Date acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
    }

    public String getOrganizationUnit() {
        return organizationUnit;
    }

    public void setOrganizationUnit(String organizationUnit) {
        this.organizationUnit = organizationUnit;
    }

    public String getExpertsList() {
        return expertsList;
    }

    public void setExpertsList(String expertsList) {
        this.expertsList = expertsList;
    }

    public String getAcceptanceIdea() {
        return acceptanceIdea;
    }

    public void setAcceptanceIdea(String acceptanceIdea) {
        this.acceptanceIdea = acceptanceIdea;
    }
}
