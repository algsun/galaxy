package com.microwise.proxima.service.impl;

import com.microwise.proxima.bean.PhotographScheduleBean;
import com.microwise.proxima.dao.PhotographScheduleDao;
import com.microwise.proxima.service.PhotographScheduleService;
import com.microwise.proxima.sys.Proxima;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 
 * @author zhang.licong
 * @date 2012-08-08
 */
@Service
@Transactional
@Proxima
public class PhotographScheduleServiceImpl implements PhotographScheduleService {

	@Autowired
	private PhotographScheduleDao photographScheduleDao;
	
	@Override
	@Transactional
	public List<PhotographScheduleBean> findAllOfDVPlace(String dvPlaceId) {
		
		return this.photographScheduleDao.findAllOfDVPlace(dvPlaceId);
	}

}
