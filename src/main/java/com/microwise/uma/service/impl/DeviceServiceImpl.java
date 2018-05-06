package com.microwise.uma.service.impl;

import com.google.common.base.Strings;
import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.uma.bean.DeviceBean;
import com.microwise.uma.dao.DeviceDao;
import com.microwise.uma.service.DeviceService;
import com.microwise.uma.sys.Uma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 设备 service 实现
 * 
 * @author xubaoji
 * @date 2013-4-15
 * @check @li.jianfei 2013-06-04 #3989
 */
@Service
@Uma
@Transactional
public class DeviceServiceImpl implements DeviceService {

	/**
	 * 设备DAO
	 */
	@Autowired
	private DeviceDao deviceDao;

	@Override
	public List<DeviceBean> findDeviceList(int type, String zoneId, boolean isAll , int index,
			int pageSize) {
		return deviceDao.findDeviceList(type, zoneId, isAll ,index, pageSize);
	}

	@Override
	public int findDeviceListCount(int type, String zoneId, boolean isAll) {
		return deviceDao.findDeviceListCount(type, zoneId, isAll);
	}

	@Override
	public DeviceBean findDeviceById(int deviceId) {
		return deviceDao.findDeviceById(deviceId);
	}

	@Override
	public void updateDevice(int deviceId, String deviceName, String zoneId) {
		deviceDao.updateDeviceName(deviceId, deviceName);

		// 区域ID 不为空时为设备绑定区域
		if (!Strings.isNullOrEmpty(zoneId)) {
			deviceDao.updateDeviceZone(deviceId, zoneId);
		}
	}

	@Override
	public void setEnable(int deviceId, boolean enable) {
		deviceDao.setEnable(deviceId, enable);
	}

	@Override
	public boolean isNameUsed(int deviceId, String deviceName) {
		return deviceDao.isNameUsed(deviceId, deviceName);
	}

	@Override
	public List<DeviceBean> findAllExciter() {
		return deviceDao.findAllExciter();
	}

	@Override
	public List<DeviceBean> findAllCard() {
		return deviceDao.findAllCard();
	}

	@Override
	public boolean isCanDisableExciter(int deviceId) {
		Integer count = deviceDao.findExciterUseCount(deviceId);
		return count == 0;
	}
}
