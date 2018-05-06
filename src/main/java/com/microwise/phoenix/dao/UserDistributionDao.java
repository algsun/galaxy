package com.microwise.phoenix.dao;

import java.util.List;
import java.util.Map;

public interface UserDistributionDao {

    /**
     * 获取区域信息
     *
     * @return 区域信息
     */
    public List<Map<String, Object>> getDistrictInfo(String siteId);

    /**
     * 获取横轴数据
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 横轴数据
     */
    public List<Map<String, Object>> getUserDistriData(long startTime, long endTime, String siteId);

    /**
     * 获取活动密度最高的区域及次数
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 活动密度最高的区域及次数
     */
    public Map<String, Object> getMaxActiveAreaAndCount(long startTime, long endTime, String siteId);


    /**
     * 获取活动密度最高的时间段及次数
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 活动密度最高的时间段及次数
     */
    public Map<String, Object> getMaxActiveTimeAndCount(long startTime, long endTime, String siteId);


    /**
     * 获取活动密度最高的时间段的区域及次数
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 活动密度最高的时间段及次数
     */
    public Map<String, Object> getMaxActiveTimeAreaAndCount(long startTime, long endTime, String siteId);

}
