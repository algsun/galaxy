package com.microwise.blackhole.service.impl;

import com.microwise.blackhole.bean.Subsystem;
import com.microwise.blackhole.dao.SubsystemDao;
import com.microwise.blackhole.service.SubsystemService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.common.sys.annotation.Beans.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 业务系统Service实现
 *
 * @author zhangpeng
 * @date 2012-11-21
 * @check 2012-11-27 xubaoji svn:381
 */
@Service
@Transactional
@Blackhole
public class SubsystemServiceImpl implements SubsystemService {

    /**
     * 业务系统Dao
     */
    @Autowired
    private SubsystemDao subsystemDao;

    @Override
    public List<Subsystem> findAllSubsystems() {
        return subsystemDao.findAllSubsystems();
    }

    @Override
    public List<Subsystem> findAllSubsystemsByLanguage(String language) {
        return subsystemDao.findAllSubsystemsByLanguage(language);
    }

}
