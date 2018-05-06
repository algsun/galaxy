/**
 * 
 */
package com.microwise.proxima.service.impl;

import com.microwise.proxima.bean.InfraredDVPlaceBean;
import com.microwise.proxima.dao.InfraredDVPlaceDao;
import com.microwise.proxima.service.InfraredDVPlaceService;
import com.microwise.proxima.sys.Proxima;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <pre>
 * 红外热像仪服务层实现
 * </pre>
 * 
 * @author zhangpeng
 * @date 2012-7-9
 * 
 * @check zhang.licong 2012-07-14
 */
@Service
@Transactional
@Proxima
public class InfraredDVPlaceServiceImpl implements InfraredDVPlaceService {

	@Autowired
	private InfraredDVPlaceDao infraredDVPlaceDao;

	@Override
	public void save(InfraredDVPlaceBean infraredDVPlace) {
		 infraredDVPlaceDao.save(infraredDVPlace);
	}

	@Override
	public InfraredDVPlaceBean findById(int id) {
		return infraredDVPlaceDao.findById(id);
	}

    @Override
	public List<InfraredDVPlaceBean> findAll(String siteId) {
		return infraredDVPlaceDao.findAll(siteId);
	}

        @Override
	public List<InfraredDVPlaceBean> findAllByZoneId(String zoneId) {
		return infraredDVPlaceDao.findAllByZoneId(zoneId);
	}

	@Override
	public void update(InfraredDVPlaceBean infraredDVPlace) {
		infraredDVPlaceDao.update(infraredDVPlace);
	}


    @Override
    public InfraredDVPlaceBean findById(String id) {
        return infraredDVPlaceDao.findById(id);
    }

	@Override
	public List<InfraredDVPlaceBean> findByMonitorPointId(int monitorPointId) {
		return infraredDVPlaceDao.findByMonitorPointId(monitorPointId);
	}

}
