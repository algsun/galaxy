package com.microwise.blueplanet.service.impl;

import com.microwise.blueplanet.bean.po.AvgdataPO;
import com.microwise.blueplanet.bean.po.ZonePO;
import com.microwise.blueplanet.bean.vo.*;
import com.microwise.blueplanet.dao.DeviceDao;
import com.microwise.blueplanet.dao.SiteDao;
import com.microwise.blueplanet.dao.ZoneDao;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.service.SiteService;
import com.microwise.blueplanet.service.ZoneService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.util.WindTools;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.common.util.DateTimeUtil;
import com.microwise.common.util.ResourceBundleUtil;
import com.microwise.phoenix.bean.vo.ZoneStability;
import com.microwise.phoenix.dao.ZoneStabilityDao;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.*;

/**
 * 站点Service实现
 *
 * @author zhangpeng
 * @date 2013-1-21
 * @check 2013-02-25 zhangpeng svn:1741
 * @check 2013-04-22 xubaoji svn:2568
 * @check 2013-11-14 xiedeng svn:6621
 */
@Service
@Transactional
@Blueplanet
public class SiteServiceImpl implements SiteService {

    /**
     * 站点Dao
     */
    @Autowired
    private SiteDao siteDao;

    /**
     * 设备Dao
     */
    @Autowired
    private DeviceDao deviceDao;

    /**
     * 区域Dao
     */
    @Autowired
    private ZoneDao zoneDao;

    /**
     * 区域service
     */
    @Autowired
    private ZoneService zoneService;

    @Autowired
    private ZoneStabilityDao zoneStabilityDao;

    /**
     * 位置点service
     */
    @Autowired
    private LocationService locationService;

    @Override
    public List<RealtimeDataVO> findRealtimeDataLocation(String siteId) {
        return getRealtimeData(siteId, null);
    }

    @Override
    public List<RealtimeDataVO> findDeviceRealTimeData(String siteId, List<Integer> sensorPhysicalidList) {
        return getDeviceRealTimeDate(siteId, sensorPhysicalidList);
    }

    @Override
    public List<RealtimeDataVO> findRealtimeDataLocation(String siteId,
                                                         List<Integer> sensorPhysicalidList) {
        return getRealtimeData(siteId, sensorPhysicalidList);
    }

    /**
     * 获得站点下实时数据(设备实时数据)
     *
     * @param siteId               站点编号
     * @param sensorPhysicalidList 监测指标过滤条件，为null时查全部
     * @return List<RealtimeDataVO> 实时数据vo列表
     */
    private List<RealtimeDataVO> getDeviceRealTimeDate(String siteId,
                                                       List<Integer> sensorPhysicalidList) {
        List<RealtimeDataVO> nodeinfoList = siteDao.findNodeinfo(siteId,
                sensorPhysicalidList);
        for (RealtimeDataVO nodeinfo : nodeinfoList) {
            List<DeviceDataVO> sensorinfoList = deviceDao.findNodesensor(
                    nodeinfo.getNodeId(), sensorPhysicalidList);
            Map<Integer, DeviceDataVO> sensorinfoMap = new HashMap<Integer, DeviceDataVO>();
            for (DeviceDataVO deviceData : sensorinfoList) {
                if (deviceData.getShowType() == Constants.Blueplanet.SENSORINFO_SHOWTYPE_IS_WIND_DIRECTION) {
                    deviceData.setSensorPhysicalValue(WindTools
                            .updateWindDirection(deviceData
                                    .getSensorPhysicalValue()));
                }
                sensorinfoMap.put(deviceData.getSensorPhysicalid(), deviceData);
            }
            nodeinfo.setSensorinfoMap(sensorinfoMap);
        }
        return nodeinfoList;
    }

    //位置点
    private List<RealtimeDataVO> getRealtimeData(String siteId,
                                                 List<Integer> sensorPhysicalidList) {
        List<RealtimeDataVO> locationInfos = siteDao.findLocationInfo(siteId,
                sensorPhysicalidList);
        List<RealtimeDataVO> realtimeDatas = new ArrayList<RealtimeDataVO>();
        for (RealtimeDataVO locationInfo : locationInfos) {
            List<LocationDataVO> locationSensors = locationService.findLocationSensor(
                    locationInfo.getLocationId(), sensorPhysicalidList);
            Map<Integer, LocationDataVO> sensorinfoMap = new HashMap<Integer, LocationDataVO>();
            for (LocationDataVO locationData : locationSensors) {
                if (locationData.getShowType() == Constants.Blueplanet.SENSORINFO_SHOWTYPE_IS_WIND_DIRECTION) {
                    locationData.setSensorPhysicalValue(WindTools
                            .updateWindDirection(locationData
                                    .getSensorPhysicalValue()));
                }
                sensorinfoMap.put(locationData.getSensorPhysicalid(), locationData);
            }
            if (sensorinfoMap.size() > 0) {
                locationInfo.setLocationSensorInfoMap(sensorinfoMap);
                realtimeDatas.add(locationInfo);
            }

            if (locationInfos.size() > 0) {
                if (locationSensors != null && locationSensors.size() > 0) {
                    locationInfo.setStamp(locationSensors.get(0).getStamp());
                }
            }
        }
        return realtimeDatas;

    }

