package com.microwise.orion.vo;


/**
 * 盘点出库vo
 *
 * @author xubaoji
 * @date 2013-5-28
 */
public class InventoryOutVo {

    private Integer id;

    private Integer relicId;

    private String totalCode;

    private String relicName;

    private Integer eventId;

    private String eventName;

    public Integer getId() {
        return id;
    }

    public InventoryOutVo() {
        super();
    }

    /**
     * @param relicId
     * @param totalCode
     * @param relicName
     * @param eventId
     * @param eventName
     */
    public InventoryOutVo(Integer relicId, String totalCode, String relicName,
                          Integer eventId, String eventName) {
        super();
        this.relicId = relicId;
        this.totalCode = totalCode;
        this.relicName = relicName;
        this.eventId = eventId;
        this.eventName = eventName;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRelicId() {
        return relicId;
    }

    public void setRelicId(Integer relicId) {
        this.relicId = relicId;
    }

    public String getTotalCode() {
        return totalCode;
    }

    public void setTotalCode(String totalCode) {
        this.totalCode = totalCode;
    }

    public String getRelicName() {
        return relicName;
    }

    public void setRelicName(String relicName) {
        this.relicName = relicName;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

}
