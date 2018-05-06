package com.microwise.blueplanet.service.impl;

import com.google.common.collect.Lists;
import com.microwise.blueplanet.bean.po.DeviceState;
import com.microwise.blueplanet.bean.po.HumidityController;
import com.microwise.blueplanet.bean.po.HumidityControllerState;
import com.microwise.blueplanet.bean.vo.*;
import com.microwise.blueplanet.dao.CommonDao;
import com.microwise.blueplanet.dao.DeviceDao;
import com.microwise.blueplanet.dao.LocationDao;
import com.microwise.blueplanet.service.DeviceService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.util.JsonUtil;
import com.microwise.blueplanet.util.WindTools;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 设备Service
 *
 * @author zhangpeng
 * @date 2013-1-17
 */
@Service
@Transactional
@Blueplanet
public class DeviceServiceImpl implements DeviceService {

    /**
     * 设备 dao
     */
    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private LocationDao locationDao;

    /**
     * 公用 dao
     */
    @Autowired
    private CommonDao commonDao;

    @Override
    public void updateDevice(DeviceVO device) {
        // 对比数据版本号
        long dataVersion = getDataVersion(device.getNodeId());
        device.setDataVersion(dataVersion);
        List<DeviceVO> deviceVOs = new ArrayList<DeviceVO>();
        deviceVOs.add(device);
        if (device.getNodeType() == DeviceVO.DEVICE_TYPE_MASTER_MODULE) {
            List<DeviceVO> deviceList = deviceDao.findSlaveModule(device
                    .getNodeId());
            for (DeviceVO deviceV : deviceList) {
                DeviceVO deviceVO1 = new DeviceVO(deviceV.getNodeId(), dataVersion);
                deviceVOs.add(deviceVO1);
            }
        }
        deviceDao.updateDevices(deviceVOs);
    }

    /**
     * 获取设备应该更新的数据版本号
     *
     * @param deviceId 设备id
     * @author zhangpeng
     * @date 2013-2-25
     */
    private long getDataVersion(String deviceId) {
        long dataVersion = deviceDao.findDataVersion(deviceId);
        if (dataVersion > 0) {
            Integer tableDataVersion = commonDao
                    .findDataVersion(Constants.Blueplanet.TABLE_DEVICE_NAME);
            if (tableDataVersion != null) {
                dataVersion = tableDataVersion + 1;
            }
        }
        return dataVersion;
    }

    @Override
    public List<DeviceVO> findSlaveModule(String masterModuleDeviceId) {
        return deviceDao.findSlaveModule(masterModuleDeviceId);
    }

    @Override
    public List<DeviceVO> findDeviceList(String siteId, String deviceId, Integer deviceType, int index, int pageSize) {
        List<DeviceVO> deviceList = deviceDao.findDeviceList(siteId, deviceId, deviceType, index, pageSize);
        // 拿到主模块对应的从模块
        for (DeviceVO device : deviceList) {
            if (device.getNodeType() == DeviceVO.DEVICE_TYPE_MASTER_MODULE) {
                device.setSlaveModuleList(deviceDao.findSlaveModule(device
                        .getNodeId()));
            }
        }
        return deviceList;
    }

    @Override
    public List<DeviceVO> findDeviceList(String siteId, boolean isBinded) {
        List<DeviceVO> bindDevices = new ArrayList<DeviceVO>();
        List<DeviceVO> unBindDevices = new ArrayList<DeviceVO>();
        List<DeviceVO> devices = deviceDao.findDevicesBySiteId(siteId);

        for (DeviceVO device : devices) {
            LocationVO location = locationDao.findLocationByNodeId(device.getNodeId());
            if (location == null) {
                unBindDevices.add(device);
            } else {
                bindDevices.add(device);
            }
        }

        if (isBinded) {
            return bindDevices;
        } else {
            return unBindDevices;
        }
    }

    @Override
    public int findDeviceListCount(String siteId, String deviceId, int deviceType) {
        return deviceDao.findDeviceListCount(siteId, deviceId, deviceType);
    }

    @Override
    public RealtimeDataVO findRealtimeData(String locationId) {
        RealtimeDataVO realtimeDataVO = locationDao.findLocationData(locationId);
        if (realtimeDataVO != null) {
            List<DeviceDataVO> sensorinfoList = deviceDao.findNodesensor(
                    realtimeDataVO.getNodeId(), null);
            Map<Integer, DeviceDataVO> sensorinfoMap = new HashMap<Integer, DeviceDataVO>();
            for (DeviceDataVO deviceData : sensorinfoList) {
                sensorinfoMap.put(deviceData.getSensorPhysicalid(), deviceData);
            }
            realtimeDataVO.setSensorinfoMap(sensorinfoMap);
        }
        return realtimeDataVO;
    }