    @Override
    public List<SensorinfoVO> findSensorinfo(String siteId) {
        return siteDao.findSensorinfo(siteId);
    }

    @Override
    public List<SensorinfoVO> findDeviceSensorInfo(String siteId) {
        return siteDao.findDeviceSensorInfo(siteId);
    }

    @Override
    public List<SiteVO> findSite() {
        return siteDao.findSite();
    }

    @Override
    public SiteVO findSiteById(String siteId) {
        return siteDao.findSiteById(siteId);
    }

    @Override
    public List<SiteVO> findSiteHasSubscribeUser() {
        return siteDao.findSiteHasSubscribeUser();
    }

    @Override
    public List<LocationDataVO> findSiteChartData(String siteId) {
        //获取站点检测指标
        Date todayDate = new Date();
        Date startDate = DateTimeUtil.startOfDay(todayDate);
        List<String> locationIds = getLocationIds(siteId);
        if (locationIds.size() == 0) {
            return new ArrayList<LocationDataVO>();
        }
        List<LocationDataVO> locationDataList = siteDao.findAvgLocationById(startDate, siteId, null);
        Date date = DateTime.now().minusDays(6).toDate();
        for (LocationDataVO locationData : locationDataList) {
            locationData.setSensorPhysicalValueMap(zoneService.findLocationDataNearlyDayMap(locationData.getSensorPhysicalid(), siteId, null, date));
        }
        return locationDataList;
    }

    /**
     * 将设备list 封装成设备 id list
     *
     * @param siteId
     * @return
     */
    private List<String> getLocationIds(String siteId) {
        List<RealtimeDataVO> locationInfoList = siteDao.findLocationInfo(siteId,
                null);
        List<String> nodeIds = new ArrayList<String>();
        for (RealtimeDataVO locationInfo : locationInfoList) {
            nodeIds.add(locationInfo.getLocationId());
        }
        return nodeIds;
    }

    @Override
    public Map<String, List<LocationDataVO>> findSiteRealTimeData(String siteId) {
        Map<String, List<LocationDataVO>> zoneLocationDataMap = new HashMap<String, List<LocationDataVO>>();
        List<ZonePO> zoneVOList = siteDao.findZonePOList(siteId);
        Date todayDate = new Date();
        Date startDate = DateTimeUtil.startOfDay(todayDate);
        Date endDate = DateTimeUtil.endOfDay(todayDate);
        for (ZonePO zonePO : zoneVOList) {
            List<LocationDataVO> locationDataList = zoneDao.findGeneralLocationDataByRoomId(zonePO.getZoneId(), startDate, endDate);
            if (locationDataList.size() > 0) {
                zoneLocationDataMap.put(zonePO.getZoneName(), locationDataList);
            }
        }
        return zoneLocationDataMap;
    }

    @Override
    public Map<String, List<LocationDataVO>> findSiteRealTimeData(String siteId, int sensorId) {
        List<ZoneStability> zoneStabilityList = zoneStabilityDao.findLocations(siteId, sensorId);
        Map<String, List<LocationDataVO>> zoneLocationDataMap = new HashMap<String, List<LocationDataVO>>();
        Date todayDate = new Date();
        Date startDate = DateTimeUtil.startOfDay(todayDate);
        Date endDate = DateTimeUtil.endOfDay(todayDate);
        for (ZoneStability zoneStability : zoneStabilityList) {
            List<LocationDataVO> locationDataList = zoneDao.findGeneralLocationDataByRoomId(zoneStability.getZoneId(), startDate, endDate);
            if (locationDataList.size() > 0) {
                zoneLocationDataMap.put(zoneStability.getZoneName(), locationDataList);
            }
        }
        return zoneLocationDataMap;
    }

    @Override
    public Map<String, List<LocationDataVO>> findSiteRealTimeDataByZone(String zoneId) {
        Map<String, List<LocationDataVO>> zoneLocationDataMap = new HashMap<String, List<LocationDataVO>>();
        List<ZoneVO> zoneVOList = zoneDao.findZones(zoneId);
        Date todayDate = new Date();
        Date startDate = DateTimeUtil.startOfDay(todayDate);
        Date endDate = DateTimeUtil.endOfDay(todayDate);
        for (ZonePO zonePO : zoneVOList) {
            List<LocationDataVO> locationDataList = zoneDao.findGeneralLocationDataByRoomId(zonePO.getZoneId(), startDate, endDate);
            if (locationDataList.size() > 0) {
                zoneLocationDataMap.put(zonePO.getZoneName(), locationDataList);
            }
        }
        return zoneLocationDataMap;
    }

