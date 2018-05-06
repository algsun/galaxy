package com.microwise.blueplanet.proxy.impl;

import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.proxy.SensorinfoProxy;
import com.microwise.blueplanet.service.SensorinfoService;
import com.microwise.blueplanet.sys.Blueplanet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author xu.baoji
 * @date 2013-7-26
 */
@Component
@Scope("prototype")
@Blueplanet
public class SensorinfoProxyImpl implements SensorinfoProxy {

    /***/
    @Autowired
    private SensorinfoService sensorinfoService;

    @Override
    public SensorinfoVO findByPhysicalid(Integer sensorPhysicalid) {
        return sensorinfoService.findByPhysicalid(sensorPhysicalid);
    }

}