    @Override
    public List<SensorinfoVO> findSensorinfo(String deviceId, String language) {
        return deviceDao.findSensorinfo(deviceId,
                Constants.Blueplanet.SENSORINFO_ACTIVE_STATE_TRUE, language);
    }

    @Override
    public List<Integer> findSensorPhysicalid(String deviceId, Integer activeState) {
        return deviceDao.findSensorPhysicalid(deviceId, activeState);
    }

    @Override
    public List<SensorinfoVO> findAllSensorinfo(String deviceId, String language) {
        return deviceDao.findSensorinfo(deviceId, null, language);
    }

    @Override
    public List<HistoryDataVO> findHistoryData(String deviceId, Date startTime, Date endTime) {
        List<HistoryDataVO> historyDatas = deviceDao.findHistoryData(deviceId, startTime, endTime);
        List<DeviceDataVO> deviceDatas = deviceDao.findHistoryNodesensor(deviceId, startTime, endTime);
        assembHistoryData(historyDatas, deviceDatas);
        return historyDatas;
    }

    @Override
    public List<HistoryDataVO> findHistoryData(String deviceId, Date startTime,
                                               Date endTime, Integer index, Integer pageSize) {
        List<HistoryDataVO> historyDataList = new ArrayList<HistoryDataVO>();
        Map<String, Date> timeMap = deviceDao.findMaxAndMinTime(deviceId,
                startTime, endTime, index, pageSize);
        if (timeMap == null) {
            return historyDataList;
        }
        Date minTime = timeMap.get(Constants.Blueplanet.MAP_KEY_MIN_TIME);
        Date maxTime = timeMap.get(Constants.Blueplanet.MAP_KEY_MAX_TIME);
        historyDataList = deviceDao.findHistoryData(deviceId, minTime, maxTime);
        List<DeviceDataVO> deviceDataList = deviceDao.findHistoryNodesensor(
                deviceId, minTime, maxTime);
        assembHistoryData(historyDataList, deviceDataList);
        return historyDataList;
    }

    /**
     * 组装 历史数据
     *
     * @param historyDatas 设备  历史数据 列表 没有监测指标历史数据
     * @param deviceDatas  设备监测指标历史数据
     * @author xu.baoji
     * @date 2013-10-17
     */
    private void assembHistoryData(List<HistoryDataVO> historyDatas, List<DeviceDataVO> deviceDatas) {
        for (DeviceDataVO deviceDataVO : deviceDatas) {
            if (deviceDataVO.getShowType() == Constants.Blueplanet.SENSORINFO_SHOWTYPE_IS_WIND_DIRECTION) {
                deviceDataVO.setSensorPhysicalValue(WindTools
                        .updateWindDirection(deviceDataVO
                                .getSensorPhysicalValue()));
            }
            for (HistoryDataVO historyData : historyDatas) {
                Map<Integer, DeviceDataVO> deviceDataMap = historyData
                        .getSensorinfoMap();
                if (deviceDataMap == null) {
                    deviceDataMap = new HashMap<Integer, DeviceDataVO>();
                }
                if (historyData.getStamp().getTime() == deviceDataVO.getStamp()
                        .getTime()) {
                    deviceDataMap.put(deviceDataVO.getSensorPhysicalid(),
                            deviceDataVO);
                }
                historyData.setSensorinfoMap(deviceDataMap);
            }
        }
    }

    @Override
    public int findHistoryDataCount(String deviceId, Date startTime,
                                    Date endTime) {
        return deviceDao.findHistoryDataCount(deviceId, startTime, endTime);
    }

    @Override
    public DeviceVO findDeviceById(String deviceId) {
        return deviceDao.findDeviceById(deviceId);
    }

    @Override
    public void updateDeviceInterval(DeviceVO deviceVO) {
        deviceDao.updateDevicesInterval(deviceVO);
    }

    @Override
    public List<DeviceVO> findDevicesByType(String siteId, int type) {
        return deviceDao.findDevicesByType(siteId, type);
    }

    @Override
    public List<DeviceVO> findSubDevicesByDeviceId(String deviceId, String language) {
        DeviceVO device = deviceDao.findDeviceById(deviceId);
        List<DeviceVO> devices;
        if (device.getNodeVersion() == 1) {
            // 根据协议版本，子网, parentIP 查找 @gaohui 2014-20-17
            String subNet = device.getNodeId().substring(8, 10);
            devices = deviceDao.findV13Devices(device.getSiteId(), Integer.parseInt(subNet), Integer.parseInt(device.getCurrentIP()));
        } else {
            // 根据协议版本, parentIP 查找
            devices = deviceDao.findDevices(device.getSiteId(), device.getNodeVersion(), Integer.parseInt(device.getCurrentIP()));
        }
        // 带 sensor
        for (DeviceVO subDevice : devices) {
            subDevice.setSensors(deviceDao.findSensorinfo(subDevice.getNodeId(), null, language));
        }

        return devices;
    }

