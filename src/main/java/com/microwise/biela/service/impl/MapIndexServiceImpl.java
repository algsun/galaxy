package com.microwise.biela.service.impl;

import com.google.common.base.Strings;
import com.microwise.biela.bean.po.*;
import com.microwise.biela.bean.vo.CustomizeVO;
import com.microwise.biela.bean.vo.LocationInfoVO;
import com.microwise.biela.bean.vo.NodeSensorInfoVO;
import com.microwise.biela.dao.MapIndexDao;
import com.microwise.biela.service.MapIndexService;
import com.microwise.biela.sys.Biela;
import com.microwise.blackhole.bean.AreaCodeCN;
import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.dao.AreaCodeDao;
import com.microwise.blackhole.proxy.LogicGroupProxy;
import com.microwise.blueplanet.bean.po.ZonePO;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.common.sys.annotation.Beans;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 站点地图service接口实现类
 *
 * @author liuzhu
 * @date 14-1-2
 * @check @wang.geng 2014-1-17 #7708
 */

@Beans.Service
@Transactional
@Biela
public class MapIndexServiceImpl implements MapIndexService {

    @Autowired
    private MapIndexDao mapIndexDao;

    @Autowired
    private LocationService locationService;

    @Autowired
    private AreaCodeDao areaCodeDao;

    /**
     * 站点service代理
     */
    @Autowired
    private LogicGroupProxy logicGroupProxy;

    @Override
    public AreaCodePO findAreaCodePOBySiteId(String siteId) {
        Integer integer = mapIndexDao.findAreaNameBySiteId(siteId);
        return findCodeNameByAreaCode(mapIndexDao.findAreaCodePOByAreaCode(integer));
    }

    @Override
    public List<SitePO> findSitePOList(Integer logicGroupId, int userId) {
        List<SitePO> tempLogicGroupList = new ArrayList<SitePO>();
        //根据站点组id，userId 获取站点
        List<LogicGroup> logicGroupList = logicGroupProxy.findLogicGroupForMap(logicGroupId, userId);
        Date startDate = DateTime.now().minusDays(7).toDate();
        for (LogicGroup l : logicGroupList) {
            SitePO sitePO = new SitePO();
            //set 属性
            sitePO.setId(l.getId());
            sitePO.setSiteId(l.getSite().getSiteId());
            if (l.getSite().getLngBaiDu() != null) {
                sitePO.setLatBaiDu(l.getSite().getLatBaiDu());
            }
            if (l.getSite().getLatBaiDu() != null) {
                sitePO.setLngBaiDu(l.getSite().getLngBaiDu());
            }
            sitePO.setLogicGroupName(l.getSite().getSiteName());
            //获取区域
            sitePO.setAreaCodePO(findAreaCodePOBySiteId(l.getSite().getSiteId()));

            //获取定制监测指标
            List<CustomizeVO> customizeVOList = findCustomizeVOList(l.getSite().getSiteId());
            List<LocationInfoVO> tempNodeSensorInfoVOList = new ArrayList<LocationInfoVO>();
            for (CustomizeVO customizeVO : customizeVOList) {
                LocationInfoVO locationInfoVO = mapIndexDao.findNodeSensorInfoVOByDeviceSensorId(customizeVO.getLocationId(), customizeVO.getSensorId());
                if (locationInfoVO != null) {
                    locationInfoVO.setZoneName(customizeVO.getZoneName());
                    locationInfoVO.setLocationName(customizeVO.getLocationName());
                    if (customizeVO.getCustomizeRemark() != null && !"".equals(customizeVO.getCustomizeRemark())) {
                        locationInfoVO.setCustomizeRemark(customizeVO.getCustomizeRemark());
                    }
                    tempNodeSensorInfoVOList.add(locationInfoVO);
                }
            }
            sitePO.setNodeSensorInfoVOList(tempNodeSensorInfoVOList);
            //根据站点获取所有设备id
            List<String> locationIds = mapIndexDao.findLocationIdsBySite(l.getSite().getSiteId());
            float temperatureVariance = 0;
            int temperatureTimes = 0;
            //计算温度稳定性
            for (String locationId : locationIds) {
                Float tempTemperature = mapIndexDao.findVariances(locationId, 33, startDate, new Date());
                if (tempTemperature != 0.0) {
                    temperatureVariance += tempTemperature;
                    temperatureTimes++;
                }
            }
            if (temperatureTimes != 0) {
                temperatureVariance = getStandardDeviation(temperatureVariance / temperatureTimes);
                sitePO.setTemperatureStability(temperatureVariance);
            }
            tempLogicGroupList.add(sitePO);
        }
        tempLogicGroupList = getSiteSensorRank(tempLogicGroupList);
        return tempLogicGroupList;
    }

