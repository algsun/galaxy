package com.microwise.blueplanet.dao;

import com.microwise.blueplanet.bean.vo.SensorinfoVO;

import java.util.List;

/**
 * 监测指标Dao
 *
 * @author zhangpeng
 * @date 2013-1-17
 * @check 2013-02-25 xubaoji svn:1750
 */
public interface SensorinfoDao {

    /**
     * 获取系统所有监测指标
     *
     * @return List<SensorinfoVO> 监测指标vo对象列表
     * @author zhangpeng
     * @date 2013-1-18
     */
    public List<SensorinfoVO> findSensorinfo();

    /**
     * 根据监测指标标识查询监测指标对象
     *
     * @param sensorPhysicalid 监测指标标识
     * @return SensorinfoVO 监测指标vo对象
     * @author zhangpeng
     * @date 2013-2-22
     */
    public SensorinfoVO findByPhysicalid(Integer sensorPhysicalid);

}
