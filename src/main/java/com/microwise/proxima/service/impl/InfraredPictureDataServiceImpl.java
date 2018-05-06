package com.microwise.proxima.service.impl;

import com.microwise.proxima.bean.InfraredPictureDataBean;
import com.microwise.proxima.dao.InfraredPictureDataDao;
import com.microwise.proxima.service.InfraredPictureDataService;
import com.microwise.proxima.sys.Proxima;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 * 红外图片数据信息服务层实现
 * </pre>
 *
 * @author li.jianfei
 * @date 2012-09-03
 */
@Service
@Transactional
@Proxima
public class InfraredPictureDataServiceImpl implements
		InfraredPictureDataService {
	
	@Autowired
	private InfraredPictureDataDao infraredPictureDataDao; 
	
	@Override
	public int save(InfraredPictureDataBean infraredPictureData) {
		return (Integer) infraredPictureDataDao.save(infraredPictureData);
	}

	@Override
	public InfraredPictureDataBean findById(int id) {
		return infraredPictureDataDao.findById(id);
	}

	@Override
	public InfraredPictureDataBean findByPicId(String picId) {
		return infraredPictureDataDao.findByPicId(picId);
	}

    @Override
	public double findMaxHighTemperature(String dvPlaceId, Date startDate,
			Date endDate) {
		return infraredPictureDataDao.findMaxHighTemperature(dvPlaceId, startDate, endDate);
	}

	@Override
	public double findMinLowTemperature(String dvPlaceId, Date startDate,
			Date endDate) {
		return infraredPictureDataDao.findMinLowTemperature(dvPlaceId, startDate, endDate);
	}

	@Override
	public List<InfraredPictureDataBean> findListForChart(String dvPlaceId,
			Date startDate, Date endDate) {
		
		return infraredPictureDataDao.findListForChart(dvPlaceId, startDate, endDate);
	}
}
