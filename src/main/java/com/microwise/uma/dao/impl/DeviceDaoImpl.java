package com.microwise.uma.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.uma.bean.DeviceBean;
import com.microwise.uma.dao.DeviceDao;
import com.microwise.uma.sys.Uma;
import com.microwise.uma.sys.UmaBaseDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备 dao 实现
 * 
 * @author xubaoji
 * @date 2013-4-15
 */
@Dao
@Uma
public class DeviceDaoImpl extends UmaBaseDao implements DeviceDao {

	@Override
	public List<DeviceBean> findDeviceList(int type, String zoneId, boolean isAll , int index,
			int pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("zoneId", zoneId);
		map.put("start", (index - 1) * pageSize);
		map.put("pageSize", pageSize);
		map.put("isAll", isAll);
		return getSqlSession().selectList(
				"uma.mybatis.DeviceDao.findDeviceList", map);
	}

	@Override
	public int findDeviceListCount(int type, String zoneId, boolean isAll) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("zoneId", zoneId);
		map.put("isAll", isAll);
		return getSqlSession().<Integer> selectOne(
				"uma.mybatis.DeviceDao.findDeviceListCount", map);
	}

	@Override
	public DeviceBean findDeviceById(int deviceId) {
		return getSqlSession().selectOne(
				"uma.mybatis.DeviceDao.findDeviceById", deviceId);
	}

	@Override
	public void updateDeviceName(int deviceId, String deviceName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", deviceId);
		map.put("name", deviceName);
		getSqlSession().update("uma.mybatis.DeviceDao.updateDeviceName", map);
	}

	@Override
	public void updateDeviceZone(int deviceId, String zoneId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", deviceId);
		map.put("zoneId", zoneId);
		getSqlSession().insert("uma.mybatis.DeviceDao.updateDeviceZone", map);
	}

	@Override
	public List<DeviceBean> findAllExciter() {

		return getSqlSession().selectList(
				"uma.mybatis.DeviceDao.findAllExciter");
	}

	@Override
	public List<DeviceBean> findAllCard() {

		return getSqlSession().selectList("uma.mybatis.DeviceDao.findAllCard");
	}

	@Override
	public boolean isNameUsed(int deviceId, String deviceName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", deviceId);
		map.put("name", deviceName);
		return getSqlSession().<Integer> selectOne(
				"uma.mybatis.DeviceDao.isNameUsed", map) > 0;
	}

	@Override
	public void setEnable(int deviceId, boolean enable) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", deviceId);
		map.put("enable", enable ? 1 : 0);
		getSqlSession().update("uma.mybatis.DeviceDao.setEnable", map);
	}

	@Override
	public Integer findExciterUseCount(int deviceId) {
		return getSqlSession().selectOne(
				"uma.mybatis.DeviceDao.findExciterUseCount", deviceId);
	}

}
