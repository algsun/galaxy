package com.microwise.blueplanet.proxy.impl;

import com.microwise.blueplanet.bean.vo.DeviceVO;
import com.microwise.blueplanet.bean.vo.HistoryDataVO;
import com.microwise.blueplanet.bean.vo.RealtimeDataVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.proxy.DeviceProxy;
import com.microwise.blueplanet.service.DeviceService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.sys.freemarker.tools.LocaleBundleTools;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 设备代理实现
 * 
 * @author xu.baoji
 * @date 2013-10-17
 */
@Beans.Bean
@Blueplanet
public class DeviceProxyImpl implements DeviceProxy {

	/**设备 service 实现*/
	@Autowired
	private  DeviceService deviceService;
	
	@Override
	public List<HistoryDataVO> findHistoryData(String deviceId, Date startTime, Date endTime,
			Integer index, Integer pageSize) {
		return deviceService.findHistoryData(deviceId, startTime, endTime, index, pageSize);
	}

	@Override
	public int findHistoryDataCount(String deviceId, Date startTime, Date endTime) {
		return deviceService.findHistoryDataCount(deviceId, startTime, endTime);
	}

	@Override
	public List<RealtimeDataVO> findRealtimeData(List<String> deviceIds) {
		List<RealtimeDataVO>  realtimeData = new ArrayList<RealtimeDataVO>();
	    for (String deviceId : deviceIds) {
			realtimeData.add(deviceService.findRealtimeData(deviceId));
		}
		return realtimeData;
	}

    @Override
    public List<SensorinfoVO> findSensorinfo(List<String> deviceIds) {
        Set<SensorinfoVO> sensorinfos = new HashSet<SensorinfoVO>();
        for (String deviceId :deviceIds){
           sensorinfos.addAll(deviceService.findSensorinfo(deviceId, LocaleBundleTools.appLocale())) ;
        }
        return new ArrayList<SensorinfoVO>(sensorinfos);
    }

    @Override
    public DeviceVO findDeviceById(String deviceId) {
        return deviceService.findDeviceById(deviceId);
    }

    @Override
    public List<SensorinfoVO> findAllSensorinfo(String deviceId) {
        return deviceService.findAllSensorinfo(deviceId,LocaleBundleTools.appLocale());
    }


}
