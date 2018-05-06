package com.microwise.blueplanet.service.impl;

import com.google.common.base.Strings;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.dao.LocationDao;
import com.microwise.blueplanet.dao.OfflineDao;
import com.microwise.blueplanet.service.OfflineService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 离线数据批次Service实现
 *
 * @author chenyaofei
 * @date 2016-4-20
 */
@Service
@Transactional
@Blueplanet
public class OfflineServiceImpl implements OfflineService {

    /**
     * 用户Dao
     */
    @Autowired
    private OfflineDao offlineDao;
    @Autowired
    private LocationDao locationDao;

    @Override
    public void addLocationSensor(String locationId, List<Integer> checkedSensorInfoList) {
        offlineDao.addLocationSensor(locationId, checkedSensorInfoList);
    }

    @Override
    public List<LocationVO> findLocationByNameAndZoneId(String siteId,String locationName,String zoneId,int page,int pageSize) {
        List<LocationVO> locations = offlineDao.findLocationByNameAndZoneId(siteId,Strings.emptyToNull(locationName),Strings.emptyToNull(zoneId),page,pageSize);
          for(LocationVO location : locations){
              location.setZone(offlineDao.findZoneByZoneId(location.getZoneId()));
          }
        return locations;
    }

    @Override
    public int findAllLocationByNameAndZoneId(String siteId,String locationName,String zoneId) {
        return offlineDao.findAllLocationByNameAndZoneId(siteId, Strings.emptyToNull(locationName), Strings.emptyToNull(zoneId));
    }

    @Override
    public boolean findLocationDataById(String locationId) {
        int dataSize=locationDao.findRecentDataList(locationId,1).size();
        if(dataSize>0){
           return false;
        }
        return true;
    }

    @Override
    public void updateLocation(LocationVO location) {
        offlineDao.updateLocation(location);
    }

    @Override
    public void updateSensorById(String locationId, List<Integer> checkedSensorInfoList) {
        locationDao.deleteLocationSensor(locationId);
        addLocationSensor(locationId,checkedSensorInfoList);
    }


}
