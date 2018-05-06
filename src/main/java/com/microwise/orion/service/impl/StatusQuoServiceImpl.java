package com.microwise.orion.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.StatusQuo;
import com.microwise.orion.dao.StatusQuoDao;
import com.microwise.orion.service.StatusQuoService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 王耕
 * @date 15-9-28
 */
@Beans.Service
@Orion
@Transactional
public class StatusQuoServiceImpl implements StatusQuoService {

    @Autowired
    private StatusQuoDao statusQuoDao;


    @Override
    public StatusQuo findStatusQuos(int relicId) {
        return statusQuoDao.findLatestStatusQuoDao(relicId);
    }

    @Override
    public StatusQuo findById(int id) {
        return statusQuoDao.findById(id);
    }

    @Override
    public void saveOrUpdateStatusQuo(StatusQuo statusQuo) {
        statusQuoDao.saveOrUpdateStatusQuo(statusQuo);
    }

    @Override
    public void updateStatusQuo(StatusQuo statusQuo){
        statusQuoDao.updateStatusQuo(statusQuo);
    }

    @Override
    public void deleteStatusQuo(StatusQuo statusQuo) {
        statusQuoDao.delete(statusQuo);
    }

}
