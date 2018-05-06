package com.microwise.orion.dao;

import com.microwise.blackhole.bean.User;
import com.microwise.common.sys.hibernate.BaseDao;
import com.microwise.orion.bean.UserZone;
import com.microwise.proxima.bean.Zone;

import java.util.List;

/**
 * 用户和区域 对应关系 到
 * 
 * @author xubaoji
 * @date 2013-6-17
 */
public interface UserZoneDao extends BaseDao<UserZone> {

	/***
	 * 删除用户和区域对应关系实体
	 * 
	 * @param userId
	 *            用户id
	 * @param zoneId
	 *            区域id
	 * 
	 * @author 许保吉
	 * @date 2013-6-14
	 * 
	 * @return void
	 */
	public void deleteUserZone(Integer userId, String zoneId);

	/**
	 * 通过站点编号查询有管理员的所有区域
	 * 
	 * @param siteId
	 *            站点编号
	 * 
	 * @author 许保吉
	 * @date 2013-6-17
	 * 
	 * @return List<Zone> 有管理员的区域实体列表
	 */
	public List<Zone> findZones(String siteId);

	/**
	 * 查询 区域管理员列表
	 * 
	 * @param zoneId
	 *            区域id
	 * 
	 * @author 许保吉
	 * @date 2013-6-17
	 *  
	 * @return List<User>用户
	 */
	public List<User> findZoneUser(String zoneId);

}
