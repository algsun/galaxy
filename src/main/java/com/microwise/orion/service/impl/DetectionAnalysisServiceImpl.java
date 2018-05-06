package com.microwise.orion.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.DetectionAnalysis;
import com.microwise.orion.dao.DetectionAnalysisDao;
import com.microwise.orion.service.DetectionAnalysisService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 王耕
 * @date 15-9-29
 */
@Beans.Service
@Orion
@Transactional
public class DetectionAnalysisServiceImpl implements DetectionAnalysisService {

    @Autowired
    private DetectionAnalysisDao detectionAnalysisDao;

    @Override
    public void saveOrUpdate(DetectionAnalysis detectionAnalysis) {
        detectionAnalysisDao.saveOrUpdate(detectionAnalysis);
    }

    @Override
    public List<DetectionAnalysis> findByRepairRecordId(int repairRecordId) {
        return detectionAnalysisDao.findByRepairRecordId(repairRecordId);
    }

    @Override
    public void deleteDetectionAnalysis(int analysisNum) {
        detectionAnalysisDao.deleteDetectionAnalysis(analysisNum);
    }
}
