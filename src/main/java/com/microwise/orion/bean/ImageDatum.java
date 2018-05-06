package com.microwise.orion.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.microwise.blackhole.bean.User;

import java.io.File;
import java.util.Date;

/**
 * 影像登记bean
 *
 * @author liuzhu
 * @date 2015-11-10
 */
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ImageDatum {

    /**
     * 自增id
     */
    private int id;

    /**
     * 关联修复记录id
     */
    private RepairRecord repairRecord;

    /**
     * 内容描述
     */
    private String content;

    /**
     * 介质
     */
    private String media;

    /**
     * 时长(min)
     */
    private int duration;

    /**
     * 提交人
     */
    private User submitPerson;

    /**
     * 采集时间
     */
    private Date stamp;

    /**
     * 文件名
     */
    private String filePath;

    private File image;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public User getSubmitPerson() {
        return submitPerson;
    }

    public void setSubmitPerson(User submitPerson) {
        this.submitPerson = submitPerson;
    }

    public Date getStamp() {
        return stamp;
    }

    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }
}
