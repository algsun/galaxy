package com.microwise.saturn.bean;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 文献资料
 *
 * @author li.jianfei
 * @date 2015-03-13
 */
public class Literature {

    private int id;
    /**
     * 资料分类
     */
    private String catalog;

    /**
     * 文献名称
     */
    private String title;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 期刊
     */
    private String magazine;

    /**
     * 期别
     */
    private String periodical;

    /**
     * 作者
     */
    private String author;

    /**
     * 发表日期
     */
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private Date publicDate;

    /**
     * 关键字
     */
    private String keywords;

    /**
     * 创建时间
     */
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private Date createTime;

    /**
     * 收录人
     */
    private String createBy;

    /**
     * 站点ID
     */
    private String siteId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getMagazine() {
        return magazine;
    }

    public void setMagazine(String magazine) {
        this.magazine = magazine;
    }

    public String getPeriodical() {
        return periodical;
    }

    public void setPeriodical(String periodical) {
        this.periodical = periodical;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(Date publicDate) {
        this.publicDate = publicDate;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
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

}
