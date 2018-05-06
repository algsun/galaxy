package com.microwise.phoenix.service;

import com.microwise.phoenix.bean.vo.ZoneStability;

import java.util.Date;
import java.util.List;

/**
 * 区域稳定性Service
 *
 * @author zhangpeng
 * @date 13-8-7
 * @check @wang.geng 2013年8月14日 #4950
 */
public interface ZoneStabilityService {

    /**
     * 按年月日条件查询站点下所有区域的稳定性对比
     *
     * @param siteId           站点编号
     * @param sensorPhysicalid 监测指标id
     * @param date             时间参数
     * @param type             类型（年/月/日）
     * @return List<ZoneStability> 区域稳定性对象列表
     * @author zhangpeng
     * @date 13-8-6
     */
    public List<ZoneStability> findZoneStability(String siteId, int sensorPhysicalid, Date date, int type);

    /**
     * 按年月日条件查询站点下区域的子区域稳定性对比
     *
     * @param siteId           站点编号
     * @param sensorPhysicalid 监测指标id
     * @param date             时间参数
     * @param type             类型（年/月/日）
     * @return List<ZoneStability> 区域稳定性对象列表
     * @author 许月希
     * @date 14-12-30
     */
    public List<ZoneStability> findZoneStabilityByZone(String siteId, String zoneId, int sensorPhysicalid, Date date, int type);
}
