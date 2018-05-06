package com.microwise.orion.service.impl;

import com.microwise.blackhole.bean.User;
import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.orion.bean.UserZone;
import com.microwise.orion.dao.UserZoneDao;
import com.microwise.orion.service.UserZoneService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.vo.UserZoneVo;
import com.microwise.proxima.bean.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户和区域对应关系 service 实现
 * 
 * @author xubaoji
 * @date 2013-6-17
 */
@Service
@Orion
@Transactional
public class UserZoneServiceImpl implements UserZoneService {

	/** 用户区域对应dao */
	@Autowired
	private UserZoneDao userZoneDao;

	@Override
	public void addUserZone(String zoneId, int...userIds) {
		for (int userId : userIds) {
			UserZone  userZone = new UserZone() ;
			userZone.setUserId(userId);
			userZone.setZoneId(zoneId);
			userZoneDao.save(userZone);
		}
	}

	@Override
	public void deleteUserZone(Integer userId, String zoneId) {
		userZoneDao.deleteUserZone(userId, zoneId);
	}

	@Override
	public List<UserZoneVo> findUserZoneList(String siteId) {
		List<UserZoneVo> userZoneVos = new ArrayList<UserZoneVo>();
		// 查询有管理的区域 列表
		List<Zone> zones = userZoneDao.findZones(siteId);
		if (zones != null) {
			// 查每个区域下的管理列表
			for (Zone zone : zones) {
				UserZoneVo userZoneVo = new UserZoneVo();
				List<User> users = userZoneDao.findZoneUser(zone.getId());
				userZoneVo.setUsers(users);
				userZoneVo.setZone(zone);
				userZoneVos.add(userZoneVo);
			}
		}
		return userZoneVos;
	}

}
