package com.microwise.blueplanet.service.impl;

import com.microwise.blueplanet.bean.po.LocationSensorPO;
import com.microwise.blueplanet.bean.vo.LocationDataVO;
import com.microwise.blueplanet.bean.vo.RecentDataVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.dao.OfflineDataDao;
import com.microwise.blueplanet.service.OfflineDataService;
import com.microwise.blueplanet.sys.AppCacheHolder;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

/**
 * 离线数据 数据 操作 service
 *
 * @author liuzhu
 * @date 2016-4-20
 */
@Beans.Service
@Blueplanet
@Transactional
public class OfflineDataServiceImpl implements OfflineDataService {

    @Autowired
    private OfflineDataDao offlineDataDao;

    @Autowired
    private AppCacheHolder appCacheHolder;

    @Override
    public void deleteOfflineData(String locationId, Date date) {
        offlineDataDao.deleteOfflineData(locationId, date);
    }


    @Override
    public void insert(List<LocationSensorPO> sensorList) {
        for (LocationSensorPO locationSensor : sensorList) {
            try {
                SensorinfoVO sensorinfoVO = appCacheHolder.loadSensorinfo(locationSensor.getSensorPhysicalid());
                if (sensorinfoVO != null) {
                    double minValue = sensorinfoVO.getMinValue();
                    double maxValue = sensorinfoVO.getMaxValue();
                    double sensorValue = Double.parseDouble(locationSensor.getSensorValue());

                    //rangeTyep 为0时 没有最大值 最小值范围
                    int rangeType = sensorinfoVO.getRangeType();

                    //只有最小值范围
                    if (rangeType == 1) {
                        if (sensorValue < minValue) {
                            locationSensor.setState(0);
                        }
                    } else if (rangeType == 2) { //只有最大值范围
                        if (sensorValue > maxValue) {
                            locationSensor.setState(0);
                        }
                    } else if (rangeType == 3) { //两个都有
                        if (sensorValue > maxValue || sensorValue < minValue) {
                            locationSensor.setState(0);
                        }
                    }
                }

            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        offlineDataDao.insert(sensorList);
    }

    @Override
    public List<RecentDataVO> findOfflineHistory(String locationId) {
        List<RecentDataVO> recentDataList = offlineDataDao.findHistoryOfflineCreateTime(locationId);
        List<LocationDataVO> locationDataList = offlineDataDao.findLocationHistoryOffline(locationId);
        //根据位置点id、最小时间、最大时间获取位置点数据
        for (RecentDataVO nodeinfo : recentDataList) {
            Vector<LocationDataVO> locationDatas = new Vector<LocationDataVO>();
            for (LocationDataVO locationData : locationDataList) {
                if (nodeinfo.getStamp().getTime() == locationData.getStamp().getTime()) {
                    locationDatas.add(locationData);
                }
            }
            nodeinfo.setLocationDataVOs(locationDatas);
        }
        return recentDataList;
    }
}
