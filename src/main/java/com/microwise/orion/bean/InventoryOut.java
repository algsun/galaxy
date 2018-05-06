package com.microwise.orion.bean;

/**
 * 盘点出库文物 实体
 *
 * @author xubaoji
 * @date 2013-5-25
 * @check 2013-06-04 zhangpeng svn:3732
 */
public class InventoryOut {

    /**
     * id编号
     */
    private Integer id;

    /**
     * 文物实体
     */
    private Relic relic;

    /**
     * 出库事件实体
     */
    private OutEvent outEvent;

    /**
     * 盘点记录
     */
    private Inventory inventory;

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

    public OutEvent getOutEvent() {
        return outEvent;
    }

    public void setOutEvent(OutEvent outEvent) {
        this.outEvent = outEvent;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

}
