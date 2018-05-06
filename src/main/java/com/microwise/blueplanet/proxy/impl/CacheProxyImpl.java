package com.microwise.blueplanet.proxy.impl;

import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.proxy.CacheProxy;
import com.microwise.blueplanet.sys.AppCacheHolder;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author gaohui
 * @date 13-7-4 11:31
 */
@Beans.Bean
@Blueplanet
public class CacheProxyImpl implements CacheProxy {

    @Autowired
    private AppCacheHolder appCache;

    @Override
    public List<SensorinfoVO> loadAvailableSensorinfoOfSite(String siteId) throws ExecutionException {
        return appCache.loadAvailableSensorinfoOfSite(siteId);
    }
}
