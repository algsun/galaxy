package com.microwise.phoenix.service;

import com.microwise.phoenix.bean.vo.ZonePeakValue;

import java.util.Date;
import java.util.List;

/**
 * 区域峰值Service
 *
 * @author xuyuexi
 * @date 14-9-26
 */
public interface ZonePeakValueService {


    /**
     * 按年条件查询站点下区域的峰值
     *
     * @param siteId           站点编号
     * @param sensorPhysicalid 监测指标id
     * @param date             时间参数
     * @param type             类型（全部室内室外）
     * @return List<ZonePeakValue> 区域稳定性对象列表
     * @author xuyuexi
     * @date 14-9-26
     */
    public List<ZonePeakValue> findZonePeakValue(String siteId, int sensorPhysicalid, Date date, int type);

}
