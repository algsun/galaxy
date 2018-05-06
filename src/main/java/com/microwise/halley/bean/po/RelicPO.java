package com.microwise.halley.bean.po;

/**
 * 外展文物实体类
 *
 * @author wanggeng
 * @date 13-9-25 下午4:09
 */
public class RelicPO {

    /** 序列号id */
    private int id;

    /** 关联的装箱单ID h_packing _list.id */
    private int packingId;

    /** 文物的总登记号*/
    private String totalCode;

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
}
