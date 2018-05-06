package com.microwise.proxima.proxy;

import com.microwise.proxima.bean.Zone;

import java.util.List;

/**
 * proxima区域Service代理层
 * 
 * @author zhangpeng
 * @date 2013-3-28
 */
public interface ZoneProxy {

	/**
	 * <pre>
	 * 判断区域下是否有摄像机
	 * 
	 * @param zoneId 区域id
	 * 
	 * @author zhangpeng
	 * @date 2013-3-28
	 * 
	 * @return boolean true有/false没有
	 * </pre>
	 */
	public boolean hasDV(String zoneId);

	/**
	 * 通过siteId 查询所有 顶级区域
	 * 
	 * @param siteId
	 *            站点编号
	 * 
	 * @author 许保吉
	 * @date 2013-5-21
	 * 
	 * @return List<Zone> 区域列表
	 */
	public List<Zone> findZoneList(String siteId);
}
