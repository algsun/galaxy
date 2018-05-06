package com.microwise.orion.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.RepairLog;
import com.microwise.orion.dao.RepairLogDao;
import com.microwise.orion.service.RepairLogService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 王耕
 * @date 15-10-9
 */
@Beans.Service
@Orion
@Transactional
public class RepairLogServiceImpl implements RepairLogService {

    @Autowired
    private RepairLogDao repairLogDao;

    @Override
    public List<RepairLog> findRepairLogs(int repairRecordId) {
        return repairLogDao.findRepairLogs(repairRecordId);
    }

    @Override
    public void saveRepairLog(RepairLog repairLog) {
        repairLogDao.saveRepairLog(repairLog);
    }
}
