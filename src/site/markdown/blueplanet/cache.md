# 数据缓存

目前由于环境监控的数据量比较大，考虑将一些常用数据进行缓存。

环境监控的数据缓存目前通过 `blueplanet/sys/AppCacheHolder`统一管理。
内部实现暂时借助 guava cache 来实现，AppCacheHolder 是单例的，AppCacheHolder 没有负责线程安全，线程安全是通过 guava cache 来保证的。

通常加载数据通过 `loadXxx` 方法，数据发生改变，数据库与缓存不一致时可以通过 `evictXxx` 方法驱逐数据。

## 区域设备树

页面左侧现在有区域设备树，树中为了能够快速搜索和按监测指标过虑，一次性加载了整个站点数据。
页面会经常刷新，左侧区域设备树会频繁请求，如果每次请求都请求数据库会影响性能。

    public class AppCacheHolder{

        // 加载数据
        public loadZoneDeviceTree();

        // 驱逐数据
        public evictZoneDeviceTree();

    }

## 区域

区域缓存

    public ZoneVO loadZone(String zoneId);

    public void evictZone(String zoneId);

## 设备

设备缓存, 如果是传感器或者从模块则携带监测指标集合

    public DeviceVO loadDevice(String deviceId);

    public void evictDevice(String deviceId);


## 监测指标

监测指标缓存

    public SensorinfoVO loadSensorinfo(Integer sensorPhysicalId);

    public void evictSensorinfo(Integer sensorPhysicalId);

## 站点下激活的监测指标

    public List<SensorinofVO> loadAvailableSensorinfoOfSite(String siteId);

    public void evictAvailabelSensorinfoOfSite(String siteId);
