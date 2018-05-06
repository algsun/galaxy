package com.microwise.phoenix.dao;

import com.microwise.phoenix.bean.vo.ZoneStability;

import java.util.Date;
import java.util.List;

/**
 * 区域稳定性Dao
 *
 * @author zhangpeng
 * @date 13-8-7
 * @check @wang.geng 2013年8月14日 #4915
 */
public interface ZoneStabilityDao {

    /**
     * 查询站点下有指定指标的设备
     *
     * @param siteId
     * @param sensorPhysicalId
     * @return
     */
    public List<ZoneStability> findLocations(String siteId, int sensorPhysicalId);

    /**
     * 查询站点区域下有指定指标的设备
     *
     * @param siteId
     * @param sensorPhysicalId
     * @return
     */
    public List<ZoneStability> findLocationsByZone(String siteId, String zoneId, int sensorPhysicalId);

    /**
     * 查询当前设备指定时间段的方差
     *
     * @param locationIds      位置点id集合
     * @param sensorPhysicalid 监测指标id
     * @param startDate        开始时间
     * @param endDate          结束时间
     * @return List<Float> 设备列表对应的方差列表
     * @author zhangpeng
     * @date 13-8-7
     */
    public List<Float> findVariances(List<String> locationIds, int sensorPhysicalid, Date startDate, Date endDate);

}
