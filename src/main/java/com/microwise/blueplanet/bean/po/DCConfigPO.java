package com.microwise.blueplanet.bean.po;

/**
 * 信息发布系统配置实体类
 *
 * @author wang.geng
 * @date 14-1-22 上午10:10
 */
public class DCConfigPO {

    /**
     * 主键，关联到布局表的layoutId
     */
    private String related_layoutId;

    /**
     * 背景图片路径
     */
    private String url;

    public String getRelated_layoutId() {
        return related_layoutId;
    }

    public void setRelated_layoutId(String related_layoutId) {
        this.related_layoutId = related_layoutId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
