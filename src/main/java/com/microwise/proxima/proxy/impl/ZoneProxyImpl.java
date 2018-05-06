package com.microwise.proxima.proxy.impl;

import com.microwise.proxima.bean.Zone;
import com.microwise.proxima.proxy.ZoneProxy;
import com.microwise.proxima.service.ZoneService;
import com.microwise.proxima.sys.Proxima;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * proxima区域代理实现
 * 
 * @author zhangpeng
 * @date 2013-3-28
 */
@Component
@Scope("prototype")
@Proxima
public class ZoneProxyImpl implements ZoneProxy {

	/** 区域Service */
	@Autowired
	private ZoneService zoneService;

	@Override
	public boolean hasDV(String zoneId) {
		return zoneService.hasDV(zoneId);
	}
	
	@Override
	public List<Zone> findZoneList(String siteId) {
		return zoneService.findAllZone(siteId);
	}

}
