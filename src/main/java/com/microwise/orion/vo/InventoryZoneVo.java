package com.microwise.orion.vo;

import com.microwise.orion.bean.InventoryZone;

/**
 * 按区域 盘点统计信息 vo 实体
 *
 * @author xubaoji
 * @date 2013-6-3
 */
public class InventoryZoneVo extends InventoryZone {

    /**
     * 区域名称
     */
    private String zoneName;

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

}
