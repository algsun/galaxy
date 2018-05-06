package com.microwise.orion.dao;

import com.microwise.proxima.bean.Zone;

import java.util.List;


/**
 * 区域dao
 *
 * @author xubaoji
 * @date 2013-5-21
 *
 * @check 2013-06-04 zhangpeng svn:3746
 */
public interface ZoneDao {

	/**
	 * 查询站点下有文物的区域
	 *
	 * @param siteId
	 *            站点编号
	 *
	 * @author 许保吉
	 * @date 2013-5-21
	 *
	 * @return List<Zone> 区域列表
	 */
	public List<Zone> findHasRelicZone(String siteId);
}
