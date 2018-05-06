package com.microwise.blackhole.service.impl;

import com.microwise.blackhole.bean.SysLog;
import com.microwise.blackhole.dao.LogDao;
import com.microwise.blackhole.service.LogService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.common.sys.annotation.Beans.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 日志Service实现
 *
 * @author zhangpeng
 * @date 2012-11-15
 * @check 2012-11-27 xubaoji svn:381
 */
@Service
@Transactional
@Blackhole
public class LogServiceImpl implements LogService {

    /**
     * 日志Dao
     */
    @Autowired
    private LogDao logDao;

    @Override
    public void saveLog(SysLog sysLog) {
        logDao.saveLog(sysLog);
    }

    @Override
    public List<SysLog> findLogList(SysLog sysLog, int start, int max) {
        return logDao.findLogList(sysLog, start, max);
    }

    @Override
    public int findLogListCount(SysLog sysLog) {
        return logDao.findLogListCount(sysLog);
    }

    @Override
    public List<SysLog> findLogList(SysLog sysLog, Date startTime,
                                    Date endTime, int start, int max) {
        return logDao.findLogList(sysLog, startTime, endTime, start, max);
    }

    @Override
    public int findLogListCount(SysLog sysLog, Date startTime, Date endTime) {
        return logDao.findLogListCount(sysLog, startTime, endTime);
    }

}
