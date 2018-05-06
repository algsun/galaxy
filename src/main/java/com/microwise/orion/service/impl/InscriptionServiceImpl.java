package com.microwise.orion.service.impl;

import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.orion.bean.Inscription;
import com.microwise.orion.dao.InscriptionDao;
import com.microwise.orion.service.InscriptionService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 铭文题跋 service
 * 
 * @author xubaoji
 * @date 2013-5-21
 *
 * @check 2013-06-04 zhangpeng svn:3590
 */
@Service
@Orion
@Transactional
public class InscriptionServiceImpl implements InscriptionService {

	/**铭文题跋 dao*/
	@Autowired
	private  InscriptionDao inscriptionDao ;
	
	@Override
	public void addInscription(Inscription inscription) {
        inscriptionDao.save(inscription);
	}
	
	@Override
	public void deleteInscription(Integer id) {
		inscriptionDao.deleteInscription(id);
	}
	
    @Override
    public void deleteInscriptionByRelicId(int relicId){
        inscriptionDao.deleteInscriptionByRelicId(relicId);
    }

    @Override
    public Inscription findById(Integer id) {
        return inscriptionDao.findById(id);
    }
}
