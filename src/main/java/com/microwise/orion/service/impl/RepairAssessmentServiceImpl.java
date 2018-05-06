package com.microwise.orion.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.RepairAssessment;
import com.microwise.orion.dao.RepairAssessmentDao;
import com.microwise.orion.service.RepairAssessmentService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 自评估
 *
 * @author liuzhu
 * 15-9-23.
 */

@Beans.Service
@Orion
@Transactional
public class RepairAssessmentServiceImpl implements RepairAssessmentService {

    @Autowired
    private RepairAssessmentDao repairAssessmentDao;

    @Override
    public void save(RepairAssessment repairAssessment) {
        repairAssessmentDao.save(repairAssessment);
    }

    @Override
    public void delete(int repairRecordId) {
        repairAssessmentDao.delete(repairRecordId);
    }

    @Override
    public RepairAssessment findByRepairRecordId(int repairRecordId) {
        return repairAssessmentDao.findByRepairRecordId(repairRecordId);
    }
}