    @Override
    public List<DeviceVO> findAllDevicesBySiteId(String siteId) {
        return siteDao.findAllDevicesBySiteId(siteId);
    }

    @Override
    public DeviceStatusPieData findDeviceStatusPieDataBySiteId(String siteId) {
        List<DeviceVO> deviceVOs = deviceDao.findDevicesBySiteId(siteId);
        return assembleStatusPieData(deviceVOs);
    }

    @Override
    public DeviceStatusPieData findDeviceStatusPieDataByZoneId(String zoneId) {
        List<DeviceVO> deviceVOs = deviceDao.findDevicesByZoneId(zoneId);
        return assembleStatusPieData(deviceVOs);
    }

    private DeviceStatusPieData assembleStatusPieData(List<DeviceVO> deviceVOs) {
        DecimalFormat df = new DecimalFormat(".0");
        DeviceStatusPieData deviceStatusPieData = new DeviceStatusPieData();
        if (deviceVOs.size() > 0) {
            deviceStatusPieData.setHasData(true);
        }

        //组装饼图数据
        //组装设备状态饼图数据

        //被除数不能为0
        double size = 0;
        if (deviceVOs.size() != 0) {
            size = 100.0 / deviceVOs.size();
        }

        List<Map<String, Object>> deviceStatusPie = disposePieChartData(deviceVOs, Double.parseDouble(df.format(size)));
        deviceStatusPieData.setPieData(disposePieChartDataToList(deviceStatusPie));
        return deviceStatusPieData;
    }

    @Override
    public List<SiteVO> findAllSite() {
        return siteDao.findAllSite();
    }

    public Map<String, AvgdataPO> findZoneHumidity(List<ZoneVO> zoneVOList) {

        Map<String, AvgdataPO> avgDataMap =new HashMap<String, AvgdataPO>();
        // 区域、范围值、平均值添加数据
        for (ZoneVO zone : zoneVOList) {
            //获取时间  注意：数据库中的时间是"yyyy-mm-dd" 类型，形如：date>start and date<end 的条件，所以获取前一天数据当获取前两天的时间和当天时间两者之间便是前一天
            Date start = DateTime.now().minusDays(2).toDate();
            Date end = DateTimeUtil.startOfDay(new Date());

            List<AvgdataPO> avgDatas = zoneDao.findLocationMaxMinValue(Constants.ChartConstants.SENSORINFO_HUM, zone.getZoneId(), start, end);
            if (avgDatas.size() > 0) {
                avgDataMap.put(zone.getZoneId(), avgDatas.get(0));
            }
        }
        return avgDataMap;
    }

    public List<SensorUsedDataVO> findSensorUsedInfo(String siteId, String language) {
        return siteDao.findSensorUsedInfo(siteId, language);
    }

    /**
     * *
     * 处理 饼状图数据
     *
     * @param devices 饼状图 基本数据
     * @return List<Object> 处理后的 饼状图数据
     * @author 王耕
     * @date 2014-10-24
     */
    private List<Map<String, Object>> disposePieChartData(List<DeviceVO> devices, double singlePersent) {
        List<Map<String, Object>> pieChartDatas = new ArrayList<Map<String, Object>>();

        List<DeviceVO> normalList = new ArrayList<DeviceVO>();
        List<DeviceVO> lowPowerList = new ArrayList<DeviceVO>();
        List<DeviceVO> overPowerList = new ArrayList<DeviceVO>();
        List<DeviceVO> overTimeList = new ArrayList<DeviceVO>();

        for (DeviceVO device : devices) {
            if (device.getAnomaly() == -1) {
                overTimeList.add(device);
            } else if (device.getAnomaly() == 0) {
                normalList.add(device);
            } else if (device.getAnomaly() == 1) {
                lowPowerList.add(device);
            } else if (device.getAnomaly() == 2) {
                overPowerList.add(device);
            }
        }

        Map<String, Object> overTimeMap = new HashMap<String, Object>();

        overTimeMap.put("key", ResourceBundleUtil.getBundle().getString("blueplanet.device.anomaly.overtime"));
        overTimeMap.put("number", disposeDeviceTypePieData(overTimeList, singlePersent));
        overTimeMap.put("value", overTimeList.size() * singlePersent);

        Map<String, Object> normalMap = new HashMap<String, Object>();
        normalMap.put("key", ResourceBundleUtil.getBundle().getString("blueplanet.device.anomaly.normal"));
        normalMap.put("number", disposeDeviceTypePieData(normalList, singlePersent));
        normalMap.put("value", normalList.size() * singlePersent);

        Map<String, Object> lowPowerMap = new HashMap<String, Object>();
        lowPowerMap.put("key", ResourceBundleUtil.getBundle().getString("blueplanet.device.anomaly.lowVoltage"));
        lowPowerMap.put("number", disposeDeviceTypePieData(lowPowerList, singlePersent));
        lowPowerMap.put("value", lowPowerList.size() * singlePersent);

        Map<String, Object> overPowerMap = new HashMap<String, Object>();
        overPowerMap.put("key", ResourceBundleUtil.getBundle().getString("blueplanet.device.anomaly.powerDown"));
        overPowerMap.put("number", disposeDeviceTypePieData(overPowerList, singlePersent));
        overPowerMap.put("value", overPowerList.size() * singlePersent);

        pieChartDatas.add(overTimeMap);
        pieChartDatas.add(normalMap);
        pieChartDatas.add(lowPowerMap);
        pieChartDatas.add(overPowerMap);

        return pieChartDatas;
    }


