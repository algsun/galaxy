package com.microwise.blueplanet.sys.cache;

import com.google.common.base.Function;
import com.google.common.cache.CacheLoader;
import com.google.common.collect.Lists;
import com.microwise.blueplanet.bean.vo.DeviceVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.service.DeviceService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.freemarker.tools.LocaleBundleTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 整个应用的设备缓存
 *
 * @author gaohui
 * @date 13-3-12 13:22
 */
@Component
@Scope("prototype")
@Blueplanet
public class DeviceCacheLoader extends CacheLoader<String, DeviceVO> {
    @Autowired
    private DeviceService deviceService;

    @Override
    public DeviceVO load(String deviceId) throws Exception {
        DeviceVO deviceVO = null;

        deviceVO = deviceService.findDeviceById(deviceId);
        int nodeType = deviceVO.getNodeType();
        if (nodeType == DeviceVO.DEVICE_TYPE_SENSOR || nodeType == DeviceVO.DEVICE_TYPE_SLAVE_MODULE) {
            List<SensorinfoVO> sensorinfos = deviceService.findSensorinfo(deviceId, LocaleBundleTools.appLocale());
            List<Integer> sensorPhysicalIds = Lists.transform(sensorinfos, new Function<SensorinfoVO, Integer>() {
                @Override
                public Integer apply(SensorinfoVO sensorinfoVO) {
                    return sensorinfoVO.getSensorPhysicalid();
                }
            });

            deviceVO.setSensorPhysicalidList(sensorPhysicalIds);
        }
        return deviceVO;
    }
}
