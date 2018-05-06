package com.microwise.saturn.bean;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by lijianfei on 15-3-19.
 */
public class Affair {

    /**
     * id
     */
    private int id;

    /**
     * 事务类型
     */
    private int type;

    /**
     * 事务标题
     */
    private String title;

    /**
     * 姓名
     */
    private String incharge;

    /**
     * 期刊
     */
    private String magazine;

    /**
     * 发表日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publicDate;

    /**
     * 期刊来源
     */
    private String magazineSrc;

    /**
     * 项目类型
     */
    private int projectType;

    /**
     * 项目级别
     */
    private int projectLevel;

    /**
     * 进展情况
     */
    private String progress;

    /**
     * 获奖情况
     */
    private String prize;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 站点ID
     */
    private String siteId;

    /**
     * 类型数量
     */
    private int totalCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIncharge() {
        return incharge;
    }

    public void setIncharge(String incharge) {
        this.incharge = incharge;
    }

    public String getMagazine() {
        return magazine;
    }

    public void setMagazine(String magazine) {
        this.magazine = magazine;
    }

    public Date getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(Date publicDate) {
        this.publicDate = publicDate;
    }

    public String getMagazineSrc() {
        return magazineSrc;
    }

    public void setMagazineSrc(String magazineSrc) {
        this.magazineSrc = magazineSrc;
    }

    public int getProjectType() {
        return projectType;
    }

    public void setProjectType(int projectType) {
        this.projectType = projectType;
    }

    public int getProjectLevel() {
        return projectLevel;
    }

    public void setProjectLevel(int projectLevel) {
        this.projectLevel = projectLevel;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
