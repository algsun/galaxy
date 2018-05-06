package com.microwise.orion.bean;


import java.util.Date;

/**
 * 专家点评实体类
 *
 * @author bai.weixing
 * @since 2017/6/22.
 */
public class Comment {
    /**
     * 自增id
     */
    private int id;

    /**
     * 组织单位
     */
    private String organization;

    /**
     * 专家姓名
     */
    private String expert;

    /**
     * 验收意见
     */
    private String advice;

    /**
     * 评价日期
     */
    private Date date = new Date();

    /**
     * 修复记录id
     */
    private int  repairRecordId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getExpert() {
        return expert;}

    public void setExpert(String expert) {
        this.expert = expert;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public int getRepairRecordId() {
        return repairRecordId;
    }

    public void setRepairRecordId(int repairRecordId) {
        this.repairRecordId = repairRecordId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
