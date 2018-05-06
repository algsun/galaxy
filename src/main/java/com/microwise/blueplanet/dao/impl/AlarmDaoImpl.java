package com.microwise.blueplanet.dao.impl;

import com.microwise.blueplanet.bean.vo.AlarmRecordVO;
import com.microwise.blueplanet.bean.vo.MeasureVO;
import com.microwise.blueplanet.dao.AlarmDao;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetBaseDao;
import com.microwise.common.sys.annotation.Beans;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuzhu
 * @date 14-3-11
 */
@Beans.Dao
@Blueplanet
public class AlarmDaoImpl extends BlueplanetBaseDao implements AlarmDao {

    @Override
    public void addMeasure(MeasureVO measureVO) {
        getSqlSession().insert("blueplanet.AlarmDao.addMeasure", measureVO);
    }

    @Override
    public List<MeasureVO> findMeasureList(String siteId) {
        return getSqlSession().selectList("blueplanet.AlarmDao.findMeasureList", siteId);
    }

    @Override
    public MeasureVO findMeasureVOById(String id) {
        return getSqlSession().selectOne("blueplanet.AlarmDao.findMeasureById", id);
    }

    @Override
    public void updateMeasure(MeasureVO measureVO) {
        getSqlSession().update("blueplanet.AlarmDao.updateMeasure", measureVO);
    }

    @Override
    public void deleteMeasure(String id) {
        getSqlSession().delete("blueplanet.AlarmDao.deleteMeasure", id);
    }

//    @Override
//    public List<MeasureVO> findMeasureVOByZoneId(String zoneId) {
//        return getSqlSession().selectList("blueplanet.AlarmDao.findMeasureByZoneId", zoneId);
//    }

    @Override
    public List<AlarmRecordVO> findRecordVOByPages(Integer sensorId,
                                                   Date startTime, Date endTime, Integer index,
                                                   Integer size, String siteId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (sensorId != null && sensorId != 0) {
            paramMap.put("sensorId", sensorId);
        }
        if (startTime != null) {
            paramMap.put("startTime", startTime);
        } else {
            paramMap.put("startTime", new Date(0));
        }
        if (endTime != null) {
            paramMap.put("endTime", endTime);
        } else {
            paramMap.put("endTime", new Date());
        }

        paramMap.put("index", (index - 1) * size);
        paramMap.put("size", size);
        paramMap.put("siteId", siteId);
        return getSqlSession().selectList("blueplanet.AlarmDao.findRecordVOByPages", paramMap);
    }

    @Override
    public Integer findRecordVOCount(Integer sensorId, Date startTime, Date endTime, String siteId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (sensorId != null && sensorId != 0) {
            paramMap.put("sensorId", sensorId);
        }
        if (startTime != null) {
            paramMap.put("startTime", startTime);
        } else {
            paramMap.put("startTime", new Date(0));
        }
        if (endTime != null) {
            paramMap.put("endTime", endTime);
        } else {
            paramMap.put("endTime", new Date());
        }
        paramMap.put("siteId", siteId);
        return getSqlSession().selectOne("blueplanet.AlarmDao.findRecordVOCount", paramMap);
    }

    @Override
    public List<MeasureVO> findMeasureByRecordId(String recordId) {
        return getSqlSession().selectList("blueplanet.AlarmDao.findMeasureByRecordId",recordId);
    }

//    @Override
//    public boolean hasZoneMeasureId(String measureId) {
//        Integer count = getSqlSession().selectOne("blueplanet.AlarmDao.hasZoneMeasureId",measureId);
//        if(count.intValue()>0){
//            return true;
//        }else{
//            return false;
//        }
//    }

    @Override
    public List<AlarmRecordVO> findAlarmRecordList(String zoneId) {
        return getSqlSession().selectList("blueplanet.AlarmDao.findAlarmRecordList", zoneId);
    }

    @Override
    public AlarmRecordVO findRecentAlarmRecord(String locationId) {
        return getSqlSession().selectOne("blueplanet.AlarmDao.findRecentAlarmRecord", locationId);
    }
}
