package com.microwise.orion.bean;

/**
 * @author 王耕
 * @date 15-9-9
 */
public class RepairReason {
    /**
     * 主键，自增ID
     */
    private Integer id;
    /**
     * 修复原因
     */
    private String reason;

    private String siteId;

    public RepairReason(){

    }

    public RepairReason(int id){
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }
}
