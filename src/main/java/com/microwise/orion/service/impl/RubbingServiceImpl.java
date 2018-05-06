package com.microwise.orion.service.impl;

import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.orion.bean.Rubbing;
import com.microwise.orion.dao.RubbingDao;
import com.microwise.orion.service.RubbingService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 文物拓扑 service 实现类
 * 
 * @author xubaoji
 * @date 2013-5-21
 *
 * @check 2013-06-04 zhangpeng svn:3587
 */
@Service
@Orion
@Transactional
public class RubbingServiceImpl implements RubbingService {

	/**文物拓扑dao*/
	@Autowired
	private RubbingDao rubbingDao;

	@Override
	public void addRubbing(Rubbing rubbing) {
		rubbingDao.save(rubbing);
	}

    @Override
    public Rubbing findById(Integer id) {
         return rubbingDao.findById(id);
    }

	@Override
	public void deleteRubbing(Integer id) {
		rubbingDao.deleteRubbing(id);
	}
}
