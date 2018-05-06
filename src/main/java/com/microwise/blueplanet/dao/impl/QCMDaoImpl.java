package com.microwise.blueplanet.dao.impl;

import com.microwise.blueplanet.bean.po.EvaluateCriterionPO;
import com.microwise.blueplanet.bean.po.ReplaceSensorPO;
import com.microwise.blueplanet.bean.vo.QCMVO;
import com.microwise.blueplanet.dao.QCMDao;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetBaseDao;
import com.microwise.common.sys.annotation.Beans;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * QCM dao实现
 *
 * @author liuzhu
 * @date 2015-5-7.
 */
@Beans.Dao
@Blueplanet
public class QCMDaoImpl extends BlueplanetBaseDao implements QCMDao {

    private String findReplaceSensorDate;

    @Override
    public List<QCMVO> findQCM(String locationId, Date startDate, Date endDate, int sensorId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("sensorId", sensorId);
        return getSqlSession().selectList("blueplanet.QCMDao.findQCM", paramMap);
    }

    public List<Date> findQCMReplaceSensorDate(String locationId) {
        return getSqlSession().selectList("blueplanet.QCMDao.findReplaceSensorDate", locationId);
    }

    @Override
    public List<Date> findQCMMinMaxDate(String locationId) {
        return getSqlSession().selectList("blueplanet.QCMDao.findQCMMinMaxDate", locationId);
    }

    @Override
    public float findSensorNum(String locationId, int sensorId, Date startDate, Date endDate) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("locationId", locationId);
        paramMap.put("sensorId", sensorId);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        return getSqlSession().selectOne("blueplanet.QCMDao.findSensorNum", paramMap);
    }

    @Override
    public List<EvaluateCriterionPO> findEvaluateCriterions() {
        return getSqlSession().selectList("blueplanet.QCMDao.findEvaluateCriterions");
    }
}
