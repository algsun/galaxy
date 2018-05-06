package com.microwise.blueplanet.service.impl;

import com.microwise.blueplanet.bean.po.AvgdataPO;
import com.microwise.blueplanet.bean.vo.*;
import com.microwise.blueplanet.dao.*;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.service.ZoneService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.util.WindTools;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.common.util.DateTimeUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 区域Service实现
 *
 * @author zhangpeng
 * @date 2013-1-21
 * @check 2013-02-25 zhangpeng svn:1741
 * @check 2013-04-22 xubaoji svn:2709
 * @check 2013-11-14 xiedeng svn:6621
 */
@Service
@Transactional
@Blueplanet
public class ZoneServiceImpl implements ZoneService {

    /**
     * 区域Dao
     */
    @Autowired
    private ZoneDao zoneDao;

    /**
     * 位置点dao
     */
    @Autowired
    private LocationDao locationDao;

    /**
     * 站点dao
     */
    @Autowired
    private SiteDao siteDao;

    @Autowired
    private SensorinfoDao sensorinfoDao;

    /**
     * 位置点service
     */
    @Autowired
    private LocationService locationService;

    /**
     * 公用dao
     */
    @Autowired
    private CommonDao commonDao;

    @Override
    public long findDataVersion(String zoneId) {
        return zoneDao.findDataVersion(zoneId);
    }

    @Override
    public List<LocationVO> findLocations(String zoneId) {
        return locationService.findLocationsByZoneId(zoneId, false);
    }

    @Override
    public List<String> findChildrenIdList(String zoneId) {
        return zoneDao.findChildrenIdList(zoneId);
    }

    @Override
    public List<RealtimeDataVO> findRealtimeDataLocation(String zoneId) {
        return getRealtimeDataLocation(zoneId, null);
    }

    @Override
    public List<RealtimeDataVO> findRealtimeDataLocation(String zoneId,
                                                         List<Integer> sensorPhysicalidList) {
        return getRealtimeDataLocation(zoneId, sensorPhysicalidList);
    }

    @Override
    public List<LocationOnceDataVO> findLocationHistoryData(String zoneId, int sensorPhysicalId, Date time, int deltaMinute) {
        List<LocationOnceDataVO> locationDatas = new ArrayList<LocationOnceDataVO>();

        SensorinfoVO sensor = sensorinfoDao.findByPhysicalid(sensorPhysicalId);
        List<LocationVO> locations = locationDao.findLocationsByZoneId(zoneId);

        for (LocationVO location : locations) {
            List<Integer> sensorIds = locationDao.findLocationSensorIdList(location.getId());
            if (sensorIds == null) continue;
            boolean flag = false;
            for (Integer sensorId : sensorIds) {
                if (Objects.equals(sensorId, sensor.getSensorPhysicalid())) {
                    flag = true;
                    break;
                }
            }
            if (!flag) continue;

            PlanImageCoordinateVO planImageCoordinateVO = findPlanImageCoordinate(zoneId, location.getId());
            if (planImageCoordinateVO != null) {
                location.setCoordinateX(planImageCoordinateVO.getCoordinateX());
                location.setCoordinateY(planImageCoordinateVO.getCoordinateY());
            }
            LocationDataVO locationDataVO = locationDao.findHistoryData(location.getId(), sensorPhysicalId, time, deltaMinute);
            if (locationDataVO == null) continue;

            locationDataVO.setCnName(sensor.getCnName());
            locationDataVO.setUnits(sensor.getUnits());
            locationDataVO.setShowType(sensor.getShowType());
            locationDatas.add(new LocationOnceDataVO(location, locationDataVO));
        }

        return locationDatas;
    }

