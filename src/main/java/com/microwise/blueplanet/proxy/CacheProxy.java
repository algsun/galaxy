package com.microwise.blueplanet.proxy;

import com.microwise.blueplanet.bean.vo.SensorinfoVO;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 环境监测下，缓存的代理
 *
 * @author gaohui
 * @date 13-7-4 11:30
 */
public interface CacheProxy {

    /**
     * 返回某个站点下可用的监测指标
     *
     * @param siteId 站点ID
     * @return 监测指标集合
     * @throws ExecutionException
     */
    List<SensorinfoVO> loadAvailableSensorinfoOfSite(String siteId) throws ExecutionException;
}
