package com.microwise.saturn.bean;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * 展示内容
 */
public class MediaDetailPO {

    public MediaDetailPO(){

    }

    public MediaDetailPO(int id){
        this.id = id;
    }

    /** 展示内容ID */
    private int id;

    /** 展示类别的ID */
    private int mediaShowId;

    /** 主展示内容标题 */
    private String detailTitle;

    /** 主图片展示内容 */
    private String detailContent;

    /** 主图片 */
    private String detailImage;

    /** 副展示内容标题 */
    private String detailSubTitle;

    /** 副图片展示内容 */
    private String detailSubDesc;

    /** 副图片 */
    private String detailSubImage;

    /** 录入时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createOn;

    /** 录入人 */
    private String createBy;

    /**  */
    private int line;

    /** 启用停用.1:启用,2：禁用 */
    private int enable;

    /** 发布时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date pubDate;

    /** */
    private String material;

    /** 主副图片文件 */
    private MultipartFile detailImageFile;
    private MultipartFile detailSubImageFile;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMediaShowId() {
        return mediaShowId;
    }

    public void setMediaShowId(int mediaShowId) {
        this.mediaShowId = mediaShowId;
    }

    public String getDetailTitle() {
        return detailTitle;
    }

    public void setDetailTitle(String detailTitle) {
        this.detailTitle = detailTitle;
    }

    public String getDetailContent() {
        return detailContent;
    }

    public void setDetailContent(String detailContent) {
        this.detailContent = detailContent;
    }

    public String getDetailImage() {
        return detailImage;
    }

    public void setDetailImage(String detailImage) {
        this.detailImage = detailImage;
    }

    public String getDetailSubTitle() {
        return detailSubTitle;
    }

    public void setDetailSubTitle(String detailSubTitle) {
        this.detailSubTitle = detailSubTitle;
    }

    public String getDetailSubDesc() {
        return detailSubDesc;
    }

    public void setDetailSubDesc(String detailSubDesc) {
        this.detailSubDesc = detailSubDesc;
    }

    public String getDetailSubImage() {
        return detailSubImage;
    }

    public void setDetailSubImage(String detailSubImage) {
        this.detailSubImage = detailSubImage;
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

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public MultipartFile getDetailImageFile() {
        return detailImageFile;
    }

    public void setDetailImageFile(MultipartFile detailImageFile) {
        this.detailImageFile = detailImageFile;
    }

    public MultipartFile getDetailSubImageFile() {
        return detailSubImageFile;
    }

    public void setDetailSubImageFile(MultipartFile detailSubImageFile) {
        this.detailSubImageFile = detailSubImageFile;
    }
}

