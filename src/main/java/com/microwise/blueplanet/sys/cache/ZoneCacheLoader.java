package com.microwise.blueplanet.sys.cache;

import com.google.common.cache.CacheLoader;
import com.microwise.blueplanet.bean.vo.ZoneVO;
import com.microwise.blueplanet.service.ZoneService;
import com.microwise.blueplanet.sys.Blueplanet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author gaohui
 * @date 13-1-28 10:02
 */
@Component
@Scope("singleton")
@Blueplanet
public class ZoneCacheLoader extends CacheLoader<String, ZoneVO> {

    @Autowired
    private ZoneService zoneService;

    @Override
    public ZoneVO load(String zoneId) throws Exception {
        return zoneService.findZoneById(zoneId);
    }
}