    @Override
    public List<SensorInfoPO> findSensorInfo(String siteId) {
        return mapIndexDao.findSensorInfo(siteId);
    }

    @Override
    public List<LocationInfoVO> findGeneralLocationInfoBySite(String siteId) {
        return mapIndexDao.findGeneralLocationInfoBySite(siteId);
    }

    @Override
    public LogicGroupPO findLogicGroupBySiteId(String siteId) {
        return mapIndexDao.findLogicGroupBySiteId(siteId);
    }

    @Override
    public List<ZoneLocationPO> findZoneLocationBySiteIdSensorId(String siteId, Integer sensorId) {
        //查询站点下所有的位置点
        List<LocationVO> locations = locationService.findLocationsBySiteIdAndLocationName(siteId, null);
        //返回结果对象
        List<ZoneLocationPO> zoneLocationPOList = new ArrayList<ZoneLocationPO>();
        //未部署位置点
        List<LocationVO> unDeployLocationList = new ArrayList<LocationVO>();
        //zone去重，将未部署位置点放入unDeployLocationList
        Map<String, Object> zoneIdMap = new HashMap<String, Object>();
        for (LocationVO location : locations) {
            if (Strings.isNullOrEmpty(location.getNodeId())) {
                continue;
            }
            String zoneId = location.getZoneId();
            if (!Strings.isNullOrEmpty(zoneId)) {
                if (!zoneIdMap.containsKey(zoneId)) {
                    zoneIdMap.put(zoneId, "");
                }
            } else {
                unDeployLocationList.add(location);
            }
        }

        for (String zoneId : zoneIdMap.keySet()) {
            ZoneLocationPO zoneLocationPO = new ZoneLocationPO();
            String zoneName = "";
            List<LocationVO> tempLocationList = mapIndexDao.findZoneLocationByZoneIdSensorId(siteId, zoneId, sensorId);
            //获取设备直连区域
            ZonePO findZonePO = mapIndexDao.findZoneById(zoneId);
            if (findZonePO.getParentId() != null) {
                //获取直连区域的上一级区域
                ZonePO findParentZonePO = mapIndexDao.findZoneById(findZonePO.getParentId());
                zoneName += findParentZonePO.getZoneName() + "/" + findZonePO.getZoneName();
            } else {
                zoneName += findZonePO.getZoneName();
            }
            zoneLocationPO.setZoneName(zoneName);
            zoneLocationPO.setLocationList(tempLocationList);
            zoneLocationPOList.add(zoneLocationPO);
        }

        ZoneLocationPO unDeployZoneLocationPO = new ZoneLocationPO();
        unDeployZoneLocationPO.setZoneName("未部署位置点");
        unDeployZoneLocationPO.setLocationList(unDeployLocationList);
        zoneLocationPOList.add(unDeployZoneLocationPO);

        return zoneLocationPOList;
    }

    @Override
    public void saveCustomize(String siteId, String locationId, Integer sensorId, String customizeRemark) {
        mapIndexDao.saveCustomize(siteId, locationId, sensorId, customizeRemark);
    }

    @Override
    public List<CustomizeVO> findCustomizeVOList(String siteId) {
        //获取所有定制，根据站点
        List<CustomizeVO> customizeVOList = mapIndexDao.findCustomizeVOList(siteId);
        for (CustomizeVO customizeVO : customizeVOList) {
            LocationVO locationVO = locationService.findLocationById(customizeVO.getLocationId());
            String zoneName = "";
            if (locationVO != null) {
                if (!Strings.isNullOrEmpty(locationVO.getZoneId())) {//已绑定设备
                    ZonePO zonePO = mapIndexDao.findZoneById(locationVO.getZoneId());
                    if (zonePO.getParentId() != null) {
                        ZonePO parentZonePO = mapIndexDao.findZoneById(zonePO.getParentId());
                        zoneName += parentZonePO.getZoneName() + "/" + zonePO.getZoneName();
                    } else {
                        zoneName += zonePO.getZoneName();
                    }
                    customizeVO.setZoneName(zoneName);
                } else {
                    customizeVO.setZoneName("未部署设备");
                }

                customizeVO.setLocationName(locationVO.getLocationName());
                //set监测指标名称
                customizeVO.setSensorName(mapIndexDao.findSensorNameBySensorId(customizeVO.getSensorId()));
            }
        }
        return customizeVOList;
    }

