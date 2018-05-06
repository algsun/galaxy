package com.microwise.biela.dao.impl;

import com.microwise.biela.bean.vo.ComplianceRateVO;
import com.microwise.biela.bean.vo.LocationSensorVO;
import com.microwise.biela.bean.vo.MixtureVO;
import com.microwise.biela.dao.EvaluateDao;
import com.microwise.biela.sys.Biela;
import com.microwise.biela.sys.BielaBaseDao;
import com.microwise.common.sys.annotation.Beans;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 达标率 dao
 */

@Beans.Dao
@Biela
public class EvaluateDaoImpl extends BielaBaseDao implements EvaluateDao {
    @Override
    public List<ComplianceRateVO> findComplianceRates(String siteId, Date date) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId", siteId);
        paramMap.put("date", date);
        return getSqlSession().selectList("biela.Evaluate.findComplianceRate", paramMap);
    }

    @Override
    public List<LocationSensorVO> findAvgPeak(String siteId, Date date, int sensorId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId", siteId);
        paramMap.put("date", date);
        paramMap.put("sensorId", sensorId);
        return getSqlSession().selectList("biela.Evaluate.findAvgPeak", paramMap);
    }

    public List<MixtureVO> findMixture(String siteId, Date date) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteId", siteId);
        paramMap.put("date", date);
        return getSqlSession().selectList("biela.Evaluate.findMixture", paramMap);
    }

    @Override
    public List<ComplianceRateVO> findComRate(int siteLogicId, Date date) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("siteLogicId", siteLogicId);
        paramMap.put("date", date);
        return getSqlSession().selectList("biela.Evaluate.findComRate", paramMap);
    }
}
