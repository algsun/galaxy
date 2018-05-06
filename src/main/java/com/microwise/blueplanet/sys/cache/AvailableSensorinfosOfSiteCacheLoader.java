package com.microwise.blueplanet.sys.cache;

import com.google.common.cache.CacheLoader;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.service.SiteService;
import com.microwise.blueplanet.sys.Blueplanet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 站点下激活的监测指标集合缓存
 *
 * @author gaohui
 * @date 13-2-25 09:25
 */
@Component
@Scope("singleton")
@Blueplanet
public class AvailableSensorinfosOfSiteCacheLoader extends CacheLoader<String, List<SensorinfoVO>> {
    @Autowired
    private SiteService siteService;

    @Override
    public List<SensorinfoVO> load(String siteId) throws Exception {
        return siteService.findSensorinfo(siteId);
    }
}
