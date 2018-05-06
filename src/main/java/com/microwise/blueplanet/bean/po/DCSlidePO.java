package com.microwise.blueplanet.bean.po;

/**
 * 信息发布节目实体类.
 *
 * @author wang.geng
 * @date 14-1-22 上午10:16
 */
public class DCSlidePO {

    /**
     * 主键，自增，序号
     */
    private int id;

    /**
     * 外键，关联到布局表的layoutId
     */
    private String relatedLayoutId;

    /**
     * 外键，关联到控件表的选择器ID
     */
    private String relatedItemId;

    /**
     * 文字描述标题
     */
    private String title;

    /**
     * 图片路径
     */
    private String url;

    /**
     * 播放时长，默认10秒
     */
    private int refresh = 10;

    /**
     * 关联的位置点
     */
    private String locationId;

    /**
     * 文字描述
     */
    private String detail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRelatedLayoutId() {
        return relatedLayoutId;
    }

    public void setRelatedLayoutId(String relatedLayoutId) {
        this.relatedLayoutId = relatedLayoutId;
    }

    public String getRelatedItemId() {
        return relatedItemId;
    }

    public void setRelatedItemId(String relatedItemId) {
        this.relatedItemId = relatedItemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getRefresh() {
        return refresh;
    }

    public void setRefresh(int refresh) {
        this.refresh = refresh;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
