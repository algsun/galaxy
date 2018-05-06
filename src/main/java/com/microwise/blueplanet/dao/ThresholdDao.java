package com.microwise.blueplanet.dao;

import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.bean.vo.ThresholdVO;
import com.microwise.blueplanet.bean.vo.ZoneThresholdVO;

import java.util.List;

/**
 * 阈值 dao 接口
 *
 * @author xu.baoji
 * @date 2013-8-26
 */
public interface ThresholdDao {

    /**
     * 添加  监测指标阈值信息
     *
     * @param thresholdVO
     * @author xu.baoji
     * @date 2013-8-26
     */
    public void saveThreshold(ThresholdVO thresholdVO);

    /**
     * 删除区域下监测指标阈值
     *
     * @param locationId
     * @author xu.baoji
     * @date 2013-8-26
     */
    public void deleteThreshold(String locationId, int thresholdType);

    /**
     * 查询区域下要设置阈值的监测指标(位置点)
     *
     * @param zoneId 区域下子孙区域id 列表
     * @return List<SensorinfoVO> 监测指标实体
     * @author wang.geng
     * @date 2014-7-23
     * author : chenyaofei
     * date 2016-07-22
     */
    public List<SensorinfoVO> findThresholdLocationData(String zoneId);

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
     * 获取区域下有位置点阈值或者质地阈值的位置点集合
     *
     * @zoneId 区域Id
     * author : chenyaofei
     * date 2016-07-22
     */
    public List<String> findNoZoneThresholdLocationIds(String zoneId);

    /**
     * 查询区域下 监测指标 阈值设置信息
     *
     * @param zoneId 区域下位置点Id
     * @return List<ThresholdVO>  监测指标阈值实体列表
     * @author xu.baoji
     * @date 2013-8-26
     * @author : chenyaofei
     * date 2016-07-22
     */
    public List<ZoneThresholdVO> findZoneThresholds(String zoneId);

    /**
     * 添加  监测指标阈值信息
     *
     * @param zoneThresholdVO
     * @author chenyaofei
     * @date 2016-07-22
     */
    public void saveZoneThreshold(ZoneThresholdVO zoneThresholdVO);

    /**
     * 添加  监测指标阈值信息
     *
     * @param zoneId
     * @author chenyaofei
     * @date 2016-07-22
     */
    public void deleteZoneThreshold(String zoneId);

    /**
     * 查询位置点监测指标指定类型的数据集合
     *
     * @author chenyaofei
     * @date 2016-07-22
     */
    public List<ThresholdVO> findThresholdsByLocationId(String locationId, int thresholdType);

//    /**
//     * 查询位置点监测指标集合
//     *
//     * @author chenyaofei
//     * @date 2016-07-22
//     */
//    public List<ThresholdVO> findThresholdsByLocationId(String locationId);

    /**
     * 查询位置点报警阈值
     *
     * @param locationId 位置点ID
     * @return
     */
    public List<ThresholdVO> findThresholds(String locationId);


    /**
     * 查询位置点指定监测指标报警信息
     *
     * @param locationId 位置点id
     * @param sensorId   监测指标ID
     * @return
     */
    public ThresholdVO findThreshold(String locationId, int sensorId);


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


    /**
     * 更新位置点报警阈值
     *
     * @param threshold 位置点报警阈值
     */
    public void update(ThresholdVO threshold);
}