    /**
     * 查询区域下设备监测指标 实时数据并 组装到实时数据对象（位置点存储）
     *
     * @param zoneId               区域id
     * @param sensorPhysicalidList 监测指标过滤条件 （可以为null）
     * @return List<RealtimeDataVO> 实时数据vo列表
     * @author wang.geng
     * @date 2014-7-11
     */
    private List<RealtimeDataVO> getRealtimeDataLocation(String zoneId,
                                                         List<Integer> sensorPhysicalidList) {
        List<String> zoneIds = zoneDao.findChildrenIdList(zoneId);
        List<RealtimeDataVO> realtimeDataList = zoneDao.findLocationInfo(zoneIds, sensorPhysicalidList);
        List<RealtimeDataVO> realtimeDatas = new ArrayList<RealtimeDataVO>();
        for (RealtimeDataVO locationInfo : realtimeDataList) {
            List<LocationDataVO> locationDatas = locationService.findLocationSensor(
                    locationInfo.getLocationId(), sensorPhysicalidList);

            Map<Integer, LocationDataVO> sensorinfoMap = new HashMap<Integer, LocationDataVO>();
            for (LocationDataVO locationData : locationDatas) {
                if (locationData.getShowType() == Constants.Blueplanet.SENSORINFO_SHOWTYPE_IS_WIND_DIRECTION) {
                    locationData.setSensorPhysicalValue(WindTools
                            .updateWindDirection(locationData
                                    .getSensorPhysicalValue()));
                }
                sensorinfoMap.put(locationData.getSensorPhysicalid(),
                        locationData);
            }
            if (sensorinfoMap.size() > 0) {
                locationInfo.setLocationSensorInfoMap(sensorinfoMap);
                realtimeDatas.add(locationInfo);
            }

            if (locationDatas.size() > 0) {
                locationInfo.setStamp(locationDatas.get(0).getStamp());
            }
        }
        return realtimeDatas;
    }

    @Override
    public List<SensorinfoVO> findSensorinfo(String zoneId) {
        List<String> list = zoneDao.findChildrenIdList(zoneId);
        return zoneDao.findSensorinfo(list);
    }

    @Override
    public List<SensorinfoVO> findSensorInfos(String zoneId, int showType) {
        List<String> list = zoneDao.findChildrenIdList(zoneId);
        List<SensorinfoVO> sensorinfos = findSensorInfoByZoneList(list);
        for (Iterator<SensorinfoVO> it = sensorinfos.iterator(); it.hasNext(); ) {
            if (it.next().getShowType() != showType) {
                it.remove();
            }
        }
        return sensorinfos;
    }

    private List<SensorinfoVO> findSensorInfoByZoneList(List<String> zoneIds) {
        List<SensorinfoVO> sensorinfos = new ArrayList<SensorinfoVO>();
        for (String zoneId : zoneIds) {
            List<LocationVO> locations = locationDao.findLocationsByZoneId(zoneId);
            for (LocationVO location : locations) {
                List<SensorinfoVO> sensors = locationDao.findSensorInfoList(location.getId());
                sensorinfos.addAll(sensors);
            }
        }
        return sensorinfos;
    }

    @Override
    public List<ZoneVO> findZoneList(String siteId, String parentZoneId) {
        return zoneDao.findZoneList(siteId, parentZoneId);
    }

    @Override
    public List<ZoneVO> findZoneLineList(String siteId) {
        return zoneDao.findZoneLineList(siteId);
    }

    @Override
    public ZoneVO findZoneById(String zoneId) {
        return zoneDao.findZoneById(zoneId);
    }

    @Override
    public List<ZoneVO> findZones(String zoneId) {
        return zoneDao.findZones(zoneId);
    }

    @Override
    public String saveZone(ZoneVO zone) {
        String zoneId = UUID.randomUUID().toString();
        zone.setZoneId(zoneId);
        zoneDao.saveZone(zone);
        return zoneId;
    }

    @Override
    public void deleteZone(String zoneId) {
        zoneDao.deleteZone(zoneId);
    }

    @Override
    public boolean isEmpty(String zoneId) {
        List<ZoneVO> zones = zoneDao.findZones(zoneId);
        List<LocationVO> locations = locationDao.findLocationsByZoneId(zoneId);
        return (zones.size() == 0 && locations.size() == 0);
    }

    @Override
    public boolean containsName(String siteId, String parentZoneId,
                                String zoneName) {
        return zoneDao.findZoneByName(siteId, parentZoneId, zoneName) != null;
    }

    @Override
    public List<ZoneVO> findZonesBySiteId(String siteId) {
        return zoneDao.findZonesBySiteId(siteId);
    }