    @Override
    public void deleteCustomizeById(Integer id) {
        mapIndexDao.deleteCustomizeById(id);
    }

    @Override
    public Integer verifyCustomize(String siteId, String locationId, Integer sensorId) {
        return mapIndexDao.verifyCustomize(siteId, locationId, sensorId);
    }

    @Override
    public Integer customizeCount(String siteId) {
        return mapIndexDao.customizeCount(siteId);
    }

    @Override
    public Map<String, List<NodeSensorInfoVO>> findSiteRealtimeAvg(List<Integer> sensorIds, int logicGroupId) {
        Map<String, List<NodeSensorInfoVO>> map = new HashMap<String, List<NodeSensorInfoVO>>();
        List<NodeSensorInfoVO> nodeSensorInfos = mapIndexDao.findSiteRealtimeAvg(sensorIds, logicGroupId);
        List<AreaCodeCN> areaCodes = areaCodeDao.findAllArea();


        //Set集合不能重复，按站点将平均数据分开
        Set<Integer> areaCodeSet = new HashSet<Integer>();
        for (NodeSensorInfoVO nodeSensorInfo : nodeSensorInfos) {
            areaCodeSet.add(nodeSensorInfo.getAreaCode());
        }

        for (int code : areaCodeSet) {
            String name = getAreaName(code, areaCodes, "");
            List<NodeSensorInfoVO> list = new ArrayList<NodeSensorInfoVO>();
            for (NodeSensorInfoVO nodeSensorInfo : nodeSensorInfos) {
                if (nodeSensorInfo.getAreaCode() == code) {
                    list.add(nodeSensorInfo);
                }
            }
            map.put(name, list);
        }
        return disposeMapData(map, sensorIds);
    }

    /**
     * 计算省/直辖市内的监测指标的平均值
     *
     * @param map 数据对象
     * @return 计算后的结果数据对象
     */
    private Map<String, List<NodeSensorInfoVO>> disposeMapData(Map<String, List<NodeSensorInfoVO>> map, List<Integer> sensorIds) {
        Map<String, List<NodeSensorInfoVO>> returnMap = new HashMap<String, List<NodeSensorInfoVO>>();
        for (String areaName : map.keySet()) {
            List<NodeSensorInfoVO> returnList = new ArrayList<NodeSensorInfoVO>();
            List<NodeSensorInfoVO> list = map.get(areaName);
            for (Integer sensorid : sensorIds) {
                List<NodeSensorInfoVO> tempList = new ArrayList<NodeSensorInfoVO>();
                for (NodeSensorInfoVO nodeSensorInfo : list) {
                    if (sensorid == nodeSensorInfo.getSensorPhysicalid()) {
                        tempList.add(nodeSensorInfo);
                    }
                }
                if (tempList.size() > 0) {
                    NodeSensorInfoVO avgValueNodeSensorInfo = getAvgValue(tempList);
                    returnList.add(avgValueNodeSensorInfo);
                }
            }
            returnMap.put(areaName, returnList);
        }
        return returnMap;
    }

    /**
     * 同一个站点下的统一个监测指标的平均值计算
     *
     * @param tempList 一个站点下的所有的相同的监测指标
     * @return 计算后的平均值监测指标
     */
    private NodeSensorInfoVO getAvgValue(List<NodeSensorInfoVO> tempList) {
        double sum = 0;
        for (NodeSensorInfoVO nodeSensorInfo : tempList) {
            sum = sum + Double.parseDouble(nodeSensorInfo.getSensorPhysicalValue());
        }
        NodeSensorInfoVO nodeSensor = tempList.get(0);
        double avgValue = sum / tempList.size();
        nodeSensor.setSensorPhysicalValue(avgValue + "");
        return nodeSensor;
    }

