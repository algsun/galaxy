package com.microwise.cybertron.bean;

import java.util.Date;
import java.util.Set;

/**
 * 档案实体类
 *
 * @author li.jianfei
 * @date 2014-07-16
 */
public class Record {
    public Record() {
    }

    public Record(String uuid) {
        this.uuid = uuid;
    }

    /**
     * UUID
     */
    private String uuid;

    /**
     * 站点ID
     */
    private String siteId;

    /**
     * 档号
     */
    private String recordCode;

    /**
     * 案卷提名
     */
    private String name;

    /**
     * 密级
     */
    private int stateSecrets;

    /**
     * 立卷单位名称
     */
    private String department;

    /**
     * 立卷日期
     */
    private Date establishDate;

    /**
     * 卷编号
     */
    private int volumeCode;

    /**
     * 附件集合
     */
    private Set<Attachment> attachments;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getRecordCode() {
        return recordCode;
    }

    public void setRecordCode(String recordCode) {
        this.recordCode = recordCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStateSecrets() {
        return stateSecrets;
    }

    public void setStateSecrets(int stateSecrets) {
        this.stateSecrets = stateSecrets;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getEstablishDate() {
        return establishDate;
    }

    public void setEstablishDate(Date establishDate) {
        this.establishDate = establishDate;
    }

    public int getVolumeCode() {
        return volumeCode;
    }

    public void setVolumeCode(int volumeCode) {
        this.volumeCode = volumeCode;
    }

    public Set<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<Attachment> attachments) {
        this.attachments = attachments;
    }
}
