package com.microwise.blueplanet.sys.cache;

import com.google.common.cache.CacheLoader;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.service.SensorinfoService;
import com.microwise.blueplanet.sys.Blueplanet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 缓存整个应用的监测指标
 *
 * @author gaohui
 * @date 13-2-22 08:35
 */
@Component
@Scope("singleton")
@Blueplanet
public class SensorinfoMapCacheLoader extends CacheLoader<Integer, SensorinfoVO> {
    @Autowired
    private SensorinfoService sensorinfoService;

    @Override
    public SensorinfoVO load(Integer sensorPhysicalId) throws Exception {
        return sensorinfoService.findByPhysicalid(sensorPhysicalId);
    }
}
