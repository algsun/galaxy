package com.microwise.proxima.service.impl;

import com.microwise.proxima.dao.SystemConfigDao;
import com.microwise.proxima.service.SystemConfigService;
import com.microwise.proxima.sys.Proxima;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统配置Service实现
 * 
 * @author zhangpeng
 * @date 2013-3-26
 */
@Service
@Transactional
@Proxima
public class SystemConfigServiceImpl implements SystemConfigService {

	@Autowired
	private SystemConfigDao systemConfigDao;

	@Override
	public int findDvPlaceCodeIndex() {
		return systemConfigDao.findDvPlaceCodeIndex();
	}

	@Override
	public void updateDvPlaceCodeIndex(int index) {
		systemConfigDao.updateDvPlaceCodeIndex(index);
	}

}
