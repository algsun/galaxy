package com.microwise.orion.vo;

import java.util.Map;

/**
 * @author xiedeng
 * @date 14-4-21
 */
public class FullTextSearchRelicVo {
    /**
     * id 编号
     */
    protected String id;

    /**
     * 总登记号
     */
    protected String totalCode;

    /**
     * 编目号
     */
    protected String catalogCode;

    /**
     * 分类号
     */
    protected String typeCode;

    /**
     * 名称
     */
    protected String name;

    /**
     * 时代
     */
    protected String era;

    /**
     * 件数
     */
    protected Integer count;

    /**
     * 级别
     */
    protected String level;

    /**
     * 质地
     */
    protected String texture;

    /**
     * 照片地址
     */
    protected String photo;

    /**
     * 文物状态
     */
    protected String status;

    /**
     * 库存位次
     */
    protected String relicLocation;
    /**
     * 高亮显示文字
     */
    protected Map<String, String> highlightTexts;

    public String getTotalCode() {
        return totalCode;
    }

    public void setTotalCode(String totalCode) {
        this.totalCode = totalCode;
    }

    public String getCatalogCode() {
        return catalogCode;
    }

    public void setCatalogCode(String catalogCode) {
        this.catalogCode = catalogCode;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEra() {
        return era;
    }

    public void setEra(String era) {
        this.era = era;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRelicLocation() {
        return relicLocation;
    }

    public void setRelicLocation(String relicLocation) {
        this.relicLocation = relicLocation;
    }

    public Map<String, String> getHighlightTexts() {
        return highlightTexts;
    }

    public void setHighlightTexts(Map<String, String> highlightTexts) {
        this.highlightTexts = highlightTexts;
    }
}
