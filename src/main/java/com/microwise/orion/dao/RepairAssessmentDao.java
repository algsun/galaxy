package com.microwise.orion.dao;

import com.microwise.common.sys.hibernate.BaseDao;
import com.microwise.orion.bean.RepairAssessment;

/**
 * Created by Administrator on 15-9-23.
 */
public interface RepairAssessmentDao extends BaseDao<RepairAssessment>{

    public RepairAssessment findByRepairRecordId(int repairRecordId);

    public void delete(int repairRecordId);
}
