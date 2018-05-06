package com.microwise.blueplanet.bean.vo;

import com.microwise.blueplanet.bean.po.ZoneAvgDataPO;

/**
 * 区域均值统计VO
 *
 * @author li.jianfei
 * @since 2017/5/27
 */
public class ZoneAvgDataVO extends ZoneAvgDataPO {

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