    @Override
    public boolean isNameAvailable(String siteId, String parentZoneId,
                                   String zoneId, String zoneName) {
        ZoneVO zone = zoneDao.findZoneByName(siteId, parentZoneId, zoneName);
        return zone == null || zone.getZoneId().equals(zoneId);
    }

    @Override
    public void updateZone(String zoneId, String zoneName, String planImage, int position) {
        ZoneVO zone = zoneDao.findZoneById(zoneId);
        zone.setZoneId(zoneId);
        zone.setZoneName(zoneName);
        zone.setPlanImage(planImage);
        zone.setPosition(position);
        zone.setDataVersion(getDataVersion(zoneId));
        zoneDao.updateZone(zone);
        updateZonePosition(zone);
    }

    public void updateZonePosition(ZoneVO zone) {
        List<String> zoneIds = zoneDao.findChildrenIdList(zone.getZoneId());
        for (String zoneId : zoneIds) {
            ZoneVO zoneVO = zoneDao.findZoneById(zoneId);
            zoneVO.setPosition(zone.getPosition());
            zoneDao.updateZone(zoneVO);
        }
    }

    @Override
    public List<LocationDataVO> findZoneChartData(String zoneId, String siteId) {
        // 站点概览左边数据
        Date todayDate = new Date();
        Date startDate = DateTimeUtil.startOfDay(todayDate);
        Date date = DateTime.now().minusDays(6).toDate();
        List<String> locationIdList = getLocationIds(zoneId);
        if (locationIdList.size() == 0) {
            return new ArrayList<LocationDataVO>();
        }
        List<LocationDataVO> locationList = siteDao.findAvgLocationById(startDate, siteId, zoneId);
        for (LocationDataVO locationData : locationList) {
            locationData.setSensorPhysicalValueMap(findLocationDataNearlyDayMap(locationData.getSensorPhysicalid(), siteId, zoneId, date));
        }
        return locationList;
    }

    /**
     * 查询区域下有监测指标的设备 列表
     *
     * @param zoneId
     * @return
     */
    private List<String> getLocationIds(String zoneId) {
        List<String> zoneIds = zoneDao.findChildrenIdList(zoneId);
        List<RealtimeDataVO> realtimeDataList = zoneDao.findLocationInfo(zoneIds, null);
        List<String> nodeIds = new ArrayList<String>();
        for (RealtimeDataVO realtimeData : realtimeDataList) {
            nodeIds.add(realtimeData.getLocationId());
        }
        return nodeIds;
    }

    @Override
    public Map<LocationDataVO, List<LocationDataVO>> findZoneRealTimeData(String siteId, String zoneId) {
        Map<LocationDataVO, List<LocationDataVO>> locationDataMap = new HashMap<LocationDataVO, List<LocationDataVO>>();
        Date todayDate = new Date();
        Date startDate = DateTimeUtil.startOfDay(todayDate);
        Date endDate = DateTimeUtil.endOfDay(todayDate);

        //根据站点id及父区域id查询直接子区域,并根据zoneId查询该区域下监测指标的实时数据
        Map<LocationDataVO, List<LocationDataVO>> childZoneIdLocationDataMap = new HashMap<LocationDataVO, List<LocationDataVO>>();
        List<ZoneVO> zoneVOList = zoneDao.findZoneList(siteId, zoneId);
        for (ZoneVO zoneVO : zoneVOList) {
            LocationDataVO locationData = new LocationDataVO();
            List<LocationDataVO> locationDataChild = zoneDao.findGeneralLocationDataByRoomId(zoneVO.getZoneId(), startDate, endDate);
            if (locationDataChild.size() > 0) {
                locationData.setLocationName(zoneVO.getZoneName());
                locationData.setNode(false);
                childZoneIdLocationDataMap.put(locationData, locationDataChild);
            }
        }


        List<LocationVO> locationList = this.findLocations(zoneId);
        //情况一：只有直接子设备的情况
        if (zoneVOList.size() == 0) {
            onlyLocation(locationDataMap, startDate, endDate, locationList);
            //情况二：只有子区域
        } else if (locationList.size() == 0) {
            locationDataMap = childZoneIdLocationDataMap;
            //情况三: 即有子区域，又有子设备
        } else if (locationList.size() != 0 && zoneVOList.size() != 0) {
            locationDataMap = childZoneIdLocationDataMap;
            onlyLocation(locationDataMap, startDate, endDate, locationList);
        }
        return locationDataMap;
    }

