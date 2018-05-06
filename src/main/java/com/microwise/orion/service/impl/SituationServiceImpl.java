package com.microwise.orion.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.Situation;
import com.microwise.orion.dao.SituationDao;
import com.microwise.orion.service.SituationService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 修复情况接口
 *
 * @author 王耕
 * @date 15-9-24
 */
@Beans.Service
@Orion
@Transactional
public class SituationServiceImpl implements SituationService{

    @Autowired
    private SituationDao situationDao;

    @Override
    public Situation findByRepairRecordId(int repairRecordId) {
        return situationDao.findByRepairRecordId(repairRecordId);
    }

    @Override
    public void saveOrUpdate(Situation situation) {
        situationDao.saveOrUpdate(situation);
    }
}
