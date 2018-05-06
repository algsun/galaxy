package com.microwise.phoenix.dao;

import com.microwise.phoenix.bean.vo.ZonePeakValue;

import java.util.Date;

/**
 * 区域均峰值Dao
 *
 * @author xuyuexi
 * @date 14-9-26
 */
public interface ZonePeakValueDao {

    /**
     * 按月和区域条件查询站点下区域的峰值
     *
     * @param sensorPhysicalId 监测指标id
     * @author xuyuexi
     * @date 14-10-9
     */
    public ZonePeakValue findZonePeakValues(String siteId, int sensorPhysicalId, Date start, Date end, int month, int type);
}