    /**
     * 区域下只有位置点
     *
     * @param locationDataMap 位置点监测指标的值
     * @param startDate       开始时间
     * @param endDate         结束时间
     * @param locationList    位置点List
     * @author wang.geng
     * @date 2014-7-18
     */
    private void onlyLocation(Map<LocationDataVO, List<LocationDataVO>> locationDataMap,
                              Date startDate, Date endDate, List<LocationVO> locationList) {
        for (LocationVO location : locationList) {
            LocationDataVO locationData = new LocationDataVO();
            locationData.setLocationName(location.getLocationName());
            locationData.setNode(true);
            locationDataMap.put(locationData, zoneDao.findLocationDataByLocationId(location.getId(), startDate, endDate));
        }
    }

    @Override
    public Map<Long, LocationDataVO> findLocationDataNearlyDayMap(int sensorId, String siteId, String zoneId, Date startDate) {
        Map<Long, LocationDataVO> mapData = new TreeMap<Long, LocationDataVO>();
        //获取前五天的时间
        List<LocationDataVO> locationDatas = zoneDao.findLocationMaxMinValue(sensorId, startDate, siteId, zoneId);
        for (LocationDataVO locationData : locationDatas) {
            mapData.put(locationData.getStamp().getTime(), locationData);
        }
        return mapData;
    }


    @Override
    public void changeParent(String zoneId, String parentZoneId) {
        ZoneVO zone = zoneDao.findZoneById(zoneId);
        ZoneVO parentZone = zoneDao.findZoneById(parentZoneId);
        zone.setParentId(parentZoneId);
        if (parentZone != null) {
            zone.setPosition(parentZone.getPosition());
        }
        zone.setDataVersion(getDataVersion(zoneId));
        zoneDao.updateZone(zone);
    }

    @Override
    public void deletePlanImageCoordinate(String zoneId, String objectId) {
        zoneDao.deletePlanImageCoordinate(zoneId, objectId);
    }

    @Override
    public void updatePlanImageCoordinate(PlanImageCoordinateVO planImageCoordinate) {
        if (zoneDao.isCoordinateExist(planImageCoordinate.getZoneId(), planImageCoordinate.getObjectId())) {
            zoneDao.updatePlanImageCoordinate(planImageCoordinate);
        } else {
            planImageCoordinate.setId(UUID.randomUUID().toString());
            zoneDao.savePlanImageCoordinate(planImageCoordinate);
        }
    }

    /**
     * 获取区域应该更新的数据版本号
     *
     * @param zoneId 区域id
     * @return long 数据版本号
     * @author zhangpeng
     * @date 2013-4-11
     */
    private long getDataVersion(String zoneId) {
        long dataVersion = zoneDao.findDataVersion(zoneId);
        if (dataVersion > 0) {
            Integer tableDataVersion = commonDao
                    .findDataVersion(Constants.Blueplanet.TABLE_ZONE_NAME);
            if (tableDataVersion != 0) {
                dataVersion = tableDataVersion + 1;
            }
        }
        return dataVersion;
    }

    @Override
    public void savePlanImageCoordinate(PlanImageCoordinateVO planImageCoordinate) {
        planImageCoordinate.setId(UUID.randomUUID().toString());
        zoneDao.savePlanImageCoordinate(planImageCoordinate);
    }

    @Override
    public PlanImageCoordinateVO findPlanImageCoordinate(String zoneId, String objectId) {
        return zoneDao.findPlanImageCoordinate(zoneId, objectId);
    }

    @Override
    public boolean isCoordinateExist(String zoneId, String objectId) {
        return zoneDao.isCoordinateExist(zoneId, objectId);
    }


    @Override
    public String findParentId(String id) {
        return zoneDao.findParentId(id);
    }

