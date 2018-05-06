package com.microwise.biela.service.impl;

import com.microwise.biela.bean.vo.ComplianceRateVO;
import com.microwise.biela.bean.vo.LocationSensorVO;
import com.microwise.biela.bean.vo.MixtureVO;
import com.microwise.biela.dao.EvaluateDao;
import com.microwise.biela.service.EvaluateService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Evaluate 实现
 */

@Beans.Service
@Transactional
@Blueplanet
public class EvaluateServiceImpl implements EvaluateService {

    @Autowired
    private EvaluateDao evaluateDao;

    @Override
    public List<ComplianceRateVO> findComplianceRates(String siteId, Date date) {
        return evaluateDao.findComplianceRates(siteId, date);
    }

    @Override
    public List<LocationSensorVO> findAvgPeak(String siteId, Date date, int sensorId) {
        return evaluateDao.findAvgPeak(siteId, date, sensorId);
    }

    @Override
    public List<MixtureVO> findMixture(String siteId, Date date) {
        return evaluateDao.findMixture(siteId, date);
    }

    @Override
    public List<ComplianceRateVO> findComRate(int siteLogicId, Date date) {
        return evaluateDao.findComRate(siteLogicId, date);
    }
}
