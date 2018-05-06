package com.microwise.blueplanet.service.impl;

import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.bean.vo.ThresholdVO;
import com.microwise.blueplanet.bean.vo.ZoneThresholdVO;
import com.microwise.blueplanet.dao.ThresholdDao;
import com.microwise.blueplanet.dao.ZoneDao;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.service.ThresholdService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 阈值配置 service接口实现
 *
 * @author xu.baoji
 * @date 2013-8-26
 */
@Blueplanet
@Service
public class ThresholdServiceImple implements ThresholdService {

    /**
     * 区域dao
     */
    @Autowired
    private ZoneDao zoneDao;

    /**
     * 阈值dao 接口
     */
    @Autowired
    private ThresholdDao thresholdDao;
    @Autowired
    private LocationService locationService;

    @Override
    public List<ZoneThresholdVO> findZoneThresholds(String zoneId) {
//         查询区域下监测指标阈值 设置信息
        List<ZoneThresholdVO> zoneThresholdVos = thresholdDao.findZoneThresholds(zoneId);
        return zoneThresholdVos;
    }

    @Override
    public List<SensorinfoVO> findThresholdSensorinfo(String zoneId) {
        return thresholdDao.findThresholdLocationData(zoneId);
    }

    @Override
    public void saveZoneThreshold(String zoneId, List<ZoneThresholdVO> thresholdVOs) {
        //区域阈值表添加区域报警阈值
        thresholdDao.deleteZoneThreshold(zoneId);
        for (ZoneThresholdVO zoneThreshold : thresholdVOs) {
            zoneThreshold.setZoneId(zoneId);
            thresholdDao.saveZoneThreshold(zoneThreshold);
        }

        //获取没有位置点阈值和者质地阈值的位置点
        List<String> locatonIds = findLocationsByZoneId(zoneId);
        if (locatonIds.size() > 0) {
            //循环添加位置点区域阈值
            for (String locationId : locatonIds) {
                thresholdDao.deleteThreshold(locationId, 1);
                for (ZoneThresholdVO zoneThreshold : thresholdVOs) {
                    locationSaveZoneThreshold(locationId, zoneThreshold);
                }
            }
        }

//        //合并有阈值的位置点
//        List<String> priLocationIds = thresholdDao.findNoZoneThresholdLocationIds(zoneId);
//        for(String priLocationId : priLocationIds){
//            thresholdDao.deleteThreshold(priLocationId, 1);
//            List<ThresholdVO> thresholdVOList = thresholdDao.findThresholdsByLocationId(priLocationId);
//            for (ZoneThresholdVO zoneThreshold : thresholdVOs) {
//                boolean flag = true;
//                for (ThresholdVO locationThreshold : thresholdVOList) {
//                    if(zoneThreshold.getSensorPhysicalId() == locationThreshold.getSensorPhysicalId()){
//                        flag = false;
//                        break;
//                    }
//                }
//                if(flag){
//                    locationSaveZoneThreshold(priLocationId, zoneThreshold);
//                }
//            }
//        }
    }

    @Transactional
    @Override
    public void deleteZoneThreshold(String zoneId) {
        thresholdDao.deleteZoneThreshold(zoneId);
        List<String> locationIds = findLocationsByZoneId(zoneId);
        if (locationIds.size() > 0) {
            for (String locationId : locationIds) {
                thresholdDao.deleteThreshold(locationId, 1);
            }
        }
    }

    @Override
    public String findParentIdByZoneId(String zoneId) {
        return thresholdDao.findParentIdByZoneId(zoneId);
    }

    @Override
    public List<ThresholdVO> findThresholds(String locationId) {
        return thresholdDao.findThresholds(locationId);
    }

    @Override
    public boolean exists(ThresholdVO threshold) {
        return thresholdDao.findThreshold(threshold.getLocationId(), threshold.getSensorPhysicalId()) == null;
    }

    @Override
    public void delete(String locationId) {
        thresholdDao.delete(locationId);
    }

    @Override
    public void delete(String locationId, int sensorId) {
        thresholdDao.delete(locationId, sensorId);
    }

    @Override
    public void save(ThresholdVO threshold) {
        List<ThresholdVO> thresholdVOs = thresholdDao.findThresholdsByLocationId(threshold.getLocationId(), 1);
        if (thresholdVOs.size() != 0){
            thresholdDao.deleteThreshold(threshold.getLocationId(), 1);
        }
        thresholdDao.save(threshold);
    }

    private List<String> findLocationsByZoneId(String zoneId) {
        //获取当前区域下所有的位置点
        List<LocationVO> locationVOList = locationService.findLocationsByZoneId(zoneId,false);
        //获取当前区域下所有拥有质地阈值或者位置点阈值的位置点集合
        List<String> locationVos = thresholdDao.findNoZoneThresholdLocationIds(zoneId);
        //在所有位置点中删除locationVos
        Iterator<LocationVO> ite = locationVOList.iterator();
        while (ite.hasNext()) {
            LocationVO locationVO = ite.next();
            if (locationVos.size() > 0) {
                for (String location : locationVos) {
                    if (locationVO.getId().equals(location)) {
                        ite.remove();
                    }
                }
            }
        }
        //只返回对象的locationId
        List<String> locationIds = new ArrayList<String>();
        for (LocationVO locationVo : locationVOList) {
            locationIds.add(locationVo.getId());
        }
        return locationIds;
    }
    private void locationSaveZoneThreshold(String locationId, ZoneThresholdVO zoneThreshold){
        //保存区域阈值
        ThresholdVO thresholdVO = new ThresholdVO();
        thresholdVO.setLocationId(locationId);
        thresholdVO.setSensorPhysicalId(zoneThreshold.getSensorPhysicalId());
        thresholdVO.setConditionType(zoneThreshold.getConditionType());
        thresholdVO.setTarget(zoneThreshold.getTarget());
        thresholdVO.setFloating(zoneThreshold.getFloating());
        thresholdVO.setThresholdType(1);
        thresholdDao.saveThreshold(thresholdVO);
    }
}