    @Override
    public List<ZoneAvgDataVO> findZoneAvgData(String siteId, int sensorPhysicalId, Date begin, Date end) {
        return zoneDao.findZoneAvgData(siteId, sensorPhysicalId, begin, end);
//        //List<DeviceAvgdataVO>中已经保存的对象的zoneId
//        List zoneIds = new ArrayList<String>();
//        for (ZoneAvgDataVO avgdataPO : avgdataPOs) {
//            LocationAvgdataVO locationAvgdataVO = new LocationAvgdataVO();
//            locationAvgdataVO.setAvgdataList(new ArrayList<AvgdataPO>());
//            String zoneId = avgdataPO.getZoneId();
//            //如果已存进list的对象的zoneId中不包括这次循环的均值对象的zoneId，
//            // 给deviceAvgdataVO赋值并加入list，并将此次的zoneId存入zoneIds中
//
//            if (!zoneIds.contains(zoneId)) {
//                locationAvgdataVO.setZoneId(zoneId);
//                locationAvgdataVO.setZoneName(zoneDao.findZoneById(zoneId).getZoneName());
//                locationAvgdataVO.getAvgdataList().add(avgdataPO);
//                list.add(locationAvgdataVO);
//                zoneIds.add(zoneId);
//            }
//            //zoneIds中如果有这次循环的均值对象的zoneId，找到具有该zoneId的对象，
//            //将本次均值对象set进去
//            else {
//                for (LocationAvgdataVO locationAvgdataVO1 : list) {
//                    if (zoneId.equals(locationAvgdataVO1.getZoneId())) {
//                        locationAvgdataVO1.getAvgdataList().add(avgdataPO);
//                    }
//                }
//            }
//
//            if (locationAvgdataVO.getAvgdataList() != null) {
//                locationAvgdataVO.setHasData(true);
//            } else {
//                locationAvgdataVO.setHasData(false);
//            }
//        }
//        return list;
    }

    @Override
    public String getFileName(String zoneId, Date startTime, Date endTime) {
        ZoneVO zone = zoneDao.findZoneById(zoneId);
        String minTime = DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD, startTime);
        String maxTime = DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD, endTime);
        String fileName = zone.getZoneName() + minTime + "至" + maxTime + "历史数据.zip";
        return fileName;
    }

    /**
     * 包装成页面需要的数据结构
     * xAxis: {
     * categories: ['2014-10-1','2014-10-1','2014-10-1']
     * }
     * series: [
     * {
     * name:"",
     * data: [0,1,2]
     * }
     * ]
     */

    private Map<String, Object> packageData(List<LocationAvgdataVO> list) {
        Map<String, Object> map = new HashMap<String, Object>();
        //y轴的时间list
        List<Date> dates = new ArrayList<Date>();
        //将所有的时间值放进Date中
        for (LocationAvgdataVO deviceAvgdataVO : list) {
            List<AvgdataPO> avgdatas = deviceAvgdataVO.getAvgdataList();
            if (avgdatas != null) {
                for (AvgdataPO avgdataPO : avgdatas) {
                    if (!dates.contains(avgdataPO.getMsDate())) {
                        dates.add(avgdataPO.getMsDate());
                    }
                    //deviceAvgdataVO.getDateList()有 所有均值的时间
                    deviceAvgdataVO.getDateList().add(avgdataPO.getMsDate());
                }
            }
        }
        //根据时间排序
        Collections.sort(dates);
        for (LocationAvgdataVO deviceAvgdataVO : list) {
            //组装数据结构，某日期没有值时放入不可能的值，页面判断
            if (deviceAvgdataVO.getAvgdataList() != null) {
                for (Date date : dates) {
                    //deviceAvgdataVO 所有均值的时间不包括本次时间，则该时间没有值
                    if (!deviceAvgdataVO.getDateList().contains(date)) {
                        AvgdataPO avgdataPO1 = new AvgdataPO();
                        avgdataPO1.setAvgValue(-9999.0);
                        avgdataPO1.setMsDate(date);
                        deviceAvgdataVO.getAvgdataList().add(avgdataPO1);
                    }
                }
                //根据date排序
                Collections.sort(deviceAvgdataVO.getAvgdataList(), new Comparator<AvgdataPO>() {
                    @Override
                    public int compare(AvgdataPO o1, AvgdataPO o2) {
                        Date date = o1.getMsDate();
                        Date date1 = o2.getMsDate();
                        return date.compareTo(date1);
                    }
                });
            }
        }
        if (list.size() > 0) {
            map.put("data", list);
            map.put("categories", dates);
            return map;
        }
        return map;
    }
}
