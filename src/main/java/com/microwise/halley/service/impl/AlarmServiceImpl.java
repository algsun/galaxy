package com.microwise.halley.service.impl;

import com.microwise.blackhole.dao.UserDao;
import com.microwise.halley.bean.po.AlarmPO;
import com.microwise.halley.dao.AlarmDao;
import com.microwise.halley.service.AlarmService;
import com.microwise.halley.sys.Halley;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * AlarmService 实现
 *
 * @author li.jianfei
 * @date 2014-05-19
 */
@Service
@Transactional
@Halley
public class AlarmServiceImpl implements AlarmService {

    @Autowired
    private AlarmDao alarmDao;
    @Autowired
    private UserDao userDao;

    @Override
    public int findAlarmListCount(int exhibitionId, Date beginTime, Date endTime) {
        return alarmDao.findAlarmListCount(exhibitionId, beginTime, endTime);
    }

    @Override
    public List<AlarmPO> findAlarmListByPage(int exhibitionId, Date beginTime, Date endTime, int startPage, int pageSize) {
        return alarmDao.findAlarmListByPage(exhibitionId, beginTime, endTime, startPage, pageSize);
    }
}
