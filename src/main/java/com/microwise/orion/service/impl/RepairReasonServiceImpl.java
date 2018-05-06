package com.microwise.orion.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.RepairReason;
import com.microwise.orion.dao.RepairReasonDao;
import com.microwise.orion.service.RepairReasonService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 王耕
 * @date 15-9-9
 */
@Beans.Service
@Orion
@Transactional
public class RepairReasonServiceImpl implements RepairReasonService {

    @Autowired
    private RepairReasonDao repairReasonDao;

    @Override
    public void saveOrUpdate(RepairReason repairReason) {
        repairReasonDao.saveOrUpdateRepairReason(repairReason);
    }

    @Override
    public void deleteRepairReason(int id) {
        repairReasonDao.deleteRepairReason(id);
    }

    @Override
    public List<RepairReason> findAll(String siteId) {
        return repairReasonDao.findAll(siteId);
    }

    @Override
    public RepairReason findReasonById(int id) {
        return repairReasonDao.findReasonById(id);
    }

    @Override
    public RepairReason findReasonByReason(String reason,int id) {
        return repairReasonDao.findReasonByReason(reason,id);
    }
}
