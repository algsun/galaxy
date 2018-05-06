package com.microwise.orion.service;

import com.microwise.orion.vo.UserZoneVo;

import java.util.List;

/**
 * 用户区域关系 service
 * 
 * @author xubaoji
 * @date 2013-6-14
 */
public interface UserZoneService {

	/**
	 * 添加用户区域关系表
	 * 
	 * @param zoneId
	 *            区域id 编号
	 * @param userIds
	 *            用户id 列表
	 * 
	 * @author 许保吉
	 * @date 2013-6-14
	 * 
	 * @return void
	 */
	public void addUserZone(String zoneId, int... userIds);

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
	 * 查询一个站点下所有 有管理的区域
     *
     * 注意：如果无数据，返回空的集合而不是 null
	 * 
	 * @param siteId
	 *            站点编号
	 * 
	 * @author 许保吉
	 * @date 2013-6-17
	 * 
	 * @return List<UserZoneVo> 用户区域对应关系 vo 实体列表
	 */
	public List<UserZoneVo> findUserZoneList(String siteId);

}
