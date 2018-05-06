package com.microwise.proxima.service.impl;

import com.microwise.proxima.bean.Zone;
import com.microwise.proxima.dao.ZoneDao;
import com.microwise.proxima.service.ZoneService;
import com.microwise.proxima.sys.Proxima;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 区域Service实现
 * 
 * @author zhangpeng
 * @date 2013-3-22
 */
@Service
@Transactional
@Proxima
public class ZoneServiceImpl implements ZoneService {

	@Autowired
	private ZoneDao zoneDao;

	@Override
	public List<Zone> find(String siteId) {
		return zoneDao.find(siteId);
	}
	
	@Override
	public List<Zone> findAllZone(String siteId) {
		
		return zoneDao.findAllZone(siteId);
	}

	@Override
	public List<Zone> findHasOptics(String siteId) {
		return zoneDao.findHasOptics(siteId);
	}

	@Override
	public Zone findById(String zoneId) {
		return zoneDao.findById(zoneId);
	}

	@Override
	public boolean hasDV(String zoneId) {
		return zoneDao.getDVCount(zoneId) > 0;
	}

    @Override
    public List<Zone> findHasInfrareds(String siteId) {
        return zoneDao.findHasInfrareds(siteId);
    }
}
