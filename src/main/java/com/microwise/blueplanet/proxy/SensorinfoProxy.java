package com.microwise.blueplanet.proxy;

import com.microwise.blueplanet.bean.vo.SensorinfoVO;

/**
 * @author xu.baoji
 * @date 2013-7-26
 */
public interface SensorinfoProxy {

    /**
     * 查询监测指标信息
     *
     * @param sensorPhysicalid 监测指标标识
     * @return SensorinfoVO 监测指标信息
     * @author xu.baoji
     * @date 2013-7-26
     */
    public SensorinfoVO findByPhysicalid(Integer sensorPhysicalid);

}
