package com.microwise.orion.vo;

/**
 * 提供http 服务的 relicVo 文物实体
 *
 * @author xubaoji
 * @date 2013-5-22
 */
public class RelicVo {

    /**
     * 文物id 编号
     */
    private Integer id;

    /**
     * 总登记号
     */
    private String totalCode;

    /**
     * 标签号
     */
    private String tagCode;

    /**
     * 名称
     */
    private String name;

    /**
     * 区域
     */
    private String zoneId;

    /**
     * 站点编号
     */
    private String siteId;

    /**
     * 文物状态：0、在库 ;1、待出库；2、出库
     */
    private Integer state;

    /**
     * 件数
     */
    private int count;

    /**
     * 级别
     */
    private String level;


    private String texture;

    /**
     * 是否有电子标签 0:没有 1：有
     */
    private Integer hasTag;

    public String getTotalCode() {
        return totalCode;
    }

    public void setTotalCode(String totalCode) {
        this.totalCode = totalCode;
    }

    public String getTagCode() {
        return tagCode;
    }

    public void setTagCode(String tagCode) {
        this.tagCode = tagCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getHasTag() {
        return hasTag;
    }

    public void setHasTag(Integer hasTag) {
        this.hasTag = hasTag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }
}
