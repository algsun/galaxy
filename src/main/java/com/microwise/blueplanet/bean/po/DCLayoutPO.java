package com.microwise.blueplanet.bean.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据中心布局实体类.
 *
 * @author wang.geng
 * @date 13-12-6 上午10:34
 */
public class DCLayoutPO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 布局UUID
     */
    protected String layoutId;

    /**
     * 描述
     */
    protected String description;

    /**
     * 创建时间
     */
    protected Date createTime;

    /**
     * 站点ID
     */
    protected String siteId;

    /**
     * 站点组id
     */
    protected int logicGroupId;

    public DCLayoutPO() {
        super();
    }

    public DCLayoutPO(String layoutId) {
        this.layoutId = layoutId;
    }

    public DCLayoutPO(String layoutId, String description, int logicGroupId) {
        this.layoutId = layoutId;
        this.description = description;
        this.logicGroupId = logicGroupId;
    }

    public DCLayoutPO(String layoutId, String description, String siteId) {
        this.layoutId = layoutId;
        this.description = description;
        this.siteId = siteId;
    }

    public String getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(String layoutId) {
        this.layoutId = layoutId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public int getLogicGroupId() {
        return logicGroupId;
    }

    public void setLogicGroupId(int logicGroupId) {
        this.logicGroupId = logicGroupId;
    }
}
