package com.microwise.orion.service;

import com.microwise.orion.bean.RepairAssessment;

/**
 * 自评估
 *
 * @author liuzhu
 * 15-9-23.
 */
public interface RepairAssessmentService {

    public void save(RepairAssessment repairAssessment);

    public void delete(int repairRecordId);

    public RepairAssessment findByRepairRecordId(int repairRecordId);

}
