package com.microwise.cybertron.bean;

import java.util.Date;

/**
 * 档案附件实体类
 *
 * @author li.jianfie
 * @date 2014-07-16
 */
public class Attachment {

    /**
     * UUID
     */
    private String uuid;

    /**
     * 档案ID
     */
    private Record record;

    /**
     * 档案路径
     */
    private String path;

    /**
     * 上传文件名
     */
    private String uploadName;

    /**
     * 上传人
     */
    private int userId;

    /**
     * 上传日期
     */
    private Date uploadDate;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUploadName() {
        return uploadName;
    }

    public void setUploadName(String uploadName) {
        this.uploadName = uploadName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

}
