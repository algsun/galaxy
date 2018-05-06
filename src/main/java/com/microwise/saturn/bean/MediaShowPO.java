package com.microwise.saturn.bean;

import java.util.Date;

/**
 * 展示类别
 */
public class MediaShowPO {

    /** 展示类别ID */
    private int id;

    /** 展示类别标题*/
    private String title;

    /** 展示类别摘要 */
    private String remark;

    /** 图片路径 */
    private String indexImage;

    /** 录入时间 */
    private Date createOn;

    /** 录入人 */
    private String createBy;

    /** 站点编号 */
    private String siteCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIndexImage() {
        return indexImage;
    }

    public void setIndexImage(String indexImage) {
        this.indexImage = indexImage;
    }

    public Date getCreateOn() {
        return createOn;
    }

    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }
}
