package com.microwise.blueplanet.sys.cache;

import com.google.common.base.Function;
import com.google.common.cache.CacheLoader;
import com.google.common.collect.Lists;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.sys.Blueplanet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 位置点缓存
 *
 * @author li.jianfei
 * @date 2014-06-25
 */
@Component
@Scope("prototype")
@Blueplanet
public class LocationCacheLoader extends CacheLoader<String, LocationVO> {

    @Autowired
    private LocationService locationService;

    @Override
    public LocationVO load(String locationId) throws Exception {
        LocationVO location = locationService.findLocationById(locationId);
        List<Integer> sensorIdList = Lists.transform(location.getSensorInfoList(), new Function<SensorinfoVO, Integer>() {
            @Override
            public Integer apply(SensorinfoVO sensorinfoVO) {
                return sensorinfoVO.getSensorPhysicalid();
            }
        });
        location.setSensorIdList(sensorIdList);
        return location;
    }
}
