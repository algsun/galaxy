package com.microwise.uma.service.impl;

import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.uma.bean.PersonBean;
import com.microwise.uma.dao.AllotcardDao;
import com.microwise.uma.service.AllotcardService;
import com.microwise.uma.sys.Uma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 人员 service 实现
 * 
 * @author xubaoji
 * @date 2013-4-15
 * @check @li.jianfei 2013-06-04 #4011
 */
@Service
@Uma
@Transactional
public class AllotcardServiceImpl implements AllotcardService {

	/** 发卡dao */
	@Autowired
	private AllotcardDao allotcardDao;

	@Override
	public List<PersonBean> findPerson(String personName, boolean isHasCard, boolean isLowPower,
			Integer pageSize, Integer pageNumber,String siteId) {
		return allotcardDao.findPerson(personName, isHasCard,isLowPower, pageSize, pageNumber ,siteId);
	}

	@Override
	public Integer findPersonCount(String personName, boolean isHasCard,boolean isLowPower,String siteId) {
		return allotcardDao.findPersonCount(personName, isHasCard,isLowPower,siteId);
	}

    @Override
    public List<PersonBean> findAllPersons(int logicGroupId, boolean hasCard) {
        return allotcardDao.findAllPersons(logicGroupId, hasCard);
    }

    @Override
	public void sendCardForPerson(Integer personId, String cardSn) {
		allotcardDao.sendCardForPerson(personId, cardSn);
	}

	@Override
	public void changeCard(Integer personId, String cardSn) {
		allotcardDao.changeCard(personId, cardSn);
	}

	@Override
	public void recedeCard(Integer personId) {
		allotcardDao.recedeCard(personId);
	}

}
