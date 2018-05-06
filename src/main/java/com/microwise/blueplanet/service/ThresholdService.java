package com.microwise.blueplanet.service;

import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.bean.vo.ThresholdVO;
import com.microwise.blueplanet.bean.vo.ZoneThresholdVO;

import java.util.List;

/**
 * 阈值 设置 接口 service
 *
 * @author xu.baoji
 * @date 2013-8-26
 */
public interface ThresholdService {

    /**
     * 查询一个区域下需要设置阈值 的监测指标信息
     *
     * @param zoneId 区域id
     * @return SensorinfoVO 检测指标信息实体列表
     * @author xu.baoji
     * @date 2013-8-26
     */
    public List<SensorinfoVO> findThresholdSensorinfo(String zoneId);

    /**
     * 添加区域 阈值 设置  (直属区域)
     *
     * @param zoneId       区域id
     * @param thresholdVOs 区域下设置检测指标阈值信息（如果没有设置传size 为0 的集合）
     * @author xu.baoji
     * @date 2013-8-26
     * @author liuzhu
     * @date 2014-3-12
     */
    public void saveZoneThreshold(String zoneId, List<ZoneThresholdVO> thresholdVOs);

    /**
     * 查询区域下 监测指标 阈值设置 信息
     *
     * @param zoneId 区域id
     * @return ThresholdVO 区域下监测指标阈值设置信息
     * @author xu.baoji
     * @date 2013-8-26
     */
    public List<ZoneThresholdVO> findZoneThresholds(String zoneId);

    /**
     * 删除区域 下监测指标 阈值设置信息
     *
     * @param zoneId 区域id
     * @author xu.baoji
     * @date 2013-8-26
     */
    public void deleteZoneThreshold(String zoneId);

    /**
     * 根据区域id，查找父区域id
     *
     * @param zoneId 区域id
     * @return 父区域id
     * @author liuzhu
     * @date 2014-3-14
     */
    public String findParentIdByZoneId(String zoneId);

    /**
     * 查询位置点报警阈值
     *
     * @param locationId 位置点ID
     * @return
     */
    public List<ThresholdVO> findThresholds(String locationId);

    /**
     * 判断位置点阈值是否存在
     *
     * @param threshold 位置点阈值
     * @return
     */
    public boolean exists(ThresholdVO threshold);

    /**
     * 删除位置点报警阈值
     *
     * @param locationId
     */
    public void delete(String locationId);

    /**
     * 删除位置点报警阈值
     *
     * @param locationId 位置点ID
     * @param sensorId   监测指标ID
     */
    public void delete(String locationId, int sensorId);

    /**
     * 保存位置点报警阈值
     *
     * @param threshold 位置点报警阈值
     */
    public void save(ThresholdVO threshold);

}