    /**
     * 处理每个设备状态的饼图数据的设备类型
     *
     * @param devices       设备
     * @param singlePersent 百分比数值
     * @return 处理后的饼图数据
     */
    private List<List<Object>> disposeDeviceTypePieData(List<DeviceVO> devices, double singlePersent) {
        List<List<Object>> list = new ArrayList<List<Object>>();
        List<DeviceVO> node = new ArrayList<DeviceVO>();
        List<DeviceVO> relay = new ArrayList<DeviceVO>();
        List<DeviceVO> main_module = new ArrayList<DeviceVO>();
        List<DeviceVO> child_module = new ArrayList<DeviceVO>();
        List<DeviceVO> gateway = new ArrayList<DeviceVO>();
        for (DeviceVO device : devices) {
            if (device.getNodeType() == Constants.NODE) {
                node.add(device);
            } else if (device.getNodeType() == Constants.RELAY) {
                relay.add(device);
            } else if (device.getNodeType() == Constants.MAIN_MODULE) {
                main_module.add(device);
            } else if (device.getNodeType() == Constants.CHILD_MODULE) {
                child_module.add(device);
            } else if (device.getNodeType() == Constants.GATEWAY) {
                gateway.add(device);
            }
        }

        List<Object> deviceType1 = new ArrayList<Object>();
        List<Object> deviceType2 = new ArrayList<Object>();
        List<Object> deviceType3 = new ArrayList<Object>();
        List<Object> deviceType4 = new ArrayList<Object>();
        List<Object> deviceType7 = new ArrayList<Object>();

        deviceType1.add(ResourceBundleUtil.getBundle().getString("common.node"));
        deviceType1.add(singlePersent * node.size());
        deviceType1.add(node.size());

        deviceType2.add(ResourceBundleUtil.getBundle().getString("common.relay"));
        deviceType2.add(singlePersent * relay.size());
        deviceType2.add(relay.size());

        deviceType3.add(ResourceBundleUtil.getBundle().getString("common.mainModule"));
        deviceType3.add(singlePersent * main_module.size());
        deviceType3.add(main_module.size());

        deviceType4.add(ResourceBundleUtil.getBundle().getString("common.childModule"));
        deviceType4.add(singlePersent * child_module.size());
        deviceType4.add(child_module.size());

        deviceType7.add(ResourceBundleUtil.getBundle().getString("common.gateway"));
        deviceType7.add(singlePersent * gateway.size());
        deviceType7.add(gateway.size());

        list.add(deviceType1);
        list.add(deviceType2);
        list.add(deviceType3);
        list.add(deviceType4);
        list.add(deviceType7);

        return list;
    }

    /**
     * 将Map数据类型转换为图标识别的List类型
     *
     * @param basicDatas Map格式数据
     * @return List格式数据
     */
    private List<List<Object>> disposePieChartDataToList(List<Map<String, Object>> basicDatas) {
        List<List<Object>> pieChartDatas = new ArrayList<List<Object>>();
        for (Map<String, Object> basicData : basicDatas) {
            List<Object> pieChartData = new ArrayList<Object>();
            pieChartData.add(basicData.get("key"));
            pieChartData.add(basicData.get("value"));
            pieChartData.add(basicData.get("number"));
            pieChartDatas.add(pieChartData);
        }
        return pieChartDatas;
    }

    @Override
    public String getFileName(String siteId, Date startTime, Date endTime) {
        SiteVO site = siteDao.findSiteById(siteId);
        String minTime = DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD, startTime);
        String maxTime = DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD, endTime);
        String fileName = site.getSiteName() + minTime + "至" + maxTime + "历史数据.zip";
        return fileName;
    }
}