    /**
     * 根据areaCode递归获得省/直辖市名称(省/直辖市没有父级areaCode)
     *
     * @param areaCode  当前areaCode
     * @param areaCodes 所有的区域集合
     * @return 省/直辖市名称
     */
    private String getAreaName(int areaCode, List<AreaCodeCN> areaCodes, String name) {
        AreaCodeCN parentArea = getParentArea(areaCode, areaCodes);
        if (parentArea != null) {
            name = getAreaName(parentArea.getAreaCode(), areaCodes, name);
        } else {
            for (AreaCodeCN a : areaCodes) {
                if (a.getAreaCode() == areaCode) {
                    name = a.getAreaName();
                }
            }
        }
        return name;
    }

    /**
     * 获取父级区域对象
     *
     * @param areaCode  子区域对象
     * @param areaCodes 区域集合
     * @return 父级区域
     */
    private AreaCodeCN getParentArea(int areaCode, List<AreaCodeCN> areaCodes) {
        AreaCodeCN area = new AreaCodeCN();
        for (AreaCodeCN areaCodeCN : areaCodes) {
            if (areaCode == areaCodeCN.getAreaCode()) {
                area = areaCodeCN.getParentAreaCodeCN();
            }
        }
        return area;
    }

    /**
     * 将方差集合取平均值开根获取指标的标准差，即稳定性
     *
     * @param variances 区域某指标的方差集合
     * @date 2014-1-3
     */
    private float getStandardDeviation(Float variances) {
        float standardDeviation = (float) Math.sqrt(variances);
        return standardDeviation;
    }

    /**
     * 获取温湿度的稳定性
     *
     * @param sitePOs 站点信息
     * @return sitePOs 封装完温湿度稳定性
     * @author liuzhu
     * @date 2014-1-6
     */
    private List<SitePO> getSiteSensorRank(List<SitePO> sitePOs) {
        if (sitePOs.size() > 1) {
            List<SitePO> sitePOList = new ArrayList<SitePO>();
            for (SitePO sitePO : sitePOs) {
                if (sitePO.getTemperatureStability() != null) {
                    sitePOList.add(sitePO);
                } else {
                    sitePO.setTemperatureRank("unknown");
                }
            }
            if (sitePOList.size() > 1) {
                TemperatureStabilityComparator stabilityComparator = new TemperatureStabilityComparator();
                Collections.sort(sitePOList, stabilityComparator);
                SitePO minSitePO = sitePOList.get(0);
                SitePO maxSitePO = sitePOList.get(sitePOList.size() - 1);
                Float baseTemperature = (minSitePO.getTemperatureStability() + maxSitePO.getTemperatureStability()) / 3F;
                for (SitePO sitePO : sitePOList) {
                    if (sitePO.getTemperatureStability() < baseTemperature) {
                        sitePO.setTemperatureRank("success");
                    } else if (sitePO.getTemperatureStability() > (2 * baseTemperature)) {
                        sitePO.setTemperatureRank("danger");
                    } else {
                        sitePO.setTemperatureRank("warning");
                    }
                }
            }

        }
        return sitePOs;
    }

    //根据温度排序
    public static class TemperatureStabilityComparator implements Comparator<SitePO> {
        @Override
        public int compare(SitePO siteSensorPO1, SitePO siteSensorPO2) {
            if (siteSensorPO1.getTemperatureStability() != null) {
                if (siteSensorPO1.getTemperatureStability() > siteSensorPO2.getTemperatureStability()) {
                    return 1;
                } else if (siteSensorPO1.getTemperatureStability() < siteSensorPO2.getTemperatureStability()) {
                    return -1;
                }
            }
            return 0;
        }
    }

    /**
     * 递归找到省级区域
     *
     * @param areaCodePO
     * @return
     */
    private AreaCodePO findCodeNameByAreaCode(AreaCodePO areaCodePO) {
        if (areaCodePO.getParentCode() != 0) {
            AreaCodePO areaCodePOTemp = mapIndexDao.findAreaCodePOByAreaCode(areaCodePO.getParentCode());
            return findCodeNameByAreaCode(areaCodePOTemp);
        } else {
            return areaCodePO;
        }
    }
}
