package com.microwise.orion.bean;

/**
 * 盘点标签文物临时信息 实体
 *
 * @author xubaoji
 * @date 2013-5-25
 * @check 2013-06-04 zhangpeng svn:3750
 */
public class InventoryTag {

    /**
     * 文物 id
     */
    private Integer relicId;

    /**
     * 盘点id
     */
    private Integer inventoryId;

    /**
     * @param relicId
     * @param inventoryId
     */
    public InventoryTag(Integer relicId, Integer inventoryId) {
        super();
        this.relicId = relicId;
        this.inventoryId = inventoryId;
    }

    public InventoryTag() {
        super();
    }

    public Integer getRelicId() {
        return relicId;
    }

    public void setRelicId(Integer relicId) {
        this.relicId = relicId;
    }

    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

}
