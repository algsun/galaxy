package com.microwise.blackhole.service.impl;

import com.microwise.blackhole.bean.LogicGroupSubsystemDisable;
import com.microwise.blackhole.bean.Subsystem;
import com.microwise.blackhole.dao.LogicGroupSubsystemDisableDao;
import com.microwise.blackhole.service.LogicGroupSubsystemDisableService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.sys.freemarker.tools.LocaleBundleTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuzhu
 * @date 2014/3/24
 * @check 2014.03.27 xiedeng  #svn: 8232
 */

@Beans.Service
@Transactional
@Blackhole
public class SubsystemStateServiceImpl implements LogicGroupSubsystemDisableService {

    /**
     * 业务系统Dao
     */
    @Autowired
    private LogicGroupSubsystemDisableDao logicGroupSubsystemDisableDao;

    @Override
    public List<Subsystem> findSubsystem() {
        return logicGroupSubsystemDisableDao.findSubsystems();
    }

    @Override
    public void changeState(int systemId, int state) {
        logicGroupSubsystemDisableDao.changeState(systemId, state);
    }

    @Override
    public List<Subsystem> findSubsystemOpen(int logicGroupId) {
        List<Subsystem> subsystemList = findSubsystemByLogicGroupId(logicGroupId);
        List<Subsystem> subsystems = new ArrayList<Subsystem>();
        for (Subsystem subsystem : subsystemList) {
            if (subsystem.getState() == 0) {
                subsystems.add(subsystem);
            }
        }
        return subsystems;
    }

    @Override
    public List<Subsystem> findSubsystemByLogicGroupId(int logicGroupId) {
        List<Subsystem> subsystemList = logicGroupSubsystemDisableDao.findAllSubsystems(LocaleBundleTools.appLocale());
        return getSubsystemState(logicGroupId, subsystemList);
    }

    private List<Subsystem> getSubsystemState(int logicGroupId, List<Subsystem> subsystemList) {
        for (Subsystem subsystem : subsystemList) {
            LogicGroupSubsystemDisable logicGroupSubsystemStates = logicGroupSubsystemDisableDao.findSubsystemStateByLogicGroupId(logicGroupId, subsystem.getSubsystemId());
            if (logicGroupSubsystemStates != null) {
                subsystem.setState(1);
            } else {
                subsystem.setState(0);
            }
        }
        return subsystemList;
    }

    @Override
    public List<Subsystem> findAllSubsystemByLogicGroupId(int logicGroupId) {
        List<Subsystem> subsystemList = logicGroupSubsystemDisableDao.findSubsystems();
        return getSubsystemState(logicGroupId, subsystemList);
    }

    @Override
    public void able(int logicGroupId, int subsystemId) {
        logicGroupSubsystemDisableDao.able(logicGroupId, subsystemId);
    }

    @Override
    public void disable(String id, int logicGroupId, int subsystemId) {
        logicGroupSubsystemDisableDao.disable(id, logicGroupId, subsystemId);
    }

    @Override
    public List<Subsystem> findSubsystemByLogicGroupIdFormHome(int logicGroupId) {
        List<Subsystem> subsystemList = logicGroupSubsystemDisableDao.findAllSubsystemsFromHome();
        return getSubsystemState(logicGroupId, subsystemList);
    }

    public List<Subsystem> findSubsystemOpenFormHome(int logicGroupId) {
        List<Subsystem> subsystemList = findSubsystemByLogicGroupIdFormHome(logicGroupId);
        List<Subsystem> subsystems = new ArrayList<Subsystem>();
        for (Subsystem subsystem : subsystemList) {
            if (subsystem.getState() == 0) {
                subsystems.add(subsystem);
            }
        }
        return subsystems;
    }

}
