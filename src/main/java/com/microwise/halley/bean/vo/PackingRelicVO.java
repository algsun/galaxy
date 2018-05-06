package com.microwise.halley.bean.vo;

/**
 * 文物装箱单明细
 *
 * @author li.jianfei
 * @date 2013-09-26
 */
public class PackingRelicVO {

    /**
     * id
     */
    private int id;

    /**
     * 集装箱ID
     */
    private int packingId;

    /**
     * 文物总登记号
     */
    private String totalCode;

    /**
     * 文物名称
     */
    private String name;

    /**
     * 级别
     */
    private String level;

    /**
     * 质地
     */
    private String texture;

    /**
     * 时代
     */
    private String era;

    /**
     * 是否绑定标签
     */
    private boolean hasTag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPackingId() {
        return packingId;
    }

    public void setPackingId(int packingId) {
        this.packingId = packingId;
    }

    public String getTotalCode() {
        return totalCode;
    }

    public void setTotalCode(String totalCode) {
        this.totalCode = totalCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getEra() {
        return era;
    }

    public void setEra(String era) {
        this.era = era;
    }

    public boolean isHasTag() {
        return hasTag;
    }

    public void setHasTag(boolean hasTag) {
        this.hasTag = hasTag;
    }
}
