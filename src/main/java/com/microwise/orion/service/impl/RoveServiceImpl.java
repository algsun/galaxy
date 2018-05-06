package com.microwise.orion.service.impl;

import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.orion.bean.Rove;
import com.microwise.orion.dao.RoveDao;
import com.microwise.orion.service.RoveService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 文物流传经历 service实现
 * 
 * @author xubaoji
 * @date  2013-6-4
 *
 * @check 2013-06-04 zhangpeng svn:4046
 */
@Transactional
@Orion
@Service
public class RoveServiceImpl implements RoveService{
	
	/**文物流传经历 dao*/
	@Autowired
	private  RoveDao roveDao;
	
	@Override
	public void addRelicRove(Rove rove) {
		roveDao.save(rove);
	}

	@Override
	public Rove findById(Integer id) {
		return roveDao.findById(id);
	}


}
