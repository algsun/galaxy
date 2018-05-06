package com.microwise.blueplanet.bean.po;

import java.util.Date;

/**
 * @author 王耕
 * @date 15-6-10
 */
public class ThreeDimensionalPO {
    /** 自增id */
    private int id;

    /** 站点ID */
    private String siteId;

    /** 上传时间 */
    private Date uploadtime;

    /** 模型文件上传路径 */
    private String path;

    /** 描述 */
    private String remark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public Date getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(Date uploadtime) {
        this.uploadtime = uploadtime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
