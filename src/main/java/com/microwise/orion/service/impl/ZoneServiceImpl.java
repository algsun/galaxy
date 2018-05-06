package com.microwise.orion.service.impl;

import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.orion.dao.ZoneDao;
import com.microwise.orion.service.ZoneService;
import com.microwise.orion.sys.Orion;
import com.microwise.proxima.bean.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 区域 service 实现
 * 
 * @author xubaoji
 * @date 2013-5-21
 *
 * @check 2013-06-04 zhangpeng svn:3746
 */
@Service
@Orion
@Transactional
public class ZoneServiceImpl implements ZoneService {

	/** 区域dao */
	@Autowired
	private ZoneDao zoneDao;

	@Override
	public List<Zone> findHasRelicZone(String siteId) {
		return zoneDao.findHasRelicZone(siteId);
	}
}
