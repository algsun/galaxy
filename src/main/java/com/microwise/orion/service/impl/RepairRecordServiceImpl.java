package com.microwise.orion.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.RepairRecord;
import com.microwise.orion.dao.RelicDao;
import com.microwise.orion.dao.RepairRecordDao;
import com.microwise.orion.service.RepairRecordService;
import com.microwise.orion.sys.Orion;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 修复记录实现
 *
 * @author liuzhu
 * 15-9-14.
 */

@Beans.Service
@Orion
@Transactional
public class RepairRecordServiceImpl implements RepairRecordService {

    @Autowired
    private RepairRecordDao repairRecordDao;

    @Autowired
    private RelicDao relicDao;

    public int getIdentifier() {
        int identifier;
        RepairRecord record = repairRecordDao.lastRepairRecord();
        if (record != null) {
            if (DateTime.now().getYear() == record.getIdentifier() / 10000) {
                identifier = record.getIdentifier() + 1;
                return identifier;
            }
        }
        identifier = Integer.parseInt(DateTime.now().getYear() + String.format("%04d", 1));
        return identifier;
    }

    @Override
    public void saveOrUpdate(RepairRecord repairRecord) {
        repairRecordDao.saveOrUpdate(repairRecord);
    }

    @Override
    public List<RepairRecord> findBySiteId(String siteId) {
        List<RepairRecord> repairRecords = repairRecordDao.findBySiteId(siteId);
        for (RepairRecord repairRecord : repairRecords) {
            repairRecord.setRelic(relicDao.findRelic(repairRecord.getRelic().getId(), siteId));
        }
        return repairRecords;
    }

    @Override
    public void update(RepairRecord repairRecord) {
        repairRecordDao.update(repairRecord);
    }

    @Override
    public RepairRecord findById(int id) {
        return repairRecordDao.findById(id);
    }

    @Override
    public RepairRecord findRepairRecordById(int id) {
        return repairRecordDao.findRepairRecordById(id);
    }

    public void delete(RepairRecord repairRecord) {
        repairRecordDao.delete(repairRecord);
    }

    @Override
    public List<RepairRecord> findRepairRecords(int index, int pageCount, String siteId) {
        List<RepairRecord> repairRecords =repairRecordDao.findRepairRecords(index,pageCount,siteId);

        for (RepairRecord repairRecord : repairRecords) {
            repairRecord.setRelic(relicDao.findRelic(repairRecord.getRelic().getId(), siteId));
        }

        return repairRecords;
    }
}
