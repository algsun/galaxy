package com.microwise.blueplanet.service.impl;

import com.microwise.blueplanet.bean.vo.AlarmRecordVO;
import com.microwise.blueplanet.bean.vo.MeasureVO;
import com.microwise.blueplanet.bean.vo.ZoneVO;
import com.microwise.blueplanet.dao.AlarmDao;
import com.microwise.blueplanet.dao.ThresholdDao;
import com.microwise.blueplanet.dao.ZoneDao;
import com.microwise.blueplanet.service.AlarmService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author liuzhu
 * @date 14-3-11
 */
@Beans.Service
@Transactional
@Blueplanet
public class AlarmServiceImpl implements AlarmService {

    /**
     * 监测预警dao
     */
    @Autowired
    private AlarmDao alarmDao;

    /**
     * 区域dao
     */
    @Autowired
    private ZoneDao zoneDao;

    /**
     * 阈值dao 接口
     */
    @Autowired
    private ThresholdDao thresholdDao;

    @Override
    public void addMeasure(MeasureVO measureVO) {
        alarmDao.addMeasure(measureVO);
    }

    @Override
    public List<MeasureVO> findMeasureList(String siteId) {
        return alarmDao.findMeasureList(siteId);
    }

    @Override
    public MeasureVO findMeasureVOById(String id) {
        return alarmDao.findMeasureVOById(id);
    }

    @Override
    public void updateMeasure(MeasureVO measureVO) {
        alarmDao.updateMeasure(measureVO);
    }

    @Override
    public void deleteMeasure(String id) {
        alarmDao.deleteMeasure(id);
    }

    @Override
    public List<ZoneVO> findZoneMeasure(String siteId, String parentZoneId) {
        List<ZoneVO> zoneVOList = zoneDao.findZoneList(siteId, parentZoneId);
        for (ZoneVO zoneVO : zoneVOList) {
//            zoneVO.setMeasureVOs(alarmDao.findMeasureVOByZoneId(zoneVO.getZoneId()));
            zoneVO.setThresholdVOs(thresholdDao.findZoneThresholds(zoneVO.getZoneId()));
        }
        return zoneVOList;
    }

    @Override
    public List<AlarmRecordVO> findRecordVOByPages(Integer sensorId,
                                                   Date startTime, Date endTime, Integer index,
                                                   Integer size,String siteId) {
        List<AlarmRecordVO> alarmRecordVOs = alarmDao.findRecordVOByPages(sensorId,startTime,endTime,index,size,siteId);
        for (AlarmRecordVO alarmRecordVO : alarmRecordVOs){
            alarmRecordVO.setMeasureVOs(alarmDao.findMeasureByRecordId(alarmRecordVO.getId()));
        }
        return alarmRecordVOs;
    }

    @Override
    public Integer findRecordVOCount(Integer sensorId,
                                     Date startTime, Date endTime,String siteId){
        return alarmDao.findRecordVOCount(sensorId,startTime,endTime,siteId);
    }

//    @Override
//    public boolean hasZoneMeasureId(String measureId) {
//        return alarmDao.hasZoneMeasureId(measureId);
//    }

    @Override
    public List<AlarmRecordVO> findAlarmRecordList(String zoneId) {
        return alarmDao.findAlarmRecordList(zoneId);
    }

    @Override
    public AlarmRecordVO findRecentAlarmRecord(String locationId) {
        return alarmDao.findRecentAlarmRecord(locationId);
    }
}
