package com.microwise.halley.service.impl;

import com.microwise.blueplanet.dao.DeviceDao;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.halley.bean.po.CarPO;
import com.microwise.halley.bean.po.DevicePO;
import com.microwise.halley.bean.vo.CarVO;
import com.microwise.halley.dao.CarDao;
import com.microwise.halley.service.NodeInfoService;
import com.microwise.halley.sys.Halley;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 哈雷子项目设备查询服务实现.
 *
 * @author wang.gneg
 * @date 13-10-8 上午10:45
 * @check @li.jianfei #5821 2013-10-10
 */
@Beans.Service
@Transactional
@Halley
public class NodeInfoServiceImpl implements NodeInfoService {

    @Autowired
    private CarDao carDao;

    @Autowired
    private DeviceDao deviceDao;

    @Override
    public List<DevicePO> findDeviceByDeviceType(int nodeType, int exhibitionId) {

        List<CarVO> cars = carDao.findCarsByExhibitionId(exhibitionId);
        List<DevicePO> allDevices = new ArrayList<DevicePO>();
        for (CarPO car : cars) {
            int carId = car.getId();
            List<DevicePO> devices = carDao.findDevicesByCarId(carId);
            allDevices.addAll(devices);
        }

        List<DevicePO> returnDevices = new ArrayList<DevicePO>();

        if (allDevices.size() > 0) {
            for (DevicePO device : allDevices) {
                if (device.getDeviceType() == nodeType) {
                    returnDevices.add(device);
                }
            }
        }
        return returnDevices;
    }

    @Override
    public void updateNodeInfo(String deviceId, int deviceType) {
        deviceDao.updateDeviceType(deviceId, deviceType);
    }
}
