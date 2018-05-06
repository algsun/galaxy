package com.microwise.uma.service;

import com.microwise.uma.bean.DeviceBean;

import java.util.List;

/**
 * 设备 service 接口
 * 
 * @author xubaoji
 * @date 2013-4-15
 */
public interface DeviceService {

	/**
	 * 分页获取设备列表
	 * 
	 * @param type
	 *            设备类型（1-读卡器，2-激发器）
	 * @param zoneId
	 *            区域ID
	 * @param isAll    
	 * 			     是否查询所有设备：默认为只查询激活设备，默认值为false
	 * @param index
	 *            当前页面
	 * @param pageSize
	 *            分页单位(每页显示的数据量)
	 * @return 设备列表
	 */
	public List<DeviceBean> findDeviceList(int type, String zoneId,
			boolean isAll, int index, int pageSize);

	/**
	 * 获取设备数量
	 * 
	 * @param type
	 *            设备类型(1-读卡器，2-激发器)
	 * @param zoneId
	 *            区域ID
	 * @param isAll    
	 * 			     是否查询所有设备：默认为只查询激活设备，默认值为false
	 * @return 设备数量
	 */
	public int findDeviceListCount(int type, String zoneId , boolean isAll);

	/**
	 * 根据设备ID查找设备
	 * 
	 * @param deviceId
	 *            设备ID
	 * @return 设备对象
	 */
	public DeviceBean findDeviceById(int deviceId);

	/**
	 * 更改设备信息(名称、区域)
	 * 
	 * @param deviceId
	 *            设备ID
	 * @param deviceName
	 *            设备名称
	 */
	public void updateDevice(int deviceId, String deviceName, String zoneId);

	/**
	 * 停用/启用设备
	 * 
	 * @param deviceId
	 *            设备ID
	 * @param enable
	 *            是否启用
	 */
	public void setEnable(int deviceId, boolean enable);

	/**
	 * 查询所有激发器设备
	 * 
	 * @author 许保吉
	 * @date 2013-4-17
	 * 
	 * @return list<DeviceBean> 激发器设备列表
	 */
	public List<DeviceBean> findAllExciter();

	/**
	 * 查询所有未绑定 电子卡
	 * 
	 * @author 许保吉
	 * @date 2013-4-17
	 * 
	 * @return list<DeviceBean> 电子卡设备列表
	 */
	public List<DeviceBean> findAllCard();

	/**
	 * 查询设备名称是否已被使用
	 * 
	 * @param deviceId
	 *            设备ID
	 * @param deviceName
	 *            设备名称
	 * @return 是否被使用（true-是）
	 */
	public boolean isNameUsed(int deviceId, String deviceName);

	/**
	 * 判断一个激发器设备是否可被禁用
	 * 
	 * @param deviceId
	 * 
	 * @author 许保吉
	 * @date 2013-4-28
	 * 
	 * @return true ：可以禁用 false： 不可以禁用
	 */
	public boolean isCanDisableExciter(int deviceId);
}
