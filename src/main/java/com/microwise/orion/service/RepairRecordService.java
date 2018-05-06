package com.microwise.orion.service;

import com.microwise.orion.bean.RepairRecord;

import java.util.List;

/**
 * 修复记录实现
 *
 * @author liuzhu
 * 15-9-14.
 */

public interface RepairRecordService {

    public int getIdentifier();

    public void saveOrUpdate(RepairRecord repairRecord);

    public List<RepairRecord> findBySiteId(String siteId);

    public void update(RepairRecord repairRecord);

    public RepairRecord findById(int id);

    /**
     * 查询任务记录（携带因由）
     * @param id
     * @return
     */
    public RepairRecord findRepairRecordById(int id);

    void delete(RepairRecord repairRecord);

    public List<RepairRecord> findRepairRecords(int index,int pageCount,String siteId);

}
