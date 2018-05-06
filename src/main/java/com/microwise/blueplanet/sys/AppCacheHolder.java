package com.microwise.blueplanet.sys;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.microwise.blueplanet.bean.vo.DeviceVO;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.bean.vo.ZoneVO;
import com.microwise.blueplanet.sys.cache.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 负责 环境监测 业务系统下的缓存
 * <p/>
 * 注意：此类单例
 *
 * @author gaohui
 * @date 13-1-23 09:58
 */
@Component
@Scope("singleton")
@Blueplanet
public class AppCacheHolder {
    private static final Logger log = LoggerFactory.getLogger(AppCacheHolder.class);

    // 缓存写入后多长时间失效
    public static final int EXPIRE_AFTER_WRITE_MINUTE = 120;

    // 站点下站点区域设备树缓存(ztree)
    private LoadingCache<String, List<Map<String, Object>>> zoneDeviceTreeCache = null;

    // zoneId:String => zone:ZoneVO
    private LoadingCache<String, ZoneVO> zoneCache;

    // locationId:String => location:locationVO
    private LoadingCache<String, LocationVO> locationCache;

    /**
     * TODO 位置点开发完成后处理
     */
    // deviceId:String => device:DeviceVO
    private LoadingCache<String, DeviceVO> deviceCache;

    // sensorPhysicalId:Integer => sensorinfo:SensorinfoVO
    private LoadingCache<Integer, SensorinfoVO> sensorinfoCache;

    // 站下激活的监测指标
    // siteId:String => sensorinfo:List<SensorinfoVO>
    private LoadingCache<String, List<SensorinfoVO>> availableSensorinfoOfSiteCache;

    public AppCacheHolder() {
    }

    /**
     * 初始化区域设备树
     *
     * @param zoneDeviceTreeCacheLoader
     */
    @Autowired
    public void initZoneDeviceTreeCacheLoader(ZoneDeviceTreeCacheLoader zoneDeviceTreeCacheLoader) {
        zoneDeviceTreeCache = CacheBuilder.newBuilder()
                // 最后一次写入 10 分钟后过期
                .expireAfterWrite(EXPIRE_AFTER_WRITE_MINUTE, TimeUnit.MINUTES)
                .build(zoneDeviceTreeCacheLoader);
    }

    /**
     * 初始化区域
     *
     * @param zoneCacheLoader
     */
    @Autowired
    public void initZoneCacheLoader(ZoneCacheLoader zoneCacheLoader) {
        zoneCache = CacheBuilder.newBuilder()
                // 最后一次访问 10 分钟后过期
                .expireAfterAccess(EXPIRE_AFTER_WRITE_MINUTE, TimeUnit.MINUTES)
                .build(zoneCacheLoader);
    }

    @Autowired
    public void initLocationCacheLoader(LocationCacheLoader locationCacheLoader) {
        locationCache = CacheBuilder.newBuilder()
                .expireAfterAccess(EXPIRE_AFTER_WRITE_MINUTE, TimeUnit.MINUTES)
                .build(locationCacheLoader);
    }

    /**
     * 初始化设备缓存
     *
     * @param deviceCacheLoader
     */
    @Autowired
    public void initDeviceCacheLoader(DeviceCacheLoader deviceCacheLoader) {
        deviceCache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_AFTER_WRITE_MINUTE, TimeUnit.MINUTES)
                .build(deviceCacheLoader);
    }

    /**
     * 初始化监测指标
     *
     * @param sensorinfoMapCacheLoader
     */
    @Autowired
    public void initSensorinfoCacheLoader(SensorinfoMapCacheLoader sensorinfoMapCacheLoader) {
        sensorinfoCache = CacheBuilder.newBuilder()
                .build(sensorinfoMapCacheLoader);
    }

    /**
     * 初始化站点下的监测指标
     *
     * @param sensorinfosOfSiteCacheLoader
     */
    @Autowired
    public void initSensorinfoOfSiteCacheLoader(AvailableSensorinfosOfSiteCacheLoader sensorinfosOfSiteCacheLoader) {
        availableSensorinfoOfSiteCache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_AFTER_WRITE_MINUTE, TimeUnit.MINUTES)
                .build(sensorinfosOfSiteCacheLoader);
    }

    /**
     * 根据站点获取站点下的区域设备树
     *
     * @param siteId
     * @return
     * @throws ExecutionException
     */
    public List<Map<String, Object>> loadZoneDeviceTree(String siteId) throws ExecutionException {
        return zoneDeviceTreeCache.get(siteId);
    }

    /**
     * 驱逐缓存区域设备树. 当缓存的数据更改后，需要显示的调整此方法清除缓存.
     *
     * @param siteId
     */
    public void evictZoneDeviceTree(String siteId) {
        log.debug("evict zone device tree. siteId:{}", siteId);
        zoneDeviceTreeCache.invalidate(siteId);
    }

    /**
     * 获取区域
     *
     * @param zoneId
     * @return
     * @throws ExecutionException
     */
    public ZoneVO loadZone(String zoneId) throws ExecutionException {
        return zoneCache.get(zoneId);
    }

    /**
     * 根所区域 id 驱逐区域缓存
     *
     * @param zoneId
     */
    public void evictZone(String zoneId) {
        zoneCache.invalidate(zoneId);
    }

    public LocationVO loadLocation(String locationId) throws ExecutionException {
        return locationCache.get(locationId);
    }

    public void putLocation(LocationVO location) {
        locationCache.put(location.getId(), location);
    }

    public void evictLocatoin(String locationId) {
        locationCache.invalidate(locationId);
    }

    /**
     * 根据设备 id 查找设备, 如果是传感器和从模块同时携带监测指标
     *
     * @param deviceId
     * @return
     * @throws ExecutionException
     */
    public DeviceVO loadDevice(String deviceId) throws ExecutionException {
        return deviceCache.get(deviceId);
    }

    /**
     * 驱逐缓存
     *
     * @param deviceId
     */
    public void evictDevice(String deviceId) {
        deviceCache.invalidate(deviceId);
    }

    /**
     * 根据 sensorPhysicalId 获取对应 sensorinfo
     *
     * @param sensorPhysicalId
     * @return
     */
    public SensorinfoVO loadSensorinfo(Integer sensorPhysicalId) throws ExecutionException {
        return sensorinfoCache.get(sensorPhysicalId);
    }

    /**
     * 驱逐缓存
     *
     * @param sensorPhysicalId
     * @return
     */
    public void evictSensorinfo(Integer sensorPhysicalId) {
        sensorinfoCache.invalidate(sensorPhysicalId);
    }

    public List<SensorinfoVO> loadAvailableSensorinfoOfSite(String siteId) throws ExecutionException {
        return availableSensorinfoOfSiteCache.get(siteId);
    }

    public void evictAvailableSensorinfoOfSite() {
        availableSensorinfoOfSiteCache.invalidateAll();
    }
}