    @Override
    public void deleteDevice(String nodeId) {
        DeviceVO device = findDeviceById(nodeId);
        if (device.getNodeType() == 3) {
            for (DeviceVO slaveModule : findSlaveModule(nodeId)) {
                if (slaveModule != null) {
                    deviceDao.deleteDevice(slaveModule.getNodeId());
                }
            }
        }
        deviceDao.deleteDevice(nodeId);
    }

    @Override
    public List<DeviceVO> findDevicesBySiteId(String siteId) {
        List<DeviceVO> devices = deviceDao.findDevicesBySiteId(siteId);
        for (DeviceVO device : devices) {
            List<Integer> sensorPhysicalid = deviceDao.findSensorPhysicalid(
                    device.getNodeId(),
                    Constants.Blueplanet.SENSORINFO_ACTIVE_STATE_TRUE);
            device.setSensorPhysicalidList(sensorPhysicalid);
            device.setLocation(locationDao.findLocationById(device.getLocationId()));
        }
        return devices;
    }

    @Override
    public RainDeviceStateVO findRainDeviceState(String deviceId, Date createTime) {
        String content = deviceDao.findRainDeviceState(deviceId, createTime);
        return (RainDeviceStateVO) JsonUtil.toEntity(content, RainDeviceStateVO.class);
    }

    @Override
    public List<DeviceState> findHistoryLowVoltage(String deviceId, Date startTime, Date endTime) {
        return deviceDao.findHistoryLowVoltage(deviceId, startTime, endTime);
    }

    @Override
    public List<ProductStateVO> findProductStateVO(String siteId) {
        return deviceDao.findProductStateVO(siteId);
    }

    @Override
    public List<ProductStateVO> findProductStateVONoSn(String siteId) {
        return deviceDao.findProductStateVONoSn(siteId);
    }

    @Override
    public String findDeviceId(String sn) {
        return deviceDao.findDeviceId(sn);
    }

    @Override
    public DevicePropertyVO findLastContent(String deviceId) {
        String content = deviceDao.findContent(deviceId);
        DevicePropertyVO devicePropertyVO = (DevicePropertyVO) JsonUtil.toEntity(content, DevicePropertyVO.class);
        return devicePropertyVO;
    }

    @Override
    public String findLastFaultCode(String deviceId) {
        return deviceDao.findLastFaultCode(deviceId);
    }

    @Override
    public DevicePropertyVO findContents(String deviceId, int count) {
        // TODO why??
        DevicePropertyVO deviceProperty = findLastContent(deviceId);

        List<String> contents = deviceDao.findContents(deviceId, count);
        List<DevicePropertyVO> devicePropertyVOs = Lists.newArrayList();
        for (String con : contents) {
            DevicePropertyVO devicePropertyVO = (DevicePropertyVO) JsonUtil.toEntity(con, DevicePropertyVO.class);
            devicePropertyVOs.add(devicePropertyVO);
        }
        deviceProperty.setDevicePropertyVO(devicePropertyVOs);
        return deviceProperty;
    }

    @Override
    public List<String> findHumidities(String siteId) {
        return deviceDao.findHumidities(siteId);
    }

    public boolean isBuzzerSwitch(List<SensorinfoVO> sensorinfoes) {
        int muwei = 0;
        for (SensorinfoVO sensorinfoVO : sensorinfoes) {
            if (sensorinfoVO.getSensorPhysicalid() == 2048) {
                muwei++;
                continue;
            }
            if (sensorinfoVO.getSensorPhysicalid() == 2049) {
                muwei++;
                continue;
            }
            if (sensorinfoVO.getSensorPhysicalid() == 2050 || sensorinfoVO.getSensorPhysicalid() == 85) {
                muwei++;
                continue;
            }
            if (sensorinfoVO.getSensorPhysicalid() == 2051 || sensorinfoVO.getSensorPhysicalid() == 83) {
                muwei++;
                continue;
            }
            if (sensorinfoVO.getSensorPhysicalid() == 2056) {
                muwei++;
                continue;
            }
        }
        if (muwei > 1) {
            return true;
        }
        return false;
    }

    @Override
    public List<String> findAirConditioners(String siteId) {
        return deviceDao.findAirConditioners(siteId);
    }

    @Override
    public List<String> findControlDevices(String siteId, int deviceType) {
        return deviceDao.findControlDevices(siteId, deviceType);
    }
}