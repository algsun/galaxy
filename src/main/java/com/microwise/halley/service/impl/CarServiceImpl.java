package com.microwise.halley.service.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.microwise.blueplanet.bean.vo.DeviceVO;
import com.microwise.blueplanet.bean.vo.LocationDataVO;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.proxy.LocationProxy;
import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.halley.bean.po.*;
import com.microwise.halley.bean.vo.CarVO;
import com.microwise.halley.dao.CarDao;
import com.microwise.halley.dao.PathDao;
import com.microwise.halley.service.CarService;
import com.microwise.halley.sys.Halley;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 车辆信息查询服务接口实现
 *
 * @author wanggeng
 * @date 13-9-26 下午2:3
 * @check @li.jianfei #5834 2013-10-10
 */
@Service
@Transactional
@Halley
public class CarServiceImpl implements CarService {

    /**
     * 车辆信息查询Dao接口
     */
    @Autowired
    private CarDao carDao;

    @Autowired
    private PathDao pathDao;

    /**
     * 车辆信息查询Dao接口
     */
    @Autowired
    private LocationProxy locationProxy;

    @Override
    public List<CarVO> findCarsWithDeviceByExhibitionId(int exhibitionId) {
        List<CarVO> cars = carDao.findCarsByExhibitionId(exhibitionId);
        for (CarVO car : cars) {
            int carId = car.getId();
            List<DevicePO> devices = carDao.findDevicesByCarId(carId);
            List<LocationVO> locations = carDao.findLocationsByCarId(carId);
            for (LocationVO location : locations) {
                DeviceVO deviceVO = locationProxy.findDeviceByLocationId(location.getId());
                location.setDevice(deviceVO);
            }
            car.setDevicePOList(devices);
            car.setLocationVOs(locations);
        }
        return cars;
    }

    @Override
    public List<CarPO> findAllCars() {
        return carDao.findAllCars();
    }

    @Override
    public void saveCarPO(CarPO carPO) {
        carDao.saveCarPO(carPO);
    }

    @Override
    public void updateCarPO(CarPO carPO) {
        carDao.updateCarPO(carPO);
    }

    @Override
    public void deleteCarByCarId(int carId) {
        carDao.deleteCarByCarId(carId);
    }

    @Override
    public void deleteDeviceByCarId(int carId) {
        carDao.deleteDeviceByCarId(carId);
    }

    @Override
    public void saveDeviceList(List<DevicePO> devicePOList) {
        for (DevicePO devicePO : devicePOList) {
            carDao.saveDevice(devicePO);
        }
    }

    @Override
    public void updateDeviceList(List<DevicePO> devicePOList, int carId) {
        carDao.deleteDeviceByCarId(carId);
        this.saveDeviceList(devicePOList);
    }

    @Override
    public void saveUserPO(UserPO userPO) {
        carDao.saveUserPO(userPO);
    }

    @Override
    public void deleteAllUser(int exhibitionId) {
        carDao.deleteAllUserPO(exhibitionId);
    }

    @Override
    public List<UserPO> findUserPO(int exhibitionId) {
        return carDao.findUserPO(exhibitionId);
    }

    @Override
    public void saveConfigPO(ConfigPO configPO) {
        int exhibitionId = configPO.getExhibitionId();
        carDao.deleteConfigPO(exhibitionId);
        carDao.saveConfigPO(configPO);
    }

    @Override
    public List<ConfigPO> findConfigByExhibitionId(int exhibitionId) {
        return carDao.findConfigPO(exhibitionId);
    }

    @Override
    public Integer findConfigExhibitionId(int exhibitionId) {
        return carDao.findConfigExhibitionId(exhibitionId);
    }

    @Override
    public Map<String, String> findCarSensor(int carId) {

        Map<String, String> map = new HashMap<String, String>();

        //根据车查设备
        List<Integer> sensorIds = Lists.newArrayList();
        //加速度、震动、开关门监测指标
        sensorIds.add(87);
        sensorIds.add(88);
        sensorIds.add(89);

        //震动加速度设备一个车上面只有一个
        String locationId = carDao.findLocationIds(carId);
        if (!Strings.isNullOrEmpty(locationId)) {
            List<LocationDataVO> locationDataVOs = locationProxy.findSensorByLocationId(locationId, sensorIds);
            for (LocationDataVO locationDataVO : locationDataVOs) {
                map.put(locationDataVO.getEnName(), locationDataVO.getSensorPhysicalValue());
            }
        }

        //根据设备查监测指标
        RouteHistoryPO routeHistoryPO = pathDao.findRouteHistoryPO(carId);
        if (routeHistoryPO != null) {
            map.put("longitude", String.valueOf(routeHistoryPO.getLongitude()));
            map.put("latitude", String.valueOf(routeHistoryPO.getLatitude()));
        }
        return map;
    }
}
