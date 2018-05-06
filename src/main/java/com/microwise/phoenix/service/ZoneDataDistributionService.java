package com.microwise.phoenix.service;

import com.microwise.phoenix.bean.po.LocationDate;

import java.util.Date;
import java.util.List;

/**
 * 区域数据分布Service
 * author : chenyaofei
 * date : 2016-10-10
 */
public interface ZoneDataDistributionService {

    /**
     * 获取区域位置点指定监测指标的动态数据
     * @param zoneId 区域id
     * @param sensorId 监测指标id
     * @param startDate 开始时间
     * @param endDate 结束时间
     * author : chenyaofei
     * date : 2016-10-10
     */
    public List<LocationDate> findDataDistribution(String zoneId, int sensorId, Date startDate, Date endDate);


    /**
     * 获取位置点指定监测指标集合的四分位数
     * @param locationId 位置点id
     * @param sensorId 监测指标id
     * @param startDate  开始时间
     * @param endDate 结束时间
     * @param valuePrecision 保留小数位数
     * author : chenyaofei
     * date : 2016-10-10
     */
    public List<Double> findFourQuantile(String locationId, int sensorId, Date startDate, Date endDate, int valuePrecision);
}
