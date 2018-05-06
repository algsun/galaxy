package com.microwise.orion.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;

import java.util.Date;

/**
 * 文档 挂接 实体 （不是文物档案中附件字段）
 *
 * @author xubaoji
 * @date 2013-5-13
 * @check 2013-06-04 zhangpeng svn:3606
 */
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Attachment {

    /**
     * 编号
     */
    private Integer id;

    /**
     * 文物基本信息实体
     */
    @JsonIgnore
    private Relic relic;

    /**
     * 文档上传时间
     */
    @Expose
    private Date attachmentDate;

    /**
     * 文档存储路径
     */
    @Expose
    private String path;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Relic getRelic() {
        return relic;
    }

    public void setRelic(Relic relic) {
        this.relic = relic;
    }

    public Date getAttachmentDate() {
        return attachmentDate;
    }

    public void setAttachmentDate(Date attachmentDate) {
        this.attachmentDate = attachmentDate;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
