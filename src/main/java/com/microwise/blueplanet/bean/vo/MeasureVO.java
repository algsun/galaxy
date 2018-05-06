package com.microwise.blueplanet.bean.vo;

import java.util.Date;

/**
 * 措施vo类
 *
 * @author liuzhu
 * @date 14-3-11
 */
public class MeasureVO {

    /**
     * 措施id
     */
    private String id;

    /**
     * 措施描述
     */
    private String description;

    /**
     * 站点id
     */
    private String siteId;

    /**
     * 创建时间
     */
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
